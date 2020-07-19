# vue脚手架中使用axios

axios在html页面中使用直接在页面引入js文件即可。那么在vue-cli中怎么使用呢？

> 创建vue项目，在项目中下载安装axios

```bash
npm install axios --save-dev
```

## 方法一：直接在组件中引入使用

```html
<script>
  # 导入axios组件
  import axios from 'axios'
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      aaa: "aaa"
    }
  },
    # 在该模板被创建时请求后端数据接口
  created() {
    //请求接口
    axios.get("http://localhost:8081/text").then((res)=>{
      this.aaa = res
    })
  }

}
</script>
```

## 方法二：将vue的原型属性替换

在main.js中：

```js
// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios' # 在vue中引入该组件

Vue.prototype.$http=axios	# 替换原属性
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
})
```

模板请求：

```html
<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      aaa: "aaa"
    }
  },
  created() {
      # 注意在组件这样请求
    this.$http.get("http://localhost:8081/text").then((res)=>{
      this.aaa = res
    })
  }

}
</script>
```

