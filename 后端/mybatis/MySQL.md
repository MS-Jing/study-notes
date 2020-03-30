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

