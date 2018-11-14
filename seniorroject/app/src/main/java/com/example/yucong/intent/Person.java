package com.example.yucong.intent;

import java.io.Serializable;

/**
 * 有两种序列化方法Serializable
 */

public class Person  implements Serializable{
    private String name;
    private int age;

    public Person() {
        super();
    }

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
}

