在resultMap中，我们可以对我们的表和实体类字段做映射。但是类与类之间有继承关系不至于一个类的字段很多，如果多个类都有相同语义的属性，那维护起来是不是就会困难些。那实体类有继承，resultMap也有继承

```xml
<!--人的一个父类-->
<resultMap id="personMap" type="person">
    <result property="name" column="name"/>
    <result property="sex" column="sex"/>
</resultMap>

<!--学生类继承人类-->
<resultMap id="studentMap" type="student" extends="personMap">
    <result property="uid" column="id"/>
    <result property="schoolId" column="school_id"/>
    <!--职位 班长或副班长或同学-->
    <result property="position" column="position"/>
</resultMap>
```

这样我们就可以直接引用学生的resultMap了。

如果说有一个班级对象，包含了两个学生，一个是班长，一个是副班长怎么办？

```xml
<resultMap id="classMap" type="classGrade">
    <!--班长-->
    <association property="squadLeader" resultMap="studentMap"/>
    <!--副班长-->
    <association property="viceMonitor" resultMap="studentMap"/>
</resultMap>
```

