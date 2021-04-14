# 自动填充功能

我使用的是3.0.5版本的

```xml
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus-boot-starter</artifactId>
    <version>3.0.5</version>
</dependency>
```

+ 在需要做自动填充的实体类字段上加入相应字段

  + ```java
    @TableField(fill = FieldFill.INSERT)
    private Date creatTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    ```

+ 实现`MetaObjectHandler`接口自定义处理类(记得@Component注解哦)

  + ```java
    @Component
    public class MyMetaObjectHandler implements MetaObjectHandler {
    
        //新增方法时会执行
        @Override
        public void insertFill(MetaObject metaObject) {
            this.setFieldValByName("creatTime",new Date(),metaObject);
            this.setFieldValByName("updateTime",new Date(),metaObject);
        }
    
        //更新方法时会执行
        @Override
        public void updateFill(MetaObject metaObject) {
            this.setFieldValByName("updateTime",new Date(),metaObject);
        }
    }
    ```

+ 一切就绪，开始测试吧！