package com.example.yucong.gamedemo.entity;

public class user {
    private int userid;
    private  String account;
    private  String password;

    public String getAccount() {
        return account;
    }
    public String getPassword() {
        return password;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
