# 1.MQ引言！



# 2.RabbitMQ引言

## 2.1RabbitMQ是什么？

> ​	RabbitMQ是基于==AMQP==协议，erlang语言开发的，是部署最为广泛的开源消息中间件

## 2.2RabbitMQ安装

[官网](https://www.rabbitmq.com/)

> #什么是AMQP协议
>
> ​			AMQP（advanced message queuing protocol）,在2003年时被提出，最早用于解决金融领域不同平台之间的消息传递交互问题。顾名思义，AMQP是一种协议，更准确的说是一种链接协议。这是其和JMS的本质差别，AMQP不从API层进行限定，而是直接定义网络交换的数据格式，这使得实现了AMQP的provider天然性就是跨平台的。





## 2.3docker安装

```shell
docker pull rabbitmq:3-management

docker run -d --name rabbitmq01 -p 5672:5672 -p 15672:15672 -v  /home/rabbitmq/data:/var/lib/rabbitmq --hostname my_rabbitmq -e RABBITMQ_DEFAULT_VHOST=my_vhost -e RABBITMQ_DEFAULT_USER=root -e RABBITMQ_DEFAULT_PASS=612612 rabbitmq:3-management
```

