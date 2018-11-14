package com.example.yucong.web.HttpUrlConnection;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yucong.web.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlConnection extends AppCompatActivity {
   private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_connection);
        button=findViewById(R.id.send);
        textView=findViewById(R.id.restext);

    }

    public void send(View view){

        sendRequesu();
    }

    private void sendRequesu(){
        //开启网络线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection=null;
                BufferedReader bufferedReader=null;
                try {
                    URL url=new URL("https://www.baidu.com");
                    httpURLConnection=(HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setReadTimeout(15000);
                 InputStream inputStream= httpURLConnection.getInputStream();
                 bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                 StringBuilder res=new StringBuilder();
                 Log.i("code",httpURLConnection.getResponseCode()+"");

                 String line;
                 while ((line=bufferedReader.readLine())!=null){
                     res.append(line);

                    }

                    showRes(res.toString());


                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if(bufferedReader!=null){
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }if(httpURLConnection!=null){
                        httpURLConnection.disconnect();
                    }
                }


            }
        }).start();
    }


    /**
     *在activity中更新 ui 界面的问题
     * @param response
     */
    private void showRes(final String response){
     runOnUiThread(new Runnable() {
         @Override
         public void run() {
             //在这里进行ui操作
             textView.setText(response);

         }
     });
    }
}
