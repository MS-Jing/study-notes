# 引入poi依赖

```xml
<!--poi 读取.docx文档-->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.0.0</version>
</dependency>
<!--poi 读取.doc文档-->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-scratchpad</artifactId>
    <version>5.0.0</version>
</dependency>
<!--poi的依赖，可以不引入，因为上面的 ooxml和scratchpad已经引入了-->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>5.0.0</version>
</dependency>
```



# 分析

poi解析excel只需要关注表格就好了，但是解析word的时候需要关注**段落**和**表格**



# 写

## 写出到文件

```java
//创建word文档对象
XWPFDocument document = new XWPFDocument();
//创建一个标题的段落
XWPFParagraph title = document.createParagraph();
//设置段落对齐方式
title.setAlignment(ParagraphAlignment.CENTER);
//创建段落的run对象
XWPFRun titleRun = title.createRun();
titleRun.setBold(true); // 设置段落字体加粗
titleRun.setColor("FF0000"); //字体颜色
titleRun.setFontSize(20); // 字体大小
titleRun.setFontFamily("宋体"); //字体格式
titleRun.setText("这是标题！"); //设置段落内容

//换行
titleRun.addBreak();

//创建一个新的段落
XWPFParagraph firstParagraph = document.createParagraph();
firstParagraph.setAlignment(ParagraphAlignment.LEFT);//段落左对齐
XWPFRun firstRun = firstParagraph.createRun();
firstRun.setColor("000000"); //字体颜色
firstRun.setFontSize(13); // 字体大小
firstRun.setFontFamily("黑体"); //字体格式

firstRun.addTab(); //tab键
firstRun.setText("“躺平”实际上是其他国家一种无奈的选择。在经过多种尝试努力后，仍然找不到一种理想的控制" +
                 "新冠的策略，疫情依然严重流行，很多国家干脆就选择“躺平”，即除强调疫苗接种外，不再强调其他防控措施" +
                 "。我们国家的抗疫实践已经证明，“动态清零”是符合我国实际的，也是我国当前抗击新冠肺炎疫情的最佳选择。");
firstRun.addBreak();
firstRun.addTab();
firstRun.setText("发布会上，中国疾控中心流行病学首席专家吴尊友介绍，我国的防控总方针概括起来，就是“动态清零”，" +
                 "为的是尽一切可能保护人民群众的身体健康和生命安全，尤其是保护老年人和婴幼儿等易受病毒危害的群体。");

//第二个段落
XWPFParagraph lastParagraph = document.createParagraph();
lastParagraph.setAlignment(ParagraphAlignment.RIGHT);
XWPFRun lastRun = lastParagraph.createRun();
lastRun.setText("张三报道！");

document.write(new FileOutputStream("test\\test.docx"));
document.close();
```



## 写表格

```java
public static void main(String[] args) throws IOException {
    //创建word文档对象
    XWPFDocument document = new XWPFDocument();
    //创建一个标题的段落
    XWPFParagraph title = document.createParagraph();
    //设置段落对齐方式
    title.setAlignment(ParagraphAlignment.CENTER);
    //创建段落的run对象
    XWPFRun titleRun = title.createRun();
    titleRun.setBold(true); // 设置段落字体加粗
    titleRun.setColor("FF0000"); //字体颜色
    titleRun.setFontSize(20); // 字体大小
    titleRun.setFontFamily("宋体"); //字体格式
    titleRun.setText("表格测试"); //设置段落内容

    //换行
    titleRun.addBreak();

    //创建一个表格 一行四列
    XWPFTable table = document.createTable(1, 4);
    CTTbl ctTbl = table.getCTTbl();
    CTTblPr ctTblPr = ctTbl.getTblPr() == null ? ctTbl.addNewTblPr() : ctTbl.getTblPr();
    CTTblWidth ctTblWidth = ctTblPr.isSetTblW() ? ctTblPr.getTblW() : ctTblPr.addNewTblW();

    //整个表格的宽度
    ctTblWidth.setW(new BigInteger("10000"));
    ctTblWidth.setType(STTblWidth.DXA);

    // 设置表头
    table.getRow(0).setHeight(500);
    setCellText(table.getRow(0).getCell(0), "第一列", "FFFFFF", 1000);
    setCellText(table.getRow(0).getCell(1), "第二列", "FFFFFF", 1000);
    setCellText(table.getRow(0).getCell(2), "第三列", "FFFFFF", 1000);
    setCellText(table.getRow(0).getCell(3), "第四列", "FFFFFF", 1000);

    // 填充表格，初始化为1（因为第0行是表头已经填充了）
    // 填充三行
    for (int i = 1; i < 3; i++) {
        // 创建一个行对象
        XWPFTableRow tableRow = table.createRow();
        //设置行高
        tableRow.setHeight(450);

        for (XWPFTableCell cell : tableRow.getTableCells()) {
            //                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            setCellText(cell, "第 " + i + " 行内容", "FFFFFF", 1000);
        }
    }


    document.write(new FileOutputStream("test\\test.docx"));
    document.close();
}

/**
     * @param cell    单元格对象
     * @param text    单元格内容
     * @param bgcolor 单元格背景色
     * @param width   单元格宽度
     */
private static void setCellText(XWPFTableCell cell, String text, String bgcolor, int width) {
    CTTc cttc = cell.getCTTc();
    CTTcPr cellPr = cttc.addNewTcPr();
    cellPr.addNewTcW().setW(BigInteger.valueOf(width));
    cell.setColor(bgcolor);
    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
    CTTcPr ctpr = cttc.addNewTcPr();
    ctpr.addNewVAlign().setVal(STVerticalJc.CENTER);
    cell.setText(text);
}
```



## 写出到响应流中

```java
@RequestMapping("/exportWord")
public void exportWord(HttpServletResponse response) throws IOException {
    response.setContentType("application/msword");
    response.setCharacterEncoding("utf-8");
    String fileName = URLEncoder.encode("测试word", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".doc");

    //创建word文档对象
    XWPFDocument document = new XWPFDocument();
    //创建一个标题的段落
    XWPFParagraph title = document.createParagraph();
    //设置段落对齐方式
    title.setAlignment(ParagraphAlignment.CENTER);
    //创建段落的run对象
    XWPFRun titleRun = title.createRun();
    titleRun.setText("这是标题！"); //设置段落内容
    titleRun.setBold(true); // 设置段落字体加粗
    titleRun.setColor("FF0000"); //字体颜色
    titleRun.setFontSize(20); // 字体大小
    titleRun.setFontFamily("宋体"); //字体格式

    document.write(response.getOutputStream());
    document.close();
}
```





# 读