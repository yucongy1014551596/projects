package com.example.yucong.gamedemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.yucong.gamedemo.minetetris.TetrisActivityAW;


public class MainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
    }


    public void intoMineTetrisActivity(View view) {
        Intent intent=new Intent();
        intent.setClassName("com.example.yucong.gamedemo","com.example.yucong.gamedemo.minetetris.TetrisActivityAW");
        startActivity(intent);

    }
}
