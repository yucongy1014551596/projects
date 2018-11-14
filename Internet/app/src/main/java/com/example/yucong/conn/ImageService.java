package com.example.yucong.conn;

import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageService {
    public static byte[] getImage(String path) throws Exception{

        URL url = new URL(path);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();//
        conn.setConnectTimeout(50000);
        conn.setRequestMethod("GET");
        Log.i("ppp",conn.getResponseCode()+"");
        if(conn.getResponseCode() == 200){
            InputStream inStream = conn.getInputStream();
            return StreamTool.read(inStream);
        }
        return null;
    }

}
