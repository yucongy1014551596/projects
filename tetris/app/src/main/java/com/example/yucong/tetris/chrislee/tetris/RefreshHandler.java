package com.example.yucong.tetris.chrislee.tetris;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


/**
 *
 * Handler 消息处理机制  主要用于主界面数据刷新
 */


class RefreshHandler extends Handler {
    final static int MESSAGE_REFRESH = 0xeeeeeeee;

    final static int DELAY_MILLIS = 100;//延迟时间为0.1毫秒
    TetrisView mV = null;

    boolean mIsPaused = false;

    public RefreshHandler(TetrisView v) {
        super();
        mV = v;
    }

    public void handleMessage(Message ms) {
        if (!mIsPaused) {
            if (ms.what == MESSAGE_REFRESH) {
                mV.logic();
                mV.invalidate();
            }
        }
    }


    public void pause() {
        mIsPaused = true;
    }


    public void resume() {
        mIsPaused = false;
    }

}
