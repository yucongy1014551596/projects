package com.example.yucong.gamedemo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.yucong.gamedemo.minetetris.TetrisActivityAW;
import com.example.yucong.gamedemo.util.LogUtil;


public class MainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

//        insertValues();
        //  testUpdate();
//        testDelete();
    }


    public void intoMineTetrisActivity(View view) {




    }


}
