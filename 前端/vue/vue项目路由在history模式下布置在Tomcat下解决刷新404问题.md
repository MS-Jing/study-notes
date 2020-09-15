# vue项目路由在history模式下布置在Tomcat下解决刷新404问题

在项目目录下创建WEB-INF文件夹，并在该文件夹下建立web.xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>

<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee
                      http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
  version="3.1"
  metadata-complete="true">

  <display-name>Welcome to myblog</display-name>
  <error-page>
    <error-code>404</error-code>
    <location>/</location>
	</error-page>
  <description>
     Welcome to myblog
  </description>

</web-app>

```

