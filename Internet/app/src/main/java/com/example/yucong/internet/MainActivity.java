package com.example.yucong.internet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view){
        Intent intent=new Intent();
        intent.setClassName("com.example.yucong.internet","com.example.yucong.httpclient.Client");
        startActivity(intent);
    }

    public void conn(View view){
        Intent intent=new Intent();
        intent.setClassName("com.example.yucong.internet","com.example.yucong.conn.conn");
        startActivity(intent);
    }
}
