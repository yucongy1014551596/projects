package com.example.yucong.webdemo.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtils {

    /**
     *
     * @param address  传入地址
     * @param callback 工具会在子线程开启 可以得到回调的请求结果
     */
    public static void sendHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);


    }
}
