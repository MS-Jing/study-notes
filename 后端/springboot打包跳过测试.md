# springboot打包项目跳过测试

springboot打包的时候，有时候会出现测试出错等问题。我们需要跳过测试。我们知道maven打包等操作都是通过一个个的插件来完成的。我们可以跳过打包的插件来解决这个问题

方法一：

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>

        <!--跳过测试-->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <configuration>
                <skip>true</skip>
            </configuration>
        </plugin>
    </plugins>
</build>
```

方法二：

```xml
<properties>
    <skipTests>true</skipTests>
</properties>
```



更倾向于方式二，简单明了，其实原理都一样