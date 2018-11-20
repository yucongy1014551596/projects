package com.example.yucong.proxy;

import android.util.Log;

public class xiaomin implements IlawSuit{
    private static final String TAG = "xiaomin";
    @Override
    public void submit() {
//        Log.i(TAG, "submit: ");
        System.out.println("submit: ");
    }

    @Override
    public void burden() {
        System.out.println("burden: ");

    }
}
