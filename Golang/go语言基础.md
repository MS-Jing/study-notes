# 下载安装

官网：https://golang.google.cn/dl/

下载对应系统的SDK。

解压后进入go/bin/目录输入 `go version`查看版本

## 配置环境变量

| 环境变量 | 说明                           |
| -------- | ------------------------------ |
| GOROOT   | 指定SDK的安装路径，就是go/那层 |
| Path     | go/bin/                        |
| GOPATH   | 工作目录                       |



# 第一个go程序

```go
package main

import "fmt"

func main(){
	fmt.Println("hello word");
}
```

## 编译

`go build helloword.go`

会在当前目录下生成一个`helloword.exe`,即可执行！

+ 指定生成可执行文件名 go build -o：
  + `go build -o ./aaa/aaa.exe helloword.go`

# 常用转义字符

| \t   | 一个制表位 |
| ---- | ---------- |
| \n   | 换行符     |
| `\\` | 一个\      |
| `\"` | 一个“      |
| `\r` | 一个回车   |

# 注释

行注释：//

多行注释: /* */

# 变量

```go
import "fmt"

//申明全局变量
var (
	name = "张三"
	age = 18
)

func main() {

	//打印全局变量
	fmt.Println(name,age)

	// 定义变量并赋值
	var a int = 10
	//先定义再赋值，指定了变量类型如果不赋值会是默认值
	var b int
	b = 11
	//根据值自己判断变量类型
	var c = 12
	//省略var ,注意变量之前没有被申明过
	d := 13
	fmt.Println(a, b, c, d)

	// 一次申明多个变量
	var e,f,j int
	var h,i,g = 1,"张三",0 //这里也可以把var去掉像这样  h,i,g := 1,"张三",0
	fmt.Println(e,f,j,h,i,g)
}
```

# 数据类型

## 基本数据类型

+ 数值型
  + 整数类型
  + 浮点类型
+ 字符型
+ 布尔型
+ 字符串
  + 字符串可以中""来表示，也可以用``来表示，这样里面的特殊字符就不会被转义

## 复杂数据类型

+ 指针
+ 数组
+ 结构体
+ 管道
+ 函数
+ 切片
+ 接口
+ map



## 基本数据类型的转换

```go
package main

import (
	"fmt"
	"strconv"
)

func main() {
	var a int = 100
	b := float32(a) //int转float32
	c:=10.11
	d:=int(c)// float32转int
	fmt.Printf("%v,%v \n",b,d)

	//基本数据类型转string
	str1 := fmt.Sprintf("转换后：%v",a)
	fmt.Println(str1)
	// strconv包
	str2 := strconv.FormatInt(int64(a),10)
	fmt.Println(str2)
	str2 = strconv.Itoa(a)
	fmt.Println(str2)

	//string转基本数据类型
	str3,_ := strconv.ParseInt("110",10,64)
	fmt.Println(str3)

}
```



# 指针

```go
func main() {
	a := 10
	var p *int = &a
	fmt.Printf("p=%v,*p=%v,a=%v \n", p, *p, a)
	*p = 100
	fmt.Printf("p=%v,*p=%v,a=%v \n", p, *p, a)

	var p1 **int = &p
	fmt.Printf("p1=%v,*p1=%v,**P1=%v", p1, *p1, **p1)
}
```



# 值类型和引用类型

+ 值类型：基本数据类型 int系列，float系列，bool,string、数组和结构体struct
+ 引用类型：指针、slice切片、map、管道chan、interface等

## 使用特点

+ 值类型：变量直接存储值，内存通常在栈中分配
+ 引用类型：变量存储的是一个地址，这个地址对应的空间才是真正存储数据，内存通常在堆上分配，当没有任何变量引用这个地址时，该地址对应的数据就是一个垃圾由GC来回收

# 标识符

英文字母大小写，数字，下滑线组成，数字不能开头。

不能是保留关键字

注意：如果变量名，函数名，常量名首字母大写，则其他包可以访问，小写只有本包可以访问

# 终端输入

```go
func main() {
	var name string
	fmt.Printf("姓名：")
	fmt.Scanln(&name)
	fmt.Println("你的姓名: " + name)

	//像C语言那样原样输入
	var age int
	fmt.Println("姓名,年龄")
	fmt.Scanf("%s %d", &name, &age)
	fmt.Printf("%s,%d", name, age)
}
```





