package com.example.yucong.lianliankan.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.example.yucong.lianliankan.MainActivity;


public class DensityUtil {

    public static int px2dp(Context context,int px){
        final float density=context.getResources().getDisplayMetrics().density;
        return (int)((px/density)+0.5f);
    }
    public static int dp2px(Context context,int dp){
        final float density=context.getResources().getDisplayMetrics().density;
        return  (int)(dp*density+0.5f);
    }
    public static DisplayMetrics getDisplayMetrics(Context context){
        WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }
    public static int[] getCenter(  Context context,int piewid,int piehei){
        DisplayMetrics dm=getDisplayMetrics(context);
        int height=dm.heightPixels;
        LogUtil.v("DensityUtil","屏幕宽度"+height);
        int wdith=dm.widthPixels;
        LogUtil.v("DensityUtil","屏幕高度"+wdith);
        int realhei=(height-piehei)/2;
        int realwid=(wdith-piewid)/2;
        int[] p={realwid,realhei};
        return p;
    }
    public static int getPiecewidth( Context context){
        DisplayMetrics dm=getDisplayMetrics(context);
        int width=dm.widthPixels;
        LogUtil.v("DensityUtil","屏幕宽度"+width);
        int realwid=(int)width/( MainActivity.GAMEROW+2);
        return realwid;
    }

}
