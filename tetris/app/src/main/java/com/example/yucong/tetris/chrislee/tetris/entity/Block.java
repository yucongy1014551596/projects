package com.example.yucong.tetris.chrislee.tetris.entity;

import com.example.yucong.tetris.chrislee.tetris.Court;

import org.litepal.crud.DataSupport;

public class Block  extends DataSupport {
   private int  tileColor ;  //颜色
    private int tileShape ;   //类型
    private int tileOffsetX ;  //x坐标
    private int tileOffsetY;
    private  String tileMatrix;
    private  String courtMatrix;
    private int gamestate;
    private int speed;
    private int score;
    private int deLine;
    private boolean IsCombo; //方块是否落地标志
    private boolean IsPaused ;//游戏默认状态
    private boolean IsVoice ;  //音乐是否播放
    private boolean IsTitle =true;  //
    private boolean IsCourt =true;  //
    private boolean Flag =true;  //

    public boolean isFlag() {
        return Flag;
    }

    public void setFlag(boolean flag) {
        Flag = flag;
    }

    public boolean isCourt() {
        return IsCourt;
    }

    public boolean isTitle() {
        return IsTitle;
    }

    public void setTitle(boolean title) {
        IsTitle = title;
    }

    public void setCourt(boolean court) {
        IsCourt = court;
    }

    public void setTileColor(int tileColor) {
        this.tileColor = tileColor;
    }

    public void setTileOffsetX(int tileOffsetX) {
        this.tileOffsetX = tileOffsetX;
    }

    public void setTileOffsetY(int tileOffsetY) {
        this.tileOffsetY = tileOffsetY;
    }

    public void setTileShape(int tileShape) {
        this.tileShape = tileShape;
    }

    public int getTileColor() {
        return tileColor;
    }

    public int getTileOffsetX() {
        return tileOffsetX;
    }

    public int getTileOffsetY() {
        return tileOffsetY;
    }

    public int getTileShape() {
        return tileShape;
    }

    public String getTileMatrix() {
        return tileMatrix;
    }

    public void setTileMatrix(String tileMatrix) {
        this.tileMatrix = tileMatrix;
    }


    public void setCourtMatrix(String courtMatrix) {
        this.courtMatrix = courtMatrix;
    }

    public String getCourtMatrix() {
        return courtMatrix;
    }


    public void setScore(int score) {
        this.score = score;
    }

    public void setCombo(boolean combo) {
        IsCombo = combo;
    }

    public void setDeLine(int deLine) {
        this.deLine = deLine;
    }

    public void setGamestate(int gamestate) {
        this.gamestate = gamestate;
    }

    public void setPaused(boolean paused) {
        IsPaused = paused;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setVoice(boolean voice) {
        IsVoice = voice;
    }

    public int getDeLine() {
        return deLine;
    }

    public int getGamestate() {
        return gamestate;
    }

    public int getScore() {
        return score;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean isCombo() {
        return IsCombo;
    }

    public boolean isPaused() {
        return IsPaused;
    }

    public boolean isVoice() {
        return IsVoice;
    }

    @Override
    public boolean isSaved() {
        return super.isSaved();
    }
}
