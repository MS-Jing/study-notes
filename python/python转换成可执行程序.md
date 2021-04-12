## 介绍

> 我们python程序必须要在python环境下才能运行起来。像java程序必须要在虚拟机里执行。如果想要给你没有安装python环境的小伙伴你写的python代码。他是运行不起来的。所以这里就有一个转换成可执行程序的问题

## 相关参数

` pyinstaller [<args>] Target.py `

+ --distpath <path>: 打包到哪个目录下
+ -w: 指定生成 GUI 软件，也就是运行时不打开控制台
+ -c: 运行时打开控制台
+ -i <Icon File>: 指定打包后可执行文件的图标
+ --clean: 在构建之前清理PyInstaller缓存并删除临时文件
+ -D: 创建包含可执行文件的单文件夹包，同时会有一大堆依赖的 dll 文件，这是默认选项
+ -F: 只生成一个 .exe 文件，如果项目比较小的话可以用这个，但比较大的话就不推荐



## 用虚拟环境解决打包太大的问题

 Pipenv 是一款管理虚拟环境的命令行软件，简单来讲，它可以创建一个只在某个目录下的局部 Python 环境，而这个环境是可以和全局环境脱离开的。 

+ 安装Pipenv
  + `pip install pipenv`
+ 选择一个目录做虚拟环境 (自己电脑是什么环境就填什么环境，我的是3.8)
  + `pipenv install --python 3.8`
+ 进入虚拟环境
  + `pipenv shell`
+ 虚拟环境下安装相关的第三方库 可以使用`pip list` 查看
  + `pipenv install pyinstaller`

+ 打包
  + `pyinstaller -w --clean -i favicon.ico target.py`