# VSFTP概述

​		FTP是File Transfer Protocol (文件传输协议)的英文简称，用于Internet. 上的文件的双向
传输。使用FTP来传输时，是具有一定程度的危险性，因为数据在因特网上面是完全没有受到保护
的明文传输方式! 
​		VSFTP是一个基于GPL发布的类Unix系统上使用的FTP服务器软件,它的全称是Very Secure FTP,
从名称定义上基本可以看出，这是为了解决ftp传输安全性问题的。。



# 登录验证方式

## 匿名用户验证

用户账号名称：ftp或anonymous

用户账号密码：无密码

工作目录：/var/ftp

默认权限：默认可下载不可上传，上传权限由两部分组成（主配置文件和文件系统）

### 权限控制

anonymous_enable=YES				#启用匿名访问

anon_umask=022							#匿名用户所上传文件的权限掩码

anon_root=/var/ftp						  #匿名用户的FTP根目录

anon_upload_enable=YES			  #允许上传文件

anon_mkdir_write_enable=YES	  #允许创建目录

anon_other_write_enable=YES		#开放其他写入权限（删除，覆盖，重命名）

anon_max_rate=0								#限制最大传输速率（0为不限速，单位：bytes/秒）

## 本地用户验证

用户账号名称：本地用户

用户账号密码：用户密码

工作目录：登录用户的宿主目录

默认权限：最大权限（drwx------）

### 权限控制

local_enable=YES			#是否启用本地系统用户

local_umask=022			#本地用户上传文件的权限掩码

local_root=/var/ftp			#设置本地用户的FTP根目录

chroot_local_user=YES		#是否将用户禁锢在主目录

local_max_rate=0			#限制最大传输速率

ftpd_ banner=Welcome to blah FTP service 		#用户 登录时显示的欢迎信息

userlist_ enable=YES & userlist_deny=YES		#禁止/etc/vsftpd/user_ list 文件中出现的用户名登录FTP

userlist_ enable=YES & userlist_deny=NO		#仅允许/etc/vsftpd/user_ list 文件中出现的用户名登录FTP

配置文件: ftpusers 			#禁止/etc/vsftpd/ftpusers文件中出现的用户名登录FTP,权限比user_ list 更高，即时生效。

## 虚拟用户验证

创建虚拟用户用来代替本地用户

使用本地用户作为虚拟用户的映射用户，为虚拟用户提供工作目录和权限控制

能够设置严格的权限（为每一个用户生成单独的配置文件）

# 安装和匿名用户测试

```shell
#安装服务器端
yum -y install vsftpd
#服务启动和关闭
systemctl start vsftpd
systemctl stop vsftpd

#查看服务端口
netstat -antp
#查看防火墙状态
firewall-cmd --state
#开启关闭防火墙
systemctl start[stop] firewalld
#关闭SELinux
setenforce 0
#切换至/var/ftp操作文件
======================================
#安装客户端
yum -y install ftp

#连接客户端
ftp ip
#用?号查看ftp的内置命令

============================
#主配置文件 /etc/vsftpd/vsftpd.conf
```



# 匿名用户测试

**==默认匿名用户可以下载，但是不能上传==**

+ 实现匿名用户上传
  + anon_upload_enable=YES
  + 修改上传目录权限，让匿名用户有写权限
    + mkdir upload
    + chmod o+w upload

==**默认情况下开放上传权限后，上传的文件是无法被下载的，因为文件的其他人位置没有r权限
设置anon_umask=022， 可以让上传的文件其他人位置拥有r权限，然后才能被其他人下载。**==

+ 实现上传文件可下载
  + anon_umask=022
+ 用户进入某一个文件夹时，弹出相应说明
  + 在对应目录下创建.message 文件，并写入相应内容
  + 确认dirmessage_enable=YES启用

# 本地用户测试

+ 将部分用户禁锢在自己的家目录下
  + chroot_list_enable=YES
  + chroot_list_file=/etc/vsftpd/chroot_list   # 白名单文件只有里面的用户可以随意切换

+ 修改被动模式数据传输使用端口。
  pasv_ enable=YES
  pasv_ min_ port=30000 
  pasv_ max_ port=35000

# 虚拟用户测试

## 建立FTP的虚拟用户的用户数据库文件(在/etc/vsftpd)

```bash
# 注:该文件名可以随便定义，文件内容格式:奇数行用户，偶数行密码
vim vsftpd.user
#将用户密码的存放文本转化为数据库类型，并使用hash加密
db_load -T -t hash -f vsftpd.user vsftpd.db
#修改文件权限为600，保证其安全性
chmod 600 vsftpd.db
```

## 创建FTP虚拟用户的映射用户，并制定其用户家目录

```bash
#创建virtual用户作为ftp的虚拟用户的映射用户，不是用virtual用户登录是让virtual提供一个根目录给虚拟用户使用
useradd -d /var/ftproot -s /sbin/nologin virtual
+ -s /sbin/nologin 用户不能登录
+ -d /var/ftproot  匿名用户根目录
```

## 建立支持虚拟用户的PAM认证文件，添加虚拟用户支持

```bash
#使用模板生成自己的认证配置文件，方便一会调用
cp -a /etc/pam.d/vsftpd /etc/pam.d/vsftpd.pam
#编辑新生成的文件vsftpd.pam (清空原来内容，添加下列两行)
auth	required	pam_userdb.so db=/etc/vsftpd/vsftpd
account	required	pam_userdb.so db=/etc/vsftpd/vsftpd
# 在vsftpd.conf文件中添加支持配置
修改:
	pam_service_name=vsftpd.pam
添加:
	guest_enable=YES	# 虚拟开启
	guest_username=virtual	#虚拟用户的映射用户是谁
	user_config_dir=/etc/vsftpd/dir	#匿名用户的配置文件在哪里（需要提前创建dir目录）
```

## 为虚拟用户建立独立的配置文件，启动服务并测试

**==注:做虚拟用户配置文件设置时，将主配置文件中自定义的匿名用户相关设置注释掉==**

**==注:给映射用户的家目录设置o+r让虚拟用户有读权限==**

