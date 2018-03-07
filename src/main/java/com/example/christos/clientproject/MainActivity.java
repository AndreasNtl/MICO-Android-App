package com.example.christos.clientproject;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.christos.clientproject.InternetAvailability.CheckingInternetAvailabilityThread;
import com.example.christos.clientproject.mqttservice.MqttMessageService;
import com.example.christos.clientproject.mqttservice.OurCallback;
import com.example.christos.clientproject.myfunctions.MyFunctions;
import com.example.christos.clientproject.mythreads.FlashOffRunnable;
import com.example.christos.clientproject.mythreads.MusicOffRunnable;
import com.example.christos.clientproject.mythreads.PublisherThread;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static String TAG = "MainActivity";
    private MyFunctions functions;
    private Button flashButton;
    private Button musicButton;
    private ProgressBar progressBar;
    private TextView connectingTxtView;
    private CheckingInternetAvailabilityThread internetAv;
    private Button manualButton;
    private Button autoButton;
    private Button subButton;
    private Button unsubButton;

    private void manageInternetNetwork(boolean networkavailable, boolean automatic, boolean subscribed) {
        Log.e("######", networkavailable + " " + automatic + " " + subscribed);
        if (!networkavailable) {
            Toast.makeText(this, "M.I.N.: Please open WiFi", Toast.LENGTH_SHORT).show();
        } else {
            if (!subscribed) {
                if (automatic) {
                    Toast.makeText(this, "M.I.N.: Trying to subscribe... ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MqttMessageService.class);
                    stopService(intent);
                    startService(intent);
                } else {
                    Toast.makeText(this, "M.I.N.: Please press subscribe button", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            Log.d(TAG, "msg received from handler");
            Bundle bundle = msg.getData();

            String text = bundle.getString("message");

            String[] parts = text.split(" ");
            String part1 = parts[0];
            String part2 = parts[1];

            part1 = part1.toLowerCase();


            switch (part1) {
                case "musicon": {
                    final int duration = Integer.parseInt(part2);
                    boolean MusicIsOn = functions.StartMusic();
                    if (MusicIsOn) {
                        musicButton.setText("MUSIC OFF");
                        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();

                        Runnable r = new MusicOffRunnable(handler, duration, functions);
                        Thread t = new Thread(r);
                        t.start();

                        PublisherThread publisher = new PublisherThread("Music turned on");
                        publisher.start();
                    } else {
                        Toast.makeText(MainActivity.this, "Music is already on", Toast.LENGTH_SHORT).show();

                        PublisherThread publisher = new PublisherThread("Music already on");
                        publisher.start();
                    }
                    break;
                }
                case "musicoff": {
                    Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                    functions.StopMusic();
                    musicButton.setText("MUSIC ON");

                    PublisherThread publisher = new PublisherThread("Music turned off");
                    publisher.start();
                    break;
                }
                case "flashon": {
                    final int duration = Integer.parseInt(part2);
                    boolean FlashIsOpen = functions.FlashOpen();
                    if (FlashIsOpen) {
                        flashButton.setText("FLASH OFF");
                        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();

                        Runnable r = new FlashOffRunnable(handler, duration, functions);
                        Thread t = new Thread(r);
                        t.start();

                        PublisherThread publisher = new PublisherThread("Flash turned on");
                        publisher.start();
                    } else
                        Toast.makeText(MainActivity.this, "Flash is already on", Toast.LENGTH_SHORT).show();

                    PublisherThread publisher = new PublisherThread("Flash already on");
                    publisher.start();
                    break;
                }
                case "flashoff": {
                    Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                    functions.FlashClose();
                    flashButton.setText("FLASH ON");

                    PublisherThread publisher = new PublisherThread("Flash turned off");
                    publisher.start();
                    break;
                }
                case "networkerror": {
                    Toast.makeText(MainActivity.this, "NETWORKERROR: " + text, Toast.LENGTH_SHORT).show();
                    break;
                }case "subscribe": {
                    if (part2.equals("successful")) {
                        progressBar.setVisibility(View.GONE);
                        connectingTxtView.setVisibility(View.GONE);
                        musicButton.setEnabled(true);
                        flashButton.setEnabled(true);
                        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                    }
                    if (part2.equals("unsuccessful")) {
                        progressBar.setVisibility(View.GONE);
                        connectingTxtView.setVisibility(View.GONE);
                        musicButton.setEnabled(true);
                        flashButton.setEnabled(true);
                        Toast.makeText(MainActivity.this, text + "\n" + "check your ip configurations", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case "notify": {
                    if (part2.equalsIgnoreCase("CLOSEFLASH")) {
                        flashButton.setText("FLASH ON");
                    }
                    if (part2.equalsIgnoreCase("STOPMUSIC")) {
                        musicButton.setText("MUSIC ON");
                    }
                }
                case "network": {
                    boolean automatic = functions.IsAutomaticOn();

                    if (part2.equalsIgnoreCase("available")) {
                        manageInternetNetwork(true, automatic, MqttMessageService.isConnected());
                    }
                    if (part2.equalsIgnoreCase("notavailable")) {
                        manageInternetNetwork(false, automatic, MqttMessageService.isConnected());
                    }
                }
                case "eyes_closed" :
                    functions.StopMusic();
                    functions.FlashClose();
                    break;
                case "eyes_open" :
                    functions.StartMusic();
                    functions.FlashOpen();
                    break;

            }
        }
    };

    public static OurCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        internetAv = new CheckingInternetAvailabilityThread(handler,this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            }
        }

        Camera camera = Camera.open();

        if (camera == null) {
            Toast.makeText(this, "Ooops, your phone cannot use this application", Toast.LENGTH_LONG).show();
            finish();
        } else {
            functions = new MyFunctions(this, camera);
        }


        flashButton = (Button) findViewById(R.id.flash_button);
        flashButton.setText("Flash On");
        flashButton.setOnClickListener(this);
        flashButton.setEnabled(false);

        musicButton = (Button) findViewById(R.id.music_button);
        musicButton.setText("Music On");
        musicButton.setOnClickListener(this);
        musicButton.setEnabled(false);

        manualButton = (Button) findViewById(R.id.manual_button);
        manualButton.setOnClickListener(this);

        autoButton = (Button) findViewById(R.id.automatic_button);
        autoButton.setOnClickListener(this);

        subButton = (Button) findViewById(R.id.sub_button);
        subButton.setOnClickListener(this);

        unsubButton = (Button) findViewById(R.id.unsubbutton);
        unsubButton.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar) ;
        progressBar.setVisibility(View.VISIBLE);

        connectingTxtView = (TextView) findViewById(R.id.ConnectingtextView) ;

        callback = new OurCallback(handler);

        // start service
        if (callback != null) {
            Intent intent = new Intent(MainActivity.this, MqttMessageService.class);
            startService(intent);
        }

        internetAv.start();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if(!MqttMessageService.isConnected() ) {
            flashButton.setEnabled(false);
            musicButton.setEnabled(false);
            if (callback != null) {
                progressBar.setVisibility(View.VISIBLE);
                connectingTxtView.setVisibility(View.VISIBLE);
                Intent intent = new Intent(MainActivity.this, MqttMessageService.class);
                stopService(intent);
                startService(intent);
            } else {
                progressBar.setVisibility(View.VISIBLE);
                connectingTxtView.setVisibility(View.VISIBLE);
                Intent intent = new Intent(MainActivity.this, MqttMessageService.class);
                startService(intent);
            }
        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.b:
                functions.FlashClose();
                functions.StopMusic();
                finish();
                break;
            case R.id.c:
                Intent frequencyIntent = new Intent(MainActivity.this, FrequencySettingsActivity.class);
                startActivity(frequencyIntent);
                break;
            case R.id.a:
                Intent aboutIntent = new Intent(MainActivity.this, ConfigurationActivity.class);
                startActivity(aboutIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flash_button:
                String flashButtonText = functions.swapFlash();
                flashButton.setText(flashButtonText);
                break;
            case R.id.music_button:
                String musicButtonText = functions.swapMusic();
                musicButton.setText(musicButtonText);
                break;
            case R.id.automatic_button:
                functions.swapToAutomatic();
                autoButton.setBackgroundColor(Color.CYAN);
                manualButton.setBackgroundColor(Color.GRAY);
                subButton.setVisibility(View.INVISIBLE);
                unsubButton.setVisibility(View.INVISIBLE);
                break;
            case R.id.manual_button:
                functions.swapToManual();
                autoButton.setBackgroundColor(Color.GRAY);
                manualButton.setBackgroundColor(Color.CYAN);
                subButton.setVisibility(View.VISIBLE);
                unsubButton.setVisibility(View.VISIBLE);
                break;
            case R.id.sub_button: {
                Intent intent = new Intent(MainActivity.this, MqttMessageService.class);
                stopService(intent);
                startService(intent);
                break;
            }
            case R.id.unsubbutton: {
                Intent intent = new Intent(MainActivity.this, MqttMessageService.class);
                stopService(intent);
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing MiCo")
                .setMessage("Are you sure you want to close MiCo?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        functions.FlashClose();
                        functions.StopMusic();
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        internetAv.stop();
        if (callback != null) {
            Intent intent = new Intent(MainActivity.this, MqttMessageService.class);
            stopService(intent);
        }

    }


}
