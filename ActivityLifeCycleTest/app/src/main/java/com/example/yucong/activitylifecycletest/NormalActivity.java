package com.example.yucong.activitylifecycletest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class NormalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_layout);
    }




    private long oldtime;
    @Override
    public void onBackPressed() {

        long backtime=System.currentTimeMillis();
        long time=backtime-oldtime;
        if(time<=1000){
            onclose();
        }else{
            oldtime=backtime;
            //Toast.makeText(this,"点击太慢了",Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this,getString(R.string.back_tishi),Toast.LENGTH_SHORT).show();

    }

    public  void onclose(){

        super.onBackPressed();
    }
}
