# Springboot自动装配原理

Springboot的主启动类就是程序的入口。我们先看主启动类

```java
@SpringBootApplication
public class ReadSpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReadSpringbootApplication.class, args);
    }
}
```

## 注解部分

我们先进入@SpringBootApplication注解看看这个注解到底做了什么：

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration  // springboot的配置注解
@EnableAutoConfiguration  //开启自动配置的注解
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) }) //组件扫描过滤器
public @interface SpringBootApplication {

    /**
    *排除特定的自动配置类，使它们永远不会被应用。
    */
	@AliasFor(annotation = EnableAutoConfiguration.class)
	Class<?>[] exclude() default {};

    /**
    *排除特定的自动配置类名，使它们永远不会出现应用。
    */
	@AliasFor(annotation = EnableAutoConfiguration.class)
	String[] excludeName() default {};

    /**
    *扫描带注解的组件的基本包名
    */
	@AliasFor(annotation = ComponentScan.class, attribute = "basePackages")
	String[] scanBasePackages() default {};

    /**
    *scanBasePackageClasses和scanBasePackages只能选择一个
    */
	@AliasFor(annotation = ComponentScan.class, attribute = "basePackageClasses")
	Class<?>[] scanBasePackageClasses() default {};

    /**
    *指定{@link Bean @Bean}方法是否应该被代理以便执行bean生命周期行为，
    */
	@AliasFor(annotation = Configuration.class)
	boolean proxyBeanMethods() default true;

}
```

我们可以看到@SpringBootApplication是一个组合注解

### @SpringBootConfiguration

我们先来看看@SpringBootConfiguration注解做了什么：

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
public @interface SpringBootConfiguration {

	@AliasFor(annotation = Configuration.class)
	boolean proxyBeanMethods() default true;

}
```

这个注解除了元注解以外，只有一个@Configuration。唉？我们平时写在config目录下的配置文件上不就是加的这个注解吗？那也就是说我们的主启动类也是一个配置类，也是spring的一个组件咯。

### @EnableAutoConfiguration

上面说到@springbootConfiguration其实就是一个@Configuration就是说我们的主启动类就是一个配置类。我们在来看看@EnableAutoConfiguration这个开启自动配置的配置类到底做了些什么:

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@AutoConfigurationPackage
@Import(AutoConfigurationImportSelector.class)
public @interface EnableAutoConfiguration {

	String ENABLED_OVERRIDE_PROPERTY = "spring.boot.enableautoconfiguration";

	Class<?>[] exclude() default {};

	String[] excludeName() default {};

}
```

我们可以看到这个注解又是一个组合注解我们先看@AutoConfigurationPackage这个自动配置包：

```java
@Import(AutoConfigurationPackages.Registrar.class)
public @interface AutoConfigurationPackage {
}
```

这里除了元注解，就一个@Import注解，前面我们说过@springbootConfiguration标注的类是一个配置类。那么也就是说@Import注解是将AutoConfigurationPackages.Registrar.class加载到spring容器中了

> 其实@AutoConfigurationPackage的主要作用是: 让包中的类以及子包中的类能够被自动扫描到spring容器中。
>
> 换句话说就是将主启动类所在的包及其子包里的组件都扫描加载到Spring容器中。
>
> 所以我们需要

---

我们再来看@Import(AutoConfigurationImportSelector.class)：也就是说

这里也是向spring容器引入了一个AutoConfigurationImportSelector.class自动配置导入选择器的类。

AutoConfigurationImportSelector有一个getAutoConfigurationEntry的方法，他会查找所有需要导入的组件以全类名的方式返回，这些组件就会被添加到容器中。

```java
protected AutoConfigurationEntry getAutoConfigurationEntry(AutoConfigurationMetadata autoConfigurationMetadata,
                                                           AnnotationMetadata annotationMetadata) {
    if (!isEnabled(annotationMetadata)) {
        return EMPTY_ENTRY;
    }
    AnnotationAttributes attributes = getAttributes(annotationMetadata);
    List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);
    configurations = removeDuplicates(configurations);
    Set<String> exclusions = getExclusions(annotationMetadata, attributes);
    checkExcludedClasses(configurations, exclusions);
    configurations.removeAll(exclusions);
    configurations = filter(configurations, autoConfigurationMetadata);
    fireAutoConfigurationImportEvents(configurations, exclusions);
    return new AutoConfigurationEntry(configurations, exclusions);
}
```

你可以对configurations这个List进行debug会发现里面全是我们常见的xxxAutoConfiguration类的全类名。那么有了这些类的全类名就可以向容器中注入他们了。那这些类是如何查找到的呢？我们来看一下getCandidateConfigurations这个方法：

```java
protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
    List<String> configurations = SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),
                                                                         getBeanClassLoader());
    Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you "
                    + "are using a custom packaging, make sure that file is correct.");
    return configurations;
}
```

这里又调用了SpringFactoriesLoader.loadFactoryNames()方法，进去看看

```java
public static List<String> loadFactoryNames(Class<?> factoryType, @Nullable ClassLoader classLoader) {
    String factoryTypeName = factoryType.getName();
    return loadSpringFactories(classLoader).getOrDefault(factoryTypeName, Collections.emptyList());
}
```

我们在进入到loadSpringFactories()方法看看：

```java
private static Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader) {
    //先从缓存中获取相关的类加载器的所有加载的数据，如果当前类加载器还没有加载数据就让他去加载
    MultiValueMap<String, String> result = cache.get(classLoader);
    if (result != null) {
        return result;
    }

    try {
        //判断当前类加载器是否为Null不为空就去加载配置
        Enumeration<URL> urls = (classLoader != null ?
                                 classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
                                 ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION)); // FACTORIES_RESOURCE_LOCATION就是META-INF/spring.factories
        result = new LinkedMultiValueMap<>();
        // 注意这里可能找到的不知一个spring.factories可能有多个，所以应该循环遍历的去解析每一个文件
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            UrlResource resource = new UrlResource(url);
            //该文件是以Properties的格式写的
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            //遍历每一个数据的键值
            for (Map.Entry<?, ?> entry : properties.entrySet()) {
                //键是一个类的全类名，而值是由多个全类名用逗号隔开的
                //所以这里使用了LinkedMultiValueMap让键为key然后可以对应多个值
                String factoryTypeName = ((String) entry.getKey()).trim();
                // 这里在切分值，以逗号分隔，然后遍历向result添加
                for (String factoryImplementationName : StringUtils.commaDelimitedListToStringArray((String) entry.getValue())) {
                    result.add(factoryTypeName, factoryImplementationName.trim());
                }
            }
        }
        //最后将该类加载器加载的数据缓存下来，后面需要使用不再需要再加载文件浪费IO了
        cache.put(classLoader, result);
        return result;
    }
    catch (IOException ex) {
        throw new IllegalArgumentException("Unable to load factories from location [" +
                                           FACTORIES_RESOURCE_LOCATION + "]", ex);
    }
}
```

这个方法就是加载我们的META-INF/spring.factories的方法，并将里面的数据以一个可复用的Map（LinkedMultiValueMap）的形式封装到一个result中。再以当前类加载器为缓存的键将result为值。那么下次这个加载器再来加载直接从缓存中取获取，不再从新加载文件。

### @ComponentScan

该注解默认会扫描该类所在的包下所有的配置类，所以这里会扫描启动类下面的所有的配置类



## Run方法的部分

```java
public class ReadSpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReadSpringbootApplication.class, args);
    }
}
```

跟进去看看：

```java
public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
    return new SpringApplication(primarySources).run(args);
}
```

这也就是说这是要创建一个ApplicationContext上下文呗，就是我们说的容器，先去看看这个SpringApplication初始化做了些什么：

```java
public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
    // 这个资源加载器是null，在他的上一层调用传过来的
    this.resourceLoader = resourceLoader;
    Assert.notNull(primarySources, "PrimarySources must not be null");
    //将我们的主启动类的class对象设置到this.primarySources
    this.primarySources = new LinkedHashSet<>(Arrays.asList(primarySources));
    //判断当前的应用什么类型
    this.webApplicationType = WebApplicationType.deduceFromClasspath();
    //设置初始化器
    setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class));
    //设置监听器
    setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
    this.mainApplicationClass = deduceMainApplicationClass();
}
```

这里只是对SpringApplication对象进行了初始化，但是值得我们关注的是设置初始化器和设置监听器。他们的逻辑是一样的我们来看看设置初始化器getSpringFactoriesInstances()方法：

```java
private <T> Collection<T> getSpringFactoriesInstances(Class<T> type, Class<?>[] parameterTypes, Object... args) {
    //获取一个类加载器
    ClassLoader classLoader = getClassLoader();
    // 获取相关类的名字
    Set<String> names = new LinkedHashSet<>(SpringFactoriesLoader.loadFactoryNames(type, classLoader));
    //创建这些类的实例
    List<T> instances = createSpringFactoriesInstances(type, parameterTypes, classLoader, args, names);
    //进行一次排序
    AnnotationAwareOrderComparator.sort(instances);
    //返回这些初始化的实例
    return instances;
}
```

我们这里获取初始化类的名字使用的是SpringFactoriesLoader.loadFactoryNames(type, classLoader)方法，是不是很熟悉，这不就是我们之前在说@EnableAutoConfiguration注解的时候，说的导入了一个AutoConfigurationImportSelector的在自动配置导入扫描器。他有一个方法叫获取自动配置的方法，里面会调用这个SpringFactoriesLoader.loadFactoryNames方法，来加载所有的自动配置类的类名。

就是在这里，先将所有类路径下的META-INF/spring.factories加载解析后缓存起来的，然后那个自动配置导入扫描器（AutoConfigurationImportSelector）直接就可以使用了。

---

好了，到这里SpringApplication的初始化就结束了，我们回到上面的run方法：

```java
public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
    return new SpringApplication(primarySources).run(args);
}
```

上面对SpringApplication进行了初始化，那这个run又做了什么呢？

```java
public ConfigurableApplicationContext run(String... args) {
    //创建一个计时器，并启动他
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    //准备对ApplicationContext进行初始化
    ConfigurableApplicationContext context = null;
    //用来做异常记录用的
    Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
    //配置了一个属性这个不重要
    configureHeadlessProperty();
    //获取运行时监听器并启动他们
    SpringApplicationRunListeners listeners = getRunListeners(args);
    listeners.starting();
    try {
        //应用启动时的参数  args是从main方法传递过来的
        ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
        //准备环境
        ConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments);
        //配置忽略的Bean信息
        configureIgnoreBeanInfo(environment);
        //打印Banner
        Banner printedBanner = printBanner(environment);
        //创建ApplicationContext上下文
        context = createApplicationContext();
        //获取所有异常记录的实例
        exceptionReporters = getSpringFactoriesInstances(SpringBootExceptionReporter.class,
                                                         new Class[] { ConfigurableApplicationContext.class }, context);
        //准备容器，在这里设置了active profile环境，如果没有就是default环境
        prepareContext(context, environment, listeners, applicationArguments, printedBanner);
        //刷新容器
        refreshContext(context);
        //容器刷新之后调用
        afterRefresh(context, applicationArguments);
        //关闭计时器
        stopWatch.stop();
        //打印应用启动成功的日志信息
        if (this.logStartupInfo) {
            new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(), stopWatch);
        }
        listeners.started(context);
        callRunners(context, applicationArguments);
    }
    catch (Throwable ex) {
        handleRunFailure(context, ex, exceptionReporters, listeners);
        throw new IllegalStateException(ex);
    }

    try {
        listeners.running(context);
    }
    catch (Throwable ex) {
        handleRunFailure(context, ex, exceptionReporters, null);
        throw new IllegalStateException(ex);
    }
    return context;
}
```

这个run的主要目的是启动spring的应用，创建并刷新一个ApplicationContext。

我们这里主要来看一下刷新容器的方法refreshContext(context);

```java
private void refreshContext(ConfigurableApplicationContext context) {
    refresh(context);
    if (this.registerShutdownHook) {
        try {
            context.registerShutdownHook();
        }
        catch (AccessControlException ex) {
            // Not allowed in some environments.
        }
    }
}
```

在进到refresh(context);

```java
protected void refresh(ApplicationContext applicationContext) {
    Assert.isInstanceOf(AbstractApplicationContext.class, applicationContext);
    ((AbstractApplicationContext) applicationContext).refresh();
}
```

```java
public void refresh() throws BeansException, IllegalStateException {
    synchronized (this.startupShutdownMonitor) {
        // 为刷新准备这个上下文。
        prepareRefresh();
        // 告诉子类刷新内部bean工厂,并返回
        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();
        // 准备在此上下文中使用的bean工厂
        prepareBeanFactory(beanFactory);
        try {
            // 允许在上下文子类中对bean工厂进行后处理。
            postProcessBeanFactory(beanFactory);
            // 调用在上下文中注册为bean的工厂处理程序。
            invokeBeanFactoryPostProcessors(beanFactory);
            // 注册拦截bean创建的bean处理器。
            registerBeanPostProcessors(beanFactory);
            // 初始化此上下文的消息源。
            initMessageSource();
            // 初始化此上下文的事件多播程序。
            initApplicationEventMulticaster();
            // 初始化特定上下文子类中的其他特殊bean。
            onRefresh();
            //检查侦听器bean并注册它们。
            registerListeners();
            // 实例化所有剩余的(非惰性init)单例对象。
            finishBeanFactoryInitialization(beanFactory);
            // 最后一步:发布相应的事件。
            finishRefresh();
        }
        catch (BeansException ex) {
            if (logger.isWarnEnabled()) {
                logger.warn("Exception encountered during context initialization - " +
                            "cancelling refresh attempt: " + ex);
            }
            // 销毁已经创建的单例，以避免浪费资源。
            destroyBeans();
            // 重置 'active' 标志位.
            cancelRefresh(ex);
            // 向调用者传播异常
            throw ex;
        }
        finally {
            //重新设置Spring内核中的常见内省缓存
			//因为单例的bean可能永远不需要元数据…
            resetCommonCaches();
        }
    }
}
```

这是一个spring的bean的加载过程，我们来看onRefresh();方法,他是一个抽象方法，我们需要通过debug找到他的具体实现类(AnnotationConfigServletWebServerApplicationContext)，但是这个具体实现类里没有这个方法，所以我们看一下他的父类（ServletWebServerApplicationContext）：

```java
@Override
protected void onRefresh() {
    super.onRefresh();
    try {
        createWebServer();
    }
    catch (Throwable ex) {
        throw new ApplicationContextException("Unable to start web server", ex);
    }
}
```

这里的createWebServer();创建web服务。我们springboot默认的web服务器不就是tomcat的吗？去看看：

```java
private void createWebServer() {
    WebServer webServer = this.webServer;
    ServletContext servletContext = getServletContext();
    if (webServer == null && servletContext == null) {
        //获取一个web服务器工厂
        ServletWebServerFactory factory = getWebServerFactory();
        //再从工厂里创建
        this.webServer = factory.getWebServer(getSelfInitializer());
    }
    else if (servletContext != null) {
        try {
            getSelfInitializer().onStartup(servletContext);
        }
        catch (ServletException ex) {
            throw new ApplicationContextException("Cannot initialize servlet context", ex);
        }
    }
    initPropertySources();
}
```

我们先来看一下如何获取这个web工厂的：

```java
protected ServletWebServerFactory getWebServerFactory() {
    // Use bean names so that we don't consider the hierarchy
    String[] beanNames = getBeanFactory().getBeanNamesForType(ServletWebServerFactory.class);
    if (beanNames.length == 0) {
        throw new ApplicationContextException("Unable to start ServletWebServerApplicationContext due to missing "
                                              + "ServletWebServerFactory bean.");
    }
    if (beanNames.length > 1) {
        throw new ApplicationContextException("Unable to start ServletWebServerApplicationContext due to multiple "
                                              + "ServletWebServerFactory beans : " + StringUtils.arrayToCommaDelimitedString(beanNames));
    }
    return getBeanFactory().getBean(beanNames[0], ServletWebServerFactory.class);
}
```

我们可以看到是从BeanFactory中获取的，就是说，这个web服务工厂应该在之前初始化的时候实例化了然后注册到了容器中的。那我们看看这个工厂又是如何创建的（我们知道默认的是tomcat服务器通过debug我们可以知道这个工厂是:TomcatServletWebServerFactory）:

```java
@Override
public WebServer getWebServer(ServletContextInitializer... initializers) {
    if (this.disableMBeanRegistry) {
        Registry.disableRegistry();
    }
    Tomcat tomcat = new Tomcat();
    File baseDir = (this.baseDirectory != null) ? this.baseDirectory : createTempDir("tomcat");
    tomcat.setBaseDir(baseDir.getAbsolutePath());
    Connector connector = new Connector(this.protocol);
    connector.setThrowOnFailure(true);
    tomcat.getService().addConnector(connector);
    customizeConnector(connector);
    tomcat.setConnector(connector);
    tomcat.getHost().setAutoDeploy(false);
    configureEngine(tomcat.getEngine());
    for (Connector additionalConnector : this.additionalTomcatConnectors) {
        tomcat.getService().addConnector(additionalConnector);
    }
    prepareContext(tomcat.getHost(), initializers);
    return getTomcatWebServer(tomcat);
}
```

这里我们可以看到，这里对tomcat的web服务进行了创建初始化然后返回给ApplicationContext。















