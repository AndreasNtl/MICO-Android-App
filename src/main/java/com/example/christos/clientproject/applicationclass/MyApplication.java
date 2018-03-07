package com.example.christos.clientproject.applicationclass;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.christos.clientproject.mqttservice.AdministratorSettings;

import static com.example.christos.clientproject.mqttservice.AdministratorSettings.DEFAULT_BROKER;
import static com.example.christos.clientproject.mqttservice.AdministratorSettings.MY_PREFS_NAME;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // load preferences
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredBroker = prefs.getString("MQTT_BROKER_URL", DEFAULT_BROKER);

        AdministratorSettings.getInstance().MQTT_BROKER_URL = restoredBroker;

        Log.d("Preferences", "************************************** onCreate");
    }


    @Override
    public void onTerminate() {
        super.onTerminate();

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("MQTT_BROKER_URL", AdministratorSettings.getInstance().MQTT_BROKER_URL);
        editor.apply();

        Log.d("Preferences", "**************************************onTerminate ");
    }
}
