package com.example.yucong.myapplication2.activityschedule;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.yucong.myapplication2.R;

/**
 * Activity01 和Activity02主要是为了验证Activity的生命周期
 */
public class Activity01 extends AppCompatActivity {

    private String Tag="Activity01";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_01);
        Log.i(Tag,"onCreate");
    }

    //当Activity成为用户可见时调用
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(Tag,"onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(Tag,"onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(Tag,"onResume");
    }
    //失去焦点
    @Override
    protected void onPause() {
        super.onPause();
        Log.i(Tag,"onPause");
    }
   //用户不可见时
    @Override
    protected void onStop() {
        super.onStop();
        Log.i(Tag,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(Tag,"onDestroy");
    }
    public void click1(View view){
        Intent intent=new Intent(this,Activity02.class);
       startActivity(intent);
    }
}
