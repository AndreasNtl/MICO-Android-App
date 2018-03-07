package com.example.christos.clientproject.mqttservice;


public class AdministratorSettings {
   // public static final String DEFAULT_BROKER = "tcp://192.168.10.104:1883";
    public static final String DEFAULT_BROKER = "tcp://192.168.43.171:1883";
    public static final String MY_PREFS_NAME = "MyPrefsFile";
   // public static final int FREQUENCY_NETWORK_MONITORING = 3000; // seconds

    public String MQTT_BROKER_URL = null;
    public String TOPIC = null;
    public String CLIENT_ID = null;
    public int FREQUENCY_AUTOMODE = 3000;


    //singleton
    private static AdministratorSettings instance = null;

    private AdministratorSettings() {
        MQTT_BROKER_URL = DEFAULT_BROKER;
        TOPIC = "MiCo";
        CLIENT_ID = "androidkt";

    }

    public static synchronized AdministratorSettings getInstance() {
        if (instance == null) {
            instance = new AdministratorSettings();
        }
        return instance;
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        super.clone();
        return new CloneNotSupportedException();
    }
}