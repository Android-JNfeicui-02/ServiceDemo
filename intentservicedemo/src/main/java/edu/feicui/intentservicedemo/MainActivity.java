package edu.feicui.intentservicedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static final String KEY = "param";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent1 = new Intent(this,MyIntentService.class);
        Bundle b1 = new Bundle();
        b1.putString(KEY,"service1");
        intent1.putExtras(b1);

        Intent intent2 = new Intent(this,MyIntentService.class);
        Bundle b2 = new Bundle();
        b2.putString(KEY,"service2");
        intent2.putExtras(b2);

        Intent intent3 = new Intent(this,MyIntentService.class);
        Bundle b3 = new Bundle();
        b3.putString(KEY,"service3");
        intent3.putExtras(b3);

        startService(intent1);
        startService(intent2);
        startService(intent3);
    }
}
