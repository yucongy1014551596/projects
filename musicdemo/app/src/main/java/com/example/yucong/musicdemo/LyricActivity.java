package com.example.yucong.musicdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import java.io.IOException;
import java.util.List;

/**
 * Created by chaofanchen on 2018/4/15.
 */

public class LyricActivity extends Activity {

    private LyricView mWordView;
    private List<Integer> mTimeList;
    private MediaPlayer mPlayer;
    private List<MusicInfor> mylist;

    @SuppressLint("SdCardPath")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lyric);

        mWordView = (LyricView) findViewById(R.id.lrcShowView);

        LyricHandle lrcHandler = new LyricHandle();
        try {
            String name = MainActivity.song;
            lrcHandler.readLRC(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+ name + ".lrc");
            mTimeList = lrcHandler.getTime();
            lrcHandler.getWords();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        final Handler handler = new Handler();

        new Thread(new Runnable() {
            int i = 0;

            @Override
            public void run() {
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        mWordView.invalidate();
                    }
                });
                try {
                    Thread.sleep(mTimeList.get(i + 1) - mTimeList.get(i));
                } catch (InterruptedException e) {
                }
                i++;
                if (i == mTimeList.size() - 1) {

                }
            }
        }).start();
    }
}
