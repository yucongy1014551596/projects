package com.example.yucong.webdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yucong.webdemo.service.HttpService;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 在主线程里不可以直接请求网络这种费时操作
 * 需要放到子线程里面去
 * 由于开了子线程 得不到服务器返回的结果，这时就需要okhttp自带的一个回调多线程工具工具
 */
public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.send1);
        textView=findViewById(R.id.restext);

    }

    public void send(View view){

        HttpService.httpRequest("https://www.baidu.com", new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //对异常情况进行处理
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的数据
                String responseData=  response.body().string();
                showRes(responseData);

            }
        });

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
