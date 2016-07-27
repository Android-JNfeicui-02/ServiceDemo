package edu.feicui.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class BoundService extends Service {
    private static final String TAG = "BoundService";

    IBinder mIBinder = new DemoBinder();
    private boolean isRunning;

    int count;

    public BoundService() {
    }

    class DemoBinder extends Binder {
        // 单例模式
        public BoundService getServiceInstance() {
            return BoundService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(BoundService.this, "绑定服务执行了", Toast.LENGTH_SHORT).show();

        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(BoundService.this, "已解除绑定服务", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {

        Log.d(TAG, "onCreate: Start");

        new Thread() {
            @Override
            public void run() {
                // 跑一个sleep
                while(!isRunning) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count++;
                    System.out.println("当前数字是： "+count);
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        isRunning = true;
        Log.d(TAG, "onDestroy: start");
        super.onDestroy();
    }
}
