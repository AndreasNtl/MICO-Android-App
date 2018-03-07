package com.example.christos.clientproject.mythreads;


import com.example.christos.clientproject.mqtt.Subscriber;
import com.example.christos.clientproject.mqttservice.OurCallback;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class SubscriberThread extends Thread {

    private MqttClient sampleClient = null;
    private MqttConnectOptions connOpts;
    private OurCallback ourcallback;

    public SubscriberThread( OurCallback ourcallback, MqttClient sampleClient, MqttConnectOptions connOpts) {

        this.sampleClient = sampleClient;
        this.connOpts = connOpts;
        this.ourcallback = ourcallback;
    }

    @Override
    public void run() {
        super.run();

        Subscriber subs = new Subscriber();
        subs.subscribe(ourcallback, sampleClient, connOpts);
    }
}
