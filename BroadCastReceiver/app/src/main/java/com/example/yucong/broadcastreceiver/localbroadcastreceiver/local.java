package com.example.yucong.broadcastreceiver.localbroadcastreceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yucong.broadcastreceiver.R;

public class local extends AppCompatActivity {
    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
   private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
        localBroadcastManager=LocalBroadcastManager.getInstance(this);//获取实例
      Button button= findViewById(R.id.but1);
      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent("com.example.Local_BroadCast");
              localBroadcastManager.sendBroadcast(intent);
          }
      });

        intentFilter=new IntentFilter();
        intentFilter.addAction("com.example.Local_BroadCast");
        localReceiver=new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }
}
