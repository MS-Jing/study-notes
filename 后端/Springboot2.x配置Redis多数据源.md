# 介绍

我的springboot版本是2.4.4，其他版本不知道会不会有问题！！！

如果我们的项目想同时操作两个redis怎么做？默认提供的Redis链接工厂不能解决，我们需要自己创建Redis链接工厂，现在2.x之后，Redis的链接工厂使用的是Lettuce。然后我们有了链接工厂还不好用啊，我们想像之前一样有个RedisTemplate，再把这个RedisTemplate封装成我们的工具类RedisUtil。这样我们需要那个Redis的工具类注入那个。

本文是我自己一点一点Debug默认的LettuceConnectionFactory然后总结出来的单机模式的，不知道集群会不会有问题。我尽量模拟出了默认的做法。如果你的需求不满足可以自行查看源码修改。

# 上代码

```java
@Configuration
public class RedisConfig {

    //这里要么不要注入到ioc容器，要么注入成原型模式，
    // 不然你会发现他们使用的是同一个链接工厂
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        //json序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //String序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        //hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        //value序列化方式采用jackjson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //hash的value序列化方式也采用Jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    private LettuceConnectionFactory createLettuceConnectionFactory(ObjectProvider<LettuceClientConfigurationBuilderCustomizer> builderCustomizers,
                                                                    RedisProperties redisProperties, ClientResources clientResources) {
        // redis 单机配置
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redisProperties.getHost());
        configuration.setPort(redisProperties.getPort());
        configuration.setPassword(redisProperties.getPassword());
        configuration.setDatabase(redisProperties.getDatabase());
        // Lettuce的链接配置
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = LettuceClientConfiguration
            .builder()
            .shutdownTimeout(redisProperties.getLettuce().getShutdownTimeout())
            .clientOptions(ClientOptions.builder().timeoutOptions(TimeoutOptions.enabled()).build())
            .clientResources(clientResources);
        builderCustomizers.orderedStream().forEach((customizer) -> {
            customizer.customize(builder);
        });
        // 生成链接工厂，记得最后一定要调afterPropertiesSet()
        // 因为默认的LettuceConnectionFactory是被注入到ioc容器中的实现了InitializingBean接口
        // 最后在afterPropertiesSet()方法里实例化了connectionProvider如果这个不实例化，启动没问题链接redis就报错
        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration, builder.build());
        factory.afterPropertiesSet();
        return factory;
    }

    @Component
    @ConfigurationProperties(prefix = "spring.redis")
    @Data
    class RedisDataSource {
        private List<RedisProperties> redisPropertiesList;
    }

    @Autowired
    private RedisDataSource redisDataSource;

    /**
     * 如果你要新加机器，按照这个样子再复制粘贴一下，记得在配置文件里配置
     * 在需要使用的地方这样进行注入：
     *
     * @Resource(name = "RedisUtil0")
     * private RedisUtil redisUtil0;
     */
    @Bean("RedisUtil0")
    public RedisUtil getRedisUtil_0(ObjectProvider<LettuceClientConfigurationBuilderCustomizer> builderCustomizers, ClientResources clientResources) {
        LettuceConnectionFactory factory = createLettuceConnectionFactory(builderCustomizers, redisDataSource.getRedisPropertiesList().get(0), clientResources);
        RedisTemplate<String, Object> template = redisTemplate(factory);
        return new RedisUtil(template);
    }

    @Bean("RedisUtil1")
    public RedisUtil getRedisUtil_1(ObjectProvider<LettuceClientConfigurationBuilderCustomizer> builderCustomizers, ClientResources clientResources) {
        LettuceConnectionFactory factory = createLettuceConnectionFactory(builderCustomizers, redisDataSource.getRedisPropertiesList().get(1), clientResources);
        RedisTemplate<String, Object> template = redisTemplate(factory);
        return new RedisUtil(template);
    }

}
```

```java
public class RedisUtil {

    private RedisTemplate<String, Object> redisTemplate;

    public RedisUtil(RedisTemplate<String, Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }
    
    ......
}
```

```yaml
spring:
  redis:
    redis-properties-list:
      - host: localhost
        port: 6379
        database: 1
      - host: localhost
        port: 6379
        database: 2
```

测试：

```java
@Resource(name = "RedisUtil0")
private RedisUtil redisUtil0;
@Resource(name = "RedisUtil1")
private RedisUtil redisUtil1;

@GetMapping("/test")
public void test() {
    redisUtil0.incrOrDecr("aaaa", 1);
    redisUtil1.incrOrDecr("aaaa", 1);
}
```

如果你需要添加机器，在配置文件里先配置，然后再RedisConfig里向容器中注入一个新的RedisUtil

# 最后

能力有限，如果有错误的地方，请大佬指正。QQ：1126184155