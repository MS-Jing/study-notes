

# 第一个SpringBoot程序

pom:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.5.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.jing</groupId>
    <artifactId>springbootstudy</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>springbootstudy</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <!--web依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

```java
package com.jing.contorller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloContorller {

    @RequestMapping("/hello")
    public String sayHello(){
        return "Hello";
    }
}
```

更新banner步骤：在resource文件目录下创建一个banner.txt 文件，在https://www.bootschool.net/ascii/自动生成器生成图片复制粘贴至banner文件即可

## 原理初探

springboot的自动配置：

**pom:**

+ spring-boot-dependencies:在这个父依赖中管理了核心依赖包和版本

**启动器：**

+ ```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter</artifactId>
  </dependency>
  ```

+ 启动器：说白了就是springboot的启动场景。使用什么功能添加什么启动器就好了


**主程序：**

```java
package com.jing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication   标注这个类是springboot的应用
 * SpringApplication    将springboot应用启动，通过反射加载SpringbootstudyApplication类
 */
@SpringBootApplication
public class SpringbootstudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootstudyApplication.class, args);
    }

}
```

+ **注解**：

  + ```java
    @SpringBootConfiguration: springboot的配置
    	@Configuration： spring的配置类
        	@Component：	说明它也是一个spring的组件
    @EnableAutoConfiguration：开启自动配置
        @AutoConfigurationPackage：	自动配置包
        	@Import(AutoConfigurationPackages.Registrar.class)：	自动配置包注册
        @Import(AutoConfigurationImportSelector.class)：自动配置导入选择，自动导入包
    @ComponentScan: 扫描当前主启动类同级的包。再到上面的自动注册去注册 
    //获取所有的配置
    List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);
    ```

    获取候选的配置：

    ```java
    protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
    		List<String> configurations = SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),
    				getBeanClassLoader());
    		Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you "
    				+ "are using a custom packaging, make sure that file is correct.");
    		return configurations;
    	}
    ```

  + **结论：**
  
  ​	springboot所有自动配置都是在启动的时候扫描并加载：`spring.factories`所有的自动配置类都在这里 面，但不一定生效，只有导入对应的start（启动器），自动装配才会生效，然后配置才能成功
  
+ 主启动类：

  + `run`开启了一个服务
  + `SpringApplication`做了四个事情：
    + 1.推断应用的类型是普通的项目还是Web项目
    + 2.查找并加载所有可用初始化器，设置到initializers属性中
    + 3.找出所有的应用程序监听器，设置到listeners属性中
    + 4.推断并设置main方法的定义类，找到运行的主类

# springboot：配置文件及自动配置原理

#### 配置文件

springboot使用一个全局的配置文件，配置文件名称是固定的

+ application.properties
  + 语法结构：key = value
+ application.yaml
  + 语法结构：key: value

**配置文件的作用：**修改springboot自动配置的默认值，因为springboot在底层都给我们自动配置好了

---

#### yaml

[yaml百度百科](https://baike.baidu.com/item/YAML/1067697?fr=aladdin)

**标记语言**

​	以前的配置文件，大多数都是使用xml来配置的；比如一个简单的端口配置

yaml配置：

```yaml
server: 
	prot: 8081
```

xml配置：

```xml
<server>
	<prot>8081</prot>
</server>
```

**基础语法：**

`k: v`中间要有一个空格，以此来表示一对键值对(空格不能省略) ;以空格的缩进来控制层级关系，只要是左边对齐的一列数据都是同一个层级的。

注意：属性和值的大小写都是十分敏感的。

值直接写在后面就可以了，字符串默认不用加上双引号或者单引号。双引号不会转义字符串里面的特殊字符，特殊字符会作为本身想表示的意思；

例如：`name: "luo \n jing"`输出为：`luo 换行 jing` 



#### yaml给实体类赋值

**步骤：**

+ 第一步编写实体类并将实体类放入到spring容器中（@Component）
+ 加入`@ConfigurationProperties(prefix = "")`注解
  + `@ConfigurationProperties`的作用：将配置文件中的每一个属性的值，映射到这个组件中；告诉springboot将本类中的所有属性和配置文件中相关的配置进行绑定。
  + 参数prefix = "person":将配置文件中的person下面的所有属性一一对应
  + 只有这个组件是容器中的组件，才能使用容器提供的`@ConfigurationProperties`功能
+ 在pom加入提示的依赖（非必须）
+ 编写yaml
+ 测试类

实例：

+ 实体类

  + ```java
    package com.jing.pojo;
    
    import org.springframework.boot.context.properties.ConfigurationProperties;
    import org.springframework.stereotype.Component;
    
    import java.util.Date;
    import java.util.List;
    import java.util.Map;
    
    @Component
    @ConfigurationProperties(prefix = "person")
    public class Person {
        private String name;
        private Integer age;
        private Boolean happy;
        private Date birth;
        private Map<String,Object> maps;
        private List<Object> lists;
        private Dog dog;
    
        public Person() {
        }
    
        public Person(String name, Integer age, Boolean happy, Date birth, Map<String, Object> maps, List<Object> lists, Dog dog) {
            this.name = name;
            this.age = age;
            this.happy = happy;
            this.birth = birth;
            this.maps = maps;
            this.lists = lists;
            this.dog = dog;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public Integer getAge() {
            return age;
        }
    
        public void setAge(Integer age) {
            this.age = age;
        }
    
        public Boolean getHappy() {
            return happy;
        }
    
        public void setHappy(Boolean happy) {
            this.happy = happy;
        }
    
        public Date getBirth() {
            return birth;
        }
    
        public void setBirth(Date birth) {
            this.birth = birth;
        }
    
        public Map<String, Object> getMaps() {
            return maps;
        }
    
        public void setMaps(Map<String, Object> maps) {
            this.maps = maps;
        }
    
        public List<Object> getLists() {
            return lists;
        }
    
        public void setLists(List<Object> lists) {
            this.lists = lists;
        }
    
        public Dog getDog() {
            return dog;
        }
    
        public void setDog(Dog dog) {
            this.dog = dog;
        }
    
        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", happy=" + happy +
                    ", birth=" + birth +
                    ", maps=" + maps +
                    ", lists=" + lists +
                    ", dog=" + dog +
                    '}';
        }
    }
    ```

  + ```java
    package com.jing.pojo;
    
    import org.springframework.boot.context.properties.ConfigurationProperties;
    import org.springframework.stereotype.Component;
    
    @Component
    @ConfigurationProperties(prefix = "dog")
    public class Dog {
        private String name;
        private Integer age;
    
        public Dog(String name, Integer age) {
            this.name = name;
            this.age = age;
        }
    
        public Dog() {
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public Integer getAge() {
            return age;
        }
    
        public void setAge(Integer age) {
            this.age = age;
        }
    
        @Override
        public String toString() {
            return "Dog{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
    ```

+ pom

  + ```xml
    <!--加入了这个注解用yaml给实体类赋值有提示-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-configuration-processor</artifactId>
        <optional>true</optional>
    </dependency>
    ```

+ yaml

  + ```yaml
    person:
      name: 罗敬${random.int}/${random.uuid}
      age: 20
      happy: true
      birth: 1999/6/12
      maps: {k1: v1,k2: v2}
      lists: [a,b,c]
      dog:
        name: 旺财
        age: 3
    ```

+ 测试

  + ```java
    @Autowired
        private Person person;
    
        @Test
        void contextLoads() {
            System.out.println(person);
        }
    ```

+ 结果

  + ```
    Person{name='罗敬1429205876/a10582e8-b5f6-4ad7-9af2-67d55ebb0edb', age=20, happy=true, birth=Sat Jun 12 00:00:00 CST 1999, maps={k1=v1, k2=v2}, lists=[a, b, c], dog=Dog{name='旺财', age=3}}xxxxxxxxxx Person{name='罗敬1429205876/a10582e8-b5f6-4ad7-9af2-67d55ebb0edb', age=20, happy=true, birth=Sat Jun 12 00:00:00 CST 1999, maps={k1=v1, k2=v2}, lists=[a, b, c], dog=Dog{name='旺财', age=3}}Person{name='罗敬', age=20, happy=true, birth=Sat Jun 12 00:00:00 CST 1999, maps={k1=v1, k2=v2}, lists=[a, b, c], dog=Dog{name='旺财', age=3}}
    ```

---

#### @ConfigurationProperties和@value对比

|                | @ConfigurationProperties | @value       |
| -------------- | ------------------------ | ------------ |
| 功能           | 批量注入属性             | 一个一个指定 |
| 松散绑定       | 支持                     | 不支持       |
| SpEL           | 不支持                   | 支持         |
| JSR303数据校验 | 支持                     | 不支持       |
| 复杂度类型封装 | 支持                     | 不支持       |

+ 松散绑定：比如yaml中写的属性写的last-name,这个和lastName是一样的。
+ JSR303数据校验，这个就是我们可以在类属性上增加一层过滤器验证，可以保证数据的合法性
+ 复杂类型封装，yaml中可以封装对象，使用@value不支持

#### 多环境配置及配置文件位置

**文件位置：**

+ 在项目目录下`file:./config/`最高
+ `file:./`次之
+ `classpath:/config`
+ `classpath`最低（默认）

**多环境配置：**

+ 可以在优先级最高的配置文件覆盖
+ 可以在resources目录下创建多个properties。
  + application.properties
    + 在默认配置文件中：`spring.profiles.active=[dev]`来选择激活哪一个配置文件
  + application-dev.properties
  + application-test.properties

+ yaml形式实现多环境测试。yaml可以实现**多文档模块**

  + 用`---`实现多文档模块的分隔，用spring：Profiles：[dev],来命名模块 

  + ```yaml
    server:
      port: 8080
    spring:
      profiles:
        active: test
    ---
    server:
      port: 8081
    spring:
      profiles: test
    ---
    server:
      port: 8082
    spring:
      profiles: dev
    ```

#### 自动配置原理

**springboot启动会加载大量的自动配置类**

在springboot的启动类里加载了spring-boot-autoconfigrue这个包里的META-INF里的spring.factories，这个文件里包括了springboot所有的自动配置类`xxxAutoConfiguration`。用于给容器添加组件

点开一个自动配置类：

+ `@Configuration(proxyBeanMethods = false)`表示这是一个配置类
+ `@EnableConfigurationProperties(xxxProperties.class)`自动配置xxx.class这个类的属性
  + 点开xxxProperties.class这个类。会发现有一个`@ConfigurationProperties(prefix = "xxx")`。所以，我们只要在配置文件里绑定这个对象，给对象赋值就可以实现自定义配置

+ `@ConditionalOnxxx`是spring的底层注解。他们可以根据不同的条件，来判断当前配置或者类是否生效

# springboot web开发

打成jar包，内嵌了Tomcat服务器

自动装配。springboot到底帮我们配置了什么。能否修改相关配置？

## 导入静态资源

"/**",  "classpath:/resources/", "classpath:/static/", "classpath:/public/"，这四个目录都可以访问静态资源。自行分析WebMvcAutoConfiguration和ResourceProperties

### 通过webjars引入静态文件

分析`WebMvcAutoConfiguration`的`addResourceHandlers`方法

[webjars官网](https://www.webjars.org/)

引入相关静态依赖包

通过`/webjars/**`来访问。例如`http://localhost:8080/webjars/jquery/3.4.1/jquery.js`

## 首页和图标

在任何静态文件位置放置index.html就可以映射成首页,设置首页位置

```yaml
#设置首页位置
server:
  servlet:
    context-path: /luo
```

类路径下放入favicon.ico做图标，然后关闭默认的图标。`spring.mvc.favicon.enabled = false`......未解决！！！

## thymeleaf模板引擎

相关依赖

```xml
<dependency>
    <groupId>org.thymeleaf</groupId>
    <artifactId>thymeleaf-spring5</artifactId>
</dependency>
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-java8time</artifactId>
</dependency>
```

在templates文件下添加html文件。这里必须由controller来跳页面

添加命名空间

```html
<html lang="en" xmlns:th="http://www.thymeleaf.org">
```

```yaml
spring:
  thymeleaf:
    cache: false	//关闭模板引擎的缓存
```

## 装配扩展springMvc

写一个mvc配置类

```java
package com.jing.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

//扩展 springmvc
@Configuration
//@EnableWebMvc springmvc被全面接管
public class MyMvcConfig implements WebMvcConfigurer {

    @Override   //视图控制器
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("").setViewName("index");
        registry.addViewController("main.html").setViewName("dashboard");
    }

    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册一个拦截器。拦截那些请求。排除那些请求
        registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").excludePathPatterns("/index.html","/","/user/login");
    }
}
```

## 国际化

+ 在resources目录下创建i18n文件夹，再创建login.properties，login_en_US.properties,login_zh_CN.properties三个文件，==手动创建不要赋值==

+ login.properties

  + ```properties
    login.btn=登陆~
    login.password=密码~
    login.remember=记住我~
    login.tip=请登陆~
    login.username=用户名~
    ```

+ application.yaml

  + ```yaml
    spring:
      thymeleaf:
        cache: false	//关闭模板引擎的缓存
      messages:
        basename: i18n.login	配置国际化配置文件的配置
    ```

+ 页面：

  + ```html
    <a class="btn btn-sm" th:href="@{/index.html(l='zh_CN')}">中文</a>
    <a class="btn btn-sm" th:href="@{/index.html(l='en_US')}">English</a>
    ```

+ MyLocaleResolver.java

  + ```java
    package com.jing.config;
    
    import org.springframework.util.StringUtils;
    import org.springframework.web.servlet.LocaleResolver;
    
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.util.Locale;
    
    
    //配置地区解析器
    public class MyLocaleResolver implements LocaleResolver {
        @Override
        public Locale resolveLocale(HttpServletRequest request) {
            //解析请求.第一次请求，language为空，会报空指针
            String language = request.getParameter("l");
            if (StringUtils.isEmpty(language)){
                language="zh_CN";
            }
    
            Locale locale = Locale.getDefault();
            if(!StringUtils.isEmpty(locale)){
                String[] s = language.split("_");
                locale = new Locale(s[0], s[1]);
            }
            return locale;
        }
    
        @Override
        public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
    
        }
    }
    ```

+ 将自己的国际化配置交给spring管理MyMvcConfig.java

  + ```java
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocaleResolver();
    }
    ```

## 404

在templates下创建error文件夹，放入404.html即可

# springData

[官网](https://spring.io/projects/spring-data)

导入JDBCAPI和MySQL 驱动

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <scope>runtime</scope>
</dependency>
```

配置文件：

```yaml
spring:
  datasource:
    username: root
    password: 612612
    url: jdbc:mysql://localhost:3306/springbootdata?serverTimezone=UTC&userUnicode=true&characterEncoding=utf8&useSSL=true
    driver-class-name: com.mysql.cj.jdbc.Driver
```

做完以上操作，springboot会帮我们自动装配`DataSource`类

```java
@Autowired
DataSource dataSource;

@Test
void contextLoads() throws SQLException {
    //查看默认的数据源 com.zaxxer.hikari.HikariDataSource
    System.out.println(dataSource.getClass());

    //获取数据连接
    Connection connection = dataSource.getConnection();
    System.out.println(connection);//用的jdbc连接实现
    connection.close();
}
```

springboot将原生的jdbc进行了封装帮我们写好了一个jdbc模板`JdbcTemplate`类,可以通过该模板类访问数据库

```java
@Autowired
JdbcTemplate jdbcTemplate;

@Test
void contextLoads() throws SQLException {
    String sql = "select * from user";
    List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
    System.out.println(maps);
}
```

## Druid

Druid首先是一个**数据库连接池**。Druid是目前最好的数据库连接池，在功能、性能、扩展性方面，都超过其他数据库连接池，包括DBCP、C3P0、BoneCP、Proxool、JBoss DataSource。

**数据库连接池**负责分配、管理和释放数据库连接，它允许应用程序重复使用一个现有的数据库连接，而不是再重新建立一个；释放空闲时间超过最大空闲时间的数据库连接来避免因为没有释放数据库连接而引起的数据库连接遗漏。这项技术能明显提高对数据库操作的性能。

引入依赖：

```xml
<!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.21</version>
</dependency>
```

配置指定连接池：

```yaml
spring:
  datasource:
    username: root
    password: 612612
    url: jdbc:mysql://localhost:3306/mybatis?serverTimezone=Asia/Shanghai&userUnicode=true&characterEncoding=utf8&useSSL=true
    driver-class-name: com.mysql.cj.jdbc.Driver
    type: com.alibaba.druid.pool.DruidDataSource
    
    # Druid配置
    #初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
    initialSize: 5
    #定义最大连接池数量
    maxActive: 20
    #获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
    maxWait: 60000
    #是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。
    #在mysql5.5以下的版本中没有PSCache功能，建议关闭掉。5.5及以上版本有PSCache，建议开启。
    poolPreparedStatements: true
    #要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。
    #在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
    maxPoolPreparedStatementPerConnectionSize: 100
    #用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会其作用。
    validationQuery: SELECT 'x'
    #申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
    testOnBorrow: false
    #归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
    testOnReturn: false
    #建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
    testWhileIdle: true
    #属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：监控统计用的filter:stat;日志用的filter:log4j;防御sql注入的filter:wall
    filters: stat,wall,log4j
    #有两个含义：1) Destroy线程会检测连接的间隔时间;2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
    timeBetweenEvictionRunsMillis: 3000
    #配置一个连接在池中最小生存的时间，单位是毫秒
    minEvictableIdleTimeMillis: 300000
```

Druid配置类:

```java
package com.jing.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class DruidConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    //后台监控
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

        //后台登陆的账号密码配置
        HashMap<String, String> initParameters = new HashMap<>();
        initParameters.put("loginUsername","root");
        initParameters.put("loginPassword","123456");

        //允许谁可以访问
        initParameters.put("allow","");

        bean.setInitParameters(initParameters);
        return bean;
    }
    
    //过滤器
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new WebStatFilter());
        //可以过滤那些请求
        HashMap<String, String> initParameters = new HashMap<>();
        //不进行统计
        initParameters.put("exclusions","*.js,*.css,/druid/*");//排除那些

        bean.setInitParameters(initParameters);
        return bean;
    }
}
```

## Mybatis整合

导入相关依赖：

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.1</version>
</dependency>
```

配置：

```yaml
spring:
  datasource:
    username: root
    password: 612612
    url: jdbc:mysql://localhost:3306/mybatis?serverTimezone=Asia/Shanghai&userUnicode=true&characterEncoding=utf8&useSSL=true
    driver-class-name: com.mysql.cj.jdbc.Driver

# 整合mybatis
mybatis:
# 扫描包别名
  type-aliases-package: com.jing.model
# 注册映射文件位置
  mapper-locations: classpath:mybatis/mapper/*.xml
```

实体类：

```java
package com.jing.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Integer user_id;
    private String user_name;
    private String password;
    private Integer user_type_id;
}
```

UserMapper接口

```java
package com.jing.mapper;

import com.jing.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    List<User> queryList();
}
```

接口映射文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.jing.mapper.UserMapper">
    <select id="queryList" resultType="User">
        select * from user;
    </select>
</mapper>
```

# SpringSecurity

Spring Security是Spring提供的一个**==安全框架==**，提供**认证（对用户认证）和授权（对不同权限授权）功能**，最主要的是它提供了简单的使用方式，同时又有很高的灵活性，简单，灵活，强大。

权限：

+ 功能权限
+ 菜单权限
+ 访问权限
+ ......

**在之前我们都是使用拦截器和过滤器需要写大量的原生代码**，本例通过狂神说java的SpringSecurity的资源做演示学习

引入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

配置类：

```java
package com.jing.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//开启WebSecurity的功能
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    //认证（从内存中）
    /**
     *从2.1.x之后所有的密码需要做加密处理
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("kuangshen").password(new BCryptPasswordEncoder().encode("123456")).roles("vip2","vip3")
                .and()
                .withUser("root").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1","vip2","vip3")
                .and()
                .withUser("guest").password(new BCryptPasswordEncoder().encode("123456")).roles("vip1");
    }


    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //首页所有人可以访问。功能页只有有权限的人才能访问
        http.authorizeRequests()
                //添加首页为所有人都可以访问的请求
                .antMatchers("/").permitAll()
                //不同页面不同权限
                .antMatchers("/level1/**").hasRole("vip1")
                .antMatchers("/level2/**").hasRole("vip2")
                .antMatchers("/level3/**").hasRole("vip3");

        //没有权限去登陆页，开启登录页（默认的）
        //  loginPage("/toLogin")设置我们自己的登录页面路由
        //  loginProcessingUrl("/login")登录认证的路由
        http.formLogin().loginPage("/toLogin")
                .usernameParameter("username").passwordParameter("password")
                .loginProcessingUrl("/login");

        //为了防止跨站攻击，boot自动开启了csrf功能，需要关闭csrf功能
        http.csrf().disable();

        //开启记住我功能，默认保存两周
        http.rememberMe().rememberMeParameter("remember");

        //开启注销，如果不设置的话他就会跳到他默认的注销页面，设置logoutSuccessUrl("/")跳转到我们的首页
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/");
    }
}
```

通过上面的配置我们可以做相应的用户认证和不同权限的授权，但是在前端用thymeleaf,我们想在未登录时显示登录，登录之后显示用户名和注销就需要引入相关的依赖和操作了

```xml
<!--security-thymeleaf整合包-->
<!-- https://mvnrepository.com/artifact/org.thymeleaf.extras/thymeleaf-extras-springsecurity5 -->
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity5</artifactId>
    <version>3.0.4.RELEASE</version>
</dependency>
```

页面加上命名空间：

```html
xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity5"
```

页面：

```html
<!--登录注销-->
<div class="right menu">

    <!--如果未登录-->
    <div sec:authorize="!isAuthenticated()"><!--对这一块的内容授权，如果未登陆显示-->
        <a class="item" th:href="@{/toLogin}">
            <i class="address card icon"></i> 登录
        </a>
    </div>


    <!--如果以登录 用户名和注销-->
    <div sec:authorize="isAuthenticated()"><!--对这一块的内容授权，如果登陆了再显示-->
        <!--注销-->
        <a class="item">
            用户名：<span sec:authentication="name"></span><!--认证；当前用户的用户名-->
            权限：<span sec:authentication="principal.authorities"></span><!--认证；当前用户的权限-->
        </a>
    </div>
    <div sec:authorize="isAuthenticated()">
        <!--注销-->
        <a class="item" th:href="@{/logout}">
            <i class="sign-out icon"></i> 注销
        </a>
    </div>

</div>


<!--授权这块内容的权限是vip1的用户权限才可以看到-->
<div class="column" sec:authorize="hasRole('vip1')">
    <div class="ui raised segment">
        <div class="ui">
            <div class="content">
                <h5 class="content">Level 1</h5>
                <hr>
                <div><a th:href="@{/level1/1}"><i class="bullhorn icon"></i> Level-1-1</a></div>
                <div><a th:href="@{/level1/2}"><i class="bullhorn icon"></i> Level-1-2</a></div>
                <div><a th:href="@{/level1/3}"><i class="bullhorn icon"></i> Level-1-3</a></div>
            </div>
        </div>
    </div>
</div>
```





# shiro

+ Apache Shiro是一个Java 的安全(权限)框架。
+ Shiro可以非常容易的开发出足够好的应用，其不仅可以用在JavaSE环境，也可以用在JavaEE环境。
+ Shiro可以完成，认证，授权，加密，会话管理, Web集成,缓存等。

创建一个maven项目

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>shirostudy</artifactId>
        <groupId>org.example</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>hello-shiro</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-core</artifactId>
            <version>1.4.1</version>
        </dependency>

        <!-- configure logging -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>jcl-over-slf4j</artifactId>
           <version>1.7.21</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>1.7.21</version>
        </dependency>
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
    </dependencies>
</project>
```

配置文件：

log4j.properties

```properties
#
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
#
log4j.rootLogger=INFO, stdout

log4j.appender.stdout=org.apache.log4j.ConsoleAppender
log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
log4j.appender.stdout.layout.ConversionPattern=%d %p [%c] - %m %n

# General Apache libraries
log4j.logger.org.apache=WARN

# Spring
log4j.logger.org.springframework=WARN

# Default Shiro logging
log4j.logger.org.apache.shiro=INFO

# Disable verbose logging
log4j.logger.org.apache.shiro.util.ThreadContext=WARN
log4j.logger.org.apache.shiro.cache.ehcache.EhCache=WARN
```

shiro.ini

```ini
#
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
#
# =============================================================================
# Quickstart INI Realm configuration
#
# For those that might not understand the references in this file, the
# definitions are all based on the classic Mel Brooks' film "Spaceballs". ;)
# =============================================================================

# -----------------------------------------------------------------------------
# Users and their assigned roles
#
# Each line conforms to the format defined in the
# org.apache.shiro.realm.text.TextConfigurationRealm#setUserDefinitions JavaDoc
# -----------------------------------------------------------------------------
[users]
# user 'root' with password 'secret' and the 'admin' role
root = secret, admin
# user 'guest' with the password 'guest' and the 'guest' role
guest = guest, guest
# user 'presidentskroob' with password '12345' ("That's the same combination on
# my luggage!!!" ;)), and role 'president'
presidentskroob = 12345, president
# user 'darkhelmet' with password 'ludicrousspeed' and roles 'darklord' and 'schwartz'
darkhelmet = ludicrousspeed, darklord, schwartz
# user 'lonestarr' with password 'vespa' and roles 'goodguy' and 'schwartz'
lonestarr = vespa, goodguy, schwartz

# -----------------------------------------------------------------------------
# Roles with assigned permissions
# 
# Each line conforms to the format defined in the
# org.apache.shiro.realm.text.TextConfigurationRealm#setRoleDefinitions JavaDoc
# -----------------------------------------------------------------------------
[roles]
# 'admin' role has all permissions, indicated by the wildcard '*'
admin = *
# The 'schwartz' role can do anything (*) with any lightsaber:
schwartz = lightsaber:*
# The 'goodguy' role is allowed to 'drive' (action) the winnebago (type) with
# license plate 'eagle5' (instance specific id)
goodguy = winnebago:drive:eagle5
```

Quickstart.java:

```java

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Simple Quickstart application showing how to use Shiro's API.
 *
 * @since 0.9 RC2
 */
public class Quickstart {

    private static final transient Logger log = LoggerFactory.getLogger(Quickstart.class);


    public static void main(String[] args) {

        // The easiest way to create a Shiro SecurityManager with configured
        // realms, users, roles and permissions is to use the simple INI config.
        // We'll do that by using a factory that can ingest a .ini file and
        // return a SecurityManager instance:

        // Use the shiro.ini file at the root of the classpath
        // (file: and url: prefixes load from files and urls respectively):
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();

        // for this simple example quickstart, make the SecurityManager
        // accessible as a JVM singleton.  Most applications wouldn't do this
        // and instead rely on their container configuration or web.xml for
        // webapps.  That is outside the scope of this simple quickstart, so
        // we'll just do the bare minimum so you can continue to get a feel
        // for things.
        SecurityUtils.setSecurityManager(securityManager);

        // Now that a simple Shiro environment is set up, let's see what you can do:

        // get the currently executing user:
        Subject currentUser = SecurityUtils.getSubject();

        // Do some stuff with a Session (no need for a web or EJB container!!!)
        Session session = currentUser.getSession();
        session.setAttribute("someKey", "aValue");
        String value = (String) session.getAttribute("someKey");
        if (value.equals("aValue")) {
            log.info("Retrieved the correct value! [" + value + "]");
        }

        // let's login the current user so we can check against roles and permissions:
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
            token.setRememberMe(true);
            try {
                currentUser.login(token);
            } catch (UnknownAccountException uae) {
                log.info("There is no user with username of " + token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                log.info("Password for account " + token.getPrincipal() + " was incorrect!");
            } catch (LockedAccountException lae) {
                log.info("The account for username " + token.getPrincipal() + " is locked.  " +
                        "Please contact your administrator to unlock it.");
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            catch (AuthenticationException ae) {
                //unexpected condition?  error?
            }
        }

        //say who they are:
        //print their identifying principal (in this case, a username):
        log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");

        //test a role:
        if (currentUser.hasRole("schwartz")) {
            log.info("May the Schwartz be with you!");
        } else {
            log.info("Hello, mere mortal.");
        }

        //test a typed permission (not instance-level)
        if (currentUser.isPermitted("lightsaber:wield")) {
            log.info("You may use a lightsaber ring.  Use it wisely.");
        } else {
            log.info("Sorry, lightsaber rings are for schwartz masters only.");
        }

        //a (very powerful) Instance Level permission:
        if (currentUser.isPermitted("winnebago:drive:eagle5")) {
            log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
                    "Here are the keys - have fun!");
        } else {
            log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
        }

        //all done - log out!
        currentUser.logout();

        System.exit(0);
    }
}
```

## springboot整合shiro

依赖：

```xml
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-spring-boot-web-starter</artifactId>
    <version>1.4.1</version>
</dependency>
```

未完。。。。。

# 任务

## 异步任务

在主启动类上标注`@EnableAsync`开启异步注解的功能，再在service方法上标注`@Async`告诉springboot这是一个异步任务。前台无需等待，直接拿到得到结果



## 定时任务

springboot自带的两个定时任务的接口`TaskExecutor`任务执行者,`TaskScheduler`任务调度,

在主程序上加上`@EnableScheduling`开启定时功能，`@Scheduld`什么时候执行



### cron表达式

```java
//一个定时任务
@Service
public class ScheduldService {

    //                秒 分 时 日 月 星期
    @Scheduled(cron = "0 15 12 * * ?")//定时任务，在每天的12点15分开始执行当前方法
    public void hello(){
        System.out.println("正在执行！");
    }
}
```

**常用表达式例子**

（1）**0/2 \* \* \* \* ?**  表示每2秒 执行任务

（1）**0 0/2 \* \* \* ?**  表示每2分钟 执行任务

（1）**0 0 2 1 \* ?**  表示在每月的1日的凌晨2点调整任务

（2）**0 15 10 ? \* MON-FRI**  表示周一到周五每天上午10:15执行作业

（3）**0 15 10 ? 6L 2002-2006**  表示2002-2006年的每个月的最后一个星期五上午10:15执行作

（4）**0 0 10,14,16 \* \* ?**  每天上午10点，下午2点，4点 

（5）**0 0/30 9-17 \* \* ?**  朝九晚五工作时间内每半小时 

（6）**0 0 12 ? \* WED**   表示每个星期三中午12点 

（7）**0 0 12 \* \* ?**  每天中午12点触发 

（8）**0 15 10 ? \* \***   每天上午10:15触发 

（9）**0 15 10 \* \* ?**   每天上午10:15触发 

（10）**0 15 10 \* \* ?**   每天上午10:15触发 

（11）**0 15 10 \* \* ? 2005**   2005年的每天上午10:15触发 

（12）**0 \* 14 \* \* ?**   在每天下午2点到下午2:59期间的每1分钟触发 

（13）**0 0/5 14 \* \* ?**   在每天下午2点到下午2:55期间的每5分钟触发 

（14）**0 0/5 14,18 \* \* ?**   在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发 

（15）**0 0-5 14 \* \* ?**   在每天下午2点到下午2:05期间的每1分钟触发 

（16）**0 10,44 14 ? 3 WED**   每年三月的星期三的下午2:10和2:44触发 

（17）**0 15 10 ? \* MON-FRI**   周一至周五的上午10:15触发 

（18）**0 15 10 15 \* ?**   每月15日上午10:15触发 

（19）**0 15 10 L \* ?**   每月最后一日的上午10:15触发 

（20）**0 15 10 ? \* 6L**   每月的最后一个星期五上午10:15触发 

（21）**0 15 10 ? \* 6L 2002-2005**  2002年至2005年的每月的最后一个星期五上午10:15触发 

（22）**0 15 10 ? \* 6#3**  每月的第三个星期五上午10:15触发

## 邮件发送

依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

相关自动配置类`MailSenderAutoConfiguration`，`JavaMailSenderImpl`相关的方法springboot在这个类里帮我们封装好了，引入相关配置即可使用

配置：

```yaml
spring:
  mail:
    username: 1126184155@qq.com
    password: yqxwzswiixerhjdd
    host: smtp.qq.com
    #开启加密验证
    properties.mail.smtp.ssl.enable: true
```

测试：

```java
@Autowired
    JavaMailSenderImpl mailSender;

    @Test
    void test(){

        //尝试发送一个简单的邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("你好！");
        simpleMailMessage.setText("正在测试！");
        simpleMailMessage.setTo("1126184155@qq.com");
        simpleMailMessage.setFrom("1126184155@qq.com");

        mailSender.send(simpleMailMessage);
    }
	@Test
    void test() throws MessagingException {
        //尝试发送一个复杂的邮件
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setSubject("你好！我是标题");
        messageHelper.setText("<h1>我是h1标题</h1>",true);

        messageHelper.setTo("1126184155@qq.com");
        messageHelper.setFrom("1126184155@qq.com");

        mailSender.send(mimeMessage);
    }
```



# 分布式

分布式系统就是若干个独立计算机的集合，这些计算机对于用户来说就像单个的相关系统

## RPC

RPC [ Remote Procedure Call]是指远程过程调用，是一种进程间通信方式， 他是一种技术的思想， 而不是规范。它允许程序调用另一个地址空间(通常是共享网络的另一台机器上)的过程或函数，而不用程序员显式编码这个远程调用的细节。即程序员无论是调用本地的还是远程的函数，本质上编写的调用代码基本相同。

## Dubbo

Apache Dubbo I' dAbeu|是一款高性能、轻量级的开源Java RPC框架，它提供了三大核心能力:面向接口的远程方法调用，智能容错和负载均衡，以及服务自动注册和发现。