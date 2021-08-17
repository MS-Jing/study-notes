引入依赖：

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
    <version>2.1.1</version>
    <optional>true</optional>
</dependency>
```

官方文档：https://www.yuque.com/easyexcel/doc/easyexcel

# 读

## 第一个例子

| 姓名 | 性别 | 年龄 |
| ---- | ---- | ---- |
| 张三 | 男   | 18   |
| 李四 | 女   | 19   |
| 王五 | 男   | 20   |

```java
@Data
public class User {
    private String name;
    private String sex;
    private int age;
}
```

```java
public class DemoListener extends AnalysisEventListener<User> {

    /**
     * 每一条数据解析都会来调用
     */
    @Override
    public void invoke(User user, AnalysisContext context) {
        System.out.println(user);
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("解析完毕");
    }
}
```

```java
public class Main {
    public static void main(String[] args) {
        EasyExcel.read("C:\\Users\\罗敬\\Desktop\\test.xlsx",
                User.class, new DemoListener()).sheet().doRead();
    }
}
```

## 指定列的下标或者列名

上面有一个问题，如果列的位置没有一一对应会有问题。我们可以指定列下标或者列名

修改User:

```java
@Data
public class User {

    //对应读取excel表中的第三列
    @ExcelProperty(index = 2)
    private int age;
    //指定列名
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("性别")
    private String sex;
}
```

再次测试

## 读取一个文件的所有sheet

```java
EasyExcel.read("C:\\Users\\罗敬\\Desktop\\test.xlsx",
                User.class, new DemoListener()).doReadAll();
```

## 日期、数字及自定义格式转换

| 姓名 | 性别 | 生日          | 占比 |
| ---- | ---- | ------------- | ---- |
| 张三 | 男   | 1900年1月18日 | 50%  |
| 李四 | 女   | 1900年1月19日 | 25%  |
| 王五 | 男   | 1900年1月20日 | 25%  |

```java
@Data
public class User {
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("性别")
    private String sex;
    @ExcelProperty("生日")
    private String birthday;
    @ExcelProperty("占比")
    private String proportion;
}
```

```java
public class Main {
    public static void main(String[] args) {
        EasyExcel.read("C:\\Users\\罗敬\\Desktop\\test.xlsx",
                User.class, new DemoListener()).sheet().doRead();
    }
}
```

```
User(name=张三, sex=男, birthday=1900-01-18 00:00:00, proportion=0.5)
User(name=李四, sex=女, birthday=1900-01-19 00:00:00, proportion=0.25)
User(name=王五, sex=男, birthday=1900-01-20 00:00:00, proportion=0.25)
解析完毕
```

时间和占比并不是我们想要的格式！！！

```java
@Data
public class User {
    @ExcelProperty("姓名")
    private String name;
    // 自定义格式转换  在性别后面加上 “性”
    @ExcelProperty(value = "性别",converter = CustomSexConverter.class)
    private String sex;
    @ExcelProperty("生日")
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    private String birthday;
    @ExcelProperty("占比")
    @NumberFormat("#.##%")
    private String proportion;
}
```

```java
public class CustomSexConverter implements Converter<String> {
    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    //转换成java数据
    @Override
    public String convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return cellData.getStringValue() + "性";
    }

    //转换成Excel数据
    @Override
    public CellData convertToExcelData(String s, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        return new CellData(s);
    }
}
```

```
User(name=张三, sex=男性, birthday=1900年01月18日00时00分00秒, proportion=50%)
User(name=李四, sex=女性, birthday=1900年01月19日00时00分00秒, proportion=25%)
User(name=王五, sex=男性, birthday=1900年01月20日00时00分00秒, proportion=25%)
解析完毕
```

## 读取请求头

在监听器中重写方法：

```java
@Override
public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
    System.out.println(headMap);
}
```

## 使用Map读取

创建reader对象时，不传类对象，监听器的泛型为 Map<Integer, String> 既可



# 写

## 第一个例子

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @ExcelProperty("姓名")
    private String name;
    // 自定义格式转换  在性别后面加上 “性”
    @ExcelProperty(value = "性别",converter = CustomSexConverter.class)
    private String sex;

    @ExcelProperty("生日")
    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    private String birthday;

    @ExcelProperty("占比")
    @NumberFormat("#.##%")
    @ExcelIgnore  //忽略该字段
    private String proportion;
}
```

```java
public class Main {
    public static void main(String[] args) {
        ArrayList<User> users = new ArrayList<>();
        users.add(new User("李四","男","2021-08-17","100%"));
        EasyExcel.write("C:\\Users\\罗敬\\Desktop\\test.xlsx",
                User.class).sheet().doWrite(users);
    }
}
```

如果 @ExcelProperty 指定了index,会指定写入哪列

## 设置行高列宽

在实体类上添加注解：

```java
@ContentRowHeight(10) //行高
@HeadRowHeight(20) //表头高
@ColumnWidth(25) //列宽
```

## 自定义样式的注解

```java
// 头背景设置成红色 IndexedColors.RED.getIndex()
@HeadStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 10)
// 头字体设置成20
@HeadFontStyle(fontHeightInPoints = 20)
// 内容的背景设置成绿色 IndexedColors.GREEN.getIndex()
@ContentStyle(fillPatternType = FillPatternType.SOLID_FOREGROUND, fillForegroundColor = 17)
// 内容字体设置成20
@ContentFontStyle(fontHeightInPoints = 20)
```

## 合并单元格

```java
// 将第6-7行的2-3列合并成一个单元格
// @OnceAbsoluteMerge(firstRowIndex = 5, lastRowIndex = 6, firstColumnIndex = 1, lastColumnIndex = 2)
```

