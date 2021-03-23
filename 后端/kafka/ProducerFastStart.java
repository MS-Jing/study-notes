package com.lj.kafkalearn.chapter1;

import com.lj.kafkalearn.utils.KafkaProducerUtils;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.Future;

/*
生产者
* */
public class ProducerFastStart {

    private static final String brokerList = "localhost:9092";
    private static final String topic = "testtopic";

    public static void main(String[] args) throws InterruptedException {

        KafkaProducer<String, String> producer = KafkaProducerUtils.getKafkaProducer(brokerList);
//        ProducerRecord<String, String> record = new ProducerRecord<>(topic, "kafka-demo", "hello kafka!ccc");
//
//        try {
//            /**
//             * 同步发送
//             * Future<RecordMetadata> send = producer.send(record);
//             *             RecordMetadata recordMetadata = send.get();
//             *             System.out.println("topic:"+recordMetadata.topic());
//             *             System.out.println("partition:"+recordMetadata.partition());
//             *             System.out.println("offset:"+recordMetadata.offset());
//             */
//
//            /**
//             * 异步发送
//             * */
//            producer.send(record, (RecordMetadata recordMetadata, Exception e) -> {
//                if (e == null) {  //如果没有出现异常
//                    System.out.println("topic:" + recordMetadata.topic());
//                    System.out.println("partition:" + recordMetadata.partition());
//                    System.out.println("offset:" + recordMetadata.offset());
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            producer.close();
//        }

        for (int i = 0; i < 500; i++) {
            producer.send(new ProducerRecord<>(topic, "kafka-demo", "test-"+i));
            Thread.sleep(1000);
        }


    }
}
