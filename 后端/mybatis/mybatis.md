

# MyBatis

+ MyBatis 是一款优秀的**持久层**框架。
+ 支持定制化 SQL、存储过程以及高级映射
+ MyBatis 避免了几乎所有的 JDBC 代码和手动设置参数以及获取结果集。
+ MyBatis 可以对配置和原生Map使用简单的 XML 或注解，将接口和 Java 的 POJOs(Plain Old Java Objects,普通的 Java对象)映射成数据库中的记录。

+ MyBatis 本是[apache](https://baike.baidu.com/item/apache/6265)的一个开源项目[iBatis](https://baike.baidu.com/item/iBatis), 2010年这个项目由apache software foundation 迁移到了google code，并且改名为MyBatis 。2013年11月迁移到Github。

```xml
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.4.5</version>
</dependency>
```

# 第一个MyBatis程序

## 搭建环境

+ 搭建数据库

+ 新建项目

  + maven依赖：

  + ```xml
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.4.5</version>
    </dependency>
    <!--JUnit是一个Java语言的单元测试框架。-->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.10</version>
        <scope>test</scope>
    </dependency>
    <!--数据库驱动包-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.6</version>
        <scope>runtime</scope>
    </dependency>
    ```

+ 编写MyBatis核心配置文件

  + MyBatis-Config.xml

  + ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE configuration
            PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-config.dtd">
    
    <!--mybatis的主配置文件-->
    <configuration>
        <!-- 配置 mybatis 的环境 -->
        <environments default="mysql">  <!--这里mysql不固定-->
            <!-- 配置 mysql 的环境 -->
            <environment id="mysql">
                <!-- 配置事务的类型 -->
                <transactionManager type="JDBC"/>
                <!-- 配置连接数据库的信息：用的是数据源(连接池) -->
                <dataSource type="POOLED">
                    <property name="driver" value="com.mysql.jdbc.Driver"/>
                    <property name="url" value="jdbc:mysql://localhost:3306/mybatis?userUnicode=true&amp;characterEncoding=utf8&amp;useSSL=true"/>
                    <property name="username" value="root"/>
                    <property name="password" value="612612"/>
                </dataSource>
            </environment>
        </environments>
    
        <!-- 告知 mybatis 映射配置的位置 -->
        <mappers>
            <mapper resource="my/mybatis/dao/UserMapper.xml"/>
        </mappers>
    </configuration>
    ```

+ SqlSessionFactory和SqlSession
  + 每个基于MyBatis的应用都是以一个SqlSessionFactory的实例为核心的。SqlSessionFactory 的实例可以
    通过SqlSessionFactoryBuilder 获得。而SqlSessionFactoryBuilder则可以从XML配置文件或一个预先定
    制的Configuration的实例构建出SqlSessionFactory的实例。既然有了SqlSessionFactory,顾名思义,我们就可以从中获得SqISession的实例了。SqlSession 完全包含了面向数据库执行SQL命令所需的所有方法。你可以通过SqlSession实例来直接执行已映射的SQL语句

  + ```java
    package my.mybatis.utlis;
    
    import org.apache.ibatis.io.Resources;
    import org.apache.ibatis.session.SqlSession;
    import org.apache.ibatis.session.SqlSessionFactory;
    import org.apache.ibatis.session.SqlSessionFactoryBuilder;
    
    import java.io.IOException;
    import java.io.InputStream;
    
    //通过sqlSessionFactory获取sqlSession
    public class MyBatisUtils {
        private static SqlSessionFactory sqlSessionFactory;
    
        static {
            try {
                InputStream in = Resources.getResourceAsStream("MyBatis-Config.xml");
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        public static SqlSession getSqlSession(){
            return sqlSessionFactory.openSession();
        }
    }
    
    ```

+ 实体类

  + ```java
    package my.mybatis.pojo;
    
    import java.io.Serializable;
    import java.util.Date;
    
    public class User implements Serializable {
        private Integer id;
        private String username;
        private Date birthday;
        private String sex;
        private String address;
    
        public Integer getId() {
            return id;
        }
    
        public void setId(Integer id) {
            this.id = id;
        }
    
        public String getUsername() {
            return username;
        }
    
        public void setUsername(String username) {
            this.username = username;
        }
    
        public Date getBirthday() {
            return birthday;
        }
    
        public void setBirthday(Date birthday) {
            this.birthday = birthday;
        }
    
        public String getSex() {
            return sex;
        }
    
        public void setSex(String sex) {
            this.sex = sex;
        }
    
        public String getAddress() {
            return address;
        }
    
        public void setAddress(String address) {
            this.address = address;
        }
    
        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", birthday=" + birthday +
                    ", sex='" + sex + '\'' +
                    ", address='" + address + '\'' +
                    '}';
        }
    }
    ```

+ 映射接口

  + ```java
    package my.mybatis.dao;
    
    import my.mybatis.pojo.QueryVo;
    import my.mybatis.pojo.User;
    import my.mybatis.pojo.User2;
    
    import java.util.List;
    
    /**
     * 用户的持久层接口
     */
    public interface UserDao {
    
        /**
         * 查询所有用户
         * * @return
         * */
        List<User> getUserList();
    
        User getUserById(int id);
    
        int addUser(User user);
    
        int upDataUser(User user);
    
        int delUser(int id);
    
    }
    
    ```

+ 映射文件UserMapper.xml

  + ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <!DOCTYPE mapper
            PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    <mapper namespace="my.mybatis.dao.UserDao">
        
        <!-- 配置查询所有操作 -->
        <select id="getUserList" resultType="my.mybatis.pojo.User">
            select * from user
        </select>
        <select id="getUserById" resultType="my.mybatis.pojo.User" parameterType="int">
            select * from user where id = #{id}
        </select>
    
        <insert id="addUser" parameterType="my.mybatis.pojo.User">
            insert into user(id,birthday,username,sex,address)values (#{id},#{birthday},#{username},#{sex},#{address})
        </insert>
    
        <update id="upDataUser" parameterType="my.mybatis.pojo.User">
            update user set birthday=#{birthday} where id=#{id}
        </update>
    
        <delete id="delUser" parameterType="int">
            delete from user where id=#{id}
        </delete>
    </mapper>
    ```

  + namespace:绑定一个对应的Mapper接口

  + id:id不能乱写，必须要写Mapper下接口的方法名

  + resultType:得到的数据封装的路径的类型

  + parameterType：参数类型

  + **注意，增删改需要提交事务**

+ 测试

  + ```java
    @Test
        public void test(){
            SqlSession sqlSession = MyBatisUtils.getSqlSession();
            UserDao mapper = sqlSession.getMapper(UserDao.class);
            List<User> userList = mapper.getUserList();
            for (User item : userList) {
                System.out.println(item);
            }
            System.out.println(userList);
            sqlSession.close();
        }
    @Test
        public void test2(){
            SqlSession sqlSession = MyBatisUtils.getSqlSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User userById = mapper.getUserById(41);
            System.out.println(userById);
            sqlSession.close();
        }
        @Test
        public void test3(){
            SqlSession sqlSession = MyBatisUtils.getSqlSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = new User();
            user.setId(1);
            user.setUsername("静静");
            user.setBirthday(new Date());
            user.setSex("男");
            user.setAddress("四川");
            int i = mapper.delUser(1);
            System.out.println(i);
            sqlSession.commit();
    
            sqlSession.close();
        }
    ```

## 常见错误

+ y映射文件SQL语句的标签不对应
+ resources 需要使用路径`\`

# 模糊查询

接口：

```java
    //模糊查询
    List<User> getUserLike(String value);
```

映射文件：

```xml
<select id="getUserLike" resultType="my.mybatis.pojo.User">
    select * from user where username like #{value}
    -- 方法二
    select * from user where username like concat("%",#{value},"%")
    -- 方法三
    select * from user where username like "%"#{value}"%"
</select>
```

测试：

```java
@Test
public void getUserLike(){
    SqlSession sqlSession = MyBatisUtils.getSqlSession();
    UserMapper mapper = sqlSession.getMapper(UserMapper.class);

    List<User> userLike = mapper.getUserLike("%王%");
    for (User item : userLike) {
        System.out.println(item);
    }


    sqlSession.close();
}
```

# 配置解析

## 1.核心配置文件（MyBatis-Config。xml）

+ MyBatis 的配置文件包含了影响 MyBatis 行为甚深的设置（settings）和属性（properties）信息。

+ 配置环境（environments）
  
+ MyBatis 可以配置成适应多种环境**不过要记住：尽管可以配置多个环境，每个 SqlSessionFactory 实例只能选择其一。**
  
+ 事务管理器（transactionManager）
  + 在 MyBatis 中有两种类型的事务管理器（也就是 type="[JDBC|MANAGED]"）：
    - JDBC – 这个配置就是直接使用了 JDBC 的提交和回滚设置，它依赖于从数据源得到的连接来管理事务范围。
    - MANAGED – 这个配置几乎没做什么。它从来不提交或回滚一个连接，而是让容器来管理事务的整个生命周期（比如 JEE 应用服务器的上下文）。 默认情况下它会关闭连接，然而一些容器并不希望这样，因此需要将 closeConnection 属性设置为 false 来阻止它默认的关闭行为。
+ 数据源（DataSource）
  + dataSource 元素使用标准的 JDBC 数据源接口来配置 JDBC 连接对象的资源。
    - 许多 MyBatis 的应用程序将会按示例中的例子来配置数据源。然而它并不是必须的。要知道为了方便使用延迟加载，数据源才是必须的。有三种内建的数据源类型（也就是 type="[UNPOOLED|POOLED|JNDI]"）

+ 我们可以通过property属性来实现引用配置文件

+ 类型别名（typeAliases）

  + 为java类型设置一个短的名字
  + 为了用来减少类完全限定名的冗余

  + ```xml
    <!--为类起别名-->
    <typeAliases>
        <typeAlias type="my.mybatis.pojo.User" alias="User"/>
        //也可以扫描一个包，别名就是对应的类名小写
        <package name="my.mybatis.pojo"/>
    </typeAliases>
    ```

+ 设置（setting）

  + 这是 MyBatis 中极为重要的调整设置，它们会改变 MyBatis 的运行时行为

+ 映射器（mapper）

  + 注册绑定mapper文件

# 生命周期和作用域

生命周期和作用域是至关重要的，因为错误的使用会导致非常严重的**并发问题**

**SqlSessionFactoryBuilder:**

+ 一旦创建了SqlSessionFactory，就不再需要了
+ t它应该是局部变量

**SqlSessionFactory：**

+ 可以理解成数据库连接池
+ **SqlSessionFactory**一旦被创建就应该在应用的运行期间一直存在。不可丢弃或者从新创建另一个实例
+ 应该是应用的作用域
+ 单例模式或者静态单例模式

**SqlSession:**

+ 连接到连接池的一个请求
+ 需要关闭



# ResultMap结果集映射

用于解决属性名和字段名不一致，和复杂的数据格式封装的问题。复杂语句的结果映射！！！

```xml
<!-- 建立 User 实体和数据库表的对应关系 type 属性：指定实体类的全限定类名  id 属性：给定一个唯一标识，是给查询 select 标签引用用的。 -->
<!--
        id 标签：用于指定主键字段
        result 标签：用于指定非主键字段
        column 属性：用于指定数据库列名
        property 属性：用于指定实体类属性名称
    -->
<resultMap type="my.mybatis.pojo.User2" id="userMap">
    <id column="id" property="userId"/>
    <result column="username" property="userName"/>
    <result column="sex" property="userSex"/>
    <result column="address" property="userAddress"/>
    <result column="birthday" property="userBirthday"/>
</resultMap>

<select id="findAll2" resultMap="userMap"><!--修改了实体类，让属性与字段不一致-->
    select * from user
</select>
```



# 日志

## 日志工厂

如果在应用运行过程中出现了异常，可以通过日志来排错

logimpl可以指定MyBatis所用日志的具体实现，未指定时会自动查找

```xml
<settings>
    //这个日志不用导包 标准的日志工厂
    <!--        <setting name="logImpl" value="STDOUT_LOGGING"/>-->
    //需要导包
    <setting name="logImpl" value="LOG4J"/>
</settings>
```

## Log4j

+ 导入相关依赖

  + ```xml
    <dependency>
    	<groupId>log4j</groupId>
        <artifactId>log4j</artifactId>    
    	<version>1.2.12</version>
    </dependency>
    ```

+ 我们可以控制日志输出的位置（控制台，文件，服务器）和输出的格式

+ 可以通过定义每一条日志信息的级别

+ 可以通过一个配置文件进行灵活的配置

  + 创建log4j.properties文件，进行配置

  + ```properties
    #将等级为DEBUG的日志信息输出到console和file这两个目的地，console和file的定义在下面的代码
    log4j.rootLogger=DEBUG,console,file
    
    #控制台输出的相关设置
    log4j.appender.console = org.apache.log4j.ConsoleAppender
    log4j.appender.console.Target = System.out
    log4j.appender.console.Threshold=DEBUG
    log4j.appender.console.layout = org.apache.log4j.PatternLayout
    log4j.appender.console.layout.ConversionPattern=[%c]-%m%n
    
    #文件输出的相关设置
    log4j.appender.file = org.apache.log4j.RollingFileAppender
    log4j.appender.file.File=./log/jing.log
    log4j.appender.file.MaxFileSize=10mb
    log4j.appender.file.Threshold=DEBUG
    log4j.appender.file.layout=org.apache.log4j.PatternLayout
    log4j.appender.file.layout.ConversionPattern=[%p][%d{yy-MM-dd}][%c]%m%n
    
    #日志输出级别
    log4j.logger.org.mybatis=DEBUG
    log4j.logger.java.sql=DEBUG
    log4j.logger.java.sql.Statement=DEBUG
    log4j.logger.java.sql.ResultSet=DEBUG
    log4j.logger.java.sql.PreparedStatement=DEBUG
    ```

+ 在指定的类使用

  + ```java
    import org.apache.log4j.Logger;
    static Logger logger = Logger.getLogger(UserDaoTest.class);
    ```

  + ```java
    @Testpublic void testLog4j(){
        logger.info("info:进入了testLog4j");
        logger.debug("debug:进入了testLog4j");
        logger.error("error:进入了testLog4j");
    }
    ```

# 使用注解开发

**直接在接口上写注解**

```java
@Select("select * from user where id = #{id}")
User getUserById(int id);
```

# Lombok

他是一个java库，插件，构建工具

+ 先安装插件

+ 再引入相关依赖

  + ```xml
    <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.10</version>
        <scope>provided</scope>
    </dependency>
    
    ```

  + ```java
    @Getter and @Setter
    @FieldNameConstants
    @ToString
    @EqualsAndHashCode6
    @AllArgsConstructor, @RequiredArgsConstructor and @NoArgsConstructor
    @Log, @Log4j, @Log4j2, @Slf4j, @XSlf4j, @CommonsLog, @JBossLog, @Flogger, @CustomLog
    @Data//无参构造，get，set,tostring,hashcode,equals
    @Builder
    @SuperBuilder
    @Singular
    @Delegate
    @Value
    @Accessors
    @Wither
    @With
    @SneakyThrows
    @val
    @var
    experimental @var
    @UtilityClass
    ```

# 动态SQL

**动态SQL就是指根据不同条件生成不同SQL语句**

搭建环境

```mysql
CREATE TABLE blog (
	id varchar(50) not null comment '博客id',
	title varchar(100) not null comment '博客标题',
	author varchar(30) not null comment '博客作者',
	create_time datetime not null comment '创建时间',
	views int(30) not null comment '浏览量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

## IF
```java
public interface BlogMapper {

    int InsertBlog(Blog blog);

    List<Blog> queryBlogByIF(Map map);
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="my.mybatis.dao.BlogMapper">

    <insert id="InsertBlog" parameterType="Blog">
        insert into Blog(id,title,author,create_time,views)
         values (#{id},#{title},#{author},#{createTime},#{views})
    </insert>

    <select id="queryBlogByIF" parameterType="map" resultType="Blog">
        select * from Blog where 1=1
        <if test="title != null">
            and title = #{title}
        </if>
        <if test="author != null">
            and author = #{author}
        </if>
    </select>

</mapper>
```

```java
@Test
    public void queryBlogByIFTest(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();
        map.put("title","spring");
//        map.put("author","luo");
        List<Blog> blogs = mapper.queryBlogByIF(map);

        for (Blog blog : blogs) {
            System.out.println(blog);
        }

        sqlSession.close();
    }
```



## choose(when,otherwise)

当一个when条件成立就走这个，否则就走otherwise

```xml
<select id="queryBlogByChoose" parameterType="map" resultType="Blog">
        select * from blog
        <where>
            <choose>
                <when test="title != null">
                    title = #{title}
                </when>
                <when test="author != null">
                    author = #{author}
                </when>
                <otherwise>
                   	1 = 1
                </otherwise>
            </choose>
        </where>
    </select>
```

## trim(where,set)

where会把第一个满足添加的前面的and或or去掉，保证SQL不出错

```xml
<select id="queryBlogByIF" parameterType="map" resultType="Blog">
    select * from Blog
    <where>
        //就算这个条件不满足也不会报错
        <if test="title != null">
            title = #{title}
        </if>
        <if test="author != null">
            and author = #{author}
        </if>
    </where>
</select>
```

set会把最后一个set的，去掉

```xml
<update id="updateBlog" parameterType="map">
    update blog
    <set>
        <if test="title != null">
            title = #{title},
        </if>
        <if test="author != null">
            author = #{author},
        </if>
    </set>
    where id = #{id}
</update>
```

## SQL片段

为了将一些功能抽取出来，方便复用

```xml
<sql id="sql">
    <if test="title != null">
        title = #{title}
    </if>
    <if test="author != null">
        and author = #{author}
    </if>
</sql>

<select id="queryBlogByIF" parameterType="map" resultType="Blog">
    select * from blog
    <where>
        <include refid="sql"></include>
    </where>
</select>
```

## Foreach

动态 SQL 的另外一个常用的必要操作是需要对一个集合进行遍历，通常是在构建 IN 条件语句的时候。

```xml
<select id="selectPostIn" resultType="domain.blog.Post">
    SELECT *
    FROM POST P
    WHERE ID in
    <foreach item="item" index="index" collection="list"
             open="(" separator="," close=")">
        #{item}
    </foreach>
</select>
```

foreach 元素的功能是非常强大的，它允许你指定一个集合，声明可以用在元素体内的集合项和索引变量。它也允许你指定开闭匹配的字符串以及在迭代中间放置分隔符。这个元素是很智能的，因此它不会偶然地附加多余的分隔符。

注意 你可以将一个 List 实例或者数组作为参数对象传给 MyBatis，当你这么做的时候，MyBatis 会自动将它包装在一个 Map 中并以名称为键。List 实例将会以"list"作为键，而数组实例的键将是"array"。

# 缓存

## 简介

在前面每次查询都太过消耗资源

缓存：一次查询的结果，暂存在内存中，需要再次查询相同数据时，直接走缓存

## Mybatis缓存

+ MyBatis包含-一个非常强大的查询缓存特性，它可以非常方便地定制和配置缓存。缓存可以极大的提升查询效
  率。
+ MyBatis系统中默认定义了两级缓存: 一级缓存和二级缓存
  + 默认情况下，只有一级缓存开启。(SqlSession级别的缓存， 也称为本地缓存)
  + 二级缓存需要手动开启和配置，他是基于namespace级别的缓存。

## 一级缓存

+ 也叫本地缓存
  + 与数据库同一次会话期间查询到的数据会放在本地缓存中。
  + 以后如果需要获取相同的数据，直接从缓存中拿,没必须再去查询数据库;
  + 一级缓存默认开启无法关闭，只在一次sqlsession中有用
  + 在对表进行修改后，手动清理缓存。都会刷新缓存

## 二级缓存

+ 默认情况下，只启用了本地的会话缓存，它仅仅对一个会话中的数据进行缓存，要启用全局的二级缓存，只需要在SQL映射文件中加一行`<cache/>`即可
+ 基于namespace级别的缓存，一个命名空间，对应一个二级缓存
+ 工作机制
  + 一个会话查询一条数据，这个数据就会被放在当前会话的一级缓存中; .
  + 如果当前会话关闭了，这个会话对应的一级缓存就没了;但是我们想要的是，会话关闭了，一级缓存中的
    数据被保存到二级缓存中;
  + 新的会话查询信息，就可以从二级缓存中获取内容;
  + 不同的mapper查出的数据会放在自己对应的缓存(map) 中;

步骤：

+ 开启全局缓存`<setting name="cacheEnabled" value="true"/>`
+ 在SQL映射文件中加一行`<cache/>`

## 自定义缓存-ehcache

导入依赖

```xml
<!-- https://mvnrepository.com/artifact/org.mybatis.caches/mybatis-ehcache -->
<dependency>
    <groupId>org.mybatis.caches</groupId>
    <artifactId>mybatis-ehcache</artifactId>
    <version>1.2.0</version>
</dependency>
```

使用：

```xml
<cache type="org.mybatis.caches.ehcache.EhcacheCache"/>
```

配置文件ehcache.xml:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
         updateCheck="false">
    <!--
       diskStore：为缓存路径，ehcache分为内存和磁盘两级，此属性定义磁盘的缓存位置。参数解释如下：
       user.home – 用户主目录
       user.dir  – 用户当前工作目录
       java.io.tmpdir – 默认临时文件路径
     -->
    <diskStore path="java.io.tmpdir/Tmp_EhCache"/>
    <!--
       defaultCache：默认缓存策略，当ehcache找不到定义的缓存时，则使用这个缓存策略。只能定义一个。
     -->
    <!--
      name:缓存名称。
      maxElementsInMemory:缓存最大数目
      maxElementsOnDisk：硬盘最大缓存个数。
      eternal:对象是否永久有效，一但设置了，timeout将不起作用。
      overflowToDisk:是否保存到磁盘，当系统当机时
      timeToIdleSeconds:设置对象在失效前的允许闲置时间（单位：秒）。仅当eternal=false对象不是永久有效时使用，可选属性，默认值是0，也就是可闲置时间无穷大。
      timeToLiveSeconds:设置对象在失效前允许存活时间（单位：秒）。最大时间介于创建时间和失效时间之间。仅当eternal=false对象不是永久有效时使用，默认是0.，也就是对象存活时间无穷大。
      diskPersistent：是否缓存虚拟机重启期数据 Whether the disk store persists between restarts of the Virtual Machine. The default value is false.
      diskSpoolBufferSizeMB：这个参数设置DiskStore（磁盘缓存）的缓存区大小。默认是30MB。每个Cache都应该有自己的一个缓冲区。
      diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔，默认是120秒。
      memoryStoreEvictionPolicy：当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。默认策略是LRU（最近最少使用）。你可以设置为FIFO（先进先出）或是LFU（较少使用）。
      clearOnFlush：内存数量最大时是否清除。
      memoryStoreEvictionPolicy:可选策略有：LRU（最近最少使用，默认策略）、FIFO（先进先出）、LFU（最少访问次数）。
      FIFO，first in first out，这个是大家最熟的，先进先出。
      LFU， Less Frequently Used，就是上面例子中使用的策略，直白一点就是讲一直以来最少被使用的。如上面所讲，缓存的元素有一个hit属性，hit值最小的将会被清出缓存。
      LRU，Least Recently Used，最近最少使用的，缓存的元素有一个时间戳，当缓存容量满了，而又需要腾出地方来缓存新的元素的时候，那么现有缓存元素中时间戳离当前时间最远的元素将被清出缓存。
   -->
    <defaultCache
            eternal="false"
            maxElementsInMemory="10000"
            overflowToDisk="false"
            diskPersistent="false"
            timeToIdleSeconds="1800"
            timeToLiveSeconds="259200"
            memoryStoreEvictionPolicy="LRU"/>

    <cache
            name="cloud_user"
            eternal="false"
            maxElementsInMemory="5000"
            overflowToDisk="false"
            diskPersistent="false"
            timeToIdleSeconds="1800"
            timeToLiveSeconds="1800"
            memoryStoreEvictionPolicy="LRU"/>

</ehcache>
```

