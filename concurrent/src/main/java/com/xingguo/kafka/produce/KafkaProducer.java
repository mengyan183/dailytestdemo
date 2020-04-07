/*
 * Copyright (c) 2020, guoxing, Co,. Ltd. All Rights Reserved
 */
package com.xingguo.kafka.produce;

import com.xingguo.kafka.factory.ProducerCreator;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.ExecutionException;

/**
 * KafkaProducer
 *
 * @author guoxing
 * @date 2020/2/29 6:00 PM
 * @since
 */
public class KafkaProducer {
    public static void main(String[] args) {
        String TOPIC = "test-topic";
        Producer<String, String> producer = ProducerCreator.createProducer();
        ProducerRecord<String, String> record =
                new ProducerRecord<>(TOPIC, "hello, Kafka!");
        try {
            while (true) {
                //send message
                RecordMetadata metadata = producer.send(record).get();
                System.out.println("Record sent to partition " + metadata.partition()
                        + " with offset " + metadata.offset());
            }
        } catch (ExecutionException | InterruptedException e) {
            System.out.println("Error in sending record");
            e.printStackTrace();
        }
        producer.close();

    }
}
