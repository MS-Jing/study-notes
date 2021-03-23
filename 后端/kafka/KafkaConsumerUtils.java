package com.lj.kafkalearn.utils;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.List;
import java.util.Properties;

/**
 * kafka消费者工具类
 */
public class KafkaConsumerUtils {
    /**
     * 消费者工具类初始化配置
     * @param serversIp   集群ip地址
     * @param groupId     消费组
     * @return
     */
    public static Properties initConfig(String serversIp,String groupId){
        Properties properties = new Properties();
        //设置key反序列化器
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        //设置值反序列化器
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());

        //设置集群地址
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, serversIp);
        //设置组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,groupId);

        //设置 自动提交
//        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,false);
        return properties;
    }

    /**
     * 获取一个消费者
     * @param serversIp   集群ip地址
     * @param groupId     消费组
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K,V> KafkaConsumer<K,V> getKafkaConsumer(String serversIp, String groupId){
        return new KafkaConsumer<>(initConfig(serversIp, groupId));
    }
}
