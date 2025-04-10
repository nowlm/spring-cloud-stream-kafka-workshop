package com.example.demo;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.cloud.stream.config.ListenerContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.messaging.Message;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class StreamConfiguration {

    @Bean
    public Consumer<Event> eventListener(){
        return new EventListener();
    }

    @Bean
    public Function<Message<List<Event>>, List<Message<Event>>> eventProcessor(){
        return new EventProcessor();
    }

    @Bean
    public Consumer<Event> dlqEventListener(){
        return new DlqEventListener();
    }

    @Bean
    public ListenerContainerCustomizer<AbstractMessageListenerContainer<?, ?>> customizer(){
         return (container, destinationName, group) -> {
            container.setBatchInterceptor(new NullValueFilterBatchInterceptor<>());
         };
    }
}
