package com.example.yucong.musicplayer;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class utils {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_PHONE_STATE",
            "android.permission.INTERNET",
            "android.permission.ACCESS_FINE_LOCATION"
    };


//    public static void verifyPermissions(Activity activity,String permissions) {
//        int permission3=ActivityCompat.checkSelfPermission(activity,
//                permissions);
//
//        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                if(permission3!= PackageManager.PERMISSION_GRANTED){
//
//
//                    ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
//                    return;
//                };
//            }else {
//                //API 版本在23以下 不需要获取权限
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public static void verifyPermissions(Activity activity) {
        int permission3=ActivityCompat.checkSelfPermission(activity,
                "android.permission.ACCESS_FINE_LOCATION");

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(permission3!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
                    return;
                };
            }else {
                //API 版本在23以下 不需要获取权限
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }








}
