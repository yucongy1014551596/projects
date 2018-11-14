package com.example.yucong.web;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void web(View view){

        switch (view.getId()){
            case R.id.webView:

                Intent intent=new Intent();
                intent.setClassName("com.example.yucong.web","com.example.yucong.web.webview.webwiew");
                startActivity(intent);
                break;
            case R.id.httpurl:

                Intent intent1=new Intent();
                intent1.setClassName("com.example.yucong.web","com.example.yucong.web.HttpUrlConnection.UrlConnection");
                startActivity(intent1);
                break;

            case R.id.okhttpurl:

                Intent intent2=new Intent();
                intent2.setClassName("com.example.yucong.web","com.example.yucong.web.okhttp.OkHttp");
                startActivity(intent2);
                break;

            case R.id.xmupull:

                Intent intent3=new Intent();
                intent3.setClassName("com.example.yucong.web","com.example.yucong.web.pull.pullxml");
                startActivity(intent3);
                break;
            case R.id.json:

                Intent intent4=new Intent();
                intent4.setClassName("com.example.yucong.web","com.example.yucong.web.json.jsonData");
                startActivity(intent4);
                break;



            default:
                    Log.i("error","无对应id");
                    break;
        }




    }




}
