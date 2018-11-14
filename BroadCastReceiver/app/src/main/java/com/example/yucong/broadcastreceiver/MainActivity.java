package com.example.yucong.broadcastreceiver;

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

    public void ipClick(View view){
        Intent intent=new Intent();
        intent.setClassName("com.example.yucong.broadcastreceiver","com.example.yucong.broadcastreceiver.ipdail.MainIp");
        startActivity(intent);
    }


    public void customize(View view){
        Intent intent=new Intent();
        intent.setClassName("com.example.yucong.broadcastreceiver","com.example.yucong.broadcastreceiver.customize.main");
        startActivity(intent);
    }


    public void local(View view){
        Intent intent=new Intent();
        intent.setClassName("com.example.yucong.broadcastreceiver","com.example.yucong.broadcastreceiver.localbroadcastreceiver.local");
        startActivity(intent);
    }
}
