package edu.feicui.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "MyService";

    public MyService() {
    }

    /**
     * 这是服务必须要实现的方法
     * 目的是用来通信
     * @param intent
     * @return
     *
     */
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: start");
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: start");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: start");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: start");
        super.onDestroy();
    }
}
