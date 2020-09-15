# 概述

Vue.js（读音 /vjuː/, 类似于 view） 是一套**构建用户界面的渐进式框架**。

Vue **只关注视图层**， 采用自底向上增量开发的设计。

Vue 的目标是通过尽可能简单的 API 实现响应的数据绑定和组合的视图组件。

Vue 学习起来非常简单

# vue基础

## 导入vue:

```html
<script src="https://cdn.jsdelivr.net/npm/vue"></script>
```

## 第一个vue程序

```html
<!DOCTYPE html>
<html lang="en" xmlns:v-bind="http://www.w3.org/1999/xhtml" xmlns:v-on="http://www.w3.org/1999/xhtml"
      xmlns:v-model="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://cdn.jsdelivr.net/npm/vue"></script>
</head>
<body>

    <div id="app">
        {{message}}
    </div>

    <script>
        var vm = new Vue({
            el: "#app",
            data:{
                message: "hello,word!",
            }
        })
    </script>

</body>
</html>
```

## V-bind指令

HTML 属性中的值应使用 v-bind 指令。

```html
<span v-bind:title="message">
    aaa
</span>
<script>
    var vm = new Vue({
        el: "#app",
        data:{
            message: "hello,word!",
        }
    })
</script>
```

## 判断

```html
<p v-if="ok">yes</p>
<p v-else>no</p>
{{ ok ? 'YES' : 'NO' }}

<h1 v-if="type==='A'">A</h1>
<h1 v-else-if="type==='B'">B</h1>
<h1 v-else>C</h1>

<script>
    var vm = new Vue({
        el: "#app",
        data:{
            ok: true,
            type: "A"
        }
    })
</script>
```

## 循环

```html
<!DOCTYPE html>
<html lang="en" xmlns:v-bind="http://www.w3.org/1999/xhtml" xmlns:v-on="http://www.w3.org/1999/xhtml"
      xmlns:v-model="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://cdn.jsdelivr.net/npm/vue"></script>
</head>
<body>

    <div id="app">
        <h1 v-for="(item,index) in items">{{item.name}}{{index}}</h1>
    </div>

    <script>
        var vm = new Vue({
            el: "#app",
            data:{
                items: [
                    {name: "luo"},
                    {name: "Jing"}
                    ]
            }
        })
    </script>

</body>
</html>
```

## 事件绑定(v-on 指令)

v-on 指令，它用于监听 DOM 事件

```html
<!DOCTYPE html>
<html lang="en" xmlns:v-bind="http://www.w3.org/1999/xhtml" xmlns:v-on="http://www.w3.org/1999/xhtml"
      xmlns:v-model="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://cdn.jsdelivr.net/npm/vue"></script>
</head>
<body>

    <div id="app">
        <span v-on:click="hell">点我试试！</span>
    </div>

    <script>
        var vm = new Vue({
            el: "#app",
            data:{
                message: "hello,word!"
            },
            methods: {
                hell: function (event) {
                    alert(this.message);
                }
            }
        })
    </script>

</body>
</html>
```

## 数据双向绑定（v-model指令）

vue是一个MVVM框架，数据双向绑定，当数据发生改变时视图也立即发生改变。视图改变数据也就改变。

```html
<!DOCTYPE html>
<html lang="en" xmlns:v-bind="http://www.w3.org/1999/xhtml" xmlns:v-on="http://www.w3.org/1999/xhtml"
      xmlns:v-model="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://cdn.jsdelivr.net/npm/vue"></script>
</head>
<body>

    <div id="app">

        <input type="text" v-model="message" value="qqq"/>{{message}}

        性别：<input type="radio" value="男" v-model="sex">男
        <input type="radio" value="女" v-model="sex">女
        {{sex}}

        <select v-model="selected">
            <option>A</option>
            <option>B</option>
            <option>C</option>
        </select>
        {{selected}}

    </div>

    <script>
        var vm = new Vue({
            el: "#app",
            data:{
                message: "hello,word!",
                selected: "A"
            }
        })
    </script>

</body>
</html>
```

## vue组件

```html
<!DOCTYPE html>
<html lang="en" xmlns:v-bind="http://www.w3.org/1999/xhtml" xmlns:v-on="http://www.w3.org/1999/xhtml"
      xmlns:v-model="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://cdn.jsdelivr.net/npm/vue"></script>
</head>
<body>

    <div id="app">
        <luo v-for="item in items" v-bind:value="item.name"></luo>
    </div>


    <script>

        Vue.component("luo",{
            props: ["value"],
            template: "<li>{{value}}</li>"
        })

        var vm = new Vue({
            el: "#app",
            data:{
                items: [
                    {name: "luo"},
                    {name: "Jing"}
                    ]
            }
        })

    </script>

</body>
</html>
```

# 异步通信（axios）

```html
<!DOCTYPE html>
<html lang="en" xmlns:v-bind="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="../js/vue.js"></script>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>

    <script src="../js/jQuery3.4.1.js"></script>
</head>
<body>

<div id="app">
    {{info}}
    <a v-bind:href="info.url">点我</a>
</div>

    <script>
        var vm = new Vue({
            el: "#app",
            data: {
                info: ""
            },
            data(){
                return {
                    info: {
                    }
                }
            },
            mounted(){//钩子函数
                axios.get("../data.json").then(resule=>(this.info=resule.data))
                /**
                 * ajax的方式也可以
                 */
                // $.ajax({
                //     url:"../data.json",
                //     dateType:"json",
                //     type:"get",
                //     success:function (data) {
                //         console.log(data);
                //         vm.info=data;
                //     }
                // })
            }

        });
    </script>
</body>
</html>
```

```json
{
  "name": "luojing",
  "url": "https://www.baidu.com/",
  "page": 1,
  "isNonprofit": true,
  "address": {
    "street": "含光门",
    "city": "陕西西安",
    "country": "中国"
  },
  "links": [
    {
      "name": "bilibili",
      "url": "https://space.bilibili.com"
    },
    {
      "name": "百度",
      "url": "https://www.baidu.com"
    }
  ]
}
```

# 计算属性

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Vue 测试实例 - 菜鸟教程(runoob.com)</title>
<script src="https://cdn.staticfile.org/vue/2.2.2/vue.min.js"></script>
</head>
<body>
<div id="app">
  <p>原始字符串: {{ message }}</p>
  <p>计算后反转字符串: {{ reversedMessage }}</p>
</div>

<script>
var vm = new Vue({
  el: '#app',
  data: {
    message: 'Runoob!'
  },
  computed: {
    // 计算属性的 getter
    reversedMessage: function () {
      // `this` 指向 vm 实例
      return this.message.split('').reverse().join('')
    }
  }
})
</script>
</body>
</html>
```

## computed vs methods

我们可以使用 methods 来替代 computed，效果上两个都是一样的，但是 computed 是基于它的依赖缓存，只有相关依赖发生改变时才会重新取值。而使用 methods ，在重新渲染的时候，函数总会重新调用执行。

可以说使用 computed 性能会更好，但是如果你不希望缓存，你可以使用 methods 属性。

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>计算属性练习</title>
    <script src="https://cdn.jsdelivr.net/npm/vue"></script>
</head>
<body>
    <div id="app">
        <input type="text" v-model="a">+<input type="text" v-model="b"> = {{x}}
    </div>

    <script>
        new Vue({
            el: "#app",
            data:{
                a:null,
                b:null
            },
            computed:{
                x: function () {
                    return Number(this.a)+Number(this.b)
                }
            }
        })
    </script>
</body>
</html>
```

# 插槽（slot）

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="../js/vue.js"></script>
</head>
<body>

<div id="app">

<todo>
    <my_title slot="todo_title" :title="title"></my_title>
    <my_context slot="todo_context" v-for="(item,index) in items" :item="item"
                @removes="removeitems(index)">

    </my_context>
</todo>

</div>

<script>

    Vue.component("todo",{
        template: "<div>" +
                        "<slot name='todo_title'></slot>" +
                        "<ul>" +
                        "<slot name='todo_context'></slot>" +
                        "</ul>" +
                "</div>"
    });

    Vue.component("my_title",{
        props: ['title'],
        template: "<p>{{title}}</p>"
    })

    Vue.component("my_context",{
        props: ["item"],
        template: "<li>{{item}} <button @click='remove()'>删除</button></li> ",
        methods: {//当前的组件只能绑定当前的事件
            remove: function () {
                //自定义事件分发
                this.$emit("removes");
            }
        }
    })

   var vm =  new Vue({
        el:"#app",
       data:{
        title: "我是标题",
           items: ["java","Vue","jquery"]
       },

       methods: {
            removeitems: function (index) {
                this.items.splice(index,1)//从index处删除一个元素
            }
       }

    });
</script>

</body>
</html>
```

# 第一个vue-cli项目

## vue-cli

vue-cli是光放提供的一个脚手架，用于快速生成的一个vue项目模板

**主要功能：**

+ 同一项目的目录结构
+ 本地调试
+ 热部署
+ 单元测试
+ 集成打包上线

### 环境搭建：

+ 下载安装nodejs
  + 在cmd模式下`node -v`查看版本号
+ node自带npm,`npm -v`查看版本号
  + 安装nodejs淘宝镜像加速器cnpm
  + `npm install -g cnpm --registry=https://registry.npm.taobao.org`         -g是全局安装
+ 设置npm的缓存下载位置和模块下载位置（可以略过）
  + 查看当前默认配置`npm config ls -l`
  + 配置缓存位置`npm config set cache "D:\environment\nodejs\npm-cache"`
  + 配置本地仓库`npm config set prefix "D:\environment\nodejs\npm_global"`
  + 注意修改了配置后`cnpm`不能用了，需要配置环境变量`%NODE_HOME%\node_global`
+ 安装vue-cli模块
  + `cnpm install vue-cli -g`
  + 测试是否安装成功，还可以查看可以基于哪些模块创建vue应用程序，通常我们使用webpack`vue list`

### npm命令解释

+ `npm install modeleName`:安装模块到项目目录下
+ `npm install -g module Name`:-g将模块安装到全局
+ `npm install --save moduleName`:--save是将模块安装到项目目录下，并在package文件的dependencies节点写入依赖，-s为该命令缩写
+ `npm install --save-dev moduleName`:--save-dev是将模块安装到项目目录下，并在package文件的devDependencies节点写入依赖，-D为该命令缩写

### 第一个vue应用程序

+ 创建一个基于webpack模板的vue应用程序
  
+ `vue init webpack myvue`
  
+ 初始化运行

  + ```bash
    cd myvue
    npm install 下载安装该项目相关依赖
    npm run dev 运行
    ```

# Webpack

webpack是现代js应用程序的静态模块打包器

**安装**

+ `npm install webpack -g`
+ `npm install webpack-cli -g`

+ 测试安装
  + `webpack -v`
  + `webpack-cli -v`

**使用：**

+ 创建项目（webpack-study）

+ 创建名为modules的目录，存放js模块等资源

+ modules下创建模块文件，hello.js。编写js模块相关代码

  + ```javascript
    //暴露一个方法
    exports.sayhello = function () {
        document.write("<h1>hello word</h1>")
    };
    ```

+ 创建main.js去引入hello模块

  + ```javascript
    //导入hello模块
    var hello = require("./hello");
    hello.sayhello();
    ```

+ **打包：**

  + 在项目根目录下创建`webpack.config.js`文件

    + ```javascript
      module.exports = {
        entry: "./modules/main.js",//程序入口
        output: {
            filename: "./js/bundle.js"
        }
      };
      ```

  + 打包命令：`webpack`

+ 测试，创建index.html引入bundle.js

  + ```html
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
    </head>
    <body>
        <script src="dist/js/bundle.js"></script>
    </body>
    </html>
    ```

# vue - router路由

## 安装

+ `npm install vue-router --save-dev`一定要在当前项目中安装

## 使用

在main.js中导入,导入的Router组件必须与插件同名

```javascript
import Vue from 'vue'
import App from './App'
import router from "./router/" //自动扫描里面的配置文件

Vue.config.productionTip = false

new Vue({
  el: '#app',
  //配置路由
  router,//Router必须与插件同名
  components: { App },
  template: '<App/>'
})
```

App.vue:

```Vue
<template>
  <div id="app">
<!--    <h1>主页</h1>-->
    <router-link to="/main">首页</router-link>
    <router-link to="/content">内容页</router-link>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>

```

router相关的文件：router/index.js:(表示router的主配置)

```javascript
import Vue from "vue";
import VueRouter from "vue-router";
import Content from "../components/Content";
import Main from "../components/Main";

//安装路由,声明使用该组件
Vue.use(VueRouter);

//设置路由导出
export default new VueRouter({
  routes: [
    {
      //路由路径
      path: "/content",
      name: "content",
      //跳转的组件
      component: Content
    },
    {
      path: "/main",
      name: "main",
      component: Main
    }
  ]
});
```

相关组件：

main.vue:

```vue
<template>
    <h1>我是首页</h1>
</template>

<script>
    export default {
        name: "Main"
    }
</script>

<style scoped>

</style>

```

Content.vue:

```Vue
<template>
    <h1>我是内容页</h1>
</template>

<script>
    export default {
        name: "Content"
    }
</script>

<style scoped>

</style>
```

# vue实例

结合ElementUI组件库

+ 新建项目：`vue init webpack hello-vue`
+ 安装相关依赖
  + `npm install vue-router --save-dev`安装vue-router路由
  + `cnpm i element-ui -s`
  + 安装依赖`cnpm install`
  + `cnpm install sass-loader node-sass --save-dev`安装sass加载器

+ 新建rooter，views文件夹，在views下创建Main.vue和Login.vue组件

  + ```Vue
    <template>
        <h1>首页</h1>
    </template>
    
    <script>
        export default {
            name: "Main"
        }
    </script>
    
    <style scoped>
    
    </style>
    
    ```

  + ```Vue
    <template>
        <div>
          <el-form ref="loginForm" :model="form" :rules="rules" label-width="80px" class="login-box">
            <h3 class="login-title">欢迎登陆</h3>
            <el-form-item label="账号" prop="username">
              <el-input type="text" placeholder="请输入账号" v-model="form.username"/>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input type="password" placeholder="请输入密码" v-model="form.password"/>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" v-on:click="onSubmit('loginForm')">登陆</el-button>
            </el-form-item>
          </el-form>
    
          <el-dialog
            title="温馨提示"
            :visible.sync="dialogVisible"
            width="30%"
            :before-close="handleClose">
            <span>请输入账号和密码</span>
            <span slot="footer" class="dialog-footer">
              <el-button type="primary" @click="dialogVisible = false">确定</el-button>
            </span>
          </el-dialog>
        </div>
    </template>
    
    <script>
        export default {
            name: "Login",
            data(){
              return{
                form: {
                  username: '',
                  password: ''
                },
    
                //表单验证，需要在el-form-item 元素中增加prop属性
                rules: {
                  username: [
                    {required: true,message: "账号不能为空", trigger: 'blur'}
                  ],
                  password:[
                    {required: true,message: "密码不能为空", trigger: 'blur'}
                  ]
                },
    
                //对话框显示和隐藏
                dialogVisible: false
              }
            },
            methods: {
              onSubmit(formName){
                //为表单绑定验证功能
                this.$refs[formName].validate((valid) => {
                  if (valid) {
                    //使用vue-router 路由到指定页面，该方式称为编程式导航
                    this.$router.push("/main");
                  }else{
                    this.dialogVisible = true;
                    return false;
                  }
                });
              }
            }
        }
    </script>
    
    <style scoped>
      .login-box {
        border: 1px solid #DCDFE6;
        width: 350px;
        margin: 180px auto;
        padding: 35px 35px 15px 35px;
        border-radius: 5px;
        -webkit-border-radius: 5px;
        box-shadow: 0 0 25px #909399;
      }
    
      .login-title{
        text-align: center;
        margin: 0 auto 40px auto;
        color: #303133;
      }
    </style>
    ```

+ 将组件配到路由里

  + ```javascript
    import Vue from "vue";
    import VueRouter from "vue-router";
    import Main from "../views/Main";
    import Login from "../views/Login"
    
    //安装路由,声明使用该组件
    Vue.use(VueRouter);
    
    //设置路由导出
    export default new VueRouter({
      mode: 'history',  //去掉url中的#
      routes: [
        {
          //路由路径
          path: "/main",
          name: "Main",
          //跳转的组件
          component: Main
        },
        {
          path: "/login",
          name: "Login",
          component: Login
        }
      ]
    });
    ```

+ main.js应用路由和Elementui组件

  + ```javascript
    import Vue from 'vue'
    import App from './App'
    
    import router from './router'
    import Element from 'element-ui'
    import 'element-ui/lib/theme-chalk/index.css';
    
    Vue.use(Element);
    
    /* eslint-disable no-new */
    new Vue({
      el: '#app',
      router,
      render: h => h(App)
    })
    ```

# 路由嵌套

​	又称子路由，在实际应用中，通常由多层嵌套的组件组合而成。同样的，URL中各段动态路径也按照某种结构对应嵌套的各层组件

+ 在views下创建user文件夹

  + 创建Profile组件

  + ```vue
    <template>
        <h1>个人信息</h1>
    </template>
    
    <script>
        export default {
            name: "Profile"
        }
    </script>
    
    <style scoped>
    
    </style>
    ```

  + 创建List组件

  + ```Vue
    <template>
        <h1>用户列表</h1>
    </template>
    
    <script>
        export default {
            name: "List"
        }
    </script>
    
    <style scoped>
    
    </style>
    ```

+ Main组件

  + ```Vue
    <template>
        <div>
          <el-container>
            <el-aside width="200px">
              <el-menu :default-openeds="['1']">
                <el-submenu index="1">
                  <template slot="title"><i class="el-icon-caret-right"></i>用户管理</template>
                  <el-menu-item-group>
                    <el-menu-item index="1-1">
                      <router-link to="/user/profile">个人信息</router-link>
                    </el-menu-item>
                    <el-menu-item index="1-2">
                      <router-link to="/user/profile">用户列表</router-link>
                    </el-menu-item>
                  </el-menu-item-group>
                </el-submenu>
                <el-submenu index="2">
                  <template slot="title"><i class="el-icon-caret-right"></i>内容管理</template>
                  <el-menu-item-group>
                    <el-menu-item index="2-1">分类管理</el-menu-item>
                    <el-menu-item index="2-2">内容列表</el-menu-item>
                  </el-menu-item-group>
                  </el-submenu>
              </el-menu>
            </el-aside>
    
            <el-container>
              <el-header style="text-align: right;font-size: 12px">
                <el-dropdown>
                  <i class="el-icon-setting" style="margin-right: 15px"></i>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item>个人信息</el-dropdown-item>
                    <el-dropdown-item>退出登录</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </el-header>
    
              <el-main>
                <router-view/>
              </el-main>
            </el-container>
          </el-container>
        </div>
    </template>
    
    <script>
        export default {
            name: "Main"
        }
    </script>
    
    <style scoped lang="scss">
      .el-header{
       background-color: #B3C0D1;
        color: #333;
        line-height: 60px;
      }
    
      .el-aside{
         color: #333333;
      }
    </style>
    
    ```

+ 路由：

  + ```javascript
    import Vue from "vue";
    import VueRouter from "vue-router";
    import Main from "../views/Main";
    import Login from "../views/Login";
    import Profile from "../views/user/Profile";
    import List from "../views/user/List";
    
    //安装路由,声明使用该组件
    Vue.use(VueRouter);
    
    //设置路由导出
    export default new VueRouter({
      mode: 'history',  //去掉url中的#
      routes: [
        {
          //路由路径
          path: "/main",
          name: "Main",
          //跳转的组件
          component: Main,
          //嵌套路由
          children:[
            {path:"/user/profile",component:Profile},
            {path:"/user/list",component:List}
          ]
        },
        {
          path: "/login",
          name: "Login",
          component: Login
        }
      ]
    });
    ```

# 参数传递及重定向

## 参数传递

Main.Vue修改代码：

```Vue
<!--这里传值需要v-bind绑定,name替换成路由的name-->
<router-link :to="{name:'profile',params:{id: 1}}">个人信息</router-link>
```

路由：

```js
children:[
        {path:"/user/profile/:id",name:"profile",component:Profile},
        {path:"/user/list",component:List}
      ]
```

profile组件：

```Vue
<template>

  <div>
    <!--务必要在一个根节点下-->
    <h1>个人信息</h1>
    <!-- 通过路由去获取id -->
    {{$route.params.id}}
  </div>

</template>

<script>
    export default {
        name: "Profile"
    }
</script>

<style scoped>

</style>

```

**还可以通过props传参**

路由：

```javascript
{path:"/user/profile/:id",name:"profile",component:Profile,props: true},
```

profile:

```Vue
<template>

  <div>
    <!--务必要在一个根节点下-->
    <h1>个人信息</h1>
    <!-- 通过路由去获取id -->
    {{$route.params.id}}
    {{id}}
  </div>

</template>

<script>
    export default {
        props:['id'],
        name: "Profile"
    }
</script>

<style scoped>

</style>
```

## 重定向

路由：

```javascript
{
      path: "/gohome",
      redirect: "/main"
    },
    {
      path:"/",
      redirect:"/main"
    }
```

# 404

Notfount组件：

```Vue
<template>
    <div>
      404
    </div>
</template>

<script>
    export default {
        name: "Notfount"
    }
</script>

<style scoped>

</style>
```

路由：

```Vue
{
      path: "*",
      component: Notfount
    }
```

# 路由钩子

`beforeRouteEnter`:进入路由之前执行

`beforeRouteLeave`:离开路由之前执行

profile组件:

```Vue
<template>

  <div>
    <!--务必要在一个根节点下-->
    <h1>个人信息</h1>
    <!-- 通过路由去获取id -->
    {{$route.params.id}}
    {{id}}
  </div>

</template>

<script>
    export default {
        props:['id'],
        name: "Profile",
      //to, from, next相当于过滤器
        beforeRouteEnter:(to, from, next)=>{
          console.log("进入路由之前");
          next();
        },
        beforeRouteLeave:(to, from, next)=>{
          console.log("离开路由之前");
          next();
        }
    }
</script>

<style scoped>

</style>
```

## 参数说明：

+ to:路由将要跳转的路径信息
+ from:路由跳转前的信息
+ next：路由控制参数
  + next():跳入下一个页面
  + next('/path')改变路由的跳转方向，可以跳到另一个路由
  + next(false)返回原来的页面
  + next((vm)=>{})仅在`beforeRouteEnter`中可用，vm为组件实例

## 异步请求

+ 安装axios`cnpm install --save axios vue-axios`

+ main.js引入axios

  + ```Vue
    import axios from 'axios'
    import VueAxios from 'vue-axios'
    
    Vue.use(VueAxios, axios)
    ```

+ 在statis/mock下放入data.json一定要在static下

+ profile组件

  + ```Vue
    <template>
    
      <div>
        <!--务必要在一个根节点下-->
        <h1>个人信息</h1>
        <!-- 通过路由去获取id -->
        {{$route.params.id}}
        {{id}}
      </div>
    
    </template>
    
    <script>
        export default {
            props:['id'],
            name: "Profile",
            info:'',
          //to, from, next相当于过滤器
            beforeRouteEnter:(to, from, next)=>{
              console.log("进入路由之前");
              next(vm => {
                vm.getData();//在进入路由之前执行方法
              });
            },
            beforeRouteLeave:(to, from, next)=>{
              console.log("离开路由之前");
              next();
            },
          methods: {
              getData: function () {
                this.axios({
                  method: "get",
                  url: "http://localhost:8080/static/mock/data.json"
                }).then(resule=>(this.info=resule.data))
                // this.axios.get("http://localhost:8080/static/mock/data.json").then(resule=>(this.info=resule.data))
              }
          }
        }
    </script>
    
    <style scoped>
    
    </style>
    ```




# vue脚手架项目打包部署

```
#在项目目录下执行
npm run build
#生成dist目录
```

