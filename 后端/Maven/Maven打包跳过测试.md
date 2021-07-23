我们在进行打包maven项目的时候，默认会执行maven的测试插件` maven-surefire-plugin`,有时候还会因为环境问题导致测试失败，那么如何打包跳过测试呢？



## 方式一：

 properties中配置`<maven.test.skip>true</maven.test.skip> `

```xml
<properties>
    <maven.test.skip>true</maven.test.skip>
</properties>
```

## 方式二：

配置插件参数

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>2.12.4</version>
            <!-- 插件参数配置，对插件中所有的目标起效 -->
            <configuration>
                <skip>true</skip>
            </configuration>
        </plugin>
    </plugins>
</build>
```

