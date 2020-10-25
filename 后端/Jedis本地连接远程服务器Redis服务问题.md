# Jedis本地连接远程服务器Redis服务问题

​	刚学习了Redis，尝试用Jedis去连接我服务器的Redis服务，不要问我为什么不连本地的，懒得下载。

导入相关依赖：

```xml
<!-- https://mvnrepository.com/artifact/redis.clients/jedis -->
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>3.2.0</version>
</dependency>
```

创建一个测试的Demo：

```java
public class TestPing {
    public static void main(String[] args) {
        //创建一个Jedis对象
        Jedis jedis = new Jedis("你的服务器外网ip",6379);
        System.out.println(jedis.ping());
    }
}
```

当我点击运行的时候出现了这个问题：`Exception in thread "main" redis.clients.jedis.exceptions.JedisConnectionException: Failed connecting to host `

![](https://s1.ax1x.com/2020/06/28/NRagDf.png)



![](https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=967544472,4270483130&fm=26&gp=0.jpg)

既然说无法连接，但是我在我服务器Redis服务是打开了的并且是可以ping通的,经过我一番周折，下面记录我的解决步骤：

## 解决步骤：

先关闭的Redis服务和连接

+ 先修改redis.config文件，用vim命令打开redis.config文件,按i进入编辑模式
  + 先在bind 127.0.0.1前加个#注释掉，这样就不只是本地可以连接了--->`#bind 127.0.0.1`
  + 然后找到protected-mode 他后面是设置成'no',修改他的保护模式为on---->`protected-mode no`
  + 不要忘记保存然后退出哦，先按`Esc`退出编辑模式，再`:wq`保存并退出
  
+ **别急，你以为这就可以了？**我用的是阿里的服务器，我需要开启6379（Redis默认端口）端口的安全组设置，这步直接跳过，不演示。

+ **你以为这就可以了？**，测试了一下还是报连接不上的错误，原来是要==**开放redis端口的防火墙**==

  + `firewall-cmd --zone=public --add-port=6379/tcp --permanent`

  + `firewall-cmd --reload`

  + **如果你想关闭这个端口的防火墙**

    + `firewall-cmd --zone=public --remove-port=6379/tcp --permanent`

    + `firewall-cmd --reload`

+ 现在一切准备好了，用redis-service命令去打开你的redis服务
+ 服务打开后，运行上面的java代码。发现打印一个`PONG`说明连接成功了！！！！！！



## 最后

​	笔者能力有限，还请读者谅解哦！有错误或者其他的更好的方法可以联系笔者哦！

QQ：1126184155