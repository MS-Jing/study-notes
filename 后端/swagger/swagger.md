# swagger

为了解决前后端的联调

号称世界上最强大的api框架

==api文档和api定义同时更新==

用于测试数据接口像postman一样

## springboot集成swagger

**相关依赖**

```xml
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger2 -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
```

**编写一个hellocontroller接口**

**配置swagger**

Swagger有一个Docket的bean实例

+ 新建SwaggerConfig类

  + ```java
    package com.jing.config;
    
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.core.env.Environment;
    import org.springframework.core.env.Profiles;
    import springfox.documentation.builders.PathSelectors;
    import springfox.documentation.builders.RequestHandlerSelectors;
    import springfox.documentation.service.ApiInfo;
    import springfox.documentation.service.Contact;
    import springfox.documentation.spi.DocumentationType;
    import springfox.documentation.spring.web.plugins.Docket;
    import springfox.documentation.swagger2.annotations.EnableSwagger2;
    
    import java.util.ArrayList;
    
    @Configuration
    @EnableSwagger2 //开启swagger2
    public class SwaggerConfig {
    
        //配置swagger的Docket的bean实例
        @Bean
        public Docket mydocket(Environment environment){
    
            //设置需要显示的环境
            Profiles profiles = Profiles.of("test","dev");
            //判断是否处在设置的环境中  在为true
            boolean flag = environment.acceptsProfiles(profiles);
    
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .enable(flag)  //配置是否启用swagger 默认为true
                    .groupName("罗静")    //设置分组
                    .select()
                        //RequestHandlerSelectors 配置要扫描接口的方式
                            //basePackage基于包去扫描
                        .apis(RequestHandlerSelectors.basePackage("com.jing.controller"))
                        //PathSelectors 配置如何过滤路径
    //                    .paths(PathSelectors.ant("/jing/comtroller/**"))
                    .build();
        }
    
        @Bean
        public Docket docket1(){
            return new Docket(DocumentationType.SWAGGER_2)
                    .groupName("我是测试用的另一个分组");
        }
    
        //配置swagger信息 ==>ApiInfo
        public ApiInfo apiInfo(){
    
            //作者信息
            Contact contact = new Contact("罗静", "https://www.baidu.com/", "1126184155@qq.com");
    
            return new ApiInfo("my swagger API",
                    "这是描述",
                    "1.0",
                    "https://www.baidu.com/",
                    contact,
                    "Apache 2.0",
                    "http://www.apache.org/licenses/LICENSE-2.0",
                    new ArrayList()
            );
        }
    
    }
    ```

+ @ApiModel("用户")给实体类加注释

+ @ApiModelProperty("用户名")给实体类属性加注释

+ 通过`http://localhost:8080/swagger-ui.html`进入界面

## 总结

+ 我们可以通过Swagger给一些比较难理解的属性或者接口， 增加注释信息
+ 接口文档实时更新
+ 可以在线测试

