# 什么是shiro

> ​		Shiro是一个功能强大且易于使用的Java安全框架，它执行身份验证、授权、加密和会话管理。使用Shiro易 于理解的API，您可以快速轻松地保护任何应用程序-从最小的移动应用程序 到最大的web和企业应用程序。

创建maven项目导入依赖：

```xml
<!--引入shiro-->
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-core</artifactId>
    <version>1.5.3</version>
</dependency>
```

shiro.ini

```ini
[users]
xiaochen=123
zhangsan=123456
lisi=789
```

测试类：

```java
package org.example;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

public class TestAuthenticator {
    public static void main(String[] args) {
        //1.创建安全管理器
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        //2.给安全管理器设置realms
        securityManager.setRealm(new IniRealm("classpath:shiro.ini"));

        //3.SecurityUtils 给全局安全工具类设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);

        //4.subject 主体
        Subject subject = SecurityUtils.getSubject();

        //5.创建令牌
        UsernamePasswordToken token = new UsernamePasswordToken("xiaochen","123");

        try {
            System.out.println("未认证时--->认证状态："+subject.isAuthenticated());
            subject.login(token);//用户认证    认证成功没有返回值，失败会抛出异常
            System.out.println("认证后--->认证状态："+subject.isAuthenticated());
        }catch (UnknownAccountException e){
            e.printStackTrace(); //用户不存在异常
        }catch (IncorrectCredentialsException e){
            e.printStackTrace();//认证错误异常
        }catch (AuthenticationException e) {
            e.printStackTrace();
        }

    }
}

```

# 数据库做数据来源

自定义realm：

```java
package org.example.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 自定义realm实现
 * 目的：将认证|授权数据来源改为数据库
 */
public class CustomerRealm extends AuthorizingRealm {
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String principal = token.getPrincipal().toString();
        System.out.println(principal);
        //根据这个 principal 身份信息来调用数据库
        if ("xiaochen".equals(principal)){
            // 参数1：数据库中的用户名，参数2：数据库中的密码，参数三：当前realm的名字，this.getName()
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal,"123",this.getName());
            return simpleAuthenticationInfo;
        }
        return null;
    }
}

```

测试类：

```java
package org.example;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.example.realm.CustomerRealm;

/**
 * 使用自定义realm
 */
public class TestCustomerRealmAuthenticator {
    public static void main(String[] args) {
        //1.创建安全管理器
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        //2.给安全管理器设置realms
        securityManager.setRealm(new CustomerRealm());

        //3.SecurityUtils 给全局安全工具类设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);

        //4.subject 主体
        Subject subject = SecurityUtils.getSubject();

        //5.创建令牌
        UsernamePasswordToken token = new UsernamePasswordToken("xiaochen","123");

        try {
            System.out.println("未认证时--->认证状态："+subject.isAuthenticated());
            subject.login(token);//用户认证    认证成功没有返回值，失败会抛出异常
            System.out.println("认证后--->认证状态："+subject.isAuthenticated());
        }catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }
}
```

# 使用MD5和salt认证

```java
package org.example;

import org.apache.shiro.crypto.hash.Md5Hash;

public class TestShiroMD5 {
    public static void main(String[] args) {
        //创建MD5算法
        Md5Hash md5Hash = new Md5Hash("123");
        System.out.println(md5Hash.toHex());

        //MD5 + salt
        Md5Hash md5Hash1 = new Md5Hash("123","dsdafd42./']");
        System.out.println(md5Hash1.toHex());

        //MD5 + salt + hash散列
        Md5Hash md5Hash2 = new Md5Hash("123","dsdafd42./']",1024);
        System.out.println(md5Hash2.toHex());

    }
}

```

```java
package org.example.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 *
 * 自定义realm 加入MD5 + salt + hash散列
 */
public class CustomerMd5Realm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取身份信息
        String principal = token.getPrincipal().toString();

        //根据用户名查询数据库
        if ("xiaochen".equals(principal)){
            //参数二：MD5 + salt 后的密码
            //参数三：ByteSource.Util.bytes("dsdafd42./']") 设置随机salt
            /*return new SimpleAuthenticationInfo(principal,
                    "3feec11bf706e623217ddb0d547c5914",
                    ByteSource.Util.bytes("dsdafd42./']"),
                    this.getName());*/
            //参数二：MD5 + salt + hash散列1024次 后的密码
            //参数三：ByteSource.Util.bytes("dsdafd42./']") 设置随机salt
            return new SimpleAuthenticationInfo(principal,
                    "a3725de9ffb7a6a7fa0bd783b5b07c34",
                    ByteSource.Util.bytes("dsdafd42./']"),
                    this.getName());


        }

        return null;
    }
}
```

```java
package org.example;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.example.realm.CustomerMd5Realm;

public class TestCustomerMd5RealmAuthenticator {
    public static void main(String[] args) {
        //1.创建安全管理器
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        //2.给安全管理器设置realms
        CustomerMd5Realm realm = new CustomerMd5Realm();
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5"); //md5加密
        credentialsMatcher.setHashIterations(1024);//散列1024次

        realm.setCredentialsMatcher(credentialsMatcher);//设置realm使用hash凭证匹配器
        securityManager.setRealm(realm);

        //3.SecurityUtils 给全局安全工具类设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);

        //4.subject 主体
        Subject subject = SecurityUtils.getSubject();

        //5.创建令牌
        UsernamePasswordToken token = new UsernamePasswordToken("xiaochen","123");

        try {
            System.out.println("未认证时--->认证状态："+subject.isAuthenticated());
            subject.login(token);//用户认证    认证成功没有返回值，失败会抛出异常
            System.out.println("认证后--->认证状态："+subject.isAuthenticated());
        }catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }
}
```

# shiro中的授权

## 授权

>  		授权，即访问控制，控制谁能访问哪些资源。主体进行身份认证后需要分配权限方可访问系统的资源，对于某些资源没有权限是无法访问的。

```java
package org.example;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.example.realm.CustomerMd5Realm;

import java.util.Arrays;

public class TestCustomerMd5RealmAuthenticator {
    public static void main(String[] args) {
        //1.创建安全管理器
        DefaultSecurityManager securityManager = new DefaultSecurityManager();

        //2.给安全管理器设置realms
        CustomerMd5Realm realm = new CustomerMd5Realm();
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5"); //md5加密
        credentialsMatcher.setHashIterations(1024);//散列1024次

        realm.setCredentialsMatcher(credentialsMatcher);//设置realm使用hash凭证匹配器
        securityManager.setRealm(realm);

        //3.SecurityUtils 给全局安全工具类设置安全管理器
        SecurityUtils.setSecurityManager(securityManager);

        //4.subject 主体
        Subject subject = SecurityUtils.getSubject();

        //5.创建令牌
        UsernamePasswordToken token = new UsernamePasswordToken("xiaochen","123");

        try {
            System.out.println("未认证时--->认证状态："+subject.isAuthenticated());
            subject.login(token);//用户认证    认证成功没有返回值，失败会抛出异常
            System.out.println("认证后--->认证状态："+subject.isAuthenticated());
        }catch (AuthenticationException e) {
            e.printStackTrace();
        }

        //认证用户进行授权
        if (subject.isAuthenticated()){
            //1.基于角色的权限控制
            System.out.println("====================基于角色的权限控制=====================");
            System.out.println("是否具有admin角色的权限："+subject.hasRole("admin"));//判断用户是否有admin角色
            System.out.println("是否具有admin和user角色的权限："+subject.hasAllRoles(Arrays.asList("admin","user")));
            //基于权限字符串的访问控制     资源标识符：操作：资源类型
            System.out.println("====================基于权限字符串的访问控制=====================");
            System.out.println("是否具有user模块的所有权限："+subject.isPermitted("user:*:*"));

        }
    }
}
```

```java
package org.example.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 自定义realm 加入MD5 + salt + hash散列
 */
public class CustomerMd5Realm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("=============开始授权===============");

        String primaryPrincipal = principals.getPrimaryPrincipal().toString();//主身份信息
        System.out.println("该用户的主身份信息：" + primaryPrincipal);

        //根据主身份信息（用户名）获取角色和权限信息（来自数据库）

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //给权限对象添加角色
        simpleAuthorizationInfo.addRoles(Arrays.asList("admin","user"));

        //权限信息给权限对象
        simpleAuthorizationInfo.addStringPermission("user:*:*");

        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("=============开始认证===============");
        //获取身份信息
        String principal = token.getPrincipal().toString();

        //根据用户名查询数据库
        if ("xiaochen".equals(principal)) {
            //参数二：MD5 + salt 后的密码
            //参数三：ByteSource.Util.bytes("dsdafd42./']") 设置随机salt
            /*return new SimpleAuthenticationInfo(principal,
                    "3feec11bf706e623217ddb0d547c5914",
                    ByteSource.Util.bytes("dsdafd42./']"),
                    this.getName());*/
            //参数二：MD5 + salt + hash散列1024次 后的密码
            //参数三：ByteSource.Util.bytes("dsdafd42./']") 设置随机salt
            return new SimpleAuthenticationInfo(principal,
                    "a3725de9ffb7a6a7fa0bd783b5b07c34",
                    ByteSource.Util.bytes("dsdafd42./']"),
                    this.getName());
        }

        return null;
    }
}
```

# shiro整合springboot

导入依赖：

```xml
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-spring-boot-starter</artifactId>
    <version>1.5.3</version>
</dependency>
```

```java
package com.lj.springboot_shiro.config;

import com.lj.springboot_shiro.shiro.realms.CustomerRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 用来整合shiro框架相关的配置类
 */
@Configuration
public class shiroConfig {

    //1.创建shiroFilter    负责拦截请求
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //给shiro设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //配置系统受限资源
        //配置系统公共资源
        Map<String, String> map = new HashMap<>();
        map.put("/login","anon");    //anon  设置成公共资源
        map.put("/**","authc");   //authc  请求这个资源需要认证和授权

        //默认认证（登录）界面路径（login.jsp）
        shiroFilterFactoryBean.setLoginUrl("/login");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    //2.创建web安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("myRealm") Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();

        //设置Realm
        defaultWebSecurityManager.setRealm(realm);

        return defaultWebSecurityManager;
    }

    //3.创建自定义realm
    @Bean(name = "myRealm")
    public Realm getRealm(){
        CustomerRealm customerRealm = new CustomerRealm();

        //修改凭证校验匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置md5加密算法
        credentialsMatcher.setHashAlgorithmName("MD5");
        //设置散列次数
        credentialsMatcher.setHashIterations(1024);
        customerRealm.setCredentialsMatcher(credentialsMatcher);
        
        return customerRealm;
    }
}
```

# CacheManager的使用

引入Shiro和ehcache的依赖

```xml
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-ehcache</artifactId>
    <version>1.5.3</version>
</dependency>
```

开启缓存

```java
//开启缓存管理
        customerRealm.setCacheManager(new EhCacheManager());
        customerRealm.setCachingEnabled(true);
        customerRealm.setAuthenticationCachingEnabled(true);//开启认证缓存
        customerRealm.setAuthorizationCachingEnabled(true);//开启授权缓存
```

# Redis缓存实现

引入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

配置redis:

```yaml
spring:
  redis:
    host: 127.0.0.1
    port: 6379
    database: 0
```

指定使用自定义的redis缓存管理器：

```java
//开启缓存管理
customerRealm.setCacheManager(new RedisCacheManager());
customerRealm.setCachingEnabled(true);//开启全局缓存
customerRealm.setAuthenticationCachingEnabled(true);//开启认证缓存
customerRealm.setAuthenticationCacheName("authenticationCache");
customerRealm.setAuthorizationCachingEnabled(true);//开启授权缓存
customerRealm.setAuthorizationCacheName("authorizationCache");
```

自定义shiro的redis缓存管理器：

```java
package com.lj.springboot_shiro.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

//自定义shiro的redis缓存管理器
public class RedisCacheManager implements CacheManager {
    @Override
    public <K, V> Cache<K, V> getCache(String cacheName) throws CacheException {

        return new RedisCache<K,V>(cacheName);
    }
}

```

自定义redis缓存的实现：

```java
package com.lj.springboot_shiro.shiro.cache;

import com.lj.springboot_shiro.Utils.ApplicationContextUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Collection;
import java.util.Set;

//自定义redis缓存的实现
public class RedisCache<K,V> implements Cache<K,V> {

//    @Autowired
//    private RedisTemplate redisTemplate;
    private String cacheName;

    public RedisCache() {
    }

    public RedisCache(String cacheName) {
        this.cacheName = cacheName;
    }

    @Override
    public V get(K k) throws CacheException {
        System.out.println("get=====>:"+k);
        if (k == null){
            return null;
        }else {
            return (V) getRedisTemplate().opsForHash().get(this.cacheName,k.toString());
        }
    }

    @Override
    public V put(K k, V v) throws CacheException {
        System.out.println("put=====>k:"+k+"=====>v:"+v);
        getRedisTemplate().opsForHash().put(this.cacheName,k.toString(),v);
        return null;
    }

    @Override
    public V remove(K k) throws CacheException {
        System.out.println("=============remove=============");
        return (V) getRedisTemplate().opsForHash().delete(this.cacheName,k.toString());
    }

    @Override
    public void clear() throws CacheException {
        getRedisTemplate().delete(this.cacheName);
    }

    @Override
    public int size() {
        return getRedisTemplate().opsForHash().size(this.cacheName).intValue();
    }

    @Override
    public Set<K> keys() {
        return getRedisTemplate().opsForHash().keys(this.cacheName);
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    private RedisTemplate getRedisTemplate(){
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtils.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());//设置redis的序列化方式
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
```

salt无法序列化的问题，自定义ByteSource类：

```java
package com.lj.springboot_shiro.shiro.salt;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.util.ByteSource;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

public class MyByteSource implements ByteSource,Serializable {
    private byte[] bytes;
    private String cachedHex;
    private String cachedBase64;

    public MyByteSource(){

    }

    public MyByteSource(byte[] bytes) {
        this.bytes = bytes;
    }

    public MyByteSource(char[] chars) {
        this.bytes = CodecSupport.toBytes(chars);
    }

    public MyByteSource(String string) {
        this.bytes = CodecSupport.toBytes(string);
    }

    public MyByteSource(ByteSource source) {
        this.bytes = source.getBytes();
    }

    public MyByteSource(File file) {
        this.bytes = (new MyByteSource.BytesHelper()).getBytes(file);
    }

    public MyByteSource(InputStream stream) {
        this.bytes = (new MyByteSource.BytesHelper()).getBytes(stream);
    }

    public static boolean isCompatible(Object o) {
        return o instanceof byte[] || o instanceof char[] || o instanceof String || o instanceof ByteSource || o instanceof File || o instanceof InputStream;
    }

    @Override
    public byte[] getBytes() {
        return this.bytes;
    }

    @Override
    public boolean isEmpty() {
        return this.bytes == null || this.bytes.length == 0;
    }

    @Override
    public String toHex() {
        if (this.cachedHex == null) {
            this.cachedHex = Hex.encodeToString(this.getBytes());
        }

        return this.cachedHex;
    }

    @Override
    public String toBase64() {
        if (this.cachedBase64 == null) {
            this.cachedBase64 = Base64.encodeToString(this.getBytes());
        }

        return this.cachedBase64;
    }

    @Override
    public String toString() {
        return this.toBase64();
    }

    @Override
    public int hashCode() {
        return this.bytes != null && this.bytes.length != 0 ? Arrays.hashCode(this.bytes) : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (o instanceof ByteSource) {
            ByteSource bs = (ByteSource)o;
            return Arrays.equals(this.getBytes(), bs.getBytes());
        } else {
            return false;
        }
    }

    private static final class BytesHelper extends CodecSupport {
        private BytesHelper() {
        }

        public byte[] getBytes(File file) {
            return this.toBytes(file);
        }

        public byte[] getBytes(InputStream stream) {
            return this.toBytes(stream);
        }
    }
}
```

ApplicationContextUtils工具类：

```java
package com.lj.springboot_shiro.Utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static Object getBean(String beanName){
        return context.getBean(beanName);
    }
}
```





# springboot+shiro+jwt

依赖：

```xml
<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-spring-boot-starter</artifactId>
    <version>1.5.3</version>
</dependency>

<dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-ehcache</artifactId>
    <version>1.5.3</version>
</dependency>

<dependency>
    <groupId>com.auth0</groupId>
    <artifactId>java-jwt</artifactId>
    <version>3.4.0</version>
</dependency>

<!--加入了这个注解用yaml给实体类赋值有提示-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>

<!--实体校验包-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

