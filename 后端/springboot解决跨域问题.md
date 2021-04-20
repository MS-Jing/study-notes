# 跨域问题

​		由一个地址向另一个地址发送网络请求，只要 `网络协议`,`ip地址`,`端口号`，三个有一个不同，那就会有跨域的问题。

```
 has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
```

解决：

```
@CrossOrigin
```

使用注解，该注解可以加载Controller类上，也可以直接加载接口方法上，直接解决跨域的问题

---

也可以使用配置类的方式：

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowCredentials(true)
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .maxAge(3600);
    }
}
```

