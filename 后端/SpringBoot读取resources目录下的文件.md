​	传统的使用new File()打成jar包会找不到文件，可以使用spring提供的`ClassPathResource()`类访问resources下的文件

```java
ClassPathResource classPathResource = new ClassPathResource("status/xxx.xml");
InputStream inputStream = classPathResource.getInputStream();
```

