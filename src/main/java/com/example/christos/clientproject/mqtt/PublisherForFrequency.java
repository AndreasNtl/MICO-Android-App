package com.example.christos.clientproject.mqtt;

import com.example.christos.clientproject.mqttservice.AdministratorSettings;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Random;

public class PublisherForFrequency {

    private AdministratorSettings settings;


    public void Publish(){

        settings = AdministratorSettings.getInstance();

        String topic        = settings.TOPIC + "2";
        int qos             = 2;
        String broker       = settings.MQTT_BROKER_URL;
        String clientId     = settings.CLIENT_ID +"#" + new Random().nextInt(); //random thread id
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            sampleClient.connect(connOpts);
            String content = "frequencyoption " + settings.FREQUENCY_AUTOMODE;
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            sampleClient.disconnect();
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        }
    }
}
