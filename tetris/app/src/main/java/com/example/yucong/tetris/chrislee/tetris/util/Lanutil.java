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
            case 0: return Locale.CHINA;
            case 1: return Locale.ENGLISH;
            default: return Locale.CHINA;
        }
    }
    public static Context setLocal(Context context) {
        return updateResources(context, getSetLanguageLocale(context));
    }

    private static Context updateResources(Context context, Locale locale) {
        Locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            LogUtil.w("bbbbbb","Lanutil：运行了"+locale);
            config.setLocale(locale);
            res.updateConfiguration(config, res.getDisplayMetrics());
//            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }






//    public static Locale getSystemLocale(Context context) {
//        Locale locale;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            locale = LocaleList.getDefault().get(0);
//        } else {
//            locale = Locale.getDefault();
//        }
//        return locale;
//    }
}
