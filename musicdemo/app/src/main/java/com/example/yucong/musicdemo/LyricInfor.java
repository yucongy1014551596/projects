package com.example.yucong.musicdemo;

/**
 * Created by chaofanchen on 2018/4/2.
 */

public class LyricInfor {
    private String lrcStr;  //歌词内容
    private int  lrcTime;    //歌词当前时间
    public String getLrcStr() {
        return lrcStr;
    }
    public void setLrcStr(String lrcStr) {
        this.lrcStr = lrcStr;
    }
    public long  getLrcTime() {
        return lrcTime;
    }
    public void setLrcTime(int  lrcTime) {
        this.lrcTime = lrcTime;
    }
}
