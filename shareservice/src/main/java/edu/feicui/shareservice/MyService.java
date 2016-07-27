package edu.feicui.shareservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private boolean isRunning;

    String data = "这是服务打印的内容";

    private static final String TAG = "MyService";

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: start");
        isRunning = true;
        new Thread() {
            @Override
            public void run() {
                while (isRunning) {
                    System.out.println(data);
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(TAG, "onStartCommand: start");
        data = intent.getStringExtra("data");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        Log.d(TAG, "onDestroy: start");
        super.onDestroy();
    }
}
