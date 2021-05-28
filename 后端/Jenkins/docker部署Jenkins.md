# Docker+Jenkins，持续集成，持续部署

## 前言：

​	该文章是本人在学习部署中的记录总结，有些文章不是版本不一致就是把人绕迷糊！！本文亲测有效！

我会从一个干净的Linux环境，安装docker、部署jenkins、上传项目、自动拉取代码部署到目标服务器 进行记录。

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

+ 启动本地测试无误

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







