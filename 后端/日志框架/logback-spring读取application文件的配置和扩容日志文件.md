# logback-spring读取springboot配置文件

我们知道springboot是通过application.yaml来配置一些环境参数的。

logback-spring中的`<springProfile name="dev"></springProfile>`

标签可以根据不同运行环境来配置不同的logger。但是，如果我不是想获取环境，我想获取application.yaml中的某个配置参数来配置我们的logback呢？

## 解决

我们可以使用`<springProperty name="" source=""/>`标签来获取application.yaml中的配置文件，name为该参数取个名字之后可以用`${}`来进行引用，source为你要获取的参数。

例如`<springProperty name="port" source="server.port"/>`,这样我们就获取到了当前环境下的端口号参数啦。

# logback扩容日志文件

我们配置logback-spring.xml文件能看出来，如果日志很多的话，整个篇幅会很大。而且可能不同业务的logger都配置在了一起很是繁琐。那么如何把不同业务的日志配置分开来呢？还有通过上面我们可以动态的去读取当前spring环境中的配置。我们能不能在不同的环境配置下启用不同的日志配置文件呢？

## 解决

logback提供了`<include resource=""/>`标签，我们只需要把我们要扩容的日志文件路径写入到resource属性中既可。

例如：

+ 在resources/目录下创建一个`logback-localhost.xml`日志文件：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<included>
	<!-- ......本地的日志配置...... -->
</included>
```

注意该文件会被引入到logback-spring.xml日志配置文件中所以根节点不再是`<configuration/>`节点而是`<included>`

+ 然后再`logback-spring.xml`配置文件中引入：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- ......基础的或公用日志配置...... -->
    
    <!-- 引入logback-localhost.xml日志配置文件 -->
    <include resource="logback-localhost.xml"/>
</configuration>
```

这样就可以完成对不同业务的日志配置放到不同的日志文件了。



# 扩展

在我本地项目config/application.yaml文件我配置了`log-handler-file: logback-localhost.xml`,

在服务器项目目录下(jar包同目录)config/application.yaml文件我配置了`log-handler-file: logback-remote.xml`

我想要在本地(不管什么环境dev,test都无所谓)启用logback-localhost.xml的日志配置。同理在服务器用logback-remote.xml的日志配置。

+ 我们需要先获取当前springboot的`log-handler-file`配置参数

```xml
<springProperty name="LOG_HANDLER" source="log-handler-file"/>
```

+ 然后我们将这个日志配置文件在logback-spring.xml文件中引入既可

```xml
<!--日志处理的配置-->
<include resource="${LOG_HANDLER}"/>
```

**注意：resources目录下要有这两个配置文件哦**

这样我们就完成了根据spring不同的参数配置来指定启用哪一个配置文件啦！







