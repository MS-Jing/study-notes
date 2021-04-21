01.js:
```js
const add = function (a,b) {
    return a+b;
}

//方法暴露
module.exports = {
    add,
}
```

02.js:

```js
//调用01.js
const m_01 = require("./01.js")
console.log(m_01.add(1, 2));
```

使用node环境运行