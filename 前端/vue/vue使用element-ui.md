注：目前使用的是vue2.x版本

## 安装依赖

```bash
npm i element-ui -S
```

## Vue使用

main.js:

```javascript
import Vue from 'vue'
import App from './App'
import router from './router'

// ElementUI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI)

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
```

这样就可以全局使用了