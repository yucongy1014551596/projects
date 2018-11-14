package com.example.yucong.myapplication2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.i18n);     //一个activity中可以包含多个布局文件
          setContentView(R.layout.activity_main);

    }

    /**
     *    方法一 方法二都是显示调用  只能开启本应用的组件
     * @param view
     */
    public  void click(View view){
//        //方法一
//        Intent intent=new Intent(this,Activity02.class);
//        startActivity(intent);
        //方法二
        Intent intent1=new Intent();
        intent1.setClassName("com.example.yucong.myapplication2","com.example.yucong.myapplication2.Activity02");
        startActivity(intent1);

    }

    /**
     *  开启activity生命周期验证
     *   intent.setClassName(packageName: 当前包名 ,ClassName:  目标组件的全路径名);
     *
     *
      * @param view
     */

    public  void circle(View view){
        Intent intent=new Intent();
      intent.setClassName("com.example.yucong.myapplication2","com.example.yucong.myapplication2.activityschedule.Activity01");
//        intent.setClassName("com.example.yucong.myapplication2","sfdgdg.Activity03");
        startActivity(intent);


    }




    /**
     *  隐士 开启摄像机   功能强大
     * @param view
     */

    public  void camera(View view){
        Intent intent2=new Intent();
        intent2.setAction("android.media.action.IMAGE_CAPTURE");
        intent2.addCategory("android.intent.category.DEFAULT");
        startActivity(intent2);

    }


    /**
     * 传递数据
     * @param view
     */
    public  void passdata(View view){
        Intent intent=new Intent();
        intent.setClassName("com.example.yucong.myapplication2","com.example.yucong.myapplication2.passdata.InputData");

        startActivity(intent);


    }




}
