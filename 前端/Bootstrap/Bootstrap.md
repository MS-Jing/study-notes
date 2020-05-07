# 简介

Bootstrap 是全球最受欢迎的前端组件库，用于开发**响应式布局**、移动设备优先的 WEB 项目。

Bootstrap 是一个用于 HTML、CSS 和 JS 开发的**开源工具包**。利用 Bootstrap 提供的 **Sass 变量**和**混合（mixins）**、**响应式栅格系统**、**可扩展的预制组件以及强大的 jQuery 插件**，能够让你快速地开发出产品原型或构建整个 app。

# 快速入门

## 引入css

将 Bootstrap 的 CSS 文件放置在所有其它样式表之前就行了。

```html
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
```

## JS文件

Bootstrap 所提供的许多组件都依赖 JavaScript 才能运行。具体来说，这些组件都依赖 [jQuery](https://jquery.com/)、[Popper.js](https://popper.js.org/) 以及Bootstrap自己的 JavaScript 插件。他们之间的顺序是： jQuery 必须排在第一位，然后是 Popper.js，最后是我们的 JavaScript 插件。

```html
<script src="https://cdn.jsdelivr.net/npm/jquery@3.4.1/dist/jquery.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
```

## hello word

```html
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

    <title>Hello, world!</title>
  </head>
  <body>
    <h1>Hello, world!</h1>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.4.1/dist/jquery.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
  </body>
</html>
```

## 响应式 meta 标签

Bootstrap 天生就是 *移动设备优先* 的，依照这一策略，我们首先为移动设备优化代码，然后根据需要，基于 CSS 媒体查询来对组件进行缩小或放大。为了确保所有设备能够正确渲染和触摸缩放，**请将响应式 viewport meta 标签** 添加到 `<head>` 标签中。

```html
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
```

## 容器类

Bootstrap 4 需要一个容器元素来包裹网站的内容。

我们可以使用以下两个容器类：

- .container 类用于固定宽度并支持响应式布局的容器。
- .container-fluid 类用于 100% 宽度，占据全部视口（viewport）的容器。

# 网格系统

Bootstrap 提供了一套响应式、移动设备优先的流式网格系统，随着屏幕或视口（viewport）尺寸的增加，系统会自动**分为最多 12 列**。

Bootstrap 4 的网格系统是响应式的，列会根据屏幕大小自动重新排列。

## 网格类

Bootstrap 4 网格系统有以下 5 个类:

- .col- 针对所有设备
- .col-sm- 平板 - 屏幕宽度等于或大于 576px
- .col-md- 桌面显示器 - 屏幕宽度等于或大于 768px)
- .col-lg- 大桌面显示器 - 屏幕宽度等于或大于 992px)
- .col-xl- 超大桌面显示器 - 屏幕宽度等于或大于 1200px)

## 网格系统规则

Bootstrap4 网格系统规则:

- 网格每一行需要放在设置了 `.container` (固定宽度) 或 `.container-fluid` (全屏宽度) 类的容器中，这样就可以自动设置一些外边距与内边距。
- 使用行`.row`来创建水平的列组。
- 内容需要放置在列中，并且只有列可以是行的直接子节点。
- 预定义的类如 **.row** 和 **.col-sm-4** 可用于快速制作网格布局。
- 列通过填充创建列内容之间的间隙。 这个间隙是通过 **.rows** 类上的负边距设置第一行和最后一列的偏移。
- **网格列是通过跨越指定的 12 个列来创建**。 例如，设置三个相等的列，需要使用用三个**.col-sm-4** 来设置。
- Bootstrap 3 和 Bootstrap 4 最大的区别在于 Bootstrap 4 现在使用 flexbox（弹性盒子） 而不是浮动。 Flexbox 的一大优势是，没有指定宽度的网格列将自动设置为**等宽与等高列** 。

## 偏移列

偏移列通过 **offset-\*-\*** 类来设置。第一个星号( * )可以是 **sm、md、lg、xl**，表示屏幕设备类型，第二个星号( * )可以是 **1** 到 **11** 的数字。

为了在大屏幕显示器上使用偏移，请使用 **.offset-md-\*** 类。这些类会把一个列的左外边距（margin）增加 ***** 列，其中 ***** 范围是从 **1** 到 **11**。

# 文字排版

------

## 默认设置

Bootstrap 4 默认的 **font-size** 为 16px, **line-height** 为 1.5。

默认的 **font-family** 为 "Helvetica Neue", Helvetica, Arial, sans-serif。

此外，所有的 *<p>* 元素 **margin-top: 0** 、 **margin-bottom: 1rem** (16px)。

### Display 标题类

Bootstrap 还提供了四个 Display 类来控制标题的样式: .display-1, .display-2, .display-3, .display-4。

```html
<div class="container-fluid">
    <div class="display-1">display-1</div>
    <div class="display-2">display-2</div>
    <div class="display-3">display-3</div>
    <div class="display-4">display-4</div>
</div>
```

### small

在 Bootstrap 4 中 HTML **** 元素用于创建字号更小的颜色更浅的文本

```html
<div class="container">
  <h1>更小文本标题</h1>
  <p>small 元素用于字号更小的颜色更浅的文本:</p>       
  <h1>h1 标题 <small>副标题</small></h1>
  <h2>h2 标题 <small>副标题</small></h2>
  <h3>h3 标题 <small>副标题</small></h3>
  <h4>h4 标题 <small>副标题</small></h4>
  <h5>h5 标题 <small>副标题</small></h5>
  <h6>h6 标题 <small>副标题</small></h6>
</div>
```

### mark

Bootstrap 4 定义 *<mark>* 为黄色背景及有一定的内边距

```html
<div class="container">
  <h1>高亮文本</h1>    
  <p>使用 mark 元素来 <mark>高亮</mark> 文本。</p>
</div>
```

### abbr

Bootstrap 4 定义 HTML *<abbr>* 元素的样式为显示在文本底部的一条虚线边框

```html
<div class="container">
  <h1>Abbreviations</h1>
  <p>The abbr element is used to mark up an abbreviation or acronym:</p>
  <p>The <abbr title="World Health Organization">WHO</abbr> was founded in 1948.</p>
</div>
```

### blockquote

对于引用的内容可以在 *<blockquote>* 上添加 `.blockquote` 类

```html
<div class="container">
  <h1>Blockquotes</h1>
  <p>The blockquote element is used to present content from another source:</p>
  <blockquote class="blockquote">
    <p>For 50 years, WWF has been protecting the future of nature. The world's leading conservation organization, WWF works in 100 countries and is supported by 1.2 million members in the United States and close to 5 million globally.</p>
    <footer class="blockquote-footer">From WWF's website</footer>
  </blockquote>
</div>
```

### dl

```html
<div class="container">
  <h1>Description Lists</h1>    
  <p>The dl element indicates a description list:</p>
  <dl>
    <dt>Coffee</dt>
    <dd>- black hot drink</dd>
    <dt>Milk</dt>
    <dd>- white cold drink</dd>
  </dl>     
</div>
```

### code

```html
<div class="container">
  <h1>代码片段</h1>
  <p>可以将一些代码元素放到 code 元素里面:</p>
  <p>以下 HTML 元素: <code>span</code>, <code>section</code>, 和 <code>div</code> 用于定义部分文档内容。</p>
</div>
```

### kbd

```html
<div class="container">
  <h1>Keyboard Inputs</h1>
  <p>To indicate input that is typically entered via the keyboard, use the kbd element:</p>
  <p>Use <kbd>ctrl + p</kbd> to open the Print dialog box.</p>
</div>
```

### pre

```html
<div class="container">
<h1>Multiple Code Lines</h1>
<p>For multiple lines of code, use the pre element:</p>
<pre>
Text in a pre element
is displayed in a fixed-width
font, and it preserves
both      spaces and
line breaks.
</pre>
</div>
```

# 颜色

## 字体颜色

Bootstrap 4 提供了一些有代表意义的颜色类：

+ **.*-muted**:柔和的颜色
+  **.*-primary**：重要的颜色
+ **.*-success**：成功的颜色
+  **.*-info**：提示的颜色
+ **.*-warning**：警告的颜色
+  **.*-danger**：危险的颜色（红色）
+  **.*-secondary**：灰色
+ **.*-white**：白色
+ **.*-dark** ：深灰色
+  **.*-light**：浅灰色

这些颜色可以在前面加上text为文本颜色,bg为背景颜色,btn为按钮颜色，一通百通

# 表格

+ `.table`一个表格的基础类，加载`<table>`标签上
+ `.table-striped`,此类使得表格的行出现条纹
+ `.table-bordered`类可以为表格添加边框
+ `.table-hover` 类可以为表格的每一行添加鼠标悬停效果（灰色背景）
+ `.table-dark` 类可以为表格添加黑色背景

## 指定意义的颜色类

通过指定意义的颜色类可以为表格的**行或者单元格**设置颜色

下表列出了表格颜色类的说明:

| 类名                 | 描述                             |
| :------------------- | :------------------------------- |
| **.table-primary**   | 蓝色: 指定这是一个重要的操作     |
| **.table-success**   | 绿色: 指定这是一个允许执行的操作 |
| **.table-danger**    | 红色: 指定这是可以危险的操作     |
| **.table-info**      | 浅蓝色: 表示内容已变更           |
| **.table-warning**   | 橘色: 表示需要注意的操作         |
| **.table-active**    | 灰色: 用于鼠标悬停效果           |
| **.table-secondary** | 灰色: 表示内容不怎么重要         |
| **.table-light**     | 浅灰色，可以是表格行的背景       |
| **.table-dark**      | 深灰色，可以是表格行的背景       |

## 表头颜色

在 Bootstrap v4.0.0-beta.2 中**.thead-dark** 类用于给**表头**添加黑色背景， **.thead-light** 类用于给表头添加灰色背景

## 较小的表格

**.table-sm** 类用于通过减少内边距来设置较小的表格

## 响应式表格

**.table-responsive** 类用于创建响应式表格：在屏幕宽度小于 992px 时会创建水平滚动条，如果可视区域宽度大于 992px 则显示不同效果（没有滚动条)

你可以通过以下类设定在指定屏幕宽度下显示滚动条：

| 类名                     | 屏幕宽度 |
| :----------------------- | :------- |
| **.table-responsive-sm** | < 576px  |
| **.table-responsive-md** | < 768px  |
| **.table-responsive-lg** | < 992px  |
| **.table-responsive-xl** | < 1200px |

# 图像形状

## 圆角图片

+ **.rounded** 类可以让图片显示圆角效果
+ **.rounded-circle** 类可以设置椭圆形图片
+ **.img-thumbnail** 类用于设置图片缩略图(图片有边框)
+ 使用 **.float-right** 类来设置图片右对齐，使用 **.float-left** 类设置图片左对齐

## 响应式图片

图像有各种各样的尺寸，我们需要根据屏幕的大小自动适应。

我们可以通过在 **<img>** 标签中添加 **.img-fluid** 类来设置响应式图片。

**.img-fluid** 类设置了 **max-width: 100%;** 、 **height: auto;**

```html
<img class="img-fluid" src="img_chania.jpg" alt="Chania">
```

# Jumbotron

Jumbotron（超大屏幕）会创建一个大的灰色背景框，里面可以设置一些特殊的内容和信息。

**提示:** Jumbotron 里头可以放一些 HTML标签，也可以是 Bootstrap 的元素。

我们可以通过在 **<div>** 元素 中添加 **.jumbotron** 类来创建 jumbotron

```html
<div class="jumbotron">
  <h1>菜鸟教程</h1> 
  <p>学的不仅是技术，更是梦想！！！</p> 
</div>
```

## Jumbotron

如果你想创建一个没有圆角的全屏幕，可以在 **.jumbotron-fluid** 类里头的 div添加 **.container** 或 **.container-fluid** 类来实现

```html
<div class="jumbotron jumbotron-fluid">
  <div class="container">
      <h1>菜鸟教程</h1> 
      <p>学的不仅是技术，更是梦想！！！</p>
  </div>
</div>
```

# 信息提示框

提示框可以使用 **.alert** 类, 后面加上 **.alert-success**, **.alert-info**, **.alert-warning**, **.alert-danger**, **.alert-primary**, **.alert-secondary**, **.alert-light** 或 **.alert-dark** 类来实现

```html
<div class="alert alert-success">
  <strong>成功!</strong> 指定操作成功提示信息。
</div>
```

## 提示框添加链接

提示框中在链接的标签上添加 **alert-link** 类来设置匹配提示框颜色的链接

```html
<div class="alert alert-success">
  <strong>成功!</strong> 你应该认真阅读 <a href="#" class="alert-link">这条信息</a>。
</div>
```

## 关闭提示框

我们可以在提示框中的 div 中添加 **.alert-dismissible** 类，然后在关闭按钮的链接上添加 **class="close"** 和 **data-dismiss="alert"** 类来设置提示框的关闭操作。

```html
<div class="alert alert-success alert-dismissible">
  <button type="button" class="close" data-dismiss="alert">&times;</button>
  <strong>成功!</strong> 指定操作成功提示信息。
</div>
```

## 提示框动画

**.fade** 和 **.show** 类用于设置提示框在关闭时的淡出和淡入效果：

# 按钮

```html
<button type="button" class="btn">基本按钮</button>
<button type="button" class="btn btn-primary">主要按钮</button>
<button type="button" class="btn btn-secondary">次要按钮</button>
<button type="button" class="btn btn-success">成功</button>
<button type="button" class="btn btn-info">信息</button>
<button type="button" class="btn btn-warning">警告</button>
<button type="button" class="btn btn-danger">危险</button>
<button type="button" class="btn btn-dark">黑色</button>
<button type="button" class="btn btn-light">浅色</button>
<button type="button" class="btn btn-link">链接</button>
```

按钮类可用于 **<a>**, **<button>**, 或 **<input>** 元素上:

```html
<a href="#" class="btn btn-info" role="button">链接按钮</a>
<button type="button" class="btn btn-info">按钮</button>
<input type="button" class="btn btn-info" value="输入框按钮">
<input type="submit" class="btn btn-info" value="提交按钮">
```

## 按钮设置边框

```html
<button type="button" class="btn btn-outline-primary">主要按钮</button>
<button type="button" class="btn btn-outline-secondary">次要按钮</button>
<button type="button" class="btn btn-outline-success">成功</button>
<button type="button" class="btn btn-outline-info">信息</button>
<button type="button" class="btn btn-outline-warning">警告</button>
<button type="button" class="btn btn-outline-danger">危险</button>
<button type="button" class="btn btn-outline-dark">黑色</button>
<button type="button" class="btn btn-outline-light text-dark">浅色</button>
```

## 不同大小的按钮

Bootstrap 4 可以设置按钮的大小

```html
<button type="button" class="btn btn-primary btn-lg">大号按钮</button>
<button type="button" class="btn btn-primary">默认按钮</button>
<button type="button" class="btn btn-primary btn-sm">小号按钮</button>
```

## 块级按钮

通过添加 **.btn-block** 类可以设置块级按钮

```html
<button type="button" class="btn btn-primary btn-block">按钮 1</button>
```

## 激活和禁用的按钮

按钮可设置为激活或者禁止点击的状态。

**.active** 类可以设置按钮是可用的， **disabled** 属性可以设置按钮是不可点击的。 注意 <a> 元素不支持 disabled 属性，你可以通过添加 **.disabled** 类来禁止链接的点击。

```html
<button type="button" class="btn btn-primary active">点击后的按钮</button>
<button type="button" class="btn btn-primary" disabled>禁止点击的按钮</button>
<a href="#" class="btn btn-primary disabled">禁止点击的链接</a>
```

# 按钮组

+ 可以在`<div>` 元素上添加 **.btn-group** 类来创建按钮组。
+ 使用 **.btn-group-lg|sm** 类来设置按钮组的大小。
+ 使用 **.btn-group-vertical** 类来创建垂直的按钮组

# 徽章（Badges）

徽章（Badges）主要用于突出显示新的或未读的项。如需使用徽章，只需要将 **.badge** 类加上带有指定意义的颜色类 (如 **.badge-secondary**) 添加到`<span>` 元素上即可。 徽章可以根据父元素的大小的变化而变化

+ 使用 **.badge-pill** 类来设置药丸形状徽章
+ 徽章也可以嵌入到其他元素内例如按钮

# 进度条

进度条可以显示用户任务的完成过程。

创建一个基本的进度条的步骤如下：

- 添加一个带有 **.progress** 类的 <div>。
- 接着，在上面的 <div> 内，添加一个带有 class **.progress-bar** 的空的 <div>。
- 添加一个带有百分比表示的宽度的 style 属性，例如 **style="width:70%"** 表示进度条在 **70%** 的位置。
- 进度条高度默认为 16px。我们可以使用 CSS 的 `height` 属性来修改他
- 可以使用 `.progress-bar-striped` 类来设置条纹进度条
- 使用 `.progress-bar-animated` 类可以为进度条添加动画，一般动画与条纹联合使用

# 分页

+ 在 `<ul>` 元素上添加 **.pagination** 类。然后在 `<li>`元素上添加 **.page-item** 类
+ 当前页可以使用 **.active** 类来高亮显示
+ **.disabled** 类可以设置分页链接不可点击
+ **.pagination-lg** 类设置大字体的分页条目，**.pagination-sm** 类设置小字体的分页条目

# 列表组

+ 在 <ul>元素上添加 **.list-group** 类, 在 <li>元素上添加 **.list-group-item** 类
+ 通过添加 **.active** 类来设置激活状态的列表项
+ **.disabled** 类用于设置禁用的列表项
+ 将`<ul>`替换为 `<div>`， <li> 替换 <a>。如果你想鼠标悬停显示灰色背景就添加**.list-group-item-action** 类

# 下拉菜单

Bootstrap4 下拉菜单依赖于 popper.min.js。

下拉菜单是可切换的，是以列表格式显示链接的上下文菜单。

```html
<div class="dropdown">
  <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
    Dropdown button
  </button>
  <div class="dropdown-menu">
    <a class="dropdown-item" href="#">Link 1</a>
    <a class="dropdown-item" href="#">Link 2</a>
    <a class="dropdown-item" href="#">Link 3</a>
  </div>
</div>
```

**解析：**

+ **.dropdown** 类用来指定一个下拉菜单。
+ 我们可以使用一个按钮或链接来打开下拉菜单
+  按钮或链接需要添加 **.dropdown-toggle** 和 **data-toggle="dropdown"** 属性。
+ `<div>`元素上添加 .dropdown-menu 类来设置实际下拉菜单，然后在下拉菜单的选项中添加 .dropdown-item 类

扩展：

+ **.dropdown-divider** 类用于在下拉菜单中创建一个水平的分割线
+ **.dropdown-header** 类用于在下拉菜单中添加标题
+ 想让下拉菜单右对齐，可以在元素上的 .dropdown-menu 类后添加 **.dropdown-menu-right** 类
+ 下拉菜单向右弹出，可以在 div 元素上添加 **"dropright"** 类
+ 上拉菜单向上弹出，可以在 div 元素上添加 **"dropup"** 类

# 折叠

+ **.collapse** 类用于指定一个折叠元素 (实例中的 <div>); 点击按钮后会在隐藏与显示之间切换。

+ 控制内容的隐藏与显示，需要在 <a> 或 <button> 元素上添加 **data-toggle="collapse"** 属性。 **data-target="#id"** 属性是对应折叠的内容 (<div id="demo">)。

+ **注意:** <a> 元素上你可以使用 **href** 属性来代替 **data-target** 属性
+ 默认情况下折叠的内容是隐藏的，你可以添加 **.show** 类让内容默认显示

# 导航

+ 创建一个简单的水平导航栏，可以在 `<ul>` 元素上添加 **.nav**类，在每个 `<li> `选项上添加 **.nav-item** 类，在每个`<a>`链接上添加 **.nav-link** 类
+ **.justify-content-center** 类设置导航居中显示，
+ **.justify-content-end** 类设置导航右对齐。
+ **.flex-column** 类用于创建垂直导航
+ 使用 **.nav-tabs** 类可以将导航转化为选项卡。然后对于选中的选项使用 .active 类来标记。
+ **.nav-pills** 类可以将导航项设置成胶囊形状。
+ **.nav-justified** 类可以设置导航项齐行等宽显示。

# 导航栏

使用 **.navbar** 类来创建一个标准的导航栏，后面紧跟: **.navbar-expand-xl|lg|md|sm** 类来创建响应式的导航栏 (大屏幕水平铺开，小屏幕垂直堆叠)。

导航栏上的选项可以使用`<ul>` 元素并添加 **class="navbar-nav"** 类。 然后在`<li>` 元素上添加 **.nav-item** 类， `<a>` 元素上使用 **.nav-link** 类

```html
<nav class="navbar navbar-expand-sm bg-light">
 
  <!-- Links -->
  <ul class="navbar-nav">
    <li class="nav-item">
      <a class="nav-link" href="#">Link 1</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="#">Link 2</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="#">Link 3</a>
    </li>
  </ul>
 
</nav>
```

+ 通过删除 **.navbar-expand-xl|lg|md|sm** 类来创建垂直导航栏
+ **.navbar-brand** 类用于高亮显示品牌/Logo
+ 使用 **.navbar-text** 类来设置导航栏上非链接文本，可以保证水平对齐，颜色与内边距一样。
+ 使用 **.fixed-top** 类来实现导航栏的固定
+ **.fixed-bottom** 类用于设置导航栏固定在底部

# 表单

+ 表单元素在使用 **.form-control** 类的情况下，宽度都是设置为 100%
+ 内联表单需要在`<form>` 元素上添加 **.form-inline**类。

# 输入框组

+ 使用 **.input-group** 类来向表单输入框中添加更多的样式，如图标、文本或者按钮。

+ 使用 **.input-group-prepend** 类可以在输入框的的前面添加文本信息
+  **.input-group-append** 类添加在输入框的后面。

+ 最后还需要使用 **.input-group-text** 类来设置文本的样式。

