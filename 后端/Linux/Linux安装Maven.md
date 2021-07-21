# 下载
```bash
[root@ady01 jdk]# mkdir /opt/maven
[root@ady01 jdk]# cd /opt/maven/
[root@ady01 maven]# wget http://mirrors.tuna.tsinghua.edu.cn/apache/maven/maven-3/3.6.2/binaries/apache-maven-3.6.2-bin.tar.gz
```

# 解压

` tar -zvxf apache-maven-3.6.2-bin.tar.gz `

# 配置maven环境变量

>  在/etc/profile文件末尾追加下面几行 

```bash
export M2_HOME=/opt/maven/maven
export PATH=$M2_HOME/bin:$PATH
```

> 环境变量生效

` source /etc/profile `

> 查看maven环境

` mvn -v `

# 修改配置文件配置阿里镜像

```xml
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



