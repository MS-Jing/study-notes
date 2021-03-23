package com.lj.kafkalearn.chapter1;

import com.lj.kafkalearn.utils.KafkaConsumerUtils;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

/*
* 消费者
* */
public class ConsumerFastStart {
    private static final String brokerList = "localhost:9092";
    private static final List<String> topic = Arrays.asList("testtopic");
    private static final String groupId = "group.demo";

    public static void main(String[] args) {
        KafkaConsumer<String, String> consumer = KafkaConsumerUtils.getKafkaConsumer(brokerList,groupId);

        //消费者订阅 (可以订阅多个主题)
        consumer.subscribe(topic,new ConsumerRebalanceListener(){
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> collection) {
                System.out.println("aaaaaaaaaa");
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> collection) {
                System.out.println("bbbbbbbbbbbbbbbbb");
            }
        });

        while (true){
            //监听
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000)); //每1秒监听一次
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("partition: "+record.partition()+" offset: "+record.offset()+" message: "+record.value());
            }
            consumer.commitSync();
        }
    }
}
