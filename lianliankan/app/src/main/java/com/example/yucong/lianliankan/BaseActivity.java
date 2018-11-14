package com.example.yucong.lianliankan;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.yucong.lianliankan.sound.MusicManager;
import com.example.yucong.lianliankan.sound.SoundManager;
import com.example.yucong.lianliankan.util.GameConf;
import com.example.yucong.lianliankan.util.Lanutil;
import com.example.yucong.lianliankan.util.LogUtil;
import com.example.yucong.lianliankan.util.SPutil;


public class BaseActivity extends AppCompatActivity {
    public MusicManager musicManager;
    public SoundManager soundManager;
    protected SharedPreferences sp;
    protected SharedPreferences.Editor editor;

    public SharedPreferences getSp() {
        if(sp==null){
            sp=this.getSharedPreferences("jilu",MODE_PRIVATE);
        }
        return sp;
    }

    public SharedPreferences.Editor getEditor() {
        if(sp==null)
            sp=getSp();
        if(editor==null)
            editor=sp.edit();
        return editor;
    }

    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initsp();
        initset();
        initFullScreen();
        initmusic();
        initsound();
        LogUtil.w("bbbbbb",this.getLocalClassName()+"：onCreate（）运行了");
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Lanutil.setLocal(newBase));
    }

    private void initset(){
        GameConf.ismusicPlay= SPutil.getSPutil(this).getmusic();
        GameConf.issoundplay=SPutil.getSPutil(this).getsound();
    }
    /**
     * 全屏初始化
     */
    private void initFullScreen() {
        // set full screen
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    protected void initmusic(){
        if(musicManager==null){
            musicManager=new MusicManager(getApplicationContext());
            LogUtil.w("bbbbbb"+this.getLocalClassName(),"音乐id："+musicManager.getMusres());
            LogUtil.w("bbbbbb","音乐播放者："+this.getLocalClassName());
        }
        playmusic();
    }
    protected void playmusic() {
        if(musicManager!=null){
            musicManager.startbgmusic();
            LogUtil.w("bbbbbb",this.getLocalClassName()+"开始播放音乐");
        }
    }
    protected void stopmusic(){
        if(musicManager!=null){
            musicManager.stopbgmusic();
            LogUtil.w("bbbbbb",this.getLocalClassName()+"停止播放音乐");
        }
    }
    protected void initsound(){
        if(soundManager==null){
            soundManager=new SoundManager(this);
        }
    }
    protected void initsp(){
        sp=getSp();
        editor=getEditor();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.w("bbbbbb",this.getLocalClassName()+"：onResume（）运行了;");
        playmusic();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.w("bbbbbb",this.getLocalClassName()+"：onStop（）运行了");

    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.w("bbbbbb",this.getLocalClassName()+"：onpause（）运行了");
        stopmusic();


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.w("bbbbbb",this.getLocalClassName()+"：onDestroy（）运行了");

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.w("bbbbbb",this.getLocalClassName()+"：onRestart（）运行了");
    }
    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.w("bbbbbb",this.getLocalClassName()+"：onStart（）运行了");
    }

    @Override
    public void finish() {
        super.finish();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        LogUtil.w("bbbbbb",this.getLocalClassName()+"：onBackPressed（）运行了");

    }
}
