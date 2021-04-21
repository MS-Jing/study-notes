**！！！ES6模块化无法在Node中运行，需要Babel转换成ES5**

01.js

```js
//直接使用 export暴露
export function add(a,b) {
    return a+b;
}
export function hello(a,b) {
    console.log("hello")
}

////简化
export default{
    add(a,b) {
    	return a+b;
	},
    hello(a,b) {
    	console.log("hello")
	}
}
```

02.js

```js
//调用01.js
import {add} from "./01"

console.log(add(1, 2));

//简化后
import m from "./01"
console.log(m.add(1, 2))
```

需要使用babel转换成es5后才能在node环境中运行