# hystrix服务降级

+ 添加依赖

  + ```xml
    <!--hystrix依赖，主要是用  @HystrixCommand -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
    </dependency>
    ```

+ 在服务调用端开启熔断器

  + ```yaml
    feign:
      hystrix:
        enabled: true   #开启熔断机制
    ```

+ 创建调用接口错误时执行的实现类

  + ```java
    @Component
    public class VodClientImpl implements VodClient {
        @Override
        public ResultVO<Boolean> delete(String id) {
            return new ResultVO<>(504,"服务暂时不可用",false);
        }
    }
    ```
  
+ 在调用接口上加入注解

  + `@FeignClient(value = "service-vod",fallback = VodClientImpl.class) `

