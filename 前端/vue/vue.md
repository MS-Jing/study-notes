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

