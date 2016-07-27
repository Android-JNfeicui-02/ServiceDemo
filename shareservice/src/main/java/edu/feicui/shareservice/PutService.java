package edu.feicui.shareservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * 目的 让Service 传输 内容给 Activity
 */
public class PutService extends Service {
    private boolean isRunning;

    private String mData = "Hello";

    private static final String TAG = "PutService";

    public PutService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder {
        /**
         * 放置数据的方法
         */
        public void setData(String data) {
            PutService.this.mData = data;
        }

        public PutService getService() {
            return PutService.this;
        }
    }




    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: start");
        isRunning = true;
        new Thread(){
            @Override
            public void run() {
                int i = 0;
                while (isRunning) {
                    i++;
                    String str = i + " -- " + mData;
                    System.out.println(str);

                    if (mCallback != null) {
                        mCallback.onDataChanged(str);
                    }

                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mData = intent.getStringExtra("data");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        super.onDestroy();
    }

    private Callback mCallback = null;

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public Callback getCallback() {
        return mCallback;
    }


    // 通信 需要用一个 接口 来实现
    public interface Callback {
        void onDataChanged(String data);
    }


}
