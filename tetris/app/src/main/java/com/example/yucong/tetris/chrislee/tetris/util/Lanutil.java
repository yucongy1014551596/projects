package com.example.yucong.tetris.chrislee.tetris.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;

import java.util.Locale;


public class Lanutil {


    public static Locale getSetLanguageLocale(Context context){
        int lan= SPutil.getSPutil(context).getLan();
        LogUtil.w("bbbbbb","Lanutil：运行了"+lan);

        switch (lan){
            case -1: return Locale.getDefault();
            case 0 :  return Locale.CHINA;
            case 1 :  return Locale.ENGLISH;
            default: return Locale.getDefault();
        }
    }
    public static Context setLocal(Context context) {
        return updateResources(context, getSetLanguageLocale(context));
    }

    private static Context updateResources(Context context, Locale locale) {
        Locale.setDefault(locale);
        //android每个应用都保存着一份仅对自身有用的配置，被封装在 android.content.res.Configuration 类里，通过该类，我们可以修改语言参数，实现对应用语言的变更。
        //首先获取 Configuration 对象：
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());

        if (Build.VERSION.SDK_INT >= 17) {
            LogUtil.w("bbbbbb","Lanutil：运行了"+locale);
            config.setLocale(locale);
            res.updateConfiguration(config, res.getDisplayMetrics());
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }


}
