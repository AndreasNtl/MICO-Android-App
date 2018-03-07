package com.example.christos.clientproject.mqtt;

import android.os.Bundle;
import android.os.Message;

import com.example.christos.clientproject.mqttservice.AdministratorSettings;
import com.example.christos.clientproject.mqttservice.OurCallback;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Subscriber {

    private AdministratorSettings settings;
   // private MemoryPersistence persistence = new MemoryPersistence();
    private MqttClient sampleClient = null;

    public void subscribe(OurCallback ourcallback, MqttClient sampleclient,  MqttConnectOptions connOpts){

        settings = AdministratorSettings.getInstance();

        String topic        = settings.TOPIC;
        int qos             = 2;
        sampleClient = sampleclient;
        try {
    //        sampleClient = new MqttClient(settings.MQTT_BROKER_URL, settings.CLIENT_ID, persistence);
         //   sampleClient.setCallback(ourcallback);
            sampleClient.connect(connOpts);
            sampleClient.subscribe(topic, qos);


            Message msg = ourcallback.getHandler().obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("message", "subscribe successful");
            msg.setData(bundle);
            ourcallback.getHandler().sendMessage(msg);

        } catch(MqttException me) {
            Message msg = ourcallback.getHandler().obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("message", "subscribe unsuccessful");
            msg.setData(bundle);
            ourcallback.getHandler().sendMessage(msg);
            me.printStackTrace();
        }
    }
}
