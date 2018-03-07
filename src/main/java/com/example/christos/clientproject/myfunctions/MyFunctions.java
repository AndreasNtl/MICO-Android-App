package com.example.christos.clientproject.myfunctions;

import android.app.Activity;
import android.hardware.Camera;
import android.media.MediaPlayer;

import com.example.christos.clientproject.R;

public class MyFunctions {
    private Activity activity;
    private final Camera camera;
    private Camera.Parameters parameters;
    private boolean FlashIsOn = false;
    private boolean MusicIsOn = false;
    private MediaPlayer mediaPlayer = null;
    private boolean AutomaticIsOn = true;

    public MyFunctions(Activity activity, Camera camera) {
        this.activity = activity;
        this.camera = camera;
        this.parameters = camera.getParameters();
    }

    public boolean FlashOpen() {
        if (!FlashIsOn) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.startPreview();
            FlashIsOn = true;

            return FlashIsOn;
        }  else {
            return !FlashIsOn;
        }
    }

    public void FlashClose() {
        if (FlashIsOn) {
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.stopPreview();
            FlashIsOn = false;
        }
    }

    public boolean StartMusic() {
        if (!MusicIsOn) {
            mediaPlayer = MediaPlayer.create(activity, R.raw.starwars);
            mediaPlayer.start();
            MusicIsOn = true;
            return MusicIsOn;
        } else {
            return false;
        }

    }

    public void StopMusic() {
        if (MusicIsOn) {
            mediaPlayer.stop();
            MusicIsOn = false;
        }
    }

    public String swapFlash() {
        if (FlashIsOn) {
            FlashClose();
            return "FLASH ON" ;
        } else {
            FlashOpen();
            return "FLASH OFF" ;
        }
    }

    public String swapMusic() {
        if (MusicIsOn) {
            StopMusic();
            return "MUSIC ON" ;
        } else {
            StartMusic();
            return "MUSIC OFF" ;
        }
    }

    public void swapToAutomatic() {
        AutomaticIsOn = true;
    }

    public void swapToManual() {
        AutomaticIsOn = false;
    }

    public boolean IsAutomaticOn() {
        return AutomaticIsOn;
    }

}
