package edu.feicui.recorderservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.io.IOException;

public class MyService extends Service {

    private MediaRecorder mRecorder;
    private String        mPath;


    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        // 通过代码拿到自己手机的储存的绝对路径
        mPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        //mPath = storageDirectory.getAbsolutePath();
        System.out.println(mPath);
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        tm.listen(new MyListener(), PhoneStateListener.LISTEN_CALL_STATE);
        super.onCreate();

    }

    private class MyListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    // 录音停止  when IDLE
                    if (mRecorder != null) {
                        mRecorder.stop();
                        // 继续
                        mRecorder.reset();   // You can reuse the object by going back to setAudioSource() step
                        // 释放
                        mRecorder.release(); // Now the object cannot be reused
                    }
                    System.out.println("无状态");
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:

                    if (mRecorder == null) {
                        mRecorder = new MediaRecorder();
                        // 设置音频的来源   录音 -- 获得对方的许可 MIUI FlyMe 华为 ROM 提供功能
                         mRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
                        // mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                        // 输出的格式 3GP  MP4
                        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                        // 输出的编码方式   微信的发语音 -- AMR_NB
                        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                        // 输出路径 -- 保存在哪  定义文件名 MD5算法 BASE64 时间戳
                        mRecorder.setOutputFile(mPath + "/test.3gp");

                        // 让对象处于准备状态
                        try {
                            mRecorder.prepare();  // when  Ringing
                            mRecorder.start();   // Recording is now started
                        }catch (IllegalStateException  e){
                            Toast.makeText(getApplicationContext(), "非法状态异常", Toast.LENGTH_SHORT).show();
                        }
                        catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "写入数据异常", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        // 正式监听录音   when  OFFHOOK

                        System.out.println("接听中");
                    }
                    break;

                case TelephonyManager.CALL_STATE_RINGING:
                    System.out.println("响铃中");


                    break;


            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
