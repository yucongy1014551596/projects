package com.example.yucong.gamedemo.util;

import android.content.Context;
import android.content.SharedPreferences;


public class SPutil {
    private final SharedPreferences sp;
    private final SharedPreferences.Editor editor;
    private final static String TAG="setting";
    private final static String TAG_lan="language";


    private static volatile SPutil instance;

    private SPutil(Context context){
        sp=context.getSharedPreferences(TAG,Context.MODE_PRIVATE);
        editor=sp.edit();
    }

    /**
     *
     * @param context
     * @return
     */
    public static SPutil getSPutil(Context context){
        if(instance==null){
            synchronized (SPutil.class){
                if(instance==null){
                    instance=new SPutil(context);
                }
            }
        }
        return instance;
    }

    public void saveLan(int select){
        editor.putInt(TAG_lan,select);
        editor.commit();
    }
    public int getLan(){
        LogUtil.w("bbbbbb","：getLan（）运行了");
        return sp.getInt(TAG_lan,0);
    }


}
