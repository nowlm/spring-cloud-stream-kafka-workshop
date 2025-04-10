package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

public class EventProcessor  implements Function<Message<List<Event>>, List<Message<Event>>>{

    @Override
    public List<Message<Event>> apply(Message<List<Event>> msgs) {

        var output = new ArrayList<Message<Event>>();

        var messages = msgs.getPayload();

        messages.forEach(event -> {
            if (event.message().indexOf('w') != -1){
                throw new RuntimeException("This message has a W!");
            }
            System.out.println("This is the msg: " + event);
            output.add(MessageBuilder.withPayload(event).build());
        });

        return output;
    }

}
