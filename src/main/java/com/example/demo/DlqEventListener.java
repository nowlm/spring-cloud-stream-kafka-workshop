package com.example.demo;

import java.util.function.Consumer;

public class DlqEventListener implements Consumer<Event>{

    @Override
    public void accept(Event msg) {
        System.out.println("I AM A DLQ PERSON: " + msg);
    }

}
