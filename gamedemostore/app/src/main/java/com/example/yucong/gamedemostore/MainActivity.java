package com.example.yucong.gamedemostore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.yucong.gamedemostore.entity.user;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//
//        user user=new user();
//        user.setAccount("yucong");
//        user.setPassword("12345");
//        user.save();
//
//
//
//
//        List<user> users=DataSupport.findAll(user.class);
//        for ( user   u : users ) {
//            Log.i(TAG, "userid="+u.getId());
//            Log.i(TAG, u.getAccount());
//            Log.i(TAG, u.getPassword());
//
//        }


    }
}
