package com.example.yucong.gamedemo;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.yucong.gamedemo.util.Lanutil;
import com.example.yucong.gamedemo.util.LogUtil;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * Handler消息机制
 *
 * 新开一个定时器（Timer）, 在子线程中获取开机时间并转成字符串格式，
 * 利用handler传回UI线程显示。
 *
 *
 *
 * Message 是在线程之间传递的消息，可以在内部携带少量消息，用于在不同线程之间传递消息
 * obj字段可以用来携带一个Object对象
 *
 * Handler 用于发送和处理消息 。 一般使用Handler的sendMessage发送消息，经过一系列的辗转处理，最终会传到Handler
 * 对象的handleMessage（）方法中。
 *
 *
 *
 *
 *
 *
 */

public class TimeSchedule extends AppCompatActivity {
    private TextView timerView;
    private long baseTimer;

//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        setContentView(R.layout.activity_main);
//        TimeSchedule.this.baseTimer= SystemClock.elapsedRealtime(); //刚开机的时间
//
//        timerView = (TextView) this.findViewById(R.id.timerView);
//
//        final Handler startTimehandler = new Handler() {
//            public void handleMessage(android.os.Message msg) {
//                if (null != timerView) {
//                    timerView.setText((String) msg.obj);
//                }
//            }
//        };
//
//
//        new Timer("开机计时器").scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                int time = (int)((SystemClock.elapsedRealtime() - TimeSchedule.this.baseTimer) / 1000);//现在时间减去开机时间
//                String hh = new DecimalFormat("00").format(time / 3600);
//                String mm = new DecimalFormat("00").format(time % 3600 / 60);
//                String ss = new DecimalFormat("00").format(time % 60);
//                String timeFormat = new String(hh + ":" + mm + ":" + ss);
//                Message msg = new Message();
//                msg.obj = timeFormat;
//                startTimehandler.sendMessage(msg);//将时间格式化好发送给handler
//            }
//
//        }, 0, 1000L);//周期1秒   没有延时启动
//
//        super.onCreate(savedInstanceState);
//
//    }


    public  void getTime(Context context , TextView textView){
        SystemClock.elapsedRealtime(); //刚开机的时间

        final Handler startTimehandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (null != timerView) {
                    timerView.setText((String) msg.obj);
                }
            }
        };



        new Timer("开机计时器").scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int time = (int)((SystemClock.elapsedRealtime() -  SystemClock.elapsedRealtime()) / 1000);//现在时间减去开机时间
                String hh = new DecimalFormat("00").format(time / 3600);
                String mm = new DecimalFormat("00").format(time % 3600 / 60);
                String ss = new DecimalFormat("00").format(time % 60);
                String timeFormat = new String(hh + ":" + mm + ":" + ss);
                Message msg = new Message();
                msg.obj = timeFormat;
                startTimehandler.sendMessage(msg);//将时间格式化好发送给handler
            }

        }, 0, 1000L);//周期1秒   没有延时启动


    }


}
