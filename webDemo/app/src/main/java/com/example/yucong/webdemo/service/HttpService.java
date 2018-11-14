package com.example.yucong.webdemo.service;

import com.example.yucong.webdemo.util.HttpUtils;

public class HttpService {

    public static void httpRequest(String address,okhttp3.Callback callback){

        HttpUtils.sendHttpRequest(address,callback);



    }
}
