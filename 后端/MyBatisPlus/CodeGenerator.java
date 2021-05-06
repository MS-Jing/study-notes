package com.lj;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/*
 * 将该生成器放到你要生成的项目的测试目录下
 * 根据情况修改相应的属性
 * */
public class CodeGenerator {

    private static final String author = "luojing";  //作者
    private static final IdType idType = IdType.ID_WORKER_STR; //主键生成策略
    private static final boolean swagger2 = true;  //生成的是否含有swagger注解

    private static final String url = "jdbc:mysql://localhost:3306/guli?serverTimezone=GMT%2B8";
    private static final String userName = "root";
    private static final String passWord = "root";

    private static final String packageParent = "com.lj";  //包名
    private static final String moduleName = "eduservice"; //模块名

    private static final String[] includes = new String[]{"edu_course","edu_course_description","edu_chapter","edu_video"}; //要生成的表名


    public static void globalConfig(AutoGenerator generator) {
        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
//        System.out.println(projectPath);
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(author);
        gc.setOpen(false); //生成后是否打开资源管理器
        gc.setFileOverride(false); //重新生成时文件是否覆盖
        gc.setServiceName("%sService");    //去掉Service接口的首字母I
        gc.setIdType(idType); //主键策略
        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
        gc.setSwagger2(swagger2);//开启Swagger2模式
        generator.setGlobalConfig(gc);
    }

    public static void dataSourceConfig(AutoGenerator generator) {
        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(url);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(userName);
        dsc.setPassword(passWord);
        dsc.setDbType(DbType.MYSQL);
        generator.setDataSource(dsc);
    }

    public static void packageConfig(AutoGenerator generator) {
        // 4、包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(packageParent);
        pc.setModuleName(moduleName); //模块名
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        generator.setPackageInfo(pc);
    }

    public static void strategyConfig(AutoGenerator generator) {
        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude(includes);
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略

        //生成实体时去掉表前缀
        List<String> tablePrefixs = new ArrayList<>();
        for (String s : strategy.getInclude()) {
            tablePrefixs.add(s.split("_")[0] + "_");
        }
        strategy.setTablePrefix(tablePrefixs.toArray(new String[tablePrefixs.size()]));

        //填充生成和更新时间的注解
        List<TableFill> tableFills = new ArrayList<>();
        tableFills.add(new TableFill("gmt_create", FieldFill.INSERT));
        tableFills.add(new TableFill("gmt_modified", FieldFill.INSERT_UPDATE));
        strategy.setTableFillList(tableFills);

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作

        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符

        generator.setStrategy(strategy);
    }

    //自定义模板配置
//    public static void templateConfig(AutoGenerator generator){
//        TemplateConfig templateConfig = new TemplateConfig();
//        templateConfig.setController("templates/controller.java");
//        generator.setTemplate(templateConfig);
//    }


    @Test
    public void run() {

        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 2、全局配置
        globalConfig(mpg);
//        templateConfig(mpg);

        // 3、数据源配置
        dataSourceConfig(mpg);

        // 4、包配置
        packageConfig(mpg);

        // 5、策略配置
        strategyConfig(mpg);

        // 6、执行
        mpg.execute();
    }
}