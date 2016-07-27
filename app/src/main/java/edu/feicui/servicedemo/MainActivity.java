package edu.feicui.servicedemo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";
    private BoundService mBoundService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void startService(View view) {
        //startService(mIntent);

    }

    public void stopService(View view) {
        //stopService(mIntent);
    }

    public void startBind(View view) {
        Intent intent = new Intent(this,BoundService.class);
        Toast.makeText(MainActivity.this, "点击了启动绑定", Toast.LENGTH_SHORT).show();
        bindService(intent, cc, Service.BIND_AUTO_CREATE);
    }

    public void stopBind(View view) {
        unbindService(cc);
    }

    private ServiceConnection cc = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: -----启动了-----");
            BoundService.DemoBinder demoBinder = (BoundService.DemoBinder) service;
            mBoundService = demoBinder.getServiceInstance();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: -----被解除绑定了-----");

        }
    };
}
