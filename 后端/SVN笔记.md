# SVN笔记

[官网下载](https://svnbucket.com/#/projects)

### centos下载

```
使用 yum install subversion 命令进行安装。
安装成功之后，执行 svn --version 命令。

创建SVN版本库目录 mkdir -p /var/svn/svntest
创建版本库  svnadmin create /var/svn/svntest
进入conf目录
	- authz文件是权限控制文件
	- passwd是账号密码文件
	- svnserve.conf SVN服务配置文件
设置密码：vim passwd
	+ [users]块添加用户：账号=密码
设置权限 vim authz
	+ [/]
	+ lj = rw  #lj对版本库的根目录有读写权限
vi svnserve.conf
	+ anon-access = read #未授权用户有可读权限
	+ auth-access = write #授权用户可写
	+ password-db = passwd #使用的账号文件
	+ authz-db = authz #使用的权限文件
	+ realm = /var/svn/svntest #认证空间名，版本库所在的目录
启动svn版本库：svnserve -d -r /var/svn
停止服务：
	+ killall svnserve
	+ ps aux|grep svn
	+ kill -s 9 pid
```



## 基本操作

### 检出 checkout



### 新增 add



### 提交 commit



### 更新 update



### 历史记录



