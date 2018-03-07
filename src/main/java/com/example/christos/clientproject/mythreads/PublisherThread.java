package com.example.christos.clientproject.mythreads;


import com.example.christos.clientproject.mqtt.Publisher;

public class PublisherThread extends Thread {
    private String message;

    public PublisherThread(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        super.run();

        Publisher p = new Publisher();
        p.Publish(message);
    }
}
