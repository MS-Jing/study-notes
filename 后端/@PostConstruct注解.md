# @PostConstruct注解

​	在我学习人人开源项目时，我发现定时任务时从数据库里拿出数据，再执行定时任务的，但是一直找不到再哪里初始化的，最后终于找到了

```java
/**
	 * 项目启动时，初始化定时器
	 */
@PostConstruct
public void init(){
    List<ScheduleJobEntity> scheduleJobList = this.list();
    for(ScheduleJobEntity scheduleJob : scheduleJobList){
        CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
        //如果不存在，则创建
        if(cronTrigger == null) {
            ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
        }else {
            ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
        }
    }
}
```

注意`@PostConstruct`注解！！！

```java
package javax.annotation;

@Documented
@Retention (RUNTIME)
@Target(METHOD)
public @interface PostConstruct {
}
```

## 介绍：

+ @PostConstruct注解好多人以为是Spring提供的。其实是Java自己的注解。
+  @PostConstruct该注解被用来修饰一个**非静态的void（）方法**。被@PostConstruct修饰的方法会在服务器加载Servlet的时候运行，并且只会被服务器执行一次。PostConstruct在构造函数之后执行，init（）方法之前执行。 
+ 执行顺序： Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法) 