package com.example.demo;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.listener.BatchInterceptor;

public class NullValueFilterBatchInterceptor<K,V> implements BatchInterceptor<K, V>{

    @Override
    public ConsumerRecords<K, V> intercept(
            ConsumerRecords<K, V> records,
            Consumer<K, V> consumer
        ) {

        return records.partitions().stream()
                .map(partition -> StreamSupport.stream(records.spliterator(), false)
                        .filter(consumerRecord -> partition.partition() == consumerRecord.partition())
                        .filter(consumerRecord -> {
                            var hasValue = consumerRecord.value() != null;

                            if (!hasValue) {
                                System.out.println("Dropping value due to null! " + consumerRecord.value());
                            }

                            return hasValue;
                        })
                        .collect(Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> Map.entry(partition, list))))
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue),
                        ConsumerRecords::new));
    }

}
