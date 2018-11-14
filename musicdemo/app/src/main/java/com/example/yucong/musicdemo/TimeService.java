package com.example.yucong.musicdemo;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

/**
 * Created by chaofanchen on 2018/4/4.
 */

public class TimeService extends Service {

    public static int timelong = 0; //时间
    public static int tickCount = 0; //计数器

    @Override
    public IBinder onBind(Intent intent) {
        //不允许service被绑定，返回null
        return null;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        Notification.Builder builder = new Notification.Builder(this);
        Intent notificationIntent = new Intent(this,TimeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
        //notification.setLatestEventInfo(this,"TimeServer","定时关闭音乐播放器",pendingIntent);
        builder.setContentIntent(pendingIntent).setContentTitle("TimeServer")
                .setContentText("定时关闭音乐播放器").setSmallIcon(R.mipmap.music).setTicker("ticker")
                .setContentIntent(pendingIntent).build();
        Notification notification = builder.getNotification();
        startForeground(1,notification);
    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        long setTime = 1000 + SystemClock.elapsedRealtime();
        Intent intent1 = new Intent(this,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent1,0);
        //设置一次性闹钟，第一个参数表示闹钟类型，第二个参数表示闹钟执行时间，第三个参数表示闹钟响应动作。
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,setTime,pendingIntent);
        return super.onStartCommand(intent,flags,startId);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}
