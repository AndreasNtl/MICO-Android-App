package com.example.christos.clientproject.mythreads;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.christos.clientproject.myfunctions.MyFunctions;

public class FlashOffRunnable implements Runnable {

    private int duration;
    private MyFunctions functions;
    private Handler handler;

    public FlashOffRunnable(Handler handler, int duration, MyFunctions functions) {
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
        functions.FlashClose();

        Message msg = handler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putString("message", "NOTIFY CLOSEFLASH");
        msg.setData(bundle);
        handler.sendMessage(msg);
    }

}
