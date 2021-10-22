package com.sweethome.notificationservice;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
// import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
// import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.*;
// import org.slf4j.LoggerFactory;
// import org.slf4j.Logger;
// import org.apache.log4j.Logger;
// import org.apache.log4j.spi.LoggerFactory;

public class Consumer {
 
    public static void main(String[] args) {
        // Create a logger
        // final Logger logger = 
        //     LoggerFactory.getLogger(Consumer.class);
        
        // Create variables for strings
        final String bootstrapServer = "127.0.0.1:9092";
        final String consumerGroupId = "booking-notifcation-consumer";
        // Create and populate props
        Properties p = new Properties();
        p.setProperty(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
            bootstrapServer
        );
        p.setProperty(
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class.getName()
        );
        p.setProperty(
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            StringDeserializer.class.getName()
        );
        p.setProperty(
            ConsumerConfig.GROUP_ID_CONFIG,
            consumerGroupId
        );
        p.setProperty(
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
            "earliest"
        );

        // Create consumer
        final KafkaConsumer<String,String> consumer = 
            new KafkaConsumer<String,String>(p);
        
        // Subscribe to topics
        consumer.subscribe(
            Arrays.asList("message")
        );

        // Poll and consume records
        final int giveUp = 100;   int noRecordsCount = 0;
        while(true){
            final ConsumerRecords<String,String> records = consumer.poll(
                Duration.ofMillis(1000)
            );

            if (records.count()==0) {
                noRecordsCount++;
                if (noRecordsCount > giveUp) break;
                else continue;
            }

            for (
                ConsumerRecord record: records
            ){
                // logger.info(
                //     "--- Received new booking metadata ---\n" + 
                //     "Key: " + record.key() + "\n" + 
                //     "Value: " + record.value() + "\n" + 
                //     "Topic: " + record.topic() + "\n" + 
                //     "Partition: " + record.partition() + "\n" + 
                //     "Offset: " + record.offset() + "\n"
                // );
                System.out.printf(
                    "--- Received new booking metadata ---\n (%s,%s,%s,%s,%s)\n",
                    record.key(), 
                    record.value(), 
                    record.topic(), 
                    record.partition(), 
                    record.offset()
                );
            }
            consumer.commitAsync();
        }
        consumer.close();
        System.out.println("\n ===== DONE...Please run the program again to continue reading more messages..");

    }
    
}
