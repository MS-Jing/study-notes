# 什么是Redis

Redis(Remote Dictionary Server) 远程字典服务

> Redis能干嘛？
>
> + 内存存储，也可以持久化（rdb,aof）
> + 效率高，可以用于高速缓存
> + 发布订阅系统
> + 地图信息分析
> + 计时器，计数器
> + 。。。

+ Redis是一个开源的，内存中的数据结构存储系统，它可以用作**数据库**，**缓存**和**消息中间件**。

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

# 基础知识

+ redis默认有16个数据库
  + 默认使用第0个数据库
  + 可以使用select进行切换数据库！
    + 切换到第三个数据库`select 3`
    + 查看当前数据库大小`dbsize`
  + 查看所有的key`keys *`
    + 清空当前数据库`flushdb`
    + 清空所有数据库`flushall`

+ Redis是单线程的！
  + Redis很快的，官方表示，Redis是基于内存操作的，CPU不是Redis的性能瓶颈，Redis的瓶颈是根据机器的==**内存**==和==**网络带宽**==
  + Redis是C语言写的
  + 高性能的服务器不一定是多线程的
  + 多线程不一定比单线程效率高
  + **==Redis将所有的数据全部放在内存中==**
  + 多线程（CPU会上下文切换，这样很消耗性能），对于内存来说，没有上下文切换，多次读写都在一个cpu

# Redis-key

+ 查看所有的键：`keys *`
+ 设置键值对` set name luojing`
+ 得到相应键对应的值`get name`
+ 判断某个键是否存在`exists name`
+ 移除某个键`del name`
+ 移动某个键到某个库`move name 1`	
+ 设置某个键的过期时间`expire name 10`     设置name10秒过期
  + 查看还有几秒过期 `ttl name`

+ 查看键对应的值的类型：`type name`



# 五大数据类型

## String（字符串）

+ 往一个字符串后追加一个字符串`append key1 jing`,如果当前key不存在相当于set了一个key

+ 获取某个值字符串的长度`strlen key`

+ 设置加1 (常用与网站的浏览量统计)

  + `set num 0`
  + `incr num`

+ 设置减一`decr num`

+ 设置自增步长 `incrby num 10`

+ 设置自减步长`decrby num 10`

+ 截取指定范围字符串`getrange key1 1 3`从0开始包括包括3

+ 替换字符串指定位置字符串

  + ```bash
    set key2 aaaaaaaaa
    setrange key2 1 xxx
    get key2
    "axxxaaaaa"
    ```

+ setex (set with expire)    设置过期时间

  + 设置一个key3 设置30秒过期
  + `setex key3 30 luojing`

+ setnx (set if not exist)      不存在再设置值

  + ```bash
    127.0.0.1:6379> setnx key4 luo
    (integer) 1    #不存在可以设置
    127.0.0.1:6379> setnx key4 jing
    (integer) 0		# 存在不允许设置值
    127.0.0.1:6379> get key4
    "luo"			#仍是“luo”
    ```

+ 批量设置值mset

  + ```bash
    127.0.0.1:6379> mset k1 v1 k2 v2 k3 v3
    OK
    ```

+ 都不存在再批量设置msetnx 

  + ```bash
    127.0.0.1:6379> msetnx k4 v4 k5 v5
    (integer) 1	#现在数据库没有k4,k5,所以批量set成功
    # 如果k4存在，k5不存在k5也不会设置成功哦
    127.0.0.1:6379> keys *
    1) "k1"
    2) "k5"
    3) "k2"
    4) "k4"
    5) "k3"
    127.0.0.1:6379> mget k1 k2 k3 k4 k5
    1) "v1"
    2) "v2"
    3) "v3"
    4) "v4"
    5) "v5"
    ```

+ 批量get值mget

  + ```bash
    127.0.0.1:6379> mget k1 k2 k3
    1) "v1"
    2) "v2"
    3) "v3"
    ```

+ 先get再set`getset`，就是返回修改前的值，再设置成现在的值

  + ```bash
    127.0.0.1:6379> getset name luo
    (nil)	#由于数据库里没有所以为空，但是现在已经设置了值
    127.0.0.1:6379> get name
    "luo"
    127.0.0.1:6379> getset name jing
    "luo"	#返回了上一次的值，再设置现在的值
    127.0.0.1:6379> get name
    "jing"
    ```



## List

**所有的list命令都是用l开头的**

+ 从列表的左边，往一个列表放值

  + ```bash
    127.0.0.1:6379> lpush list1 a
    (integer) 1
    127.0.0.1:6379> type list1
    list
    
    ```

+ 获取list中的值

  + ```bash
    127.0.0.1:6379> lrange list1 0 -1
    1) "c"
    2) "b"
    3) "a"
    ```

+ 从列表的右边，往一个列表放值

  + ```bash
    127.0.0.1:6379> rpush list1 d
    (integer) 4
    127.0.0.1:6379> lrange list1 0 -1
    1) "c"
    2) "b"
    3) "a"
    4) "d"
    
    ```

+ 从列表左边移除

  + ```bash
    127.0.0.1:6379> lrange list1 0 -1 #移除前
    1) "c"
    2) "b"
    3) "a"
    4) "d"
    127.0.0.1:6379> lpop list1	#从左边移除
    "c"
    127.0.0.1:6379> lrange list1 0 -1 #移除后
    1) "b"
    2) "a"
    3) "d"
    ```

  + 

+ 从列表右边移除

  + ```bash
    127.0.0.1:6379> rpop list1
    "d"
    127.0.0.1:6379> lrange list1 0 -1
    1) "b"
    2) "a"
    ```

+ 获取列表中的某个值

  + ```bash
    127.0.0.1:6379> lrange list1 0 -1
    1) "c"
    2) "b"
    3) "a"
    127.0.0.1:6379> lindex list1 2   #获取列表的下标为2的元素
    "a"
    ```

+ 获取列表长度`llen list1`

+ 移除集合中指定个数的值

  + ```bash
    127.0.0.1:6379> lpush list1 c
    (integer) 4
    127.0.0.1:6379> lrange list1 0 -1
    1) "c"
    2) "c"
    3) "b"
    4) "a"
    127.0.0.1:6379> lrem list1 1 a		#移除list1中的一个a
    (integer) 1
    127.0.0.1:6379> lrange list1 0 -1
    1) "c"
    2) "c"
    3) "b"
    127.0.0.1:6379> lrem list1 2 c    # 移除list1中的两个c
    (integer) 2
    127.0.0.1:6379> lrange list1 0 -1 
    1) "b"
    
    ```

+ 截断列表，获取列表中的一部分

  + ```bash
    127.0.0.1:6379> lrange list1 0 -1
    1) "d"
    2) "c"
    3) "b"
    4) "a"
    127.0.0.1:6379> ltrim list1 1 2
    OK
    127.0.0.1:6379> lrange list1 0 -1
    1) "c"
    2) "b"
    ```

+ 移除一个列表的最后一个元素，加到另外一个列表

  + ```bash
    127.0.0.1:6379> lrange list1 0 -1
    1) "c"
    2) "b"
    3) "a"
    127.0.0.1:6379> rpoplpush list1 list2 #移除list1中的"a"到list2中
    "a"
    127.0.0.1:6379> lrange list1 0 -1
    1) "c"
    2) "b"
    127.0.0.1:6379> lrange list2 0 -1
    1) "a"
    
    ```

+ 修改列表中指定下标的值

  + ```bash
    127.0.0.1:6379> lrange list1 0 -1
    1) "d"
    2) "c"
    3) "b"
    4) "a"
    127.0.0.1:6379> lset list1 3 x	#替换list1中的下标为3 的值为x，不存在此下标会报错
    OK
    127.0.0.1:6379> lrange list1 0 -1
    1) "d"
    2) "c"
    3) "b"
    4) "x"
    
    ```

+ 往列表中的指定值前面（或后面）插入一个值

  + ```bash
    127.0.0.1:6379> lrange list1 0 -1
    1) "d"
    2) "c"
    3) "b"
    4) "x"
    127.0.0.1:6379> linsert list1 before b xx 往list1中的b元素的前面插入'xx'
    (integer) 5
    127.0.0.1:6379> lrange list1 0 -1
    1) "d"
    2) "c"
    3) "xx"
    4) "b"
    5) "x"
    
    ```

> ==总结==
>
> + 实际上是一个链表
> + 如果移除了所有值，空链表也就代表不存在
> + 在两边插入或者改动值，效率最高。



## set（集合）

​	set中的值是不能重复的

+ 往set集合中添加一个值`sadd set1 aaa`
+ 获取所有的元素`smembers set1`

+ 判断是否有某个元素`sismember set1 aaa`
+ 获取set集合中的元素个数`scard set1`
+ 移除set中的某个元素`srem set1 aaa`
+ set 无序不重复的集合。可以用来抽随机
  
+ 随机抽一个元素`srandmember set1 1`
  
+ 随机移除一个元素`spop set1 1`
+ 将一个指定的值，移动到另外一个set集合中
  
+ `smove set1 set2 a`将set1中的a元素移动到set2中
  
+ 集合的差集

  + ```shell
    127.0.0.1:6379> smembers set2
    1) "f"
    2) "a"
    3) "e"
    127.0.0.1:6379> smembers set1
    1) "a"
    2) "b"
    3) "c"
    4) "d"
    127.0.0.1:6379> sdiff set1 set2
    1) "b"
    2) "c"
    3) "d"
    # set1有的但是set2中没有的
    ```

+ 集合交集

  + ```shell
    127.0.0.1:6379> sinter set1 set2
    1) "a"
    # set1和set2都有的
    ```

+ 集合并集

  + ```shell
    127.0.0.1:6379> sunion set1 set2
    1) "d"
    2) "b"
    3) "c"
    4) "e"
    5) "a"
    6) "f"
    ```

## Hash(哈希)

​		想象成一个map集合   key:  Map集合。这个值是一个map集合

+ 向hash中放入一个name:luojing的键值对

  + ```shell
    127.0.0.1:6379> hset hash1 name luojing
    (integer) 1
    ```

+ 获取hash中的name字段

  + ```shell
    127.0.0.1:6379> hget hash1 name
    "luojing"
    ```

+ 同时设置多个字段，同时获取多个字段

  + ```shell
    127.0.0.1:6379> hmset hash1 name jing age 20
    OK
    127.0.0.1:6379> hmget hash1 name age
    1) "jing"
    2) "20"
    ```

+ 将一个key中的所有键值取出来

  + ```shell
    127.0.0.1:6379> hgetAll hash1
    1) "name"
    2) "jing"
    3) "age"
    4) "20"
    ```

+ 删除hash中的某个字段

  + ```shell
    127.0.0.1:6379> hdel hash1 name
    (integer) 1
    127.0.0.1:6379> hgetAll hash1
    1) "age"
    2) "20"
    ```

+ 获得hash中的字段个数

  + ```shell
    127.0.0.1:6379> hlen hash1
    (integer) 1
    ```

+ 判断某个hash字段是否存在

  + ```shell
    127.0.0.1:6379> hexists hash1 age
    (integer) 1
    ```

+ 只获得所有的字段

  + ```shell
    127.0.0.1:6379> hkeys hash1
    1) "age"
    2) "name"
    ```

+ 只获取所有的字段对应的值

  + ```shell
    127.0.0.1:6379> hvals hash1
    1) "20"
    2) "luojing"
    ```

+ 指定字段值自增或者自减

  + ```shell
    127.0.0.1:6379> hincrby hash1 age 1
    (integer) 21
    127.0.0.1:6379> hget hash1 age
    "21"
    ################
    127.0.0.1:6379> hincrby hash1 age -10
    (integer) 11
    127.0.0.1:6379> hget hash1 age
    "11"
    ```

+ 如果hash中字段不存在再创建

  + ```shell
    127.0.0.1:6379> hkeys hash1
    1) "age"
    2) "name"
    127.0.0.1:6379> hsetnx hash1 sex 1
    (integer) 1
    127.0.0.1:6379> hsetnx hash1 name aaa
    (integer) 0
    127.0.0.1:6379> hgetall hash1
    1) "age"
    2) "11"
    3) "name"
    4) "luojing"
    5) "sex"
    6) "1"
    
    ```



## Zset(有序集合)

​		增加了一个权值

+ 增加一个值

  + ```shell
    127.0.0.1:6379> zadd zset1 1 not 
    (integer) 1
    #往zset1的集合中增加了一个‘not’元素，他的权重为1
    ```

+ 获取全部的值

  + ```shell
    127.0.0.1:6379> zrange zset1 0 -1
    1) "not"
    2) "tw"
    3) "two"
    4) "three"
    ```

+ 排序

  + ```shell
    127.0.0.1:6379> zadd zset2 2500 zhangsan 5000 lisi 200 wangwu
    (integer) 3
    # 张三 2500元，李四 5000元，王五 200元
    ```

  + ```shell
    # 从无限小到无限大排序
    127.0.0.1:6379> zrangebyscore zset2 -inf +inf
    1) "wangwu"
    2) "zhangsan"
    3) "lisi"
    # 从无限小到无限大排序 带上钱数
    127.0.0.1:6379> zrangebyscore zset2 -inf +inf withscores
    1) "wangwu"
    2) "200"
    3) "zhangsan"
    4) "2500"
    5) "lisi"
    6) "5000"
    # 从无限大到无限小排序。带上钱数
    127.0.0.1:6379> zrevrangebyscore zset2 +inf -inf withscores
    1) "lisi"
    2) "5000"
    3) "zhangsan"
    4) "2500"
    5) "wangwu"
    6) "200"
    
    ```

+ 移除某个元素

  + ```shell
    127.0.0.1:6379> zrange zset2 0 -1
    1) "wangwu"
    2) "wangwu2"
    3) "zhangsan"
    4) "lisi"
    127.0.0.1:6379> zrem zset2 wangwu2
    (integer) 1
    127.0.0.1:6379> zrange zset2 0 -1
    1) "wangwu"
    2) "zhangsan"
    3) "lisi"
    ```

+ 查看zset集合中的元素个数

  + ```shell
    127.0.0.1:6379> zcard zset2
    (integer) 3
    ```

+ 区间计算

  + ```shell
    127.0.0.1:6379> zcount zset2 350 3000
    (integer) 1
    # 计算成绩在350-3000的元素个数
    ```




# 三种特殊数据类型

## geospatial(地理位置)

[城市经纬度查询](http://www.jsons.cn/lngcode/)

+ 添加地理位置

  + ```shell
    127.0.0.1:6379> geoadd city 116.40 39.90 beijin
    (integer) 1
    127.0.0.1:6379> geoadd city 121.47 31.23 shanghai
    (integer) 1
    127.0.0.1:6379> geoadd city 106.50 29.53 chongqing
    (integer) 1
    127.0.0.1:6379> geoadd city 114.08 22.54 shengzheng
    (integer) 1
    127.0.0.1:6379> geoadd city 120.16 30.24 hangzhou
    (integer) 1
    127.0.0.1:6379> geoadd city 108.93 34.23 xian
    (integer) 1
    
    ```

+ 查询北京的经纬度

  + ```shell
    127.0.0.1:6379> geopos city beijin
    1) 1) "116.39999896287918091"
       2) "39.90000009167092543"
       
    ```

+ 两地之间的距离

  + 单位：

    + m 米
    + km 千米
    + mi 英里
    + ft 英尺

  + ```shell
    127.0.0.1:6379> geodist city beijin xian km
    "914.3715"
    #北京和西安的距离，km单位
    ```

+ 以给定的经度纬度为中心，找到某一半径内的元素

  + ```shell
    # 以经度纬度为110 30 为中心的1000km为半径的城市
    127.0.0.1:6379> georadius city 110 30 1000 km
    1) "chongqing"
    2) "xian"
    3) "shengzheng"
    4) "hangzhou"
    #限制只显示最近的两个
    127.0.0.1:6379> georadius city 110 30 1000 km count 2
    1) "chongqing"
    2) "xian"
    
    ```

+ 以一个城市为中心，找到某一半径内的元素

  + ```shell
    #以北京为中心。1000km的城市
    127.0.0.1:6379> georadiusbymember city beijin 1000 km
    1) "beijin"
    2) "xian"
    
    ```

+ 所有的城市

  + ```shell
    127.0.0.1:6379> zrange city 0 -1
    1) "chongqing"
    2) "xian"
    3) "shengzheng"
    4) "hangzhou"
    5) "shanghai"
    6) "beijin"
    ```

+ 移除一个城市

  + ```shell
    127.0.0.1:6379> zrem city beijin
    (integer) 1
    127.0.0.1:6379> zrange city 0 -1
    1) "chongqing"
    2) "xian"
    3) "shengzheng"
    4) "hangzhou"
    5) "shanghai"
    
    ```


## Hyperloglog

> 简介：

+ 一种数据结构
+ 基数统计算法
+ 占用的内存固定 

> 使用

+ 放一组数据

  + ```shell
    127.0.0.1:6379> pfadd mykey a b c d e f g h i j
    (integer) 1
    ```

+ 统计存放的元素（不会重复）

  + ```shell
    127.0.0.1:6379> pfcount mykey
    (integer) 10
    ```

+ 合并两个集合，到第三个

  + ```shell
    127.0.0.1:6379> pfadd mykey a b c d e f g h i j
    (integer) 1
    127.0.0.1:6379> pfcount mykey
    (integer) 10
    127.0.0.1:6379> pfadd mykey2 i j z x c v b n m
    (integer) 1
    127.0.0.1:6379> pfmerge mykey3 mykey mykey2
    OK
    127.0.0.1:6379> pfcount mykey3
    (integer) 15
    ```

## Bitmaps

+ 他是位存储，位图，操作二进制位来进行记录，只有0和1两个状态

> 使用

+ 加入一个值

  + ```shell
    #  第一个位置为0
    127.0.0.1:6379> setbit bit1 0 0
    (integer) 0
    #设置第二位为1
    127.0.0.1:6379> setbit bit1 1 1
    (integer) 0
    ```

+ 查看第二位的状态

  + ```shell
    127.0.0.1:6379> getbit bit1 1
    (integer) 1
    ```

+ 统计为1的个数

  + ```shell
    127.0.0.1:6379> setbit bit1 2 1
    (integer) 0
    127.0.0.1:6379> bitcount bit1
    (integer) 2
    
    ```


# 事务

要么同时成功，要么同时失败-----原子性

+ ==redis事务不保证原子性==
+ ==Redis事务没有隔离级别的概念==

+ Redis事务本质：一组命令的集合！一个事务中的所有命令都会被序列化，在事务执行过程中，会按照顺序执行！
+ 一次性，顺序性，排他性
+ 所有的命令在事务中，并没有直接被执行！只有发起执行命令的时候才会执行！
+ 事务：
  + 开启事务（multi）
  + 命令入队（）
  + 执行事务（exec）

```shell
127.0.0.1:6379> multi	#开启事务
OK
127.0.0.1:6379> set k1 v1 #命令入队列
QUEUED
127.0.0.1:6379> set k2 v2
QUEUED
127.0.0.1:6379> get k2
QUEUED
127.0.0.1:6379> set k3 v3
QUEUED
127.0.0.1:6379> exec   #执行事务
1) OK
2) OK
3) "v2"
4) OK
```

+ 取消事务：`discard`

+ 出现编译型异常（代码有错），事务中的所有命令都不会执行

+ 运行时异常：执行命令时，其他命令正常执行，错误命令抛出异常

## Redis实现乐观锁

> 监控   

**悲观锁：**

+ 很悲观，认为什么时候都会出问题无论做什么都会加锁！很消耗性能

**乐观锁：**watch

+ 很乐观，认为什么时候都不会出问题，所以不会上锁！更新数据的时候判断一下，在此期间是否有人修改过这个数据！version
+ 获取version
+ 更新的时候比较version

# jedis

> 官方推荐的java连接开发工具

导入依赖：

```xml
<!-- https://mvnrepository.com/artifact/redis.clients/jedis -->
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>3.2.0</version>
</dependency>

<!--fastjson-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.62</version>
</dependency>
```

步骤：

+ 连接数据库
+ 操作命令
+ 断开连接！

# Springboot整合

在springboot2.X之后，原来使用的jedis被替换成了lettuce

依赖：

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

配置连接：

```yaml
spring:
  redis:
    host: 47.98.164.190
    port: 6379
```

测试：

```java
@Autowired
private RedisTemplate redisTemplate;

@Test
void contextLoads() {

    //opsForValue操作字符串，类似于String
    redisTemplate.opsForValue().set("name","aaa");
    System.out.println(redisTemplate.opsForValue().get("name"));

}
```

# 自定义RedisTemplate

```java
package com.lj.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory)
            throws UnknownHostException {

        RedisTemplate<String, Object> template = new RedisTemplate<>();

        template.setConnectionFactory(factory);

        //json序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //String序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        //key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        //hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        //value序列化方式采用jackjson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //hash的value序列化方式也采用Jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();


        return template;
    }
}
```

# 配置文件

```bash
bind 127.0.0.1  #绑定的ip
protected-mode yes #保护模式
port 6379 #端口设置

daemonize yes # 以守护进程的方式运行
pidfile /var/run/redis_6379.pid #如果以后台方式运行，我们需要制定一个pid进程文件

#日志级别
# Specify the server verbosity level.
# This can be one of:
# debug (a lot of information, useful for development/testing)
# verbose (many rarely useful info, but not a mess like the debug level)
# notice (moderately verbose, what you want in production probably)
# warning (only very important / critical messages are logged)
loglevel notice

#日志文件位置名
# Specify the log file name. Also the empty string can be used to force
# Redis to log on the standard output. Note that if you use standard
# output for logging but daemonize, logs will be sent to /dev/null
logfile ""

databases 16#默认数据库个数
always-show-logo yes #是否显示log
```

> 快照

+ 在规定的时间内，执行了多少次操作，则会持久化到文件
+ 持久化的文件：`.rdb`,`.aof`文件

```bash
save 900 1 #900s内，至少有一个key有修改，就持久化到文件
save 300 10
save 60 10000

#持久化失败是否继续工作
stop-writes-on-bgsave-error yes

#是否压缩rdb文件
rdbcompression yes

#保存rdb文件时，是否校验rdb文件
rdbchecksum yes

#持久化文件保存目录
dir ./

```

> SECURITY 安全

```bash
#密码
requirepass foobared

#设置密码
config set requirepass 123456
#密码验证
auth 123456
```

> APPEND ONLY MODE 模式 aof配置

```bash
#默认不开启aof模式
appendonly no
#持久化文件名：
appendfilename "appendonly.aof"

# appendfsync always   #每次修改都会同步
appendfsync everysec #每秒执行一次sync(同步)，可能会丢失这一秒的数据
# appendfsync no

```

# Redis持久化

## RDB（Redis DataBase）

​	在指定的时间间隔内将内存中的数据写入磁盘，恢复的时候将快照的文件直接读到内存中

​	Redis会单独创建（fork）一个子进程来进行持久化，会先将数据写入到一个临时文件，待持久化过程都结束了，再用这个临时文件替换上一次持久化好的文件。整个过程中，主进程是不进行任何的io操作，这就确保了极高的性能。如果需要进行大规模数据的恢复，且对于数据恢复的完整性不是非常敏感，但RDB比AOF方式更加高效。RDB的缺点是最后一次持久化后的数据可能丢失。

​	优点：

+ 适合大规模的数据恢复
+ 对数据的完整性要求不高

   缺点：

+ 需要一定的时间间隔进程操作
+ fork进程的时候，会占用一定的内存空间

## AOF(Append Only File)

​	以日志的形式记录每一个写的操作。将Redis执行过的所有指令记录下来（读操作不记录），只允许追加文件但不可以改写文件，Redis启动之初会读取该文件重新创建数据，换而言之。redis重启的话会根据日志文件的内容将写指令从前到后执行一次，以完成数据的恢复

==appendonly.aof文件==

> 开启配置

```shell
appendonly yes
```

如果aof文件出错，redis启动不起来，可以使用`redis-check-aof --fix appendonly.aof`来修复

 **优点：**

+ 每次修改都同步，文件的完整会更好
+ 每秒同步一次，可能会丢失一秒的数据

**缺点：**

+ aof会在工作中越来越大，大于rdb,修复的速度也比rdb慢
+ aof运行效率慢

# Redis发布订阅

redis发布订阅（pub/sub）是一种消息通信模式：发送者（pub）发送消息。订阅者（sub）接收消息

redis客户端可以订阅任意数量的频道

## Redis 发布订阅命令

下表列出了 redis 发布订阅常用命令：

| 序号 | 命令及描述                                                   |
| :--- | :----------------------------------------------------------- |
| 1    | [PSUBSCRIBE pattern [pattern ...\]](https://www.runoob.com/redis/pub-sub-psubscribe.html) 订阅一个或多个符合给定模式的频道。 |
| 2    | [PUBSUB subcommand [argument [argument ...\]]](https://www.runoob.com/redis/pub-sub-pubsub.html) 查看订阅与发布系统状态。 |
| 3    | [PUBLISH channel message](https://www.runoob.com/redis/pub-sub-publish.html) 将信息发送到指定的频道。 |
| 4    | [PUNSUBSCRIBE [pattern [pattern ...\]]](https://www.runoob.com/redis/pub-sub-punsubscribe.html) 退订所有给定模式的频道。 |
| 5    | [SUBSCRIBE channel [channel ...\]](https://www.runoob.com/redis/pub-sub-subscribe.html) 订阅给定的一个或多个频道的信息。 |
| 6    | [UNSUBSCRIBE [channel [channel ...\]]](https://www.runoob.com/redis/pub-sub-unsubscribe.html) 指退订给定的频道。 |























