 OAuth 2.0 是目前最流行的授权机制，用来授权第三方应用，获取用户数据 



# 场景理解

我点了一份外卖，但是我的小区是有门禁的。小区保安需要有业主同意才会对外卖小哥放行。

+ 外卖小哥对门卫说：我是来给xxx栋xx单元xxx业主的xxx送外卖的
+ 门卫就会问他：你是哪个外卖平台（这个外卖平台必须是我们小区认可的）的叫什么名字，是只是送餐吗？
+ 外卖小哥：......
+ 门卫就会打电话问我:xxx业主你好，这里有个外卖小哥，xxx平台的，现在需要给你送餐，需要给他进入小区给你送餐的权限吗？
+ 我：嗯，是的！
+ 门卫就会给他一个通行证( access token 令牌)，比如这个通行证今天有效，比如我一会还想在这个平台买一杯奶茶，这个小哥就不需要再来授权了，直接给我送上来。门卫保安看到了这个令牌，也只会给他放行给我这户业主送餐的权限

简单来说就是，用户向系统授权第三方应用来访问我们的数据，系统生成一个有效时间的令牌给到第三方应用。第三方应用下次可以直接携带该令牌来访问用户授权的数据



# 四种授权方式

 不管哪一种授权方式，第三方应用申请令牌之前，都必须先到系统(例如微信)备案，说明自己的身份，然后会拿到两个身份识别码：客户端 ID（client ID）和客户端密钥（client secret）。这是为了防止令牌被滥用，没有备案过的第三方应用，是不会拿到令牌的。 

##  授权码(authorization code)方式

 这种方式是最常用的流程，安全性也最高，它适用于那些有后端的 Web 应用。授权码通过前端传送，令牌则是储存在后端，而且所有与资源服务器的通信都在后端完成。这样的前后端分离，可以避免令牌泄漏 

我们写了一个博客平台，现在想要实现微信扫码登录功能，我们先要去微信开发者平台备案。获取到客户端 ID（client ID）和客户端密钥（client secret）。然后当用户点击我们的微信登录时我们会跳转到微信的扫码登录界面，用户扫码授权后，我们在备案的时候会备案一个重定向的地址，微信会跳转这个地址然后拼接上授权码，然后我们的博客后台就收到这个授权码的时候，再携带授权码去请求微信平台的颁发令牌的接口



第一步：跳转微信的授权页面(扫码微信二维码授权登录)

```text
https://weiXing.com/oauth/authorize?
  response_type=code&
  client_id=CLIENT_ID&
  redirect_uri=CALLBACK_URL&
  scope=read
```

+ response_type 请求返回授权码
+ client_id 让微信知道是那个平台(以备案)的用户来授权的
+ redirect_uri 授权成功或失败的给用户跳转的地址
+ scope 需要的授权的数据范围

第二步：用户授权成功微信回调

```text
https://blog.com/callback?code=AUTHORIZATION_CODE
```

code就会给我们传回来

第三步：博客系统拿到授权码后，向微信请求令牌

```text
https://weiXing.com/oauth/token?
 client_id=CLIENT_ID&
 client_secret=CLIENT_SECRET&
 grant_type=authorization_code&
 code=AUTHORIZATION_CODE&
 redirect_uri=CALLBACK_URL
```

+ client_id 让微信知道是那个平台的
+ client_secret 备案平台的密钥
+ grant_type 是刚才重定向回来的授权码
+ redirect_uri 令牌颁发后的回调地址

然后微信平台认证了我们博客系统的身份和授权码后，就会给我们的回调地址返回令牌的相关信息

```json
{    
  "access_token":"ACCESS_TOKEN",  // 访问令牌
  "token_type":"bearer",
  "expires_in":2592000, //令牌有效期
  "refresh_token":"REFRESH_TOKEN", //令牌失效时用来刷新令牌的令牌
  "scope":"read",
  "uid":100101,
  "info":{...}
}
```

## 隐藏式 **implicit** 

 有些 Web 应用是纯前端应用，没有后端。这时就不能用授权码的方式了，必须将令牌储存在前端 

前端博客系统直接给微信平台发送请求令牌的请求

```text
https://weiXing.com/oauth/authorize?
  response_type=token&
  client_id=CLIENT_ID&
  redirect_uri=CALLBACK_URL&
  scope=read
```

response_type 直接请求token

用户跳转到微信扫码界面授权后直接重定向到redirect_uri的页面

```text
https://a.com/callback#token=ACCESS_TOKEN
```

 这种方式把令牌直接传给前端，是很不安全的。因此，只能用于一些安全要求不高的场景，并且令牌的有效期必须非常短，通常就是会话期间（session）有效，浏览器关掉，令牌就失效了 

## 密码式( **password** )

 **用户把用户名和密码，直接告诉第三方应用。该应用就使用你的密码，申请令牌** 

 这种方式需要用户给出自己的用户名/密码，显然风险很大，因此只适用于其他授权方式都无法采用的情况，而且必须是用户高度信任的应用。 

##  凭证式(client credentials)

 **适用于没有前端的命令行应用，即在命令行下请求令牌** 

直接通过后台给向微信请求令牌

```text
https://oauth.weiXing.com/token?
  grant_type=client_credentials&
  client_id=CLIENT_ID&
  client_secret=CLIENT_SECRET
```

+ grant_type 采用凭证的方式
+  client_id和client_secret让微信来确认第三方应用的身份

确认身份后直接返回令牌

 种方式给出的令牌，是针对第三方应用的，而不是针对用户的，即有可能多个用户共享同一个令牌。 