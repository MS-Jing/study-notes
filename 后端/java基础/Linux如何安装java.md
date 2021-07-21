# 将JDK的压缩包解压

` tar -zvxf jdk-8u181-linux-x64.tar.gz `

# 配置环境变量

> 在/etc/profile文件末尾追加下面几行 

```bash
export JAVA_HOME=/opt/jdk/jdk1.8.0_181
export PATH=$JAVA_HOME/bin:$PATH
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
```

> 环境变量生效

` source /etc/profile `

> 查看java 版本

` java -version `

