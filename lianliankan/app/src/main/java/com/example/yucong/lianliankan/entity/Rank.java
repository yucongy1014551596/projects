package com.example.yucong.lianliankan.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

import java.util.List;

public class Rank extends DataSupport implements Parcelable{
    private int id;
    private int imageid;
    private String name;
    private int score;
    private String time;
    private List<Glass> glassList;

    public List<Glass> getGlassList() {
        return glassList;
    }

    public void setGlassList(List<Glass> glassList) {
        this.glassList = glassList;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    public static final  Creator<Rank> CREATOR=new Creator<Rank>() {
        @Override
        public Rank createFromParcel(Parcel source) {
            return new Rank(source);
        }

        @Override
        public Rank[] newArray(int size) {
            return new Rank[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
       dest.writeInt(id);
       dest.writeInt(imageid);
       dest.writeInt(score);
       dest.writeString(name);
       dest.writeString(time);
       dest.writeList(glassList);
    }
    protected Rank(Parcel in){
      id=in.readInt();
      imageid=in.readInt();
      score=in.readInt();
      name=in.readString();
      time=in.readString();
      glassList=in.readArrayList(Glass.class.getClassLoader());
    }
    public Rank(){}
}
