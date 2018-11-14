package com.example.yucong.musicplayer;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private EditText nameText; //文件名称
    private String path;
    private MediaPlayer mediaPlayer;
    private boolean pause=false;
    private int position;//记录暂停的位置

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_PHONE_STATE",
            "android.permission.INTERNET",
            "android.permission.ACCESS_FINE_LOCATION"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = new MediaPlayer(); //初始化媒体类
        nameText = (EditText) this.findViewById(R.id.filename);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);//获取电话管理类对象
        telephonyManager.listen(new MyPhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);//监听电话状态
        verifyStoragePermissions(this);

    }


    /**
     * 监听电话状态
     */
    private final class MyPhoneListener extends PhoneStateListener {
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING://来电时则获取当前播放位置，停止播放
                    if(mediaPlayer.isPlaying()) {
                        position = mediaPlayer.getCurrentPosition();
                        mediaPlayer.stop();
                    }
                    break;

                case TelephonyManager.CALL_STATE_IDLE://电话挂断时
                    if(position>0 && path!=null){
                        play(position);
                        position = 0;
                    }
                    break;
            }
        }
    }





//    @Override
//    protected void onDestroy() {
//        mediaPlayer.release();
//        mediaPlayer = null;
//        super.onDestroy();
//    }
//
    public void mediaplay(View v){
        switch (v.getId()) {
            case R.id.playbutton:
                String filename = nameText.getText().toString();
                File audio = new File(Environment.getExternalStorageDirectory(), filename);//首先在设备sd卡上寻找
                if(audio.exists()){
                    path = audio.getAbsolutePath(); //获取绝对路径 播放音乐
                    play(0);
                }else{
                    path = null;
                    Toast.makeText(getApplicationContext(), R.string.filenoexist, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.pausebutton:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    pause = true;
                    ((Button)v).setText(R.string.continues);//改变按钮的字
                }else{
                    if(pause){
                        mediaPlayer.start();//ŒÌÐø²¥·Å
                        pause = false;
                        ((Button)v).setText(R.string.pausebutton);
                    }
                }
                break;
            case R.id.resetbutton:
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.seekTo(0);
                }else{
                    if(path!=null){
                        play(0);
                    }
                }
                break;
            case R.id.stopbutton:
                if(mediaPlayer.isPlaying()) mediaPlayer.stop();
                break;
        }
    }

    private void play(int position) {
        try {
            mediaPlayer.reset();//重置播放
            mediaPlayer.setDataSource(path); //获取播放资源
            mediaPlayer.prepare();//开始缓存
            mediaPlayer.setOnPreparedListener(new PrepareListener(position));//监听缓存状态
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final class PrepareListener implements MediaPlayer.OnPreparedListener {
        private int position;//成员类 保存播放进度
        public PrepareListener(int position) {
            this.position = position;
        }

        public void onPrepared(MediaPlayer mp) {
            mediaPlayer.start();//开始播放
            if(position>0) mediaPlayer.seekTo(position);//已经播放一段时间的话 从当前位置开始播放
        }
    }
//    public static void verifyStoragePermissions(Activity activity) {
//
//        try {
//            int permission2=ActivityCompat.checkSelfPermission(activity,
//                    "android.permission.INTERNET");
//            //检测是否有写的权限
//            int permission = ActivityCompat.checkSelfPermission(activity,
//                    "android.permission.READ_PHONE_STATE");
//            int permission1=ActivityCompat.checkSelfPermission(activity,
//                    "android.permission.WRITE_EXTERNAL_STORAGE");
//            int permission3=ActivityCompat.checkSelfPermission(activity,
//                    "android.permission.ACCESS_FINE_LOCATION");
//            int [] permissions={permission,permission1,permission2,permission3};
//            for (int i = 0; i < permissions.length-1; i++) {
//               if(permissions[i]!= PackageManager.PERMISSION_GRANTED){
//                   ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
//               };
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 动态获取权限
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {

        try {

            int permission3=ActivityCompat.checkSelfPermission(activity,
                    "android.permission.ACCESS_FINE_LOCATION");


                if(permission3!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
                };


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    public static void verifyStoragePermissions(Activity activity) {
//
//        try {
//            //检测是否有写的权限
//            int permission = ActivityCompat.checkSelfPermission(activity,
//                    "android.permission.READ_PHONE_STATE");
//            int permission1=ActivityCompat.checkSelfPermission(activity,
//                    "android.permission.WRITE_EXTERNAL_STORAGE");
//            int permission2=ActivityCompat.checkSelfPermission(activity,
//                    "android.permission.INTERNET");
//            if (permission != PackageManager.PERMISSION_GRANTED&&permission1 != PackageManager.PERMISSION_GRANTED) {
//                // 没有写的权限，去申请写的权限，会弹出对话框
//                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//
//    public static void verifyStoragePermissions(Activity activity) {
//
//        try {
//            //检测是否有写的权限
//            int permission = ActivityCompat.checkSelfPermission(activity,
//                    "android.permission.WRITE_EXTERNAL_STORAGE");
//            if (permission != PackageManager.PERMISSION_GRANTED) {
//                // 没有写的权限，去申请写的权限，会弹出对话框
//                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
