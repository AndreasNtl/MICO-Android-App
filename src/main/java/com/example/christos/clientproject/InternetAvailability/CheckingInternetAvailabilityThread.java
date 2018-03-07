package com.example.christos.clientproject.InternetAvailability;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class CheckingInternetAvailabilityThread implements Runnable {

    private Handler handler;
    private Context mContext;;
    private boolean isRunning = true;

    public CheckingInternetAvailabilityThread(Handler handler, Context mContext) {
        this.handler = handler;
        this.mContext = mContext;
    }

    public void stop() {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            try {

                ConnectivityManager cm = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();

                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();

                if(networkInfo != null && networkInfo.isConnected()){
                    bundle.putString("message", "Network available");
                } else {
                    bundle.putString("message", "Network notavailable");
                }

                msg.setData(bundle);
                handler.sendMessage(msg);
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void start() {
        isRunning = true;

        Thread t = new Thread(this);
        t.start();
    }
}
