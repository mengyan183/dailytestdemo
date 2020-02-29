/*
 * Copyright (c) 2020, Beijing Jinhaiqunying, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.kafka.consumer;

import com.xingguo.kafka.factory.ConsumerCreator;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.Collections;

/**
 * KafkaConsumer
 *
 * @author guoxing
 * @date 2020/2/29 6:02 PM
 * @since
 */
public class KafkaConsumer {

    public static void main(String[] args) {
        String TOPIC = "test-topic";
        Consumer<String, String> consumer = ConsumerCreator.createConsumer();
// 循环消费消息
        while (true) {
            //subscribe topic and consume message
            consumer.subscribe(Collections.singletonList(TOPIC));

            ConsumerRecords<String, String> consumerRecords =
                    consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
                System.out.println("Consumer consume message:" + consumerRecord.value());
            }
        }
    }
}
