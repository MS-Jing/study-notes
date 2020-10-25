# NFS介绍

​	（Network File System)网络文件系统 。主要用于在类unix系统之间进行文件共享 它允许网络中的计算机之间通过TCP/IP网络共享资源。在NFS的应用中,本地NFS的客户端应用可以透明地读写位于远端NFS服务器上的文件,就像访问本地文件一样。

# yum安装NFS服务

```bash
yum install nfs-utils nfs4-acl-tools net-tools tree -y
```



# 配置NFS服务的服务器端

+ 配置NFS共享目录

  + ```bash
    mkdir -p /mnt/nfs
    ```

  + ```bash
    cat >> /etc/exports <<EOF
    > /mnt/nfs 192.168.180.129(rw,no_root_squash,async)
    > EOF
    ```

  + /etc/exports参数解释

    + ro:共享目录只读
    + rw:共享目录读写
    + root_squash:客户端用root用户访问共享文件夹的时候,将root用户映射成匿名用户; (这是默认设置)
    + no_root_squash:客户端用root用户访问共享文件夹的时候，保持root权限
    + all_squash:客户端所有访问共享文件夹的用户都映射为匿名用户
    + no_all_squash:客户端所有访问共享文件夹的用户都映射为匿名用户; (这是默认设置)
    + anonuid=<UID>：匿名访问用户的本地用户UID;可指定， 默认设置为nf snobody(65534);
    + anongid=<GID>:匿名访间用户的本地用户组GID;可指定， 默认设置为nfsnobody(65534) ;
    + secure:限制客户端只能从小于1024的端口连接服务器; (默认)
    + insecure:允许客户端从大于1024的端口连接服务器;
    + subtree_check:如果共享子目录, nfs服务器强制检查父目录的权限; (默认)
    + sync:将数据同步写入内存缓冲区与磁盘中，可以保证数据的一致性;
    + wdelay(默认)：检查是否有相关的写操作,如果有则将这些写操作一起执行，这样可以提高效率,与sync配合使用;
    + on_wdelay:若有写操作则立即执行，与synC配合使用; .
    + async:将数据先保存在内存缓冲区中,必要时才写入磁盘，使用async参数, wdelay参数直接失效;

+ 启动NFS服务

  + ```bash
    systemctl start nfs #开启
    systemctl stop nfs  #关闭
    ```

+ 防火墙

  + ```bash
    #查看防火墙状态
    firewall-cmd --state
    #开启关闭防火墙
    systemctl start[stop] firewalld
    ```

# 配置NFS服务的客户端

+ 挂载nfsv4.2版本的2种方式

  + ``` bash
    mount -t nfs4 -s -v -o minorversion=2,soft,timeo=5,retrans=3 192.168.180.128:/mnt/nfs /mnt/
    
    -s :不管错误
    -v :显示挂载步骤
    -o :挂载的选项
    ```

  + ```bash
    mount -t nfs -s -v -o vers=4.2,soft,timeo=5,retrans=3 192.168.180.128:/mnt/nfs /mnt/
    ```

+ 解除挂载
  + `umount /mnt/`
  + 注意不要在挂载的目录下解除，否则会解除失败