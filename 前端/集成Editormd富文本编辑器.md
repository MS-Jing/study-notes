## 介绍

​		Markdown越来越受广大程序员喜爱。所见即所得。我们平时写的博客和文章都是用Markdown编辑的。那么我们如何在我们的个人网站去使用呢？

**Editor.md：** 是一款开源的、可嵌入的 Markdown 在线编辑器（组件），基于 CodeMirror、jQuery 和 Marked 构建。

## 使用

#### 下载

[Github download](https://github.com/pandao/editor.md/archive/master.zip)

下载完成后目录下有一个examples目录。里面都是一些实例源码。可以对照使用！

#### 引入相关静态资源

+ 在static文件下创建lib目录再在此下面创建editormd文件

+ 将下载的editor.md目录下的css,fonts,images,languages,lib,plugins,editormd.js,editormd.min.js放入到创建的editormd目录下

+ ` <link href="/lib/editormd/css/editormd.min.css" rel="stylesheet" type="text/css">`

+ 先引入jQuery再引入editormd.js

  + ```html
    <script src="/js/jquery.min.js" type="text/javascript"></script>
    <script src="/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="/lib/editormd/editormd.min.js" type="text/javascript"></script>
    ```

#### 一个简单的模板

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="/css/editormd.min.css" rel="stylesheet" type="text/css">
</head>
<body>
    
<div id="editormd">
    <textarea style="display:none;"></textarea>
</div>


<script src="/js/jquery.min.js" type="text/javascript"></script>
<script src="/js/editormd.js" type="text/javascript"></script>

<script type="text/javascript">
    var testEditor;

    $(function() {
        testEditor = editormd("editormd", {
            placeholder : "请编辑！支持Markdown语法",
            width   : "90%",
            height  : 640,
            syncScrolling : "single",
            path    : "/lib/editormd/lib/",
            emoji : true,
            saveHTMLToTextarea : true,
            tocm : true, // Using [TOCM]
            tex : true, // 开启科学公式TeX语言支持，默认关闭
            flowChart : true // 开启流程图支持，默认关闭

        });
    });
</script>
</body>
</html>
```

#### 将编辑的内容传递给后端

​		Markdown模板我们已经做出来了，测试后也可以当Markdown编辑器使用。但是网页一关就没有了，如何把我们编辑好的博客文章传递到后台呢？这里使用了bootstrap做界面的布局样式，

注意：

+ 标题的input和 textarea标签的name我们要和后台的属性相匹配.好用于绑定数据
+ 我们通过js将编辑器的textarea标签内容保存到表单中，再有ajax传输到后台
+ 传输时表单要系列化`$("#formBlog").serialize()`

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="/lib/editormd/css/editormd.min.css" rel="stylesheet" type="text/css">
    <link href="/css/bootstrap.min.css" rel="stylesheet" type="text/css">
</head>
<body>

<div class="card m-1">
    <div class="card-header font-weight-bold">博客编辑</div>
    <div class="card-body">
        <div class="container-fluid">
            <form id="formBlog">
                <div class="input-group input-group-sm ml-auto mb-2 mr-auto" style="width: 90%">

                    <div class="input-group-prepend">
                        <span class="input-group-text">标题</span>
                    </div>
                    <input type="text" name="title" id="title" class="form-control">

                    <div class="input-group-prepend">
                        <button class="btn btn-sm btn-success" type="button" id="commit">提交</button>
                    </div>

                    <textarea id="blog_md" name="content" value="" style="display: none;"></textarea>
                </div>
            </form>

            <div id="editormd">
                <textarea style="display:none;"></textarea>
            </div>

        </div>
    </div>


    <div class="card-footer"></div>
</div>


<script src="/js/jquery.min.js" type="text/javascript"></script>
<script src="/js/bootstrap.min.js" type="text/javascript"></script>
<script src="/lib/editormd/editormd.min.js" type="text/javascript"></script>

<script type="text/javascript">
    var testEditor;

    $(function() {
        testEditor = editormd("editormd", {
            placeholder : "请编辑！支持Markdown语法",
            width   : "90%",
            height  : 640,
            syncScrolling : "single",
            path    : "/lib/editormd/lib/",
            emoji : true,
            saveHTMLToTextarea : true,
            tocm : true, // Using [TOCM]
            tex : true, // 开启科学公式TeX语言支持，默认关闭
            flowChart : true // 开启流程图支持，默认关闭

        });
    });

    $("#commit").click(function () {
        if ($("#title").val()==""){
            alert("标题不能为空！");
            return;
        }
        //得到Markdown写的文件的html文件给表单的多行文本框
        //然后保存到后端数据库
        $("#blog_md").val(testEditor.getHTML());
        console.log($("#formBlog").serialize());

        $.ajax({
            url: "/admin/article/addArticle",
            type: "POST",
            data: $("#formBlog").serialize(),
            success: function (result) {
                if (result=="ok"){
                    alert("添加成功");
                    window.location.reload();
                }else {
                    alert(result);
                }
            }
        });

    });
</script>
</body>
</html>
```

## 页面显示

前面我们已经可以获取到Markdown写的html文件，但是我们怎么将他按照Markdown的格式显示出来呢？

> typo.css

[github](https://github.com/sofish/typo.css)

将typo.css引入到项目中，将Markdown的html写入到一个div中并加上.typo类即可显示出Markdown的格式了