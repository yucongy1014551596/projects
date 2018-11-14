package com.example.yucong.httpclient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yucong.internet.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

public class Client extends AppCompatActivity {

    private static final int CHANGE_UI=1;
    private static final int ERROR=2;
    private EditText editText;
    private ImageView imageView;
    //主线程创建消息处理器
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==CHANGE_UI){

                Bitmap bitmap=(Bitmap)msg.obj;
                imageView.setImageBitmap(bitmap);
                Toast.makeText(Client.this,"显示图片",Toast.LENGTH_SHORT).show();

            }else  if(msg.what==ERROR){
                Toast.makeText(Client.this,"显示图片错误",Toast.LENGTH_SHORT).show();

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        imageView= findViewById(R.id.iv);
        editText= findViewById(R.id.et_path);
    }


    public void click(View view){

        final String path=editText.getText().toString().trim();

        if(TextUtils.isEmpty(path)){
            Toast.makeText(Client.this,"路径不能为空",Toast.LENGTH_SHORT).show();
        } else {
            new Thread(){
                @Override
                public void run() {

                    getImageByClient(path);
                }
            }.start();
        }

    }


    /**
     *     使用HttpClient 访问网络
     */


    protected void getImageByClient(String path){

        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(path);

        try {
            HttpResponse httpResponse=client.execute(httpGet);
            if(httpResponse.getStatusLine().getStatusCode()==200){

              HttpEntity httpEntity= httpResponse.getEntity();
                InputStream inputStream=httpEntity.getContent();
             Bitmap bitmap=   BitmapFactory.decodeStream(inputStream);
             Message message=new Message();
             message.what=CHANGE_UI;
             message.obj=bitmap;
             handler.sendMessage(message);
            }else {
                Log.i("msg","nihao");
                Message message=new Message();
                message.what=ERROR;
                handler.sendMessage(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
            Message message=new Message();
            message.what=ERROR;
            handler.sendMessage(message);

        }
    }




}
