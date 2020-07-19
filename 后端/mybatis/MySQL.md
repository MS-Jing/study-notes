# mysql基础知识

我们的网站、APP。这些东西上面都存在大量的信息，这些信息都需要有地方存储，存储在哪呢？数据库。

如果我们需要开发一个网站、app，数据库我们必须掌握的技术，常用的数据库有mysql、oracle、sqlserver、db2等。

## 常见概念

**DB**：数据库，存储数据的容器。

**DBMS**：数据库管理系统，又称为数据库软件或数据库产品，用于创建或管理DB。

**SQL**：结构化查询语言，用于和数据库通信的语言，不是某个数据库软件持有的，而是几乎所有的主流数据库软件通用的语言。中国人之间交流需要说汉语，和美国人之间交流需要说英语，和数据库沟通需要说SQL语言。

## 启动mysql服务

下载安装可直接去官网即可：

安装之后需要启动服务：

+ 以管理员身份打开cmd
+ 启动命令：`net start mysql`
+ 停止命令：`net stop mysql`

## 登陆命令

`mysql -h ip -P 端口 -u 用户名 -p`

**说明：**

+ -P 大写的P后面跟端口号
+ 如果是本地登陆可以省略ip和端口

## 常见的其他命令

**查看数据库版本：**

> + `mysql --version`用于在未登录情况下，查看本机版本
> + `select version`:登陆情况下查看

**显示所有数据库：**

> `show databases;`

**使用指定数据库：**

> `use 库名；`

**显示当前使用库所有的表:**

> `show tables;`

**查看表结构：**

> `desc 表名`

## mysql语法规范

1. 不区分大小写，但建议关键字大写，表名、列名小写

2. 每条命令最好用英文分号结尾

3. 每条命令根据需要，可以进行缩进或换行

4. 注释

5. - 单行注释：#注释文字
   - 单行注释：-- 注释文字  ，注意， 这里需要加空格
   - 多行注释：/* 注释文字  */

## SQL的语言分类

- **DQL（Data Query Language）**：数据查询语言
  select 相关语句
- **DML（Data Manipulate Language）**：数据操作语言
  insert 、update、delete 语句
- **DDL（Data Define Languge）**：数据定义语言
  create、drop、alter 语句
- **TCL（Transaction Control Language）**：事务控制语言
  set autocommit=0、start transaction、savepoint、commit、rollback

# mysql数据类型（重点）

**数据类型：**

> **bit**,**bool**,**tinyint**,**smallint**,**mediumint**,**int**,**bigint**

**浮点型类型：**

> **float**,**double**,**decimal**

**字符串类型：**

> **char**,**varchar**,**tinyblob**,**blob**,**mediumblob**,**longblob**,**tinytext**,**text**,**mediumtext**,**longtext**

**日期类型：**

>**Date**,**DateTime**，**TimeStamp**,**Time**,**Year**

## 整数类型

| 类型                      | 字节数 | 无符号值范围 | 有符号范围       |
| ------------------------- | ------ | ------------ | ---------------- |
| tinyint[(n)] [unsigned]   | 1      | [0,2^8^-1]   | [-2^7^,2^7^-1]   |
| smallint[(n)] [unsigned]  | 2      | [0,2^16^-1]  | [-2^15^,2^15^-1] |
| mediumint[(n)] [unsigned] | 3      | [0,2^24^-1]  | [-2^23^,2^23^-1] |
| int[(n)] [unsigned]       | 4      | [0,2^32^-1]  | [-2^31^,2^31^-1] |
| bigint[(n)] [unsigned]    | 8      | [0,2^64^-1]  | [-2^63^,2^63^-1] |

[]内的内容是可选的，默认是有符号类型，无符号类型在后面加上unsigned

> **实例：有符号类型**

```mysql
-- 创建了demo1表，有一个c1字段 类型为tinyint 有符号
mysql> create table demo1(
      c1 tinyint
     );
Query OK, 0 rows affected (0.01 sec)

mysql> insert into demo1 values(-pow(2,7)),(pow(2,7)-1);
Query OK, 2 rows affected (0.00 sec)
Records: 2  Duplicates: 0  Warnings: 0

mysql> select * from demo1;
+------+
| c1   |
+------+
| -128 |
|  127 |
+------+
2 rows in set (0.00 sec)

-- 超出tinyint的范围了报错
mysql> insert into demo1 values(pow(2,7));
ERROR 1264 (22003): Out of range value for column 'c1' at row 1
```

> **实例：无符号类型**

```mysql
-- 创建了demo2表，有一个c1字段 类型为tinyint 无符号
mysql> create table demo2(
      c1 tinyint unsigned
     );
Query OK, 0 rows affected (0.01 sec)

-- c1是无符号的tinyint类型的，插入了负数会报错。
mysql> insert into demo2 values (-1);
ERROR 1264 (22003): Out of range value for column 'c1' at row 1
-- 超出范围会报错
mysql> insert into demo2 values (pow(2,8)+1);
ERROR 1264 (22003): Out of range value for column 'c1' at row 1

mysql> insert into demo2 values (0),(pow(2,8)-1);
Query OK, 2 rows affected (0.00 sec)
Records: 2  Duplicates: 0  Warnings: 0

mysql> select * from demo2;
+------+
| c1   |
+------+
|    0 |
|  255 |
+------+
2 rows in set (0.00 sec)
```

> **类型（n）的说明**

​		在开发中，我们会碰到有些定义整型的写法是int(11)，`int(N)`我们只需要记住两点：

+ 无论N等于多少，int永远占4个字节
+ **N表示的是显示宽度，不足的用0补足，超过的无视长度而直接显示整个数字，但这要整型设置了unsigned zerofill才有效**

```mysql
mysql> CREATE TABLE test3 (
       `a` int,
       `b` int(5),
       `c` int(5) unsigned,
       `d` int(5) zerofill,
       `e` int(5) unsigned zerofill,
       `f` int    zerofill,
       `g` int    unsigned zerofill
     );
Query OK, 0 rows affected (0.01 sec)

mysql> insert into test3 values (1,1,1,1,1,1,1),(11,11,11,11,11,11,11),(12345,12345,12345,12345,12345,12345,12345);
Query OK, 3 rows affected (0.00 sec)
Records: 3  Duplicates: 0  Warnings: 0

mysql> select * from test3;
+-------+-------+-------+-------+-------+------------+------------+
| a     | b     | c     | d     | e     | f          | g          |
+-------+-------+-------+-------+-------+------------+------------+
|     1 |     1 |     1 | 00001 | 00001 | 0000000001 | 0000000001 |
|    11 |    11 |    11 | 00011 | 00011 | 0000000011 | 0000000011 |
| 12345 | 12345 | 12345 | 12345 | 12345 | 0000012345 | 0000012345 |
+-------+-------+-------+-------+-------+------------+------------+
3 rows in set (0.00 sec)

mysql> show create table test3;
| Table | Create Table                                                   
| test3 | CREATE TABLE `test3` (
  `a` int(11) DEFAULT NULL,
  `b` int(5) DEFAULT NULL,
  `c` int(5) unsigned DEFAULT NULL,
  `d` int(5) unsigned zerofill DEFAULT NULL,
  `e` int(5) unsigned zerofill DEFAULT NULL,
  `f` int(10) unsigned zerofill DEFAULT NULL,
  `g` int(10) unsigned zerofill DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8
1 row in set (0.00 sec)
```

注意：

> ​	`show create table test3;`输出了表`test3`的创建语句，和我们原始的创建语句不一致了，原始的`d`字段用的是无符号的，可以看出当使用了`zerofill`自动会将无符号提升为有符号。
>
> ​	int(5)输出宽度不满5时，前面用0来进行填充
>
> ​	int(n)中的n省略的时候，==**宽度为对应类型无符号最大值的十进制的长度**==，如bigint无符号最大值为2的64次方-1等于18,446,744,073,709,551,615‬；长度是20位

​	

















# 事务

## 什么是事务？

**要么都成功要么都失败**

> **事务原则**：ACID 原则，原子性，一致性，隔离性，持久性

**原子性（Atomicity）**
原子性是指事务是一个不可分割的工作单位，事务中的操作要么都发生，要么都不发生。
**一致性（Consistency）**
事务前后数据的完整性必须保持一致。
**隔离性（Isolation）**
事务的隔离性是多个用户并发访问数据库时，数据库为每一个用户开启的事务，不能被其他事务的操作数据所干扰，多个并发事务之间要相互隔离。
**持久性（Durability）**
持久性是指一个事务一旦被提交，它对数据库中数据的改变就是永久性的，接下来即使数据库发生故障也不应该对其有任何影响
————————————————
版权声明：本文为CSDN博主「dengjili」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/dengjili/article/details/82468576

## 执行事务

MYSQL是默认开启事务自动提交的

**关闭事务自动提交**

```mysql
SET autocommit = 0 //设置为1开启
```

**事务开启**

```mysql
start transaction //标记一个事务的开启
```

**提交**

```mysql
commit
```

**保存点**

```mysql
savepoint 保存点名
```

**撤销指定的保存点**

```mysql
release savepoint 保存点名
```

**回滚**

```mysql
rollback
rollback to savepoint 保存点名
```

**事务结束后要开启事务自动提交**

# JDBC

## 数据库驱动

不同的数据库有不同的数据库驱动。而Sun公司为了简化开发人员的（对数据库的统一）操作。提供了一个（java操作数据库的）规范JDBC

对于开发人员来说，只需要掌握JDBC接口的操作即可

## 第一个jdbc程序

```java
package my.mybatis.mysql;

import java.sql.*;

/**
 * 这是一个jdbc的java程序，主要用于理解java连接数据的原理
 * 注意需要导入java驱动包
 */
public class JdbcTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1，加载驱动
        Class.forName("com.mysql.jdbc.Driver");

        //2，设置用户信息和url  userUnicode=true支持中文编码  useSSL=true使用安全连接
        String url = "jdbc:mysql://localhost:3306/mybatis?userUnicode=true&characterEncoding=utf8&useSSL=true";
        String username = "root";
        String password = "612612";

        //3，连接成功，然后获取到数据库对象
        Connection connection = DriverManager.getConnection(url, username, password);

        //4，通过数据库对象来获取执行SQL的对象
        Statement statement = connection.createStatement();

        //5，通过执行对象来执行SQL
        String sql = "select * from user;";
        ResultSet resultSet = statement.executeQuery(sql);

        while(resultSet.next()){
            System.out.println(resultSet.getString("username"));
        }

        //6，释放连接
        resultSet.close();
        statement.close();
        connection.close();
    }
}

```

## 对JDBC的封装

```properties
# 该配置文件用于封装jdbc的，主要理解mybatis框架封装原理
Driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/mybatis?userUnicode=true&characterEncoding=utf8&useSSL=true
username=root
password=612612
```

```java
package my.mybatis.mysql;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JdbcUtils {

    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static{
        try{
            //通过这个类的构造器去获取相关的资源文件,   得到一个输入流
            InputStream in = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbcutils.properties");
            Properties properties = new Properties();//创建一个Properties对象，然后去加载这个流到此对象中   有一个流异常
            properties.load(in);

            //获取相应的数据   获取的数据放到全局变量里，一定要加static，因为这个代码块是静态代码块
            driver = properties.getProperty("Driver");
            System.out.println(driver);
            url = properties.getProperty("url");
//            url = url.substring(1,url.length()-1);
            System.out.println(url);
            username = properties.getProperty("username");
//            username = username.substring(1,username.length()-1);
            System.out.println(username);
            password = properties.getProperty("password");
//            password = password.substring(1,password.length()-1);
            System.out.println(password);

            //开始加载驱动，由于驱动只用加载一次，所以在static代码块下
            Class.forName(driver);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    //获取连接
    public static Connection getConnection() throws SQLException {
         return DriverManager.getConnection(url, username, password);
    }
        
    //释放连接
    public static void release(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        if(resultSet != null){
            resultSet.close();
        }
        if (statement != null){
            statement.close();
        }
        if(connection != null){
            connection.close();
        }
    }
}
```

测试：

```java 
package my.mybatis.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class My {
    public static void main(String[] args) {
        try {
            Connection connection = JdbcUtils.getConnection();
            Statement statement = connection.createStatement();

            String sql = "select * from user;";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                System.out.println(resultSet.getString("username"));
            }

            JdbcUtils.release(connection,statement,resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
```

