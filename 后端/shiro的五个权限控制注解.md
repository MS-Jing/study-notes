# shiro的五个权限控制注解

+ **RequiresAuthentication:**

  > 使用该注解标注的类，实例，方法在访问或调用时，当前Subject必须在当前session中已经过认证。

+ **RequiresGuest:**

  > 使用该注解标注的类，实例，方法在访问或调用时，当前Subject可以是“gust”身份，不需要经过认证或者在原先的session中存在记录。

+ **RequiresPermissions:**

  > 当前Subject需要拥有某些特定的权限时，才能执行被该注解标注的方法。如果当前Subject不具有这样的权限，则方法不会被执行。

+ **RequiresRoles:**

  > 当前Subject必须拥有所有指定的角色时，才能访问被该注解标注的方法。如果当天Subject不同时拥有所有指定角色，则方法不会执行还会抛出AuthorizationException异常。

+ **RequiresUser**

  > 当前Subject必须是应用的用户，才能访问或调用被该注解标注的类，实例，方法。

**Shiro的认证注解处理是有内定的处理顺序的，如果有个多个注解的话，前面的通过了会继续检查后面的，若不通过则直接返回，处理顺序依次为（与实际声明顺序无关）：**

> **RequiresRoles** 
> **RequiresPermissions** 
> **RequiresAuthentication** 
> **RequiresUser** 
> **RequiresGuest**