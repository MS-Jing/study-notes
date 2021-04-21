# 作用

​		用来将ES6语法编写的代码转换成ES5语法来兼容浏览器

# 安装

```bash
npm install -g babel-cli

#查看版本
babel --version
```



# 使用

```javascript
//转码前
let input = [1,2,3]
//将每一个元素加一，箭头函数是ES6语法
input = input.map(itme=>{
    return itme + 1;
})
console.log(input)
```

在项目根目录下创建`.babelrc`文件

```json
{
   //转码规则 es5
  "presets": ["es2015"],
  "plugins": []
}
```

在项目中安装转码器

`npm install --save-dev babel-preset-es2015`

转换

```bash
# 转换单个文件  -o 指定输出文件（--out-file）
babel es6.js -o dist/es5.js
#转换整个目录 -d 参数指定输出目录（--out-dir）
babel src -d dist
```



# 结果

```javascript
//转码后
var input = [1, 2, 3];
//将每一个元素加一，箭头函数是ES6语法
input = input.map(function (itme) {
    return itme + 1;
});
console.log(input);
```

