## 介绍

> 我们python程序必须要在python环境下才能运行起来。像java程序必须要在虚拟机里执行。如果想要给你没有安装python环境的小伙伴你写的python代码。他是运行不起来的。所以这里就有一个转换成可执行程序的问题

## 环境搭建

+ 安装pywin32`pip install pywin32`
  + 到Scripts目录下执行CMD命令：`python pywin32_postinstall.py -install`
+ 安装[pyinsstaller](http://www.pyinstaller.org/downloads.html)
  + 下载下面的安装包
  + 用CMD命令行中进入`pyinstaller-pyinstaller`目录，然后执行：`python setup.py install`

## 转换

将写的hello.py放入pyinstaller-pyinstaller目录下

在该路径下执行`python pyinstaller.py -F hello.py`

成功后会在dist目录下生成hello.exe文件