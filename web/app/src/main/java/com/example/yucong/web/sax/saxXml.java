package com.example.yucong.web.sax;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yucong.web.R;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class saxXml extends AppCompatActivity {

    private TextView textView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sax_xml);
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
                    parseXMLwithSax(responseData);
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



    private void parseXMLwithSax(String xmlData){
        try {
            SAXParserFactory factory= SAXParserFactory.newInstance();
            XMLReader xmlreader= factory.newSAXParser().getXMLReader();

            MyHandler handler= new MyHandler() ;
            xmlreader.setContentHandler(handler);
            xmlreader.parse(new InputSource(new StringReader(xmlData)));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
