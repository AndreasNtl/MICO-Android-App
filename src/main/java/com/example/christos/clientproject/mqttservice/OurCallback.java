package com.example.christos.clientproject.mqttservice;

/**
 * Created by christos on 15/11/2017.
 */


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class OurCallback implements MqttCallback {

    private static final String TAG = "OurCallback";
    //Looper is from Main thread //We are in background thread
    private final Handler handler;

    public OurCallback(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String payload = new String(message.getPayload());
        Log.d(TAG, " *********************** messageArrived:" + payload);

        Message msg = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("message", payload);
        msg.setData(bundle);

        handler.sendMessage(msg);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    public Handler getHandler() {
        return handler;
    }
}
