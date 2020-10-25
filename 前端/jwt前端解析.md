# jwt前端解析

​	当我们做前后端分离项目时，需要将jwt保存在前端，有时候需要将jwt中的数据解析出来，网上有很多用第三方组件的方式，但是js的原生方法就可以解决啊。

我们jwt 数据载体是使用的base64进行加密的，所以我们只需要对载体的字符串进行base64解码即可!

```javascript
var token = res.headers.authorization;  //在请求头中获取token
let strings = token.split("."); //截取token，获取载体
var userinfo = JSON.parse(decodeURIComponent(escape(window.atob(strings[1].replace(/-/g, "+").replace(/_/g, "/"))))); //解析，需要吧‘_’,'-'进行转换否则会无法解析

```

