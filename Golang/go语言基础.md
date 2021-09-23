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

