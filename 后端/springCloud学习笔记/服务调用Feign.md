+ 先要将服务注册到注册中心

+ 引入依赖

  + ```xml
    <!--服务调用-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
    ```

+ 在服务调用端加上注解

  + ```java
    @EnableFeignClients
    ```

+ 创建调用接口

  + ```java
    @Component
    @FeignClient("service-vod")   //要调用的服务名
    public interface VodClient {
        @GetMapping("/vod/hello")
        String hello();
    }
    ```

+ 测试（记得两个服务有改动的话要重启）

  + ```java
    @Autowired
    private VodClient vodClient;
    
    @Test
    public void test(){
        System.out.println(vodClient.hello());
    }
    ```



解决服务调用 text/json 没有解析器的问题

```java
import feign.codec.Decoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FeignConfig {
    
    @Bean
    public Decoder feignDecoder(){
        MessageConverter converter = new MessageConverter();
        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(converter);
        return new SpringDecoder(objectFactory);
    }
    
    private class MessageConverter extends MappingJackson2HttpMessageConverter{
        public MessageConverter(){
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.TEXT_PLAIN);
            setSupportedMediaTypes(mediaTypes);
        }
    }
}
```

