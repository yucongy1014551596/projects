package com.example.yucong.tetris.chrislee.tetris;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.Window;

import com.example.yucong.tetris.R;

public class CoverActivity extends BaseActivity {
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(CoverActivity.this,ActivityMain.class));
                finish();
            }
        },3000);
    }
}
