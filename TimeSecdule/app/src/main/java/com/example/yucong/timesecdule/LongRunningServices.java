package com.example.yucong.timesecdule;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class LongRunningServices extends Service {
    public LongRunningServices() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //执行具体逻辑
            }
        }).start();


        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        int minute=10*1000;
        long triggerAttime= SystemClock.elapsedRealtime()+minute;
        Intent intent1=new Intent(this,LongRunningServices.class);
        PendingIntent pendingIntent=PendingIntent.getService(this,0,intent1,0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAttime,pendingIntent);
        Log.i("Alarm机制Service","Alarm机制-------2-------");

        return super.onStartCommand(intent, flags, startId);
    }
}
