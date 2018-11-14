package com.example.yucong.musicdemo;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by chaofanchen on 2018/4/4.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (TimeService.tickCount++ < TimeService.timelong){
            Intent start = new Intent(context,TimeService.class);
            context.startService(start);
        }else {
            Intent stop = new Intent(context,TimeService.class);
            context.stopService(stop);
            System.exit(0);
        }
        if (TimeService.tickCount == TimeService.timelong-1){
            Toast.makeText(context,"五秒后关闭播放器",Toast.LENGTH_LONG).show();
        }
    }
}
