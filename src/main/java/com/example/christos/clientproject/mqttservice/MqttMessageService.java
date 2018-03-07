package com.example.christos.clientproject.mqttservice;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.christos.clientproject.MainActivity;
import com.example.christos.clientproject.mqtt.Subscriber;
import com.example.christos.clientproject.mythreads.SubscriberThread;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttMessageService extends Service {

    private static final String TAG = "MqttMessageService";
    private MqttConnectOptions connOpts = new MqttConnectOptions();
    private AdministratorSettings settings = AdministratorSettings.getInstance();
    private OurCallback ourcallback;
    private MemoryPersistence persistence = new MemoryPersistence();
    public static MqttClient sampleClient = null;
    private SubscriberThread subscriber = null;

    public static boolean isConnected() {
        return sampleClient != null && sampleClient.isConnected();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "*****************************onCreate");

        connOpts.setCleanSession(true);//Set callback

        ourcallback = MainActivity.callback;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "***************************** onStartCommand");

        try {
            sampleClient = new MqttClient(settings.MQTT_BROKER_URL, settings.CLIENT_ID, persistence);
            sampleClient.setCallback(ourcallback);
//            sampleClient.connect();
            subscriber = new SubscriberThread(ourcallback, sampleClient, connOpts);
            subscriber.start();
         //   subscriber.join();
//            sampleClient = subscriber.getClient();

            Log.d(TAG, "*****************************onStartCommand - subscribe ok");
        } catch (Exception ex) {
            Log.d(TAG, "*****************************onStartCommand - subscribe EXCEPTION");
            sampleClient = null;
            ex.printStackTrace();
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "onDestroy");

        try {
            if (sampleClient != null) {
                try {
                    sampleClient.unsubscribe(settings.TOPIC);
                    sampleClient.close();
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                sampleClient = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Log.d(TAG, "*****************************onStartCommand - unsubscribe ok");
    }


}