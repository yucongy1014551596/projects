package com.example.yucong.tetris.chrislee.tetris;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.yucong.tetris.R;

/**
 * 音乐播放类
 */
public class MusicPlayer {

    private MediaPlayer mMoveVoice = null;
    private MediaPlayer mBombVoice = null;
    private boolean mIsMute = false;


    public MusicPlayer(Context context) {
        mMoveVoice = MediaPlayer.create(context, R.raw.move);
        mBombVoice = MediaPlayer.create(context, R.raw.bomb);
    }


    /**
     * 播放俄罗斯方块移除时播放音乐
     */
    public void playMoveVoice() {
        if (mIsMute)
            return;
        mMoveVoice.start();
    }

    /**
     * 播放方块落地音乐
     */
    public void playBombVoice() {
        if (mIsMute) {
            return;
        }
        mBombVoice.start();
    }

    public void setMute(boolean b) {
        mIsMute = b;
    }

    /**
     * 释放资源
     */

    public void free() {
        mMoveVoice.release();
        mBombVoice.release();
    }


}
