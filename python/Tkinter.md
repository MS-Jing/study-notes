# 工具

## Config配置组件样式

```python
.config(
    cursor="gumby", # 鼠标悬浮
    width=80,height=10, # 宽高
    fg="yellow",bg="dark green", # 前后置背景色
    font=('times','28','bold'), #字体 字体大小 加粗
)
```



## Toplevel

```python
root = Tk()
root.title("主窗口")
top = Toplevel()
top.title("子窗口")
root.mainloop()
```



## 菜单Menu

+ 添加一个标准的菜单
  + `add_command(label=string,command=callback)`
+ 添加一个分割线
  + `add_separator()`
+ 添加一个子菜单
  + `add_cascade(label=string,menu=menu instance)`,menu是它的子菜单

```python
root = Tk()
menu = Menu(root)

fileMenu = Menu(menu,tearoff=0)  # tearoff=0 为0菜单不能独立出来，为1可以独立出来
fileMenu.add_command(label="创建", command=callback)
fileMenu.add_command(label="打开", command=callback)
fileMenu.add_separator()
fileMenu.add_command(label="退出", command=callback)

helpMenu = Menu(menu,tearoff=0)
helpMenu.add_command(label="关于", command=callback)

menu.add_cascade(label="文件", menu=fileMenu)
menu.add_cascade(label="帮助", menu=helpMenu)

root.config(menu=menu)
root.mainloop()
```



# 布局

## 工具栏TooBar

```python
root = Tk()

toolBar = Frame(root)
b1 = Button(toolBar, text="第一个按钮", command=callback, width=10)  # width=6   6个字间距
b1.pack(side=LEFT, padx=2, pady=2)

b2 = Button(toolBar, text="第二个按钮", command=callback, width=10)  # width=6   6个字间距
b2.pack(side=RIGHT, padx=5, pady=5)

toolBar.pack(side=TOP,fill = X)  #放置在顶部，横向拉伸
root.mainloop()
```

## 状态栏StatusLable

```python
root = Tk()
# bd 有一个边界,relief有一个浮雕效果,anchor文字对齐方式 N上对齐、S下对齐、W左对齐、E右对齐
status = Label(root,text="状态栏",bd=1,relief=SUNKEN,anchor=E)
status.pack(side=BOTTOM,fill=X)
root.mainloop()
```

## Grid布局

```python
root = Tk()

Label(root,text="用户：").grid(row=0,column=0)
Label(root,text="密码：").grid(row=1,column=0)
e1 = Entry(root)
e2 = Entry(root)

e1.grid(row=0,column=1)
e2.grid(row=1,column=1)

root.mainloop()
```



# 事件

## 按钮

```python
def click():
    print("触发点击时间")

root = Tk()
button = Button(root,text="点我",command=click)
button.pack()
root.mainloop()
```

## 绑定

```python
def calback(event):
    print(event)

root = Tk()
label = Label(root,text="我是标签")
label.bind("<Button-1>",calback)   # 鼠标左键点击绑定
label.pack()

root.mainloop()

#########
<Button-1> 鼠标左键点击 ， <Button-1> 鼠标右键点击
<B1-Motion> 鼠标左键移动
<ButtonRelease-1>  鼠标左键移动
<Double-Button-1>  鼠标左键双击
<Enter>  进入
<Leave>	 离开	
<FocusIn>
<FocusOut>
<Return>(F1,F2,F3,Delete...)  回车键
<Key>  任意键
<a>(b,c,d...)
<Configure>
```

## 协议

```python
from tkinter import *
import tkinter.messagebox as tkMessageBox

def calback():
   if tkMessageBox.askokcancel(title="退出",message="是否要退出？"):
       root.destroy()

root = Tk()
root.protocol("WM_DELETE_WINDOW",calback)   #监听关闭窗口
root.mainloop()
```

