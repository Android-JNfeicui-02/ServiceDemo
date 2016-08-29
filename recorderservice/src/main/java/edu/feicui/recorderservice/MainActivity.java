package edu.feicui.recorderservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 *  int	CALL_STATE_IDLE
    Device call state: No activity.
    int	CALL_STATE_OFFHOOK
    Device call state: Off-hook.
    int	CALL_STATE_RINGING
    Device call state: Ringing.


    服务你在开启了软件的时候 --> 出发服务的开启
    监听开机广播 --> 触发服务
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    public void click(View view) {
        Intent intent = new Intent(this,MyService.class);
        startService(intent);
    }
}
