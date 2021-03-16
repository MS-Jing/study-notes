# 基本概念

## 主题（Topic）



## 分区（Partition）

一个主题可能包括多个分区，分区可以分布在不同的服务器上，这样主题也就可以分布在不同的服务器上



# kafka配置安装

+ 先配置安装好zookeepr
+ 官网下载解压
+ server.properties配置
  + broker.id=0表示broker的编号， 如果集群中有多个broker,则每个broker的编号需要设置的不同
  + listeners=PL AINTEXT://:9092 brokder对外提供的服务入口地址
  + log.dirs=/tmp/kafka/log设置存放消息日志文件的地址
  + zookeeper. .connect-localhost:2181 Kafka所需 Zookeeper集群地址
+ 启动
  + 通过`bin/kafka-server-start.sh config/server.properties`命令启动

# kafka测试消息生产与消费

+ 创建主题
  + `bin/kafka-topics.sh --zookeeper localhost:2181 --create --topic testtopic --partitions 2 --replication-factor 1`
  + --zookeeper：指定了kafka所链接的Zookeeper服务地址
  + --topic：指定了所要创建主题的名称
  + --partitions：指定了分区个数
  + --replication-factor：指定了副本因子

+ 展示所有主题
  + `bin/kafka-topics.sh --zookeeper localhost:2181 --list`
+ 查看主题详情
  + `bin/kafka-topics.sh --zookeeper localhost:2181 --describe --topic testtopic `

---

+ 启动消费端接收消息
  + `bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic testtopic`
  + --bootstrap-server：指定了连接kafka集群的地址
  + --topic ：指定要消费端订阅的主题

+ 生产端发送消息
  + `bin/kafka-console-producer.sh --broker-list localhost:9092 --topic testtopic`
  + --broker-list：指定了连接kafka集群的地址
  + --topic ：指定要发送消息的主题



# 使用Java程序收发消息

```xml
<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka-clients</artifactId>
    <version>2.0.0</version>
</dependency>

<dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka_2.11</artifactId>
    <version>2.0.0</version>
    <exclusions>
        <exclusion>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
        </exclusion>
        <exclusion>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
        </exclusion>
        <exclusion>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

```java
package com.lj.kafkalearn.chapter1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/*
生产者
* */
public class ProducerFastStart {

    private static final String brokerList = "localhost:9092";
    private static final String topic = "testtopic";

    public static void main(String[] args) {
        Properties properties = new Properties();
        //设置key序列化器
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //设置重试次数
        properties.put(ProducerConfig.RETRIES_CONFIG,10);

        //设置值序列化器
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        //设置集群地址
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,brokerList);

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, "kafka-demo", "hello kafka!bbb");

        try {
            producer.send(record);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            producer.close();
        }
    }
}
```

```java
package com.lj.kafkalearn.chapter1;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/*
* 消费者
* */
public class ConsumerFastStart {
    private static final String brokerList = "localhost:9092";
    private static final String topic = "testtopic";
    private static final String groupId = "group.demo";

    public static void main(String[] args) {
        Properties properties = new Properties();
        //设置key反序列化器
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //设置值反序列化器
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());

        //设置集群地址
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        //设置组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        //消费者订阅 (可以订阅多个主题)
        consumer.subscribe(Collections.singletonList(topic));

        while (true){
            //监听
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000)); //每1秒监听一次
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("====>"+record.value());
            }
        }
    }
}
```











