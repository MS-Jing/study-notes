# Webpack

webpack是一个前端资源加载/打包工具。它将根据模块的依赖关系进行静态分析,然后将这些模块按照指定的规则生成对应的静态资源

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
      const path = require("path");//node 内置模块
      module.exports = {
        entry: "./modules/main.js",//程序入口
        output: {
            path: path.resolve(__dirname,'./dist'),//输出路径，__dirname:当前文件所在的路径
            filename: "bundle.js"
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