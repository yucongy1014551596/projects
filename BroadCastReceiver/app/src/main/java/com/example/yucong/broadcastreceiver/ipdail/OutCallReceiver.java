package com.example.yucong.broadcastreceiver.ipdail;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class OutCallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String outcll= getResultData();
     SharedPreferences sp= context.getSharedPreferences("config",Context.MODE_PRIVATE);
     String inputNumber=sp.getString("ipnumber","");
     setResultData(inputNumber+outcll);

    }
}
