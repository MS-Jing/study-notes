# let 声明变量

+ 变量不得重复声明

  + ```javascript
    let a = "a";
    let a = 'b';
    // Uncaught SyntaxError: Identifier 'a' has already been declared
    ```

+ 块级作用域

+ 不存在变量提升      变量声明之前不许使用变量

+ 不影响作用域链

# const 定义常量

+ 一定要赋初值
+ 常量值不得修改
+ 块级作用域

# 变量解构赋值

+ ES6 允许按照一定模式从数组和对象中提取值，对变量进行赋值，该过程叫解构赋值

+ ```javascript
  //数组的解构
  let a = ['b','c','d'];
  let [b,c,d] = a;
  console.log(b,c,d)
  //对象解构
  let person = {
      name: "xiaoming",
      age:18,
      sayhello:function (){
          console.log("hello")
      }
  }
  let {name,age,sayhello} = person     //属性和方法名一定要一一对应
  console.log(name,age,sayhello)
  ```

# 模板字符串

+ ```javascript
  let str =  `string`;
  console.log(str)
  ```

+ 内容中可以直接出现换行符  格式可以很简洁

+ 变量拼接

  + ```javascript
    let str =  `string`;
    console.log(str)
    let hello = `hello ${str}`
    console.log(hello)
    ```

# 对象简化写法

+ ES6 允许在大括号里面，直接写入变量和函数，作为对象的属性和方法

+ ```javascript
  let name = '小明';
  let sayHello = function (){
      console.log("hello")
  }
  const person = {
      name,sayHello,
      word(){  //可以直接方法名声明方法
          console.log("word")
      }
  }
  console.log(person)
  ```

# 箭头函数

+ ```javascript
  let hello = () => {
      console.log("hello")
  }
  
  let person = {
      word: () => {
          console.log("word")
      }
  }
  ```

+ this 是静态的 this始终指向函数声明时所在作用域下的this的值

+ 不能作为构造函数实例化对象

+ 不能使用arguments变量

# 函数参数默认值

+ 允许给函数参数赋默认值

+ ```javascript
  add = function (a=0,b=0) {
      return a+b;
  }
  console.log(add(1))
  ```

+ 可以和解构一起使用

  + ```javascript
    let connect = function ({username,password,port,hostname}) {
        console.log(hostname,username,password,port)
    }
    let mysql = {
        hostname:'127.0.0.1',
        username:'root',
        password:'root',
        port:3306
    }
    connect(mysql)
    ```

# rest 参数，用于获取函数的实参，用来代替arguments

```javascript
data = function (...args){
    //相比于 arguments  rest是以数组形式保存的
    console.log(args)
}
data('a',1,"bc")
```

+ rest 参数必须放到参数最后

# 扩展运算符

+ `...` 扩展运算符能将「数组」转换为逗号分隔的「参数序列」

```javascript
let a = [1,2,3]
let b = [4,5,6]
let c = [...a,...b]
console.log(c)
```

# Symbol

+ 引入的一个新的原始数据类型 表示独一无二的
+ 值唯一
+ 值不能和其他数据类型运算
+ 定义的对象属性不能使用for...in 循环遍历，可以使用Reflect.ownKeys来获取对象的所有键名

```javascript
let s = Symbol("aaa")
let s1 = Symbol("aaa")
console.log(s===s1) //false
let b = Symbol.for("bbb")
let b1 = Symbol.for("bbb")
console.log(b===b1) //true
```

# 迭代器

​		迭代器(Iterator)是-种接口,为各种不同的数据结构提供统一的访问机制。任何数据结构只要部署Iterator 接口，就可以完成遍历操作。

```javascript
const a = ['a','b','c','d']

for (let v of a){
    console.log(v)
}
```

# Promise

​	引入的异步编程的新解决方案

```javascript
const o = new Promise((resolve, reject) => {
    setTimeout(() => {
        console.log("aaaaa")
        // resolve("aaaaa")    //调用该方法表示promise成功，可调用then（）方法
        reject("bbb")   //拒绝，调用catch方法
    }, 1000)
});
o.then(data => {
    console.log(data)
})
o.catch(res => {
    console.log(res)
})

//链式调用
new Promise((resolve, reject) => {
    //请求用户信息
    console.log("开始请求用户信息")
    // resolve("请求到用户信息")
    reject("用户信息请求失败")
}).then(data => {
    console.log(data)
    return new Promise((resolve, reject) => {
        //请求用户订单
        console.log("开始请求用户订单")
        resolve("请求到用户订单")
        // reject("用户订单请求失败")
    })
}).then(data => {
    console.log(data)
    //请求用户购物车商品
    console.log("开始请求用户购物车商品")
    console.log("请求到用户购物车商品")
}).catch(reason => {
    console.error(reason)
})
```

# Set

ES6提供了新的数据结构Set (集合)。它类似于数组，但成员的值都是唯一的，集合实现了iterator 接口,所以可以使用[扩展运算符」for ...of....进行遍历

+ size 返回元素个数
+ add 向当前集合添加新元素
+ delete 删除元素
+ has 是否有某个元素

```javascript
let s1 = new Set(['a','b','c','b'])
console.log(s1)
console.log(s1.size)
console.log(s1.add('d'))
console.log(s1.has('c'))
console.log(s1.delete('a'))
console.log(s1)
```

# Map

ES6提供了Map 数据结构。它类似于对象，也是键值对的集合。但是“键”
的范围不限于字符串，各种类型的值(包括对象)都可以当作键。Map也实现
了iterator 接口，所以可以使用[扩展运算符」和for...of..进行遍历.

+ size 元素个数
+ set 新增元素
+ get 返回键值
+ has 判断包含元素
+ clear 清空

# class类

​		ES6提供了更接近传统语言的写法，引入了Class (类)这个概念，作为对
象的模板。通过class关键字，可以定义类。基本上，ES6的class可以看作只是一个语法糖，它的绝大部分功能，ES5都可以做到，新的class写法只是让对象原型的写法更加清晰、更像面向对象编程的语法而己。

+ class 声明类
+ constructor 继承父类
+ extends 继承父类
+ super 调用父级构造方法
+ static 定义静态方法和属性
+ 父类方法可以重写

```javascript
class Person{

    static era = "石器时代"  //时代

constructor(name,age) {
    this.name = name;
    this.age = age;
}

sayhello(){
    console.log("hello")
}
}
let person = new Person("xiaoming",18);
console.log(person)
person.sayhello()
console.log(person.era)   //实例对象无法访问到类静态方法和属性
console.log(Person.era)
```

# 数值拓展

+ Number.EPSILON 表示最小精度
+ Number.isFinite 测试一个数是否为有限数
+ Number.isNaN 测试一个数值是否为NaN
+ Number.parseInt,Number.parseFloat 字符串转换
+ Number.isInteger 判断是否为整数
+ Math.trunc 将小数部分舍去
+ Math.sign 判断一个数是正数，负数，还是零

# ES6模块化语法

+ export 命令用于规定模块的对外接口
+ import 命令用于输入其他模块提供的功能