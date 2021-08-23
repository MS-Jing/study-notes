# slf4j源码阅读

 slf4j 最关键的2个接口，分别是Logger和ILoggerFactory。最关键的入口类，是LoggerFactory 

 所有的具体实现框架，一定会实现Logger接口和ILoggerFactory接口。前者实际记录日志，后者用来提供Logger 。 而LoggerFactory类，则是前面说过的入口类。 

LoggerFactory类如何提供Logger:

```java
public static Logger getLogger(String name) {  
    ILoggerFactory iLoggerFactory = getILoggerFactory();  
    return iLoggerFactory.getLogger(name);  
} 
```

这里通过调用getILoggerFactory()方法来获取一个ILoggerFactory的具体实例。在通过这个具体的日志工厂来获取对应的Logger

```java
/**
     * 返回使用中的ILoggerFactory实例
     * ILoggerFactory实例 在编译时与此类绑定
     *
     * @return 使用中的ILoggerFactory实例
     */
public static ILoggerFactory getILoggerFactory() {
    //获取环境中对应的Slf4j服务提供者（实现了SLF4JServiceProvider的实例）。
    // 然后通过服务提供者获取一个日志工厂
    return getProvider().getLoggerFactory();
}
```

这里可以看到getILoggerFactory()方法调用了getProvider()来获取一个slf4j服务提供者。再由服务提供者来提供具体的日志工厂实例

```java
/**
     * 返回使用中的 SLF4JServiceProvider
     *
     * @return 返回日志的提供者
     * @since 1.8.0
     */
static SLF4JServiceProvider getProvider() {
    //如果没有进行初始化，进行双重检查锁判断处理
    if (INITIALIZATION_STATE == UNINITIALIZED) {
        synchronized (LoggerFactory.class) {
            if (INITIALIZATION_STATE == UNINITIALIZED) {
                //初始化之前 先将初始化状态设置为正在初始化
                INITIALIZATION_STATE = ONGOING_INITIALIZATION;
                //执行初始化
                performInitialization();
            }
        }
    }
    switch (INITIALIZATION_STATE) {
        case SUCCESSFUL_INITIALIZATION:
            //初始化成功直接返回对应的 PROVIDER（提供者）
            return PROVIDER;
        case NOP_FALLBACK_INITIALIZATION:
            //返回一个应急的提供者
            return NOP_FALLBACK_FACTORY;
        case FAILED_INITIALIZATION:
            //初始化失败则抛出异常
            throw new IllegalStateException(UNSUCCESSFUL_INIT_MSG);
        case ONGOING_INITIALIZATION:
            // 支持重操作初始化   参考 http://jira.qos.ch/browse/SLF4J-97
            return SUBST_PROVIDER;
        default:
            //其他状态就抛出异常
            throw new IllegalStateException("Unreachable code");
    }
}
```

这里进行了双重检查锁，并且INITIALIZATION_STATE使用了volatile来修饰，然后判断初始化的状态，如果初始化成功直接返回相应的服务提供者。具体初始化在performInitialization()方法中

```java
private final static void performInitialization() {
    //进行绑定
    bind();
    // 判断初始化状态为成功进行检验
    if (INITIALIZATION_STATE == SUCCESSFUL_INITIALIZATION) {
        versionSanityCheck();
    }
}
```

这里先进行绑定。绑定什么？应该是绑定具体的日志实现（例如logback），然后进行一个版本检查，进入bind()方法看看如何进行绑定的

```java
//给LoggerFactory绑定服务提供者
private final static void bind() {
    try {
        //查找所有的服务提供者
        List<SLF4JServiceProvider> providersList = findServiceProviders();
        //报告是否有多个 SLF4JServiceProvider 的实例 无法抉择
        reportMultipleBindingAmbiguity(providersList);
        //判断服务提供者是否一个都没有
        if (providersList != null && !providersList.isEmpty()) {
            PROVIDER = providersList.get(0);
            // SLF4JServiceProvider.initialize() is intended to be called here and nowhere else.
            //服务提供者只能在这里进行初始化
            PROVIDER.initialize();
            //设置初始化状态为成功
            INITIALIZATION_STATE = SUCCESSFUL_INITIALIZATION;
            //打印实际绑定的是那个服务提供者
            reportActualBinding(providersList);
        } else { //如果服务提供者一个都没有
            //如果未扫描到对应的实现类，初始化状态变为无操作的应急NOPServiceProvider
            INITIALIZATION_STATE = NOP_FALLBACK_INITIALIZATION;
            //没有找到SLF4J提供程序
            Util.report("No SLF4J providers were found.");
            //默认为 no-operation (NOP) logger 实现
            Util.report("Defaulting to no-operation (NOP) logger implementation");
            Util.report("See " + NO_PROVIDERS_URL + " for further details.");

            //打印忽略掉的静态Logger绑定
            Set<URL> staticLoggerBinderPathSet = findPossibleStaticLoggerBinderPathSet();
            reportIgnoredStaticLoggerBinders(staticLoggerBinderPathSet);
        }
        postBindCleanUp();
    } catch (Exception e) {
        failedBinding(e);
        throw new IllegalStateException("Unexpected initialization failure", e);
    }
}
```

这里先查找所有的服务提供者。如果有多个会报一个警告，如果一个都没有会用应急的NOPServiceProvider来作为服务提供者，这个服务提供者提供的Logger什么都没做。如果有则会默认取第一个作为服务提供者，然后进行初始化。

到这里LoggerFactory初始化和Logger的获取就告一段落了！但是刨根问底，**==slf4j是如何查找所有的服务提供者的？==**

## slf4j是如何查找所有的服务提供者的？

我们进入到上面的findServiceProviders()方法：

```java
//查找所有的服务提供者
private static List<SLF4JServiceProvider> findServiceProviders() {
    /**
         * ServiceLoader   JDK提供的API
         * ServiceLoader.load() 基于SPI机制
         */
    ServiceLoader<SLF4JServiceProvider> serviceLoader = ServiceLoader.load(SLF4JServiceProvider.class);
    List<SLF4JServiceProvider> providerList = new ArrayList<SLF4JServiceProvider>();
    for (SLF4JServiceProvider provider : serviceLoader) {
        providerList.add(provider);
    }
    return providerList;
}
```

这里的ServiceLoader是JDK提供的API，基于SPI的机制来查找SLF4JServiceProvider接口的所有实现类

> ## 什么是SPI
>
>    SPI全称Service Provider Interface，是Java提供的一套用来被第三方实现或者扩展的接口，它可以用来启用框架扩展和替换组件。 SPI的作用就是为这些被扩展的API寻找服务实现。
>
> 通过ServiceLoader.load(SLF4JServiceProvider.class)会在类路径下查找META-INF/services/目录下的文件(文件名为接口名)中的类
>
> + 创建接口
>
>   + ```java
>     public interface User {
>     }
>     ```
>
> + 实现类
>
>   + ```java
>     public class UserImpl implements User {
>         @Override
>         public String toString() {
>             return "aaa";
>         }
>     }
>     ```
>
> + 在resources目录下创建META-INF/services/com.lj.mpdemo.User文件
>
>   + ```txt
>     com.lj.demo.UserImpl
>     ```
>
> + 测试
>
>   + ```java
>     public static void main(String[] args) {
>         ServiceLoader<User> users = ServiceLoader.load(User.class);
>         for (User user : users) {
>             System.out.println(user);
>         }
>     }
>     // 结果 aaa
>     ```

