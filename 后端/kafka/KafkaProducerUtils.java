package com.lj.kafkalearn.utils;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * kafka生产者工具类
 */
public class KafkaProducerUtils {

    /**
     * 生产者初始化配置
     * @param serversIp  集群ip地址
     * @return
     */
    public static Properties initConfig(String serversIp){
        Properties properties = new Properties();
        //设置key序列化器
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //设置重试次数
        properties.put(ProducerConfig.RETRIES_CONFIG, 10);

        //设置值序列化器
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        //设置集群地址
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, serversIp);
        return properties;
    }

    /**
     * 获取一个生产者
     * @param serversIp  集群ip地址
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K,V> KafkaProducer<K,V> getKafkaProducer(String serversIp){
        return new KafkaProducer<>(initConfig(serversIp));
    }
}
