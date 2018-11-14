package com.example.yucong.web.json;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yucong.web.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class jsonData extends AppCompatActivity {
    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_connection);
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

                    parseJson(responseData);
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



    private void parseJson(String jsonData){
        try {
            JSONArray jsonArray=new JSONArray(jsonData);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
            String id=    jsonObject.getString("id");
                String name= jsonObject.getString("name");
                String version= jsonObject.getString("version");
                Log.d("msg",id);
                Log.d("msg",name);
                Log.d("msg",version);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
