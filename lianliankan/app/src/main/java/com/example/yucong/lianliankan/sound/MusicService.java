package com.example.yucong.lianliankan.sound;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;


import com.example.yucong.lianliankan.R;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public class MusicService extends Service {
    private MediaPlayer mediaPlayer=null;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int musres=intent.getIntExtra("bgmusic", R.raw.bgmusic);
        if(mediaPlayer==null){
            mediaPlayer=MediaPlayer.create(this,musres);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }


}
