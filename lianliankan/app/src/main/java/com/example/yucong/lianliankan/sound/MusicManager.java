package com.example.yucong.lianliankan.sound;

import android.content.Context;
import android.content.Intent;

import com.example.yucong.lianliankan.R;
import com.example.yucong.lianliankan.util.GameConf;


public class MusicManager {
    private Intent musicintent=new Intent("com.example.yyh.lianliankan.MUSIC");
    private int musres= R.raw.bgmusic;

    public int getMusres() { return musres; }

    public void setMusres(int musres) { this.musres = musres; }

    private Context context;
    public MusicManager(Context context){
        this.context=context;
        musicintent.setPackage(context.getPackageName());
    }
    public void startbgmusic(){
        if(isbgplay()){
            musicintent.putExtra("bgmusic",musres);
            context.startService(musicintent);
        }

    }
    public void stopbgmusic(){
        context.stopService(musicintent);
    }
    public boolean isbgplay(){
        return GameConf.ismusicPlay;
    }
    public void setbgplay(boolean isplay){
        GameConf.ismusicPlay=isplay;
          if(isplay)
              startbgmusic();
          else
              stopbgmusic();
    }
}
