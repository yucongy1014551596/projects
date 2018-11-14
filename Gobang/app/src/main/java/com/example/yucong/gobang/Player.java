package com.example.yucong.gobang;

/**
 * Created by think on 2018/10/3.
 */

public class Player {
    private int id;
    private String account;
    private int status;
    private String statusView;

    public Player(int id, String account, int status){
        this.id = id;
        this.account = account;
        this.status = status;
        if(status == 0 ){
            if(BothSide.language==1){
                statusView ="观望中";
            }else{
                statusView = "waiting";
            }
        }else{
            if(BothSide.language==1){
                statusView ="对局中";
            }else{
                statusView = "chessing";
            }
        }
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusView() {
        return statusView;
    }

    public void setStatusView(String statusView) {
        this.statusView = statusView;
    }
}
