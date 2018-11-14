package com.example.yucong.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class GlobalContext extends Application {

    private static Context context;

    @SuppressLint("MissingSuperCall")
    public  void onCreate(){
        context=getApplicationContext();
//        Litepal.initialize(context);
    }

    public static Context getContext(){
        return context;
    }
}
