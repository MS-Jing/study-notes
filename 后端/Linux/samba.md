+ 安装

  + ```bash
    yum install samba -y
    ```

+ 配置文件（已有lj用户）

  + ```bash
    [lj]
    	comment = lj
        path = /home/lj
    	browseable=yes
    	writable=yes
    	available=yes
    	vaild users=lj
        write list = lj
    ```

+ 启动服务

  + ```bash
     systemctl start smb.service
    ```

+ 为登录用户设置密码

  + ```bash
    smbpasswd -a lj
    ```

+ 防火墙

  + ```bash
    #查看防火墙状态
    firewall-cmd --state
    #开启关闭防火墙
    systemctl start[stop] firewalld
    ```

+ 关闭验证

  + ```bash
    setenforce 0
    ```

+ 测试
  + 文件管理器-->此电脑-->映射网络驱动器-->`\\192.168.180.128\lj`

