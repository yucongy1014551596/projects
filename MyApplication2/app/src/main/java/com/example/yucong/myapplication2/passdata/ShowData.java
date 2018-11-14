package com.example.yucong.myapplication2.passdata;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;

import android.widget.TextView;

import com.example.yucong.myapplication2.R;

public class ShowData extends Activity {
    private TextView userName;
    private TextView password;
    private TextView sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);



        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

      String name=  bundle.getString("username");
        String password1= bundle.getString("password");
        String sex1= bundle.getString("sex");




        userName= findViewById(R.id.username);
        password=findViewById(R.id.password);
        sex= findViewById(R.id.sex);

        userName.setText("用户名："+name);
        password.setText("密码："+password1);
        sex.setText("性别："+sex1);


        /**
         *   数据回传    将得到的数据返回到上一个activity中   在InputData中接受传回来的值
         */

        Intent intent1=new Intent();
        intent1.putExtra("data",name);
        setResult(1,intent1);
//        finish();
    }

}
