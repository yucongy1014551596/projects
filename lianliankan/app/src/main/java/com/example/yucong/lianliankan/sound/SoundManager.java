package com.example.yucong.lianliankan.sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.yucong.lianliankan.R;
import com.example.yucong.lianliankan.util.GameConf;


public class SoundManager {
    private SoundPool soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 8);
    private int[] soundIds = new int[10];

    public SoundManager(Context context) {
        soundIds[0] = soundPool.load(context, R.raw.readygo, 1);
        soundIds[1] = soundPool.load(context, R.raw.select, 1);
        soundIds[2] = soundPool.load(context, R.raw.erase, 1);
        soundIds[3] = soundPool.load(context, R.raw.refresh, 1);
        // soundIds[4] = soundPool.load(context, R.raw.translate, 1);
        soundIds[5] = soundPool.load(context, R.raw.combo, 1);
        soundIds[6] = soundPool.load(context, R.raw.win, 1);
        soundIds[7] = soundPool.load(context, R.raw.fail, 1);
        soundIds[8] = soundPool.load(context, R.raw.prompt, 1);
        soundIds[9] = soundPool.load(context, R.raw.page, 1);
    }
    private void playSound(int source) {
        if (isplay()) {
            soundPool.play(source, 1, 1, 0, 0, 1);
        }
    }
    public void readygo(){ playSound(soundIds[0]); }
    public void select(){ playSound(soundIds[1]); }
    //消除
    public void erase(){ playSound(soundIds[2]); }
    public void refresh(){ playSound(soundIds[3]); }
    public void combo(){ playSound(soundIds[5]); }
    public void fail(){ playSound(soundIds[7]); }
    public void win(){ playSound(soundIds[6]); }
    //提示
    public void promot(){ playSound(soundIds[8]); }
    public boolean isplay(){
        return GameConf.issoundplay;
    }
    public void setplay(boolean isplay){
        GameConf.issoundplay=isplay;
    }
}
