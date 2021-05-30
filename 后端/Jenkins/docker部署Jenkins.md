# Docker+Jenkins，持续集成，持续部署

## 前言：

​		该文章是本人在学习中的记录总结！！本文亲测有效！

​		我会从一个干净的Linux环境，安装docker、部署jenkins、上传项目、自动拉取代码部署到目标服务器 等。边操作边记录，后续如果有新的知识点也会更新记录下来。

​		本人经验有限，做的不对的地方还请大佬指正     **QQ: 1126184155**    对本篇文章有疑问也可以问我

## 目标：

​	我们后端写完代码测试无误后：

+ 通过git将代码推送（git push）到远程代码仓库。
+ 然后远程代码仓库通知我们搭建的Jenkins服务，去远程代码仓库进行拉取源代码
+ 然后jenkins进行打包成jar包
+ jenkins推送相关文件（jar,Dockerfile,docker-compose...）到我们要部署项目的机器。再自动执行相关的shell脚本启动项目。达到一个自动持续集成部署的效果

## 前置技能

+ Linux、docker、docker-composer(用docker run 也行，这个也简单基本操作会用就行)
+ java、Maven，git、SpringBoot

至少有一台能连外网的服务器！！！

# 目录

[toc]



# 创建远程代码仓库和示例项目

​	不管什么远程仓库都可以，先能用就行，后面会说Gitee和GitLab相关的操作

+ 创建一个Springboot项目，勾选web

+ 将该项目创建git本地仓库，再创建远程仓库推送上去

![](.\img\1.jpg)

+ 创建TestController

```java
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/hello")
    public String sayHelloJenkins(){
        return "Hello Jenkins!";
    }
}
```

+ 本地测试无误

![](.\img\2.jpg)

# 安装Docker

+ 需要安装的包`yum install -y yum-utils`

+ 设置阿里镜像仓库

  + ```bash
    yum-config-manager \
        --add-repo \
        http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
    ```

+ 更新软件包索引`yum makecache fast`

+ 安装Docker  docker-ce是社区版的  ee是企业版的

  + `yum install docker-ce docker-ce-cli containerd.io`

+ 启动Docker`systemctl start docker`

+ 启动后查看docker版本查看是否安装成功`docker version`

+ 阿里云镜像加速

  + `sudo mkdir -p /etc/docker`

  + ```bash
    sudo tee /etc/docker/daemon.json <<-'EOF'
    {
      "registry-mirrors": ["https://jvg97r6e.mirror.aliyuncs.com"]
    }
    EOF
    ```

  + `sudo systemctl daemon-reload`

  + `sudo systemctl restart docker`

# 安装Docker Compose

+ 下载

  + ```bash
    # 国内镜像
    curl -L https://get.daocloud.io/docker/compose/releases/download/1.25.5/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose
    ```

+ 给该文件授权`sudo chmod +x /usr/local/bin/docker-compose`

# 安装Maven

​	因为Jenkins容器没有Maven环境，如在容器内部安装Maven，由于构建的项目要下载相关的依赖这样造成一部分资源浪费。所以我这里采用的将Maven和容器通过挂载达到共享本地Maven仓库

+ 去官网下载Maven,我使用的版本是3.5.0 
+ 上传至Docker服务所在的服务器
+ 我这里放到的 /usr/local/目录下了

![](.\img\3.png)

+ 解压后进入`apache-maven-3.5.0/`

  + 创建repository目录`mkdir repository`

  + 进入conf目录 vim settings.xml   配置本地仓库路径(**==换成自己的路径和版本==**)和阿里镜像源

  + ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    
    <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
      <!--这里写自己的路径-->
      <localRepository>/usr/local/apache-maven-3.5.0/repository</localRepository>
    
      <pluginGroups>
      </pluginGroups>
      
      <proxies>
      </proxies>
    
      <servers>
      </servers>
    
      <mirrors>
    		<mirror>
      		<id>aliyunmaven</id>
      		<mirrorOf>*</mirrorOf>
      		<name>阿里云公共仓库</name>
      		<url>https://maven.aliyun.com/repository/public</url>
    		</mirror>
      </mirrors>
    
      <profiles>  
      </profiles>
    
    </settings>
    
    ```

+ 回到`/usr/local/`给apache-maven-3.5.0文件**递归**增加其他用户写权限

  + `chmod -R o+w apache-maven-3.5.0`
  + ![](.\img\7.png)

+ 查看`apache-maven-3.5.0/bin/`目录下的`mvn`是否有执行权限(绿色的)，没有就添加

![](.\img\4.png)

+ 添加Maven环境变量

  + `vim /etc/profile`

  + ```bash
    # 将如下配置到/etc/profile，注意是你的Maven目录的位置
    export MAVEN_HOME=/usr/local/apache-maven-3.5.0
    export PATH=$PATH:$MAVEN_HOME/bin
    ```

  + 配置生效: `source /etc/profile`

+ 配置成功后关闭终端，从新连接
+ 在任意目录输入`mvn -version` 没有提示mvn 命令找不到说明配置完成
  
  + 如果提示找不到java 命令，只是说明你没有安装java环境。根据你自己的需要是否安装，这里可以不安（jenkins容器里有openjdk环境）



# 启动Jenkins容器

+ 选择一个目录（我选择的是 /home目录）
+ 在该目录下创建目录`mkdir jenkins`
+ 进入目录，创建data目录     用于挂载Jenkins的数据文件

![](.\img\5.png)

+ 为其他用户添加写的权限`chmod o+w data`

![](.\img\6.png)

+ 在当前目录下创建docker-compose.yaml文件   `vim docker-compose.yaml `

  + ```yaml
    version: "3.8"
    services:
      jenkins.service:
        image: jenkinsci/blueocean
        ports:
          - "8080:8080"
          - "50000:50000"
        environment:
          MAVEN_HOME: /usr/local/apache-maven-3.5.0
          PATH: /opt/java/openjdk/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/apache-maven-3.5.0/bin
        volumes:
          - /usr/local/apache-maven-3.5.0:/usr/local/apache-maven-3.5.0
          - /home/jenkins/data:/var/jenkins_home
    ```

+ docker-compose启动Jenkins  `docker-compose up`，然后等待启动（如果上面照着做没问题的话这里应该是可以启动的）

![](.\img\8.png)



## 基础配置

+ 进入界面   端口是你映射的端口

![](.\img\9.png)

+ 安装推荐插件

![](.\img\10.png)

+ 安装过程要等会，emmmm可以去喝杯水上个厕所。如果失败重试一下

![](.\img\11.png)

+ 直接使用admin用户

![](.\img\12.png)

+ 之后保存并完成，然后重启（如果你觉得重启慢的话，去终端另开一个端口，用docker restart 容器id  重启容器）。重启好了页面要刷新一下，不然一直等待页面

![](.\img\13.png)

+ 使用admin用户登录

![](.\img\14.png)

### 全局安全配置

这里可以根据自己的需求做配置

![](.\img\15.png)

![](.\img\16.png)

### 全局工具配置

> ​	通过 docker exec -it 容器id /bin/bash                进入容器（以下参数全是容器中的参数）
>
> + 获取Maven的settings文件路径
> ![](.\img\18.png)
> + 获取环境变量JAVA_HOME
>
> ![](.\img\19.png)
>
> + 获取环境变量MAVEN_HOME
>
> ![](.\img\20.png)

![](.\img\17.png)

![](.\img\21.png)

> 配置JAVA_HOME时把自动安装取消就可以了

![](.\img\22.png)

> Maven同上     git直接用容器默认的

![](.\img\23.png)

### 插件管理

![](.\img\24.png)

+ 安装Publish Over SSH插件 选中然后下载（我这里截图没来得及选）

![](.\img\25.png)

+ 等待下载完成重启一下容器就完成了（重启后记得刷新页面），之后要用的时候再配置

```bash
# 查看密码
[root@lj jenkins]# cat data/secrets/initialAdminPassword 
a86*********************26
```

# 第一个任务

![](.\img\26.png)

![](.\img\27.png)

## 源码管理

![](.\img\28.png)

![](.\img\29.png)

## 构建

![](.\img\30.png)

![](.\img\31.png)

## 测试构建

![](.\img\32.png)

项目在第一次打包构建时有点慢，因为要下载相关Maven依赖，别急，去喝口水走走！！

![](.\img\33.png)

搞定了，Jenkins已经帮我们从远程仓库自动拉取代码然后打包好了，回到工程去看一下

![](.\img\34.png)

然后点击工作区，这个目录熟不熟悉？？哈哈哈。进去target目录查看打包好的jar包吧

![](.\img\35.png)



# 使用Gitee来触发项目构建

>  **上面我们完成了Jenkins的基本构建。但是，我们在实际业务中不是去手动点击立即构建让Jenkins去构建。这tm的算什么自动构建啊！我们程序员写完代码后本地测试没问题了就会通过git的`git push`推送到远程代码仓库，我们要做到远程代码仓库接收到了新的推送然后去触发Jenkins的构建功能达到自动构建的效果！！！别急慢慢来会很快的！**
>
> [Gitee官方提供的帮助文档](https://gitee.com/help/articles/4193#article-header0)

## 安装插件

> 和刚才安装 Publish Over SSH插件插件一样  

+ 点击最上角 Dashboard 进到首页。然后   系统管理 -> 插件管理 -> 可选插件   搜索Gitee

![](.\img\36.png)

安装完成了记得重启

## 插件配置

系统管理 -> 系统配置 -> Gitee 配置

> 先去Gitee生成API 令牌   https://gitee.com/profile/personal_access_tokens
>
> ![](.\img\37.png)
>
> ![](.\img\38.png)
>
>
> ![](.\img\39.png)



![](.\img\40.png)

![](.\img\41.png)

点击高级根据你的需求选择 再点击测试连接

![](.\img\42.png)

**插件配置完成！！**

## 任务配置

> 进入我们jenkins_test任务，左边导航栏有个配置点击进入

### Gitee链接

选择我们刚才配置的链接

![](.\img\43.png)

###  源码管理配置

在源码管理点击高级

![](.\img\44.png)

###  触发器配置

![](.\img\45.png)

![](.\img\46.png)

**==保存！！！！！！！==**

> **去你的Gitee代码仓库配置WebHook**
>
> 管理  ->  WebHooks -> 添加webHook
>
> ![](.\img\47.png)

## 测试

> 去我们之前的示例项目里随便做点修改然后git push到远程仓库试试

![](.\img\48.png)

![](.\img\49.png)

**测试成功！！！**



# 持续部署

> ​		**在前面我们已经完成了有远程代码仓库推送代码触发项目构建的功能。但是，每次构建完了是不是还是要我们去下载然后部署到我们的目标服务器。这样未必过于麻烦，而且有些时候还会出现不及时等情况，或者部署后又上线新需求又要从新部署！！！我们能不能让Jenkins自动帮我们连接我们要部署的目标服务器帮我们自动化部署呢？？？  嘿嘿嘿！懒使人进步。come on！**
>
> ​		还记得我们前面安装了 Publish Over SSH插件吗？我们来思考一下，我们到底想让Jenkins帮我们做什么？emmm! 首先，帮我们把构建打包好的jar包发送到目标服务器上，然后连接我们的目标服务器去执行启动项目的命令（java -jar xxx,jar > /dev/null &）！
>
> 准备：
>
> + 在你的搭建的Jenkins服务的**==宿主机==**上生成ssh秘钥`ssh-keygen`（生成过就别生成了或者覆盖）
>
> ![](.\img\50.png)
>
> + 将你的公钥发送到你们**要部署的目标服务器**  `ssh-copy-id 目标服务器ip`

**==-------------------这里有点绕注意要填些什么东西-------------------==**

**==-------------------这里有点绕注意要填些什么东西-------------------==**

**==-------------------这里有点绕注意要填些什么东西-------------------==**

## 插件配置

进入   系统管理 -> 系统配置 -> Publish over SSH

![](.\img\51.png)

SSH Servers 点击新增    你也可以点击高级用目标服务器的账号密码登录它的ssh

![](.\img\52.png)

**==点击保存==**



## 任务配置

> 进入我们jenkins_test任务，左边导航栏有个配置点击进入

### 构建后操作

![](.\img\53.png)

![](.\img\54.png)

==**保存**==



## 测试

> 去我们之前的示例项目里随便做点修改然后git push到远程仓库

......参照上面的测试

**查看控制台**

![](.\img\55.png)

**查看目标服务器**

![](.\img\56.png)

> 你可以在**任务配置**里面添加相关的脚本，比如判断某个该服务是否启动启动就关闭启动新的服务，完成自动化部署



# 用docker-compose完成容器化自动部署

+ 在实例项目中添加Dockerfile和docker-compose.yaml文件

```dockerfile
FROM java:8
MAINTAINER Lj<1126184155@qq.com>

COPY target/jenkins_test-0.0.1-SNAPSHOT.jar /usr/local/jenkins_test-0.0.1-SNAPSHOT.jar

ENV MYPATH /usr/local
WORKDIR $MYPATH

EXPOSE 8080

CMD ["java","-jar","jenkins_test-0.0.1-SNAPSHOT.jar"]
```

```yaml
version: "3.8"
services:
  web:
    build: .
    ports:
      - "8081:8080"
```

![](.\img\57.png)



## 修改构建后配置

![](.\img\58.png)



## 测试

> ​		修改实例代码，然后推送到远程仓库，查看Jenkins控制台的构建，第一次因为要下载相关的镜像所以会比较慢。耐心等待。然后访问8081端口的/test/hello接口
>
> ![](.\img\59.png)
>
> 可以多次修改代码推送然后查看效果！
>
> 
>
> **查看docker容器**
>
> ![](.\img\60.png)













