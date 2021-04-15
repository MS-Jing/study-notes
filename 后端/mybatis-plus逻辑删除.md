+ 版本：

  + ```xml
    <mybatis-plus.version>3.0.5</mybatis-plus.version>
    ```

+ 配置插件(3.1.1以上版本不需要配置插件)

  + ```java
    //逻辑删除插件
    @Bean
    public ISqlInjector sqlInjector(){
        return new LogicSqlInjector();
    }
    ```

+ 给实体类属性加上注解

  + ```java
    @TableLogic
    private Boolean isDeleted;
    ```

