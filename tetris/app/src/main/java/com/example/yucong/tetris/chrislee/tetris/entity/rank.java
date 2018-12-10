package com.example.yucong.tetris.chrislee.tetris.entity;

import org.litepal.crud.DataSupport;

public class rank extends DataSupport{

    private int id;
    private int imageid;
    private String name;
    private int score;
    private long time;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public long getTime() {
        return time;
    }
    //   create table rank(id int primary key,name string ,score long);



}
