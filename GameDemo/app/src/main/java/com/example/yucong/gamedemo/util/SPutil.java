package com.example.yucong.gamedemo.util;

import android.content.Context;
import android.content.SharedPreferences;


public class SPutil {
    private final SharedPreferences sp;
    private final SharedPreferences.Editor editor;
    private final static String TAG="setting";
    private final static String TAG_lan="language";
    private final static String TAG_music="music";
    private final static String TAG_sound="sound";

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


        return sp.getInt(TAG_lan,0);
    }
//
//    public boolean getmusic(){
//        return  sp.getBoolean(TAG_music,true);
//    }
//    public void savemusic(boolean isplay){
//        editor.putBoolean(TAG_music,isplay);
//        editor.commit();
//    }
//    public boolean getsound(){
//        return  sp.getBoolean(TAG_sound,true);
//    }
//    public void savesound(boolean isplay){
//        editor.putBoolean(TAG_sound,isplay);
//        editor.commit();
//    }

}
