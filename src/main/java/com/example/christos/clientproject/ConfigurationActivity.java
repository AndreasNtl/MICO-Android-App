package com.example.christos.clientproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.christos.clientproject.mqttservice.AdministratorSettings;
import com.example.christos.clientproject.mqttservice.MqttMessageService;

import static com.example.christos.clientproject.mqttservice.AdministratorSettings.MY_PREFS_NAME;

public class ConfigurationActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText ip;
    private EditText port;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        ip = findViewById(R.id.Setting_Ip_Value);
        port = findViewById(R.id.Setting_Port_Value);
        saveButton = (Button) findViewById(R.id.settingsSave_button);
        saveButton.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();

        try {
            String broker = AdministratorSettings.getInstance().MQTT_BROKER_URL;
            broker = broker.replace("tcp://","");
            String[] tokens = broker.split(":");

            ip.setText(tokens[0]);
            port.setText(tokens[1]);
        } catch (Exception ex) {
            ip.setText(AdministratorSettings.DEFAULT_BROKER);
            port.setText("1883");
            Toast.makeText(this, "Note: No settings were found. ", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("MQTT_BROKER_URL", AdministratorSettings.getInstance().MQTT_BROKER_URL);
        editor.apply();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mysettingsmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.quit:
                Intent intent = new Intent(this, MqttMessageService.class);
                stopService(intent);
                finishAffinity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.settingsSave_button:
                try {
                    String sip = ip.getText().toString();
                    String sport = port.getText().toString();
                    String broker = "tcp://" + sip + ":" + sport                                            ;

                    if (sip != null && sip.isEmpty() == false && sport != null && !sport.isEmpty() ) {
                        AdministratorSettings.getInstance().MQTT_BROKER_URL = broker;
                    }
                    Toast.makeText(this, "saved successfully ", Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }
}
