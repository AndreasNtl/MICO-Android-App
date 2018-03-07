package com.example.christos.clientproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.christos.clientproject.mqttservice.AdministratorSettings;
import com.example.christos.clientproject.mythreads.PublisherForFrequencyThread;

public class FrequencySettingsActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText android_frequency;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frequency_settings);

        android_frequency = findViewById(R.id.setting_Frequency_Value);
        saveButton = (Button) findViewById(R.id.settingsSendFrequency);
        saveButton.setOnClickListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();

        try {
            int ifrequency = AdministratorSettings.getInstance().FREQUENCY_AUTOMODE/1000;
            String temp = String.valueOf(ifrequency);

            android_frequency.setText(temp);

        } catch (Exception ex) {
            Toast.makeText(this, "Note: No settings were found. ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.settingsSendFrequency:
                try {
                    String sfrequency = android_frequency.getText().toString();
                    int frequency = Integer.parseInt(sfrequency)*1000;

                    AdministratorSettings.getInstance().FREQUENCY_AUTOMODE = frequency;

                    // send
                    PublisherForFrequencyThread t = new PublisherForFrequencyThread();
                    t.start();

                    Toast.makeText(this, "saved successfully ", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }
}
