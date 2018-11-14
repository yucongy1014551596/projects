package com.example.yucong.gobang;

/**
 * Created by mrpeng on 18-9-28.
 */

public class User {
    private int rank;
    private int id;
    private String account;
    private int win;
    private int lose;
    private int grade;
    
    public User(){
        
    }

    public User(int rank,int id,String account,int win,int lose,int grade){
        this.id = id;
        this.account = account;
        this.win = win;
        this.lose = lose;
        this.grade = grade;
        this.rank = rank;
    }
    
    public User(int id,String account,int win,int lose,int grade){
        this.id = id;
        this.account = account;
        this.win = win;
        this.lose = lose;
        this.grade = grade;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
