package com.example.yucong.lianliankan.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

//游戏关卡
public class Glass extends DataSupport implements Parcelable {
    private int id;
    private int score;
    private int gameglass;

    public int getGameglass() { return gameglass; }

    public void setGameglass(int gameglass) { this.gameglass = gameglass; }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    protected Glass(Parcel in){
        id=in.readInt();
        score=in.readInt();
        gameglass=in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(score);
        dest.writeInt(gameglass);
    }
    public static final Creator<Glass> CREATOR=new Creator<Glass>() {
        @Override
        public Glass createFromParcel(Parcel source) {
            return new Glass(source);
        }

        @Override
        public Glass[] newArray(int size) {
            return new Glass[size];
        }
    };
    public Glass(){}
}
