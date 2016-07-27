package edu.feicui.shareservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {

    private Button mBtnStart, mBtnStop;
    private Button mBtnBind, mBtnUnbind, mBtnSync;

    private EditText mEditText;
    private Intent   mIntent;
    private Intent   mSyncIntent;

    private TextView mTextView;


    PutService.MyBinder mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIntent = new Intent(this, MyService.class);
        mSyncIntent = new Intent(this, PutService.class);

        mBtnStart = (Button) findViewById(R.id.btn_start);
        mBtnStop = (Button) findViewById(R.id.btn_stop);
        mBtnBind = (Button) findViewById(R.id.btn_bind);
        mBtnUnbind = (Button) findViewById(R.id.btn_unbind);
        mBtnSync = (Button) findViewById(R.id.btn_sync);

        mEditText = (EditText) findViewById(R.id.editText);

        mTextView = (TextView) findViewById(R.id.textView);

        mBtnStart.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
        mBtnBind.setOnClickListener(this);
        mBtnUnbind.setOnClickListener(this);
        mBtnSync.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_start:
                mIntent.putExtra("data", mEditText.getText().toString());
                startService(mIntent);
                break;
            case R.id.btn_stop:
                stopService(mIntent);
                break;

            case R.id.btn_bind:
                bindService(mSyncIntent, this, BIND_AUTO_CREATE);
                Toast.makeText(MainActivity.this, "绑定了服务", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_unbind:
                unbindService(this);
                Toast.makeText(MainActivity.this, "解绑服务", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_sync:
                if (mBinder != null) {
                    mBinder.setData(mEditText.getText().toString());
                }
                break;

        }
    }

    Handler handler = new Handler() {
        /**
         * 接收数据
         * @param msg
         * message对象
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mTextView.setText(msg.getData().getString("data"));

        }
    };

    /**
     * 需要在 绑定服务的时候 同时 关联 Service 和 Activity 之间的通信
     *
     * @param name
     * @param service
     */
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mBinder = (PutService.MyBinder) service;
        mBinder.getService().setCallback(new PutService.Callback() {
            @Override
            public void onDataChanged(String data) {
                // 把数据传递过来 必须要使用Handler来通信
                // 需要message对象 来帮助我们 传递内容
                Message message = new Message();
                // 使用Bundle 来放置数据
                Bundle bundle = new Bundle();
                bundle.putString("data", data);
                // message调用 set方法 传输数据 但是 数据类型是 bundle
                message.setData(bundle);
                // 调用handler的send 方法 把 message对象发送给 handler的 handleMessage方法
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }


}
