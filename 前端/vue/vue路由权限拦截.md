# vue路由权限拦截

​		在router做页面路由时，有一些路由需要用户登录才可以访问，那么路由拦截就起到了作用了！

> **在需要登录的路由加上meta**

```js
{
    path: '/blog/add',
        name: 'BlogAdd',
            component: BlogEdit,
                meta:{
                    requireAuth:true
                }
},
```

> **在项目目录下创建permission.js文件**

```js
import router from './router'
import Element from 'element-ui';

//路由判断是否需要登录权限
router.beforeEach((to, from, next) => {
  //判断要去的路由是否需要登录权限
  if (to.matched.some(record => record.meta.requireAuth)) {
    const token = localStorage.getItem('token')//获取当前用户token

    //当前用户是否登录
    if(token){
      next()
    }else {
      Element.Message.error("请先登录");
      next({
        path:'/login'
      })
    }
  }else {
    next()
  }

})

```

> **main.js引入permission.js文件即可**

```js
import './permission'
```

