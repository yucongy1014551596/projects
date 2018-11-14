package com.example.yucong.intent;

import android.os.Parcel;
import android.os.Parcelable;

public class Person1 implements Parcelable {
    private String name;
    private int age;

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static final Creator<Person1> CREATOR = new Creator<Person1>() {
        @Override
        public Person1 createFromParcel(Parcel source) {
          Person1 person1=  new Person1();
            person1.name= source.readString();

            person1.age=source.readInt();
            return person1;
        }

        @Override
        public Person1[] newArray(int size) {
            return new Person1[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }
}
