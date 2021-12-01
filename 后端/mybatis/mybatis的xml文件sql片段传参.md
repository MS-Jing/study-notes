我们使用mybatis的xml写sql的时候有些公用的比如查询列表我们会写到一个`<sql></sql>`sql片段中。那么如果我们想根据不同条件来构造这个sql片段怎么办？

我们平时应用sql片段是这样：

```xml
<sql id="userList">
    username,age,sex
</sql>

<select id="getUserList" resultType="user">
    select 
    <include refid="userList"/>
    from user 
</select>
```

如果我们要给连表或者自己连自己需要给字段去别名呢？写两个别名不一样内容一样的sql片段吗？那是不是太不友好了，而且如果要维护修改字段的话，两个都要改容易遗漏。我们可以这样:

```xml
<sql id="userList">
    ${tableAlias}.username,${tableAlias}.age,${tableAlias}.sex
</sql>

<select id="getUserList" resultType="user">
    select
    <include refid="userList">
        <property name="tableAlias" value="u"/>
    </include>
    from user as u
</select>
```

这里给表取了一个别名u，然后sql片段中有一个属性${tableAlias}需要引用放去传递，这里用到了`<property name="tableAlias" value="u"/>`来将表别名传递给了目标的sql片段，以此来达到复用的目的。