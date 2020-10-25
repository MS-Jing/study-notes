## div垂直水平居中

#### 方法一：

该方法通过定位相对于父元素或者视口定位，然后平移到中心位置

```css
.box {
    width: 300px;
    height: 500px;
    background: #332F63;
    border-radius: 20px;
    position: absolute;/*fixed*/
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
}
```

html:

```html
<div class="box">

</div>
```

#### 方法二：

使用弹性布局来居中

```css
*{
    		padding: 0;
    		margin: 0;
    	}
    	html,body{
    		width: 100%;
    		height: 100%;
    	}
    	.box{
    		width: 100%;
    		height: 100%;
    		background-color: yellow;
    		display: flex;
    		justify-content: center;
    		align-items: center;
    	}
    	.aaa{
    		width: 200px;
    		height: 500px;
    		background-color: red;
    	}
```

```html
<div class="box">
    <div class="aaa">

    </div>	
</div>
```



==注==：由于作者的能力有限或许还有其他更好的方法，可以联系作者一起交流

