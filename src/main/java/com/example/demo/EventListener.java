package com.example.demo;

import java.util.function.Consumer;

public class EventListener implements Consumer<Event> {

    @Override
    public void accept(Event event) {
        System.out.println("I got the message: " +  event.toString());
    }

}
