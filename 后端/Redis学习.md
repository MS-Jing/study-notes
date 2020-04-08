# 安装Redis

宝塔安装，傻瓜式安装

## 解压缩安装

+ 上传redis-5.0.8.tar.gz到服务器，[官网](https://redis.io/)
  + ```bash
    [root@iZbp138sn4z9yrtqweb5brZ opt]# cd /home/
    [root@iZbp138sn4z9yrtqweb5brZ home]# ls -ll
    total 1948
    drwxr-xr-x 2 www  www     4096 Mar 18 15:04 MyProject
    -rwxr-xr-x 1 root root 1985757 Mar 27 17:17 redis-5.0.8.tar.gz
    drwx------ 3 www  www     4096 Feb 27 15:56 www
    
    ```

+ 一般我们的安装包放到`/opt`目录下

  + ```bash
    [root@iZbp138sn4z9yrtqweb5brZ home]# mv redis-5.0.8.tar.gz /opt
    [root@iZbp138sn4z9yrtqweb5brZ home]# cd /opt
    [root@iZbp138sn4z9yrtqweb5brZ opt]# ls -ll
    total 1940
    -rwxr-xr-x 1 root root 1985757 Mar 27 17:17 redis-5.0.8.tar.gz
    ```

+ 解压

  + ```bash
    [root@iZbp138sn4z9yrtqweb5brZ opt]# tar -zxvf redis-5.0.8.tar.gz 
    [root@iZbp138sn4z9yrtqweb5brZ opt]# ls -ll
    total 1944
    drwxrwxr-x 6 root root    4096 Mar 12 23:07 redis-5.0.8
    -rwxr-xr-x 1 root root 1985757 Mar 27 17:17 redis-5.0.8.tar.gz
    
    ```

+ 进redis文件

  + ```bash
    [root@iZbp138sn4z9yrtqweb5brZ opt]# cd redis-5.0.8
    [root@iZbp138sn4z9yrtqweb5brZ redis-5.0.8]# ls -ll
    total 276
    -rw-rw-r--  1 root root 118338 Mar 12 23:07 00-RELEASENOTES
    -rw-rw-r--  1 root root     53 Mar 12 23:07 BUGS
    -rw-rw-r--  1 root root   2381 Mar 12 23:07 CONTRIBUTING
    -rw-rw-r--  1 root root   1487 Mar 12 23:07 COPYING
    drwxrwxr-x  6 root root   4096 Mar 12 23:07 deps
    -rw-rw-r--  1 root root     11 Mar 12 23:07 INSTALL
    -rw-rw-r--  1 root root    151 Mar 12 23:07 Makefile
    -rw-rw-r--  1 root root   6888 Mar 12 23:07 MANIFESTO
    -rw-rw-r--  1 root root  20555 Mar 12 23:07 README.md
    -rw-rw-r--  1 root root  61797 Mar 12 23:07 redis.conf //配置文件
    -rwxrwxr-x  1 root root    275 Mar 12 23:07 runtest
    -rwxrwxr-x  1 root root    280 Mar 12 23:07 runtest-cluster
    -rwxrwxr-x  1 root root    373 Mar 12 23:07 runtest-moduleapi
    -rwxrwxr-x  1 root root    281 Mar 12 23:07 runtest-sentinel
    -rw-rw-r--  1 root root   9710 Mar 12 23:07 sentinel.conf
    drwxrwxr-x  3 root root   4096 Mar 12 23:07 src
    drwxrwxr-x 11 root root   4096 Mar 12 23:07 tests
    drwxrwxr-x  8 root root   4096 Mar 12 23:07 utils
    [root@iZbp138sn4z9yrtqweb5brZ redis-5.0.8]# 
    ```

+ 安装c++编译器（基本环境）

  + ```bash
    [root@iZbp138sn4z9yrtqweb5brZ redis-5.0.8]# yum install gcc-c++
    //因为Redis是c++写的
    [root@iZbp138sn4z9yrtqweb5brZ redis-5.0.8]# gcc -v
    Using built-in specs.
    COLLECT_GCC=gcc
    COLLECT_LTO_WRAPPER=/usr/libexec/gcc/x86_64-redhat-linux/4.8.5/lto-wrapper
    Target: x86_64-redhat-linux
    Configured with: ../configure --prefix=/usr --mandir=/usr/share/man --infodir=/usr/share/info --with-bugurl=http://bugzilla.redhat.com/bugzilla --enable-bootstrap --enable-shared --enable-threads=posix --enable-checking=release --with-system-zlib --enable-__cxa_atexit --disable-libunwind-exceptions --enable-gnu-unique-object --enable-linker-build-id --with-linker-hash-style=gnu --enable-languages=c,c++,objc,obj-c++,java,fortran,ada,go,lto --enable-plugin --enable-initfini-array --disable-libgcj --with-isl=/builddir/build/BUILD/gcc-4.8.5-20150702/obj-x86_64-redhat-linux/isl-install --with-cloog=/builddir/build/BUILD/gcc-4.8.5-20150702/obj-x86_64-redhat-linux/cloog-install --enable-gnu-indirect-function --with-tune=generic --with-arch_32=x86-64 --build=x86_64-redhat-linux
    Thread model: posix
    gcc version 4.8.5 20150623 (Red Hat 4.8.5-39) (GCC) 
    [root@iZbp138sn4z9yrtqweb5brZ redis-5.0.8]# 
    ```

  + ```bash
    [root@iZbp138sn4z9yrtqweb5brZ redis-5.0.8]#make
    
    [root@iZbp138sn4z9yrtqweb5brZ redis-5.0.8]#make
    
    [root@iZbp138sn4z9yrtqweb5brZ redis-5.0.8]# make install
    cd src && make install
    make[1]: Entering directory `/opt/redis-5.0.8/src'
    
    Hint: It's a good idea to run 'make test' ;)
    
        INSTALL install
        INSTALL install
        INSTALL install
        INSTALL install
        INSTALL install
    make[1]: Leaving directory `/opt/redis-5.0.8/src'
    ```

+ redis默认安装路径

  + ```bash
    [root@iZbp138sn4z9yrtqweb5brZ redis-5.0.8]# cd /usr/local/bin/
    [root@iZbp138sn4z9yrtqweb5brZ bin]# ls
    libmcrypt-config  mcrypt  mdecrypt  redis-benchmark  redis-check-aof  redis-check-rdb  redis-cli  redis-sentinel  redis-server
    ```

+ 拷贝配置文件到安装目录下的RedisConfig目录下

  + ```bash
    [root@iZbp138sn4z9yrtqweb5brZ bin]# mkdir RedisConfig
    [root@iZbp138sn4z9yrtqweb5brZ bin]# ls
    libmcrypt-config  mcrypt  mdecrypt  redis-benchmark  redis-check-aof  redis-check-rdb  redis-cli  ==RedisConfig==  redis-sentinel  redis-server
    [root@iZbp138sn4z9yrtqweb5brZ bin]# cp /opt/redis-5.0.8/redis.conf RedisConfig/
    [root@iZbp138sn4z9yrtqweb5brZ bin]# cd RedisConfig/
    [root@iZbp138sn4z9yrtqweb5brZ RedisConfig]# ls
    redis.conf
    [root@iZbp138sn4z9yrtqweb5brZ RedisConfig]# 
    ```

+ redis默认不是后台启动的，需要修改配置文件

  + ```bash
    # By default Redis does not run as a daemon. Use 'yes' if you need it.
    # Note that Redis will write a pid file in /var/run/redis.pid when daemonized.
    ==daemonize yes==
    ```

# 启动Redis服务

+ 通过我们指定的配置文件启动服务

  + ```bash
    [root@iZbp138sn4z9yrtqweb5brZ bin]# cd /usr/local/bin/
    [root@iZbp138sn4z9yrtqweb5brZ bin]# ls
    libmcrypt-config  mcrypt  mdecrypt  redis-benchmark  redis-check-aof  redis-check-rdb  redis-cli  RedisConfig  redis-sentinel  redis-server
    [root@iZbp138sn4z9yrtqweb5brZ bin]# redis-server RedisConfig/redis.conf 
    21548:C 27 Mar 2020 18:05:35.742 # oO0OoO0OoO0Oo Redis is starting oO0OoO0OoO0Oo
    21548:C 27 Mar 2020 18:05:35.742 # Redis version=5.0.8, bits=64, commit=00000000, modified=0, pid=21548, just started
    21548:C 27 Mar 2020 18:05:35.742 # Configuration loaded
    [root@iZbp138sn4z9yrtqweb5brZ bin]# 
    ```

+ 连接服务(默认是本机)

  + ```bash
    [root@iZbp138sn4z9yrtqweb5brZ bin]# redis-cli -h 127.0.0.1 -p 6379
    127.0.0.1:6379> 
    ```

+ 查看redis进程运行情况

  + ```ba
    [root@iZbp138sn4z9yrtqweb5brZ ~]# ps -ef|grep redis
    root     21549     1  0 18:05 ?        00:00:00 redis-server 127.0.0.1:6379
    root     21817 10193  0 18:10 pts/0    00:00:00 redis-cli -h 127.0.0.1 -p 6379
    root     22018 21954  0 18:14 pts/1    00:00:00 grep --color=auto redis
    ```

+ 关闭服务

  + ```bash
    127.0.0.1:6379> shutdown
    not connected> exit
    ```

  + ```bash
    [root@iZbp138sn4z9yrtqweb5brZ ~]# ps -ef|grep redis
    root     22189 21954  0 18:17 pts/1    00:00:00 grep --color=auto redis
    ```

