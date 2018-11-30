package com.example.yucong.web.gson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yucong.web.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class gson extends AppCompatActivity {

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson);
        button=findViewById(R.id.send);
        textView=findViewById(R.id.restext);

    }

    public void send(View view){

        sendRequestWithOKHttp();
    }

    private void sendRequestWithOKHttp(){
        //开启网络线程
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder().url("http://10.0.2.2:8080/web/get_data.json").build();
                    Response response=client.newCall(request).execute();
                    String responseData = response.body().string();

                    parseGson(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
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


    /**
     * 解析gson数组形式
     * @param jsonData
     */
    private void parseGson(String jsonData){
        try {
            Gson gson=new Gson();
//            List<App> appList=gson.fromJson(jsonData,new TypeToken<App>>() {}.)

            List<App> appList = gson.fromJson(jsonData, new TypeToken<List<App>>() {}.getType());

            for (App  app:appList){


                Log.d("msg",app.getId());
                Log.d("msg",app.getName());
                Log.d("msg",app.getVersion());




            }



        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
