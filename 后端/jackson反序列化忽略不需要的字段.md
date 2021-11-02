在使用jackson做反序列化时(json字符串转换成java对象)，如果json中多了字段，但是java对象中没有，这样会报错的。

json:

```json
{
    "sex":"男",
    "name":"aaa",
    "age":19
}
```

java :

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
class User {
    private String name;
    private Integer age;
}
```

注意看json中有sex属性，但是java对象没有，反序列化后会报UnrecognizedPropertyException

```java
ObjectMapper mapper = new ObjectMapper();
User user = mapper.readValue("{\"sex\":\"男\",\"name\":\"aaa\",\"age\":19}", User.class);
System.out.println(user);
```

`UnrecognizedPropertyException: Unrecognized field "sex" (class com.lj.User), not marked as ignorable (2 known properties: "name", "age"])`

解决：

**只需要在类上加上注解`@JsonIgnoreProperties(ignoreUnknown = true)`既可**

方式二：

如果你说你不想加注解可以这样：

```java
ObjectMapper mapper = new ObjectMapper();
mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

User user = mapper.readValue("{\"sex\":\"男\",\"name\":\"aaa\",\"age\":19}", User.class);
System.out.println(user);
```

