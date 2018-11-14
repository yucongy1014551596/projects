package com.example.yucong.broadcastreceiver.customize;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

       Log.i("MyBroadCast","ds");

        Log.i("MyBroadCast",intent.getAction());
    }
}
