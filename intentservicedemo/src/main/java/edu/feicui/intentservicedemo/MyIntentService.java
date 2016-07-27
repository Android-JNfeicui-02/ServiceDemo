package edu.feicui.intentservicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 */
public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentService";


    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getExtras().getString(MainActivity.KEY);
        Log.d(TAG, "onHandleIntent: " + action);

        if (action.equals("service1")){
            Log.d(TAG, "启动的是 Service1 ");
        } else if (action.equals("service2")) {
            Log.d(TAG, "启动的是 Service2 ");
        }else if (action.equals("service3")) {
            Log.d(TAG, "启动的是 Service3 ");
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: start");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: start");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: start");
    }
}
