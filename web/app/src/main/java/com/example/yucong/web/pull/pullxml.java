package com.example.yucong.web.pull;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yucong.web.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class pullxml extends AppCompatActivity {
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
                    Request request=new Request.Builder().url("http://10.0.2.2:8080/web/index.xml").build();
                    Response response=client.newCall(request).execute();
                    String responseData = response.body().string();
//                    Log.i("ttt",responseData);
//                    showRes(responseData);
                    parseXMLwithPull(responseData);
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



    private void parseXMLwithPull(String xmlData){
        try {
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
         XmlPullParser xmlPullParser= factory.newPullParser();
         xmlPullParser.setInput(new StringReader(xmlData));//将服务器返回的数据设置进去
         int eventType=   xmlPullParser.getEventType();
            String id="";
            String name="";
            String version="";

            while (eventType!=xmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {

                    case 2: {

                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    }


                    case 3: {
                        if ("app".equals(nodeName)) {
                            Log.d("msg", "id is" + id);
                            Log.d("msg", "name is" + name);
                            Log.d("msg", "version is" + version);
                        }
                        break;
                    }
                    default:
                        break;

                }
                eventType = xmlPullParser.next();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
