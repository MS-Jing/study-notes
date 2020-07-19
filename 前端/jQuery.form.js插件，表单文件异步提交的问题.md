# jQuery.form.js插件，表单文件异步提交的问题

在我们想要对表单做异步提交是使用jQuery的ajax传递会把表单元素序列化然后当做data参数传递，但是当我们表单里有文件元素`<input type="file"/>`时文件是不能被序列化的，后端得不到数据的。怎么又要异步提交又可以解决无法提交文件的办法呢?

> jQuery.form.js插件

jquery form是一个表单异步提交的插件，可以很容易提交表单，设置表单提交的参数，并在表单提交前对表单数据进行校验和处理和表单提交后的回调函数。

这个插件有两个主要方法：ajaxForm() 和 ajaxSubmit()

---

这里只介绍ajaxFrom():

先定义一个js对象：

```js
var options = {
    url: "/day09/jqueryFormServlet", //提交地址：默认是form的action,如果申明,则会覆盖
    type: "post",   //默认是form的method（get or post），如果申明，则会覆盖
    beforeSubmit: beforeCheck, //提交前的回调函数
    success: successfun,  //提交成功后的回调函数
    target: "#output",  //把服务器返回的内容放入id为output的元素中
    dataType: "json", //html(默认), xml, script, json...接受服务端返回的类型
    clearForm: true,  //成功提交后，是否清除所有表单元素的值
    resetForm: true,  //成功提交后，是否重置所有表单元素的值
    timeout: 3000     //限制请求的时间，当请求大于3秒后，跳出请求
};

————————————————
版权声明：本文为CSDN博主「某知名砖家」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/m0_37505854/java/article/details/79639046
```

执行ajaxFrom:

```js
$('#form1').ajaxForm(options)
```

这时在后端就可以得到数据和文件了

