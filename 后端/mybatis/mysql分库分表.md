# ShardingSphere

## 简介

+ 是一套开源的分布式数据库中间件解决方案
+ 主要产品：Sharding-JDBC和Sharding-Proxy
+ 定位为关系型数据库中间件

# 分库分表

数据库数据量不可控，随着时间和业务发展，造成表里面数据越来越多，如果再去对数据库表curd操作时，会有性能上的问题

分库分表是为了解决数据量过大造成的数据库性能降低问题

## 垂直切分

### 垂直分表

操作数据库中的某张表，把这张表的一部分字段存到一张表，再把另一部分存到另一张表。例如文章基本信息和文章内容，文章基本信息一个表文章内容一个表。

### 垂直分库

把单一数据库按照业务进行划分，专库专表。例如用户管理放一个库，订单管理放一个库。

## 水平切分

### 水平分库

同一个数据库的数据量过大，水平切分成两个一样的数据库。例如用户管理的库有十万条，可以拆分成用户A库和用户B库，各存5W条。

### 水平分表

同一张表的数据量过大，水平切分成两个一样的表。例如上面的用户A库，用户继续持续增加到10W，如果查找一个用户在一张表中查找一个用户太慢了，可以再进行拆分表。

## 问题

+ 数据库设计之初就应该考虑垂直分库分表
+ 当数据量增大时，不应该马上考虑水平切分。应该先考虑缓存、读写分离、索引等操作。这些方式解决不了再考虑
+ 跨节点连接查询问题。多个表在不同的数据库，业务需要连表查询需要多次查询
+ 多数据源管理麻烦。



# Sharding-JDBC

+ 轻量版的java框架，是增强版的JDBC驱动

+ 主要两个功能：数据分片和读写分离

## 水平分表

### 环境搭建

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.1.20</version>
</dependency>
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.0.5</version>
</dependency>
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.shardingsphere</groupId>
    <artifactId>sharding-jdbc-spring-boot-starter</artifactId>
    <version>4.0.0-RC1</version>
</dependency>
```

springboot版本为`2.2.5.RELEASE`

```mysql
# 创建课程库
create database course_db;
# 创建课程表1
create table course_1
(
    cid bigint not null comment '课程id',
    cname varchar(50) not null comment '课程名称',
    user_id bigint not null comment '课程创建的用户id',
    cstatus varchar(10) not null comment '课程状态',
    constraint course_1_pk
        primary key (cid)
)
    comment '课程表1';
# 创建课程表2
create table course_2
(
    cid bigint not null comment '课程id',
    cname varchar(50) not null comment '课程名称',
    user_id bigint not null comment '课程创建的用户id',
    cstatus varchar(10) not null comment '课程状态',
    constraint course_1_pk
        primary key (cid)
)
    comment '课程表2';
```

```java
@Data
public class Course {

    @TableId
    private Long cid;
    private String cname;
    private Long userId;
    private String cstatus;
}
```

```java
@Repository
public interface CourseMapper extends BaseMapper<Course> {
}
```



### 分片策略及其配置

```properties

#数据源名称
spring.shardingsphere.datasource.names=m1
#配置数据源的连接池
spring.shardingsphere.datasource.m1.type=com.alibaba.druid.pool.DruidDataSource
spring.shardingsphere.datasource.m1.driver-class-name=com.mysql.cj.jdbc.Driver
spring.shardingsphere.datasource.m1.url=jdbc:mysql://localhost:3306/course_db?serverTimezone=GMT%2B8
spring.shardingsphere.datasource.m1.username=root
spring.shardingsphere.datasource.m1.password=root

## 指定course表分布情况，配置表在那个数据库表名称
#m1数据源的course_1和course_2表
spring.shardingsphere.sharding.tables.course.actual-data-nodes=m1.course_$->{1..2}
#表的主键和主键生成策略
spring.shardingsphere.sharding.tables.course.key-generator.column=cid
spring.shardingsphere.sharding.tables.course.key-generator.type=SNOWFLAKE
#分片策略 约定cid的值是是奇数添加到course_2中否则到course_1中
spring.shardingsphere.sharding.tables.course.table-strategy.inline.sharding-column=cid
spring.shardingsphere.sharding.tables.course.table-strategy.inline.algorithm-expression=course_$->{cid % 2 + 1}

#打开sql输出日志
spring.shardingsphere.props.sql.show=true
# spring的DataSource和Druid的DataSource重名了。设置允许bean覆盖
spring.main.allow-bean-definition-overriding=true
```

测试：

```java
@SpringBootTest
class ShardingJdbcApplicationTests {

    @Autowired
    private CourseMapper courseMapper;

    @Test
    void add() {
        Course course = new Course();
        course.setCname("java");
        course.setUserId(100L);
        course.setCstatus("Normal");
        courseMapper.insert(course);
    }

    @Test
    void find() {
        System.out.println(courseMapper.selectById(1455480260152897538L));
    }

}
```















