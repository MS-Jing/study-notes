## Bootstrap栅格系统垂直居中问题

 大家都知道水平居中`text-center`但是在栅格系统中想要垂直居中却没有这么简单

#### 方法一：

加在col类的div的子元素上，在我尝试了很多方法都不奏效后百度搜索到了如下代码：

```css
.m-center-vertical {
    position:relative;
    top:50%;
    transform:translateY(-50%);
}
```

html:

```html
<div class="col-2 border text-center">
    <img src="img/wechat.jpg" class="m-center-vertical" alt="">
</div>
```

此方法要求div里只有一个子元素，如果有多个需要用div包裹起来否则会有重叠现象



#### 方法二：

使用框架的**Flex（弹性）布局**，先使用`d-flex`将div变成一个弹性盒子容器，再用`justify-content-center`修改弹性子元素为居中排列方式，最后使用`flex-column`设置弹性子元素垂直方向显示

```html
<div class="col-2 border text-center d-flex  justify-content-center flex-column">
    <h4 class="my-header text-white">最新博客</h4>
    <div class="list-group">
        <a href="#" class="list-group-item ">用户故事（User Story）</a>
        <a href="#" class="list-group-item ">用户故事（User Story）</a>
        <a href="#" class="list-group-item ">用户故事（User Story）</a>
    </div>
</div>
```

此方法可以在div中有多个子元素，但是像图片这种元素就需要一个div包裹起来，否则不起作用！！！

```html
<div class="col-2 border text-center d-flex flex-column justify-content-center">
    <div>
        <img src="img/wechat.jpg" alt="">
    </div>
</div>
```



中部居中可以用`d-flex justify-content-center align-items-center`来完成



==注==：由于作者的能力有限或许还有其他更好的方法，可以联系作者一起交流

