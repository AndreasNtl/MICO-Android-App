package com.example.christos.clientproject.mythreads;


import com.example.christos.clientproject.mqtt.PublisherForFrequency;

public class PublisherForFrequencyThread extends Thread {
    @Override
    public void run() {
        super.run();

        PublisherForFrequency p = new PublisherForFrequency();
        p.Publish();
    }
}
