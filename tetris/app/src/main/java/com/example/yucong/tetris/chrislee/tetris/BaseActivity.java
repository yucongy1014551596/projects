package com.example.yucong.tetris.chrislee.tetris;


import android.content.Context;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.yucong.tetris.chrislee.tetris.util.Lanutil;
import com.example.yucong.tetris.chrislee.tetris.util.LogUtil;


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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initsp();
        initFullScreen();
        LogUtil.w("bbbbbb",this.getLocalClassName()+"：onCreate（）运行了");
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        LogUtil.i("attachBaseContext","我执行了");
        super.attachBaseContext(Lanutil.setLocal(newBase));
    }




    protected void initsp(){
        sp=getSp();
        editor=getEditor();
    }

    @Override
    public void finish() {
        super.finish();

    }



    /**
     * 全屏初始化
     */
    public void initFullScreen() {
        // set full screen
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }































}
