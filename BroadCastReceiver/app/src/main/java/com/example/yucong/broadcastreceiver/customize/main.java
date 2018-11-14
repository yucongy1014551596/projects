package com.example.yucong.broadcastreceiver.customize;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import com.example.yucong.broadcastreceiver.R;

public class main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zidingyi_main);



    }

    /**
     * 自定义广播  在8.0以后需要显示定义包名
     * @param view
     */
    public void send(View view){
        Intent intent=new Intent();
        intent.setAction("www.itcast.cn");
//        intent.setPackage(getPackageName());  这种方法也可以
//        Log.i("d",getPackageName());
        intent.setComponent(new ComponentName("com.example.yucong.broadcastreceiver","com.example.yucong.broadcastreceiver.customize.MyBroadCast"));

        sendBroadcast(intent);

    }

}
