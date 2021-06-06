# 安装运行

[nacos-server-1.1.4](https://github.com/alibaba/nacos/releases/download/1.1.4/nacos-server-1.1.4.zip)

springboot用的2.2.1.RELEASE版本

+ 执行bin目录下的startup.cmd（根据你的系统定）
+ 默认访问地址  http://localhost:8848/nacos/
+ 如果启动报错试试 startup -m standalone  表示单机模式（非集群模式）
+ 默认用户名密码    nacos\nacos

# 服务注册

+ 引入依赖

  + ```xml
    <!--服务注册-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    </dependency>
    ```

+ 配置application.yaml

  + ```yaml
    spring:
      cloud:
        nacos:
          discovery:
            server-addr: 127.0.0.1:8848   #nacos服务地址
    ```

+ 在启动类上加上注解

  + ```java
    @EnableDiscoveryClient
    ```

+ 重启服务在nacos控制台查看