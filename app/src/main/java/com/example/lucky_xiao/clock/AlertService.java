package com.example.lucky_xiao.clock;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

public class AlertService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setAction("com.example.lucky_xiao.braocastdemo.action.Alert");
                sendOrderedBroadcast(intent, null);
            }
        }, 0, 500);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent=new Intent(this,AlertService.class);
        startService(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
