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

# 目录

[toc]



# 创建远程代码仓库和示例项目

​	不管什么远程仓库都可以，先能用就行，后面会说Gitee和GitLab相关的操作

+ 创建一个Springboot项目，勾选web

+ 将该项目创建git本地仓库，再创建远程仓库推送上去

![](C:\Users\罗敬\Desktop\study-notes\后端\Jenkins\img\1.jpg)

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

![](C:\Users\罗敬\Desktop\study-notes\后端\Jenkins\img\2.jpg)

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