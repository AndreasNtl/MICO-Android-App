package com.example.christos.clientproject.mythreads;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.christos.clientproject.myfunctions.MyFunctions;

public class MusicOffRunnable implements Runnable {

    private int duration;
    private MyFunctions functions;
    private Handler handler;

    public MusicOffRunnable(Handler handler, int duration, MyFunctions functions) {
        this.handler = handler;
        this.duration = duration;
        this.functions = functions;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        functions.StopMusic();

        Message msg = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("message", "NOTIFY STOPMUSIC");
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

}
