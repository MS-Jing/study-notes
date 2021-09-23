# Logging学习

## 第一个日志

```python
import logging

if __name__ == '__main__':
    logging.debug("debug")
    logging.info("info")
    logging.warning("warning")
    logging.error("error")
    logging.critical("critical")
```

logging默认的日志级别是warning

## 指定日志输出级别

```python
if __name__ == '__main__':

    logging.basicConfig(level=logging.DEBUG)

    logging.debug("debug")
    logging.info("info")
    logging.warning("warning")
    logging.error("error")
    logging.critical("critical")
```

## basicConfig()的其他设置

| 参数名称 | 描述                                                         |
| -------- | ------------------------------------------------------------ |
| filename | 指定日志输出目标文件的文件名，指定该设置项后日志信息就不会被输出到控制台了 |
| filemode | 指定日志文件的打开模式，默认为'a'。需要注意的是，该选项要在filename指定时才有效 |
| format   | 指定日志格式字符串，即指定日志输出时所包含的字段信息以及它们的顺序。logging模块定义的格式字段下面会列出。 |
| datefmt  | 指定日期/时间格式。需要注意的是，该选项要在format中包含时间字段%(asctime)s时才有效 |
| level    | 指定日志器的日志级别                                         |
| stream   | 指定日志输出目标stream，如sys.stdout、sys.stderr以及网络stream。需要说明的是，stream和filename不能同时提供，否则会引发 `ValueError`异常 |
| style    | Python 3.2中新添加的配置项。指定format格式字符串的风格，可取值为'%'、'{'和'$'，默认为'%' |
| handlers | Python 3.3中新添加的配置项。该选项如果被指定，它应该是一个创建了多个Handler的可迭代对象，这些handler将会被添加到root logger。需要说明的是：filename、stream和handlers这三个配置项只能有一个存在，不能同时出现2个或3个，否则会引发ValueError异常。 |

## 自定义日志输出格式

| 字段/属性名称   | 使用格式            | 描述                                                         |
| --------------- | ------------------- | ------------------------------------------------------------ |
| asctime         | %(asctime)s         | 日志事件发生的时间--人类可读时间，如：2003-07-08 16:49:45,896 |
| created         | %(created)f         | 日志事件发生的时间--时间戳，就是当时调用time.time()函数返回的值 |
| relativeCreated | %(relativeCreated)d | 日志事件发生的时间相对于logging模块加载时间的相对毫秒数（目前还不知道干嘛用的） |
| msecs           | %(msecs)d           | 日志事件发生事件的毫秒部分                                   |
| levelname       | %(levelname)s       | 该日志记录的文字形式的日志级别（'DEBUG', 'INFO', 'WARNING', 'ERROR', 'CRITICAL'） |
| levelno         | %(levelno)s         | 该日志记录的数字形式的日志级别（10, 20, 30, 40, 50）         |
| name            | %(name)s            | 所使用的日志器名称，默认是'root'，因为默认使用的是 rootLogger |
| message         | %(message)s         | 日志记录的文本内容，通过 `msg % args`计算得到的              |
| pathname        | %(pathname)s        | 调用日志记录函数的源码文件的全路径                           |
| filename        | %(filename)s        | pathname的文件名部分，包含文件后缀                           |
| module          | %(module)s          | filename的名称部分，不包含后缀                               |
| lineno          | %(lineno)d          | 调用日志记录函数的源代码所在的行号                           |
| funcName        | %(funcName)s        | 调用日志记录函数的函数名                                     |
| process         | %(process)d         | 进程ID                                                       |
| processName     | %(processName)s     | 进程名称，Python 3.1新增                                     |
| thread          | %(thread)d          | 线程ID                                                       |
| threadName      | %(thread)s          | 线程名称                                                     |

```python
if __name__ == '__main__':
    logging.basicConfig(level=logging.DEBUG, style='{', format="[{asctime}] [{levelname}] [{threadName}] [{name}] [{pathname} {lineno}]: {message}")
    logging.info("姓名 %s", "张三")
```

# Logging模块化

logging采用了模块化设计，主要四个组件：

+ Loggers:记录器，提供应用程序使用的接口
+ Handlers:处理器，将记录器产生的日志发送至目的地
+ Filters:过滤器，提供更好的粒度控制，决定那些日志会被输出
+ Formatters:格式化器，设置日志内容的组成结构和消息字段

## Loggers记录器

提供应用程序的调用接口

logger=logging.getLogger(loggerName) // logger是单例的

决定日志记录的级别

logger.setLevel() // 决定日志记录的级别

将处理器添加/移除到Logger上

logger.addHandler(),logger.removeHandler()

 为该logger对象添加 和 移除一个filter对象 

 Logger.addFilter() 和 Logger.removeFilter() 

## Handlers处理器

+ StreamHandler
  + 标准输出stdout
  + handler = logging.StreamHandler(stream=None)

+ FileHandler
  + 日志输出到文件
  + handler = logging.FileHandler(filename,mode='a',encoding=None,delay=False)

+ RotatingFileHandler
  + 滚动日志处理器
+ TimedRotatingFileHandler
  + 时间滚动日志
+ ...

 设置handler将会处理的日志消息的最低严重级别 

 Handler.setLevel() 

为handler设置一个格式器对象 

 Handler.setFormatter() 

 为handler添加 和 删除一个过滤器对象

 Handler.addFilter() 和 Handler.removeFilter() 

## Formatters格式化器

设置日志信息格式

ft = logging.Formatter(fmt=None,datefmt=None,style='%')

datefmt默认是%Y-%m-%d %H:%M:%S

## 测试

```python
if __name__ == '__main__':
    # 创建logger 这个是单例的
    logger = logging.getLogger("app")
    logger.setLevel(logging.INFO)
    #创建处理器
    streamHandler = logging.StreamHandler(sys.stdout) # 不传默认使用的是sys.stderr
    logger.addHandler(streamHandler)
    #创建格式化器
    formatter = logging.Formatter(style='{',fmt="[{asctime}] [{levelname:^8}] [{threadName}] [{name}] [{pathname} {lineno}]: {message}")
    streamHandler.setFormatter(formatter)
    logger.info("logger")
```

## 配置文件形式

```conf
[loggers]    ;多个用逗号隔开，其他组件同理
keys=root,app  
[handlers]
keys=streamHandler
[formatters]
keys=formatter
[logger_root]
level=INFO
handlers=streamHandler
[logger_app] ; propagate=0 是否继承父logger
qualname=app
level=INFO
handlers=streamHandler
propagate=0      
[handler_streamHandler]
class=StreamHandler
args=(sys.stdout,)
formatter=formatter
[formatter_formatter]
format=[{asctime}] [{levelname:^8}] [{threadName}] [{name}] [{pathname} {lineno}]: {message}
style={
```

```python
import logging
import logging.config  #引入配置模块

if __name__ == '__main__':
    logging.config.fileConfig("logging.conf") # 加载配置文件
    logger = logging.getLogger("app")
    logger.info("logger")
```

# 补充

logging.info,logger.error等方法的第一个参数为日志信息，第二个参数为变量，第三个参数` **kwargs `,它有三个关键字参数 exc_info, stack_info, extra 

+ exc_info：默认为False。如果为True， 则会将异常信息添加到日志消息中 

  + ```python
    logger = logging.getLogger("app")
    logger.error("logger",exc_info=True)
    ```

+ stack_info:  默认值为False。如果为True，栈信息将会被添加到日志信息中

打印日志信息我们一般可以使用exception:

```python
logger = logging.getLogger("app")
try:
    1/0
except Exception as e:
    logger.exception(e)
```

+ extra :   这是一个字典（dict）参数，它可以用来自定义消息格式中所包含的字段，但是它的key不能与logging模块定义的字段冲突。 

+ ```python
  if __name__ == '__main__':
      logging.basicConfig(level=logging.DEBUG, style='{', format="{title}: {message}")
      logging.info("姓名 %s", "张三",extra={"title":"日志信息"})
  ```





