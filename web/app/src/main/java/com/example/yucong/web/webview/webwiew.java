package com.example.yucong.web.webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.yucong.web.R;

public class webwiew extends AppCompatActivity {
   private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webwiew);
        webView=findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);//支持JavaScript语言脚本
        webView.setWebViewClient(new WebViewClient());//当需要一个网页跳到另一个网页  还在webview中显示
        webView.loadUrl("http://www.baidu.com");//加载网页
    }
}
