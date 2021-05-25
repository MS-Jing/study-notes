​		在我学习阿里云视频点播服务的时候，阿里云文档有一个`aliyun-java-vod-upload.jar`包需要下载引入到项目中，虽然我们可以创建一个lib目录的方式引入或者在项目模块中引入。但是每次都需要操作，直接安装到我们的maven本地仓库然后引入依赖不香哦？

# 步骤：

+ 先下载需要的jar包，我直接使用`aliyun-java-vod-upload.jar`，版本是1.4.14

+ 在目标jar文件的目录下打开cmd

+ 然后执行：`mvn install:install-file -DgroupId=com.aliyun -DartifactId=aliyun-java-vod-upload -Dversion=1.4.14 -Dpackaging=jar -Dfile=aliyun-java-vod-upload-1.4.14.jar`

+ 然后在你项目里引入

  + ```xml
    <dependency>
        <groupId>com.aliyun</groupId>
        <artifactId>aliyun-java-vod-upload</artifactId>
        <version>1.4.14</version>
    </dependency>
    ```

+ 搞定！

## 注意点：

+ 首先，你的maven环境要配置好！你执行`mvn -version`如果出现了maven版本说明环境ok
+ 目标文件最好用tab键补全，不然输错了又开始怀疑自己，上面的参数按照自己的需求来改，比如：`-DgroupId`是组的意思



emmm!能力有限，做的不好或者不对的地方，大佬请指正！  QQ：1126184155