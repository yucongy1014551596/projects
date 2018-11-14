package com.example.yucong.musicdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.Window;
import android.view.WindowManager;
import android.os.Handler;

/**
 * Created by chaofanchen on 2018/3/28.
 */
public class CoverActivity extends Activity{
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.cover_layout);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(CoverActivity.this,MainActivity.class));
                finish();
            }
        },2000);
    }
}
