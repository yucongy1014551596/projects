package com.example.yucong.gamedemo;


import android.content.Context;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.os.ParcelUuid;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.yucong.gamedemo.util.Lanutil;
import com.example.yucong.gamedemo.util.LogUtil;



public class BaseActivity extends AppCompatActivity {

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
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initsp();
//        initFullScreen();
        LogUtil.w("bbbbbb",this.getLocalClassName()+"：onCreate（）运行了");
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(Lanutil.setLocal(newBase));
    }


    /**
     * 全屏初始化
     */
    public void initFullScreen() {
        // set full screen
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    protected void initsp(){
        sp=getSp();
        editor=getEditor();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.w("bbbbbb",this.getLocalClassName()+"：onResume（）运行了;");

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
