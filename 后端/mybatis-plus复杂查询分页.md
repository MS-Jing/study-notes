# mybatis-plus复杂查询分页

只需要给mapper层接口传入一个Page对象，当mybatis执行此方法的时候，会被mybatis-plus的分页插件自动拦截到，然后会在给你拼接相应的sql语句

```java
List<ExcelHyInfo> getHyList(Page<ExcelHyInfo> page);
```

**server层**

```java
@Override
public Page<ExcelHyInfo> getHyList(Page<ExcelHyInfo> page){
    return page.setRecords(adminMapper.getHyList(page));
}
```

