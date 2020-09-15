



# JWT工具类

引入依赖：

```xml
<dependency>
    <groupId>com.auth0</groupId>
    <artifactId>java-jwt</artifactId>
    <version>3.4.0</version>
</dependency>
```

```java
package com.lj.blog;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.swing.*;
import java.util.Calendar;
import java.util.Map;

public class JWTUtils {

    private static final String SING = "";

    /**
     * 生成Token
     */
    public static String getToken(Map<String,String> map){
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,7);//设置过期时间为7天

        JWTCreator.Builder builder = JWT.create();
        //payload
        map.forEach((K,V)->{
            builder.withClaim(K,V);
        });

        String token = builder.withExpiresAt(instance.getTime())//指定令牌过期时间
                .sign(Algorithm.HMAC256(SING));//签名

        return token;
    }

    /**
     * 验证token
     * 签名有错会抛出异常
     */
    public static void verify(String token){
        JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }

    /**
     * 获取token中的信息
     */
    public static DecodedJWT getTokenInfo(String token){
        return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }
}

```

