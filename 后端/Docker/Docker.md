# Docker概述

## Docker为什么会出现？

​		我们现在发布项目，在开发时一套环境，在发布时一套环境，这样在运维起来很麻烦，部署环境，如果是一个大型的项目环境部署很麻烦。

​		传统的：开发一个jar包，然后部署环境，发布上去

​		现在:开发打包部署上线，一套流程搞定！

​		Docker的思想来自于集装箱

​		==隔离==是Docker的核心思想！打包装箱，每个箱子都是互相隔离的



> Docker是基于go语言开发的

## Docker能干嘛？

> ==**容器化技术并不是模拟的一个完整的操作系统**==

比较Docker和虚拟机技术的不同：

+ 传统的虚拟机，虚拟出一条硬件，运行一套完整的操作系统，然后再这个系统上安装和运行软件
+ 容器内的应用直接运行在宿主机上，容器没有自己的内核，也没有虚拟我们的硬盘，所以很轻便
+ 每个容器间互相隔离，每个容器内都有一个属于自己的文件技术，互不影响

**应用更快速的交付和部署**

**更便捷的升级和扩缩容**

**更简单的系统运维**

**更高效的计算资源利用**

# Docker安装

## Docker的基本组成

+ 镜像（image）：docker镜像就好比是一个模板 ,可以通过这个模板来创建容器服务
+ 容器（container）：
  + Docker利用容器技术,独立运行一个或者一个组应用 ,通过镜像来创建的。
+ 仓库（repository）：
  + 存放镜像的地方

## 安装

> 环境准备

+ Linux基础
+ CentOS 7
+ Xshell连接远程服务器



> 环境

```bash
# 系统内核在3.10以上
[root@iZbp138sn4z9yrtqweb5brZ /]# uname -r
3.10.0-957.21.3.el7.x86_64
```

```bash
[root@iZbp138sn4z9yrtqweb5brZ /]# cat /etc/os-release 
NAME="CentOS Linux"
VERSION="7 (Core)"   #CentOS 7版本
ID="centos"
ID_LIKE="rhel fedora"
VERSION_ID="7"
PRETTY_NAME="CentOS Linux 7 (Core)"
ANSI_COLOR="0;31"
CPE_NAME="cpe:/o:centos:centos:7"
HOME_URL="https://www.centos.org/"
BUG_REPORT_URL="https://bugs.centos.org/"

CENTOS_MANTISBT_PROJECT="CentOS-7"
CENTOS_MANTISBT_PROJECT_VERSION="7"
REDHAT_SUPPORT_PRODUCT="centos"
REDHAT_SUPPORT_PRODUCT_VERSION="7"

```

> 安装

 ```bash
 # 卸载就版本   
    yum remove docker \
                      docker-client \
                      docker-client-latest \
                      docker-common \
                      docker-latest \
                      docker-latest-logrotate \
                      docker-logrotate \
                      docker-engine
# 需要的安装包
yum install -y yum-utils
# 3.设置镜像的仓库
yum-config-manager \
    --add-repo \
    http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo # 阿里的
    
    https://download.docker.com/linux/centos/docker-ce.repo #默认是从国外下载 
    
#更新软件包索引
[root@iZbp138sn4z9yrtqweb5brZ /]# yum makecache fast
    
# 4.安装Docker  docker-ce是社区版的  ee是企业版的
yum install docker-ce docker-ce-cli containerd.io

# 5.启动Docker
systemctl start docker
# 6.启动后查看docker版本查看是否安装成功
docker version
# 7.helloword
docker run hello-world

# 8.查看下载的这个hello-word镜像
[root@iZbp138sn4z9yrtqweb5brZ /]# clear
[root@iZbp138sn4z9yrtqweb5brZ /]# docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
hello-world         latest              bf756fb1ae65        5 months ago  

# 想卸载Docker怎么办？
#卸载依赖
yum remove docker-ce docker-ce-cli containerd.io
#删除资源
rm -rf /var/lib/docker #/var/lib/docker是docker的默认工作路径
 ```

## 阿里云镜像加速

+ 登录阿里云--->容器镜像服务

+ 镜像加速器---> CentOS

+ 配置使用

  + ```shell
    sudo mkdir -p /etc/docker
    
    sudo tee /etc/docker/daemon.json <<-'EOF'
    {
      "registry-mirrors": ["https://jvg97r6e.mirror.aliyuncs.com"]
    }
    EOF
    
    sudo systemctl daemon-reload
    
    sudo systemctl restart docker
    ```



# Docker常用命令

## 帮助命令

```shell
docker version
docker info
docker 命令 --help  #帮助命令
```

[参考文档](https://docs.docker.com/reference/)



## 镜像命令

> **docker images  查看所有本地的主机上的镜像**

 ```shell
 [root@iZbp138sn4z9yrtqweb5brZ home]# docker images
 REPOSITORY    TAG      IMAGE ID      CREATED       SIZE
 hello-world   latest   bf756fb1ae65  6 months ago  13.3kB

# 解释
REPOSITORY	镜像的仓库源
TAG			仓库的标签
IMAGE ID	镜像的id
CREATED 	镜像的创建时间
SIZE		镜像的大小
# 常用可选项
Options:
  -a, --all             Show all images (default hides intermediate images)
  -q, --quiet           Only show numeric IDs
 ```

> **docker search 搜索镜像**

```shell
[root@iZbp138sn4z9yrtqweb5brZ home]# docker search mysql
NAME     DESCRIPTION         STARS      OFFICIAL      AUTOMATED
mysql    MySQL is a widely…   9743                    [OK]                

#可选项
Options:
  -f, --filter filter   Filter output based on conditions provided
      --format string   Pretty-print search using a Go template
      --limit int       Max number of search results (default 25)
      --no-trunc        Don't truncate output
```

> **docker pull 下载镜像**

```shell
# docker pull 镜像名[:tag]
[root@iZbp138sn4z9yrtqweb5brZ home]# docker pull mysql
Using default tag: latest  #默认版本
latest: Pulling from library/mysql
8559a31e96f4: Pull complete 	#分层下载
d51ce1c2e575: Pull complete 
c2344adc4858: Pull complete 
fcf3ceff18fc: Pull complete 
16da0c38dc5b: Pull complete 
b905d1797e97: Pull complete 
4b50d1c6b05c: Pull complete 
571e8a282156: Pull complete 
e7cc823c6090: Pull complete 
61161ba7d2fc: Pull complete 
74f29f825aaf: Pull complete 
d29992fd199f: Pull complete 
Digest: sha256:fe0a5b418ecf9b450d0e59062312b488d4d4ea98fc81427e3704f85154ee859c #签名
Status: Downloaded newer image for mysql:latest
docker.io/library/mysql:latest	#真实地址

# 指定版本下载
[root@iZbp138sn4z9yrtqweb5brZ home]# docker pull mysql:5.7
5.7: Pulling from library/mysql
8559a31e96f4: Already exists 
d51ce1c2e575: Already exists 
c2344adc4858: Already exists 
fcf3ceff18fc: Already exists 
16da0c38dc5b: Already exists 
b905d1797e97: Already exists 
4b50d1c6b05c: Already exists 
0a52a5c57cd9: Pull complete 
3b816a39d367: Pull complete 
13ee22d6b3bb: Pull complete 
e517c3d2ba35: Pull complete 
Digest: sha256:ea560da3b6f2f3ad79fd76652cb9031407c5112246a6fb5724ea895e95d74032
Status: Downloaded newer image for mysql:5.7
docker.io/library/mysql:5.7

```



> **docker rmi 删除镜像**

```shell
docker rmi -f 镜像id  #删除指定镜像
docker rmi -f 镜像id 镜像id 镜像id #删除多个镜像
docker rmi -f $(docker images -q) #删除所有镜像
```



## 容器命令

**==有了镜像才可以创建容器==**

> **下载centos镜像测试学习**

```shell
docker pull centos
```

> **新建容器并启动**

```shell
docker run [可选参数] image

#常用参数说明
--name="Name"		#容器名字，用来区分容器
-d					#后台方式运行
-it					#使用交互方式运行,进入容器查看内容
-p					#指定容器的端口 
	-p ip:主机端口：容器端口
	-p 主机端口：容器端口（常用）
	-p 容器端口
	容器端口
-P					#随机指定端口
```

```shell

#启动centos容器并进入容器
[root@iZbp138sn4z9yrtqweb5brZ home]# docker run -it centos /bin/bash
[root@892bec3c0d72 /]# 
[root@892bec3c0d72 /]# exit  #退出容器
exit
```

> **所有正在运行的容器**

```shell
docker ps 
	-a #正在运行的容器和历史运行过的容器
	-n=? #最近创建的?个容器
	-q  #只显示容器的编号
	
[root@iZbp138sn4z9yrtqweb5brZ home]# docker ps  #正在运行的容器
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
[root@iZbp138sn4z9yrtqweb5brZ home]# docker ps -a #正在运行的容器和历史运行过的容器
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS                       PORTS               NAMES
892bec3c0d72        centos              "/bin/bash"         6 minutes ago       Exited (127) 2 minutes ago                       mystifying_banzai
cb33f8cd960f        bf756fb1ae65        "/hello"            42 hours ago        Exited (0) 42 hours ago                          wonderful_raman
[root@iZbp138sn4z9yrtqweb5brZ /]# docker ps -n=1 #最近创建的一个容器
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS                       PORTS               NAMES
892bec3c0d72        centos              "/bin/bash"         12 minutes ago      Exited (127) 8 minutes ago                       mystifying_banzai
[root@iZbp138sn4z9yrtqweb5brZ /]# docker ps -n=2
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS                       PORTS               NAMES
892bec3c0d72        centos              "/bin/bash"         12 minutes ago      Exited (127) 8 minutes ago                       mystifying_banzai
cb33f8cd960f        bf756fb1ae65        "/hello"            42 hours ago        Exited (0) 42 hours ago                          wonderful_raman
```



> **退出容器**

```shell
exit # 直接容器停止并退出

Ctrl + P + Q  #容器不停止退出
```



> **删除容器**

```shell
docker rm 容器id #删除指定的容器
docker rm -f 容器id #强制删除指定的容器
docker rm -f $(docker ps -aq) #删除所有的容器
docker ps -a -q|xargs docker rm #删除所有容器
```



> **启动和停止容器的操作**

```shell
docker start 容器id		#启动容器
docker restart 容器id		#重启容器
docker stop 容器id		#停止当前正在运行的容器
docker kill 容器id		#强制停止当前容器
```



## 其他常用命令

> **后台启动容器**

```shell
#后台启动容器
[root@iZbp138sn4z9yrtqweb5brZ ~]# docker run -d centos
#会出现的问题，centos已经停止了
# docker容器使用后台运行，就必须要有一个前台进程，docker发现没有应用就会自动停止
```

> **查看日志**

```shell
# docker logs
[root@iZbp138sn4z9yrtqweb5brZ ~]# docker logs -tf --tail 10 2ad301e9d68f #查看该容器的最近十条日志

```

> **查看容器中的进程信息**

```shell
#top命令
docker top 容器id
```

> **查看镜像的元数据**

```shell
docker inspect 容器id
```

> **进入正在运行的容器**

```shell
#方式一
#进入容器开启一个新的终端
docker exec -it 容器id /bin/bash #重启后进入容器

#方式二
#进入容器正在执行的终端
docker attach 容器id
```

> **从容器内拷贝文件到主机上**

```shell
docker cp 容器id:容器内路径 主机路径
```

## 可视化

> **portainer**

docker图形化界面管理工具！

```shell
docker run -d -p 8088:9000 \
--restart=always -v /var/run/docker.sock:/var/run/docker.sock --privileged=true portainer/portainer
```

# Docker镜像讲解

## 镜像是什么

镜像是一种轻量级、可执行的独立软件包,用来打包软件运行环境和基于运行环境开发的软件.它包含运行某个软件所需的所有内容,包括代码、运行时、库、环境变量和配置文件。

所有的应用,直接打包docker镜像,就可以直接跑起来!



## commit镜像

如何提交一个自己的镜像？

```shell
docker commit -m="提交的描述" -a="作者" 容器id 目标镜像名：[TAG]
```

提交自己修改过的Tomcat镜像

```shell
[root@iZbp138sn4z9yrtqweb5brZ ~]# docker commit -a='lj' -m='add webapps app' f7c7d1c6a59c tomcat02:1.0
sha256:e78bea2c6e287a459597e3c821f924f3112d5ac88a61d7f389386dd4e3e1f055
```



# 容器数据卷

docker是将应用和环境打包成一个镜像，运行起来成一个容器。如果数据都保存在容器中，如果容器删除了，数据也就丢失了。所以需要容器之间有一个数据共享的技术。将docker容器中产生的数据，同步到本地。

卷技术：目录挂载，将容器内的目录挂载到Linux上面

## 使用数据卷

> 方式一：使用命令挂载

```shell
docker run -it -v 主机目录:容器内目录
docker run -d --name="fs_tomcat" -v /home/webapps:/usr/local/tomcat/webapps -p 8081:8080 tomcat:9.0
```

## 实战：安装MySQL

```shell
#拉取镜像
docker pull mysql:5.7

#运行容器，做数据挂载
-e 配置数据库的root用户密码
[root@iZbp138sn4z9yrtqweb5brZ conf]# docker run -d -p 3306:3306 -v /home/mysql/conf:/etc/mysql/conf.d -v /home/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 --name mysql01 mysql:5.7
```



## 具名和匿名挂载

```shell
#具名挂载
#通过 -v 卷名：容器内路径
docker run -d -p --name nginx02 -v juming-nginx(卷名，不是路径):/etc/nginx nginx
# 查看该卷
docker vloume inspect juming-nginx

#匿名挂载   -v只写了容器内的路径，没有写容器外的路径
-v 容器内路径
-P 随机指定端口
docker run -d -P --name nginx01 -v /etc/nginx nginx

# 查看所有volume(卷)的情况
docker volume ls
```



## 数据卷容器

```shell
# bbb和ccc两个容器通过--volumes-from来共享aaa的挂载文件，达到三个容器的/home/aaa文件同步

[root@iZbp138sn4z9yrtqweb5brZ ~]# docker run -d --name aaa -v /home/aaa tomcat02:1.0
[root@iZbp138sn4z9yrtqweb5brZ ~]# docker run -d --name bbb --volumes-from aaa tomcat02:1.0
[root@iZbp138sn4z9yrtqweb5brZ ~]# docker run -d --name ccc --volumes-from aaa tomcat02:1.0
```





# DockerFile

Dockerfile 就是用来构建docker镜像的构建文件！  就是一个命令脚本

构建步骤：

+ 编写一个dockerfile文件
+ docker build 构建成为一个镜像
+ docker run 运行镜像
+ docker push 发布镜像（DockerHub，阿里云镜像仓库）

## 构建过程

**基础知识：**

+ 每一个保留关键字都必须是大写字母
+ 执行从上到下执行
+ #为注释
+ 每一个指令都会创建提交一个新的镜像层，并提交

DockerFile：构建文件，定义了一切的步骤，源代码

DockerImages：通过DockerFile构建生成的镜像

Docker容器：容器就是镜像运行起来提供的服务



## DockerFile的指令

```shell
FROM			#基础镜像
MAINTAINER		#镜像的维护者 姓名+邮箱
RUN				#镜像构建时运行的命令
ADD				#添加内容
WORKDIR			#镜像的工作目录
VOLUME			#挂载的目录
EXPOSE			#暴露端口配置
CMD				#指定容器启动时运行的命令，只有最后一个会生效，可以被替代
ENTRYPOINT		#指定容器启动时运行的命令，可以追加命令
ONBUILD			#当构建一个被集成DockerFile时运行，触发指令
COPY			#类型ADD，将文件拷贝到镜像中
ENV				#构建时设置环境变量
```

## 实战：搭建自己的Centos

> 编写dockerfile文件

```shell
FROM centos
MAINTAINER Lj<1126184155@qq.com>

ENV MYPATH /usr/local
WORKDIR $MYPATH

RUN yum -y install vim
RUN yum -y install net-tools

EXPOSE 8080

CMD echo $MYPATH
CMD echo "---end----"
CMD /bin/bash
```

> 构建镜像

```shell
docker build -f dockerfile-centos -t mycentos:0.1 .
-f	#通过dockerfile构建
-t  #文件名和版本号
```

> 查看镜像构建历史

```shell
docker history mycentos:0.1
```



## 实战：JDK+Tomcat

```shell
FROM centos
MAINTAINER Lj<1126184155@qq.com>

COPY readme.txt /usr/local/reame.txt

ADD jdk-8u211-linux-x64.tar.gz /usr/local/
ADD apache-tomcat-8.5.57.tar.gz /usr/local/

RUN yum -y install vim

ENV MYPATH /usr/local
WORKDIR $MYPATH

ENV JAVA_HOME /usr/local/jdk1.8.0_211
ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
ENV CATALINA_HOME /usr/local/apache-tomcat-8.5.57
ENV CATALINA_BASH /usr/local/apache-tomcat-8.5.57
ENV PATH $PATH:$JAVA_HOME/bin:$CATALINA_HOME/lib:$CATALINA_HOME/bin

EXPOSE 8080

CMD /usr/local/apache-tomcat-8.5.57/bin/startup.sh && tail -F /usr/local/apache-tomcat-8.5.57/bin/logs/catalina.out
```

```shell
docker build -f DockerFile -t mytomcat:1.0 .
```



## 发布自己的镜像

> 阿里云镜像服务上

1. 登录阿里云
2. 找到容器镜像服务
3. 创建命名空间
4. 创建容器镜像



# Docker网络

## 理解Docker0

1. 我们每启动一个docker容器, docker就会给docker容器分配一个ip ,我们只要安装了docker ,就会有一个网卡docker0桥接模式,使用的技术是evth-pair技术!
2. evth-pair就是一对的虚拟设备接口，他们都是成对出现的，一段连着协议，一段彼此相连。正因为有这个特性，evth-pair充当一个桥梁，连接各种虚拟网络设备的
3. docker0：默认网络，容器名不能直接访问

## 容器互联 --link

容器之间可以相互通过ip去ping通，但是，容器重启的话，docker会重新分配ip,那如何通过名字来访问容器呢？

```shell
docker run -d -P --name tomcat02 --link tomcat01 tomcat
```

这样Tomcat02和Tomcat01这两个容器就连通了（本质其实就是host映射）



## 自定义网络

```shell
docker network ls #查看所有docker网卡

#网络模式
+ bridge: 桥接，在docker容器搭桥（默认）
+ none: 不配置网络
+ host: 和宿主机共享网络
+ container: 容器内网络连通

```

```shell
#自定义网络
docker network create -d bridge --subnet 192.168.0.0/16 --gateway 192.168.0.1 mynet

-d: 网络模式
--subnet: 网卡拥有的网段
--gateway: 从哪个网段出去
mynet为该网络的名称

#查看网卡信息
docker network inspect mynet

#启动一个tomcat容器，使用自定义网络
docker run -d -p 8080:8080 --name tomcat-net-01 --net mynet tomcat:8.5

#自定义的网络容器之间可以相互通过容器名ping通
```

## 网络连通







# Docker Compose

高效管理容器。定义运行多个容器！

## 安装

**下载**

+ ```bash
  sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
  
  # 国内镜像
  curl -L https://get.daocloud.io/docker/compose/releases/download/1.25.5/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose
  ```

**给该文件授权可执行**

+ `sudo chmod +x /usr/local/bin/docker-compose`

## 快速开始

```bash
[root@localhost home]# mkdir composetest
[root@localhost home]# cd composetest
```

**创建app.py**

```python
import time

import redis
from flask import Flask

app = Flask(__name__)
cache = redis.Redis(host='redis', port=6379)

def get_hit_count():
    retries = 5
    while True:
        try:
            return cache.incr('hits')
        except redis.exceptions.ConnectionError as exc:
            if retries == 0:
                raise exc
            retries -= 1
            time.sleep(0.5)

@app.route('/')
def hello():
    count = get_hit_count()
    return 'Hello World! I have been seen {} times.\n'.format(count)
```

**创建 requirements.txt** 

```txt
flask
redis
```

**创建一个Dockerfile**

```dockerfile
# syntax=docker/dockerfile:1
FROM python:3.7-alpine
WORKDIR /code
ENV FLASK_APP=app.py
ENV FLASK_RUN_HOST=0.0.0.0
RUN apk add --no-cache gcc musl-dev linux-headers
COPY requirements.txt requirements.txt
RUN pip install -r requirements.txt
EXPOSE 5000
COPY . .
CMD ["flask", "run"]
```

**在Compose file( docker-compose.yml )定义一个services**

```yaml
version: "3.9"
services:
  web:
    build: .
    ports:
      - "5000:5000"
  redis:
    image: "redis:alpine"
```

**执行**

+ `docker-compose up` 如果没有相关镜像和容器则先build在启动容器
+ `docker-compose up --build`  从新生成镜像和容器再启动
+ `docker-compose up -d`  后台启动

+ `docker-compose down`  停止相关容器和删除相关容器







