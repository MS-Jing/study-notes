# 打包无需依赖第三方包

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-jar-plugin</artifactId>
    <version>2.4</version>
    <configuration>
        <archive>
            <!--生成的jar中是否需要包含pom.xml文件-->
            <addMavenDescriptor>false</addMavenDescriptor>
            <manifest>
                <!--是否需要把第三方jar放在manifest的Classpath路径下-->
                <addClasspath>true</addClasspath>
                <!--Classpath路径前缀-->
                <classpathPrefix>lib/</classpathPrefix>
                <!--主启动类-->
                <mainClass>com.lj.mybatis.Main</mainClass>
            </manifest>
        </archive>
    </configuration>
</plugin>
```

# 打包需要依赖第三方包

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-shade-plugin</artifactId>
    <version>2.3</version>
    <executions>
        <execution>
            <phase>package</phase>
            <goals>
                <goal>shade</goal>
            </goals>
            <configuration>
                <transformers>
                    <transformer
                                 implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                        <mainClass>com.lj.mybatis.Main</mainClass>
                    </transformer>
                </transformers>
            </configuration>
        </execution>
    </executions>
</plugin>
```

