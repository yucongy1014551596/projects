package com.example.yucong.gamedemo.entity;



public class score {

    private int id;
    private  int userid;
    private  String score;
    private String highScore;

    public String getHighScore() {
        return highScore;
    }

    public void setHighScore(String highScore) {
        this.highScore = highScore;
    }



    public String getScore() {
        return score;
    }


    public int getId() {
        return id;
    }

    public int getUserid() {
        return userid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
