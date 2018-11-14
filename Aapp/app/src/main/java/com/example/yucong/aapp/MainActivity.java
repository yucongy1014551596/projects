package com.example.yucong.aapp;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri uri = Uri.parse("content://cn.itcast.providers.personprovider/person");
        //注册内容观察者
        this.getContentResolver().registerContentObserver(uri, true, new PersonContentObserver(new Handler()));

    }

    // 自定义内容观察者
    private class PersonContentObserver extends ContentObserver {

        public PersonContentObserver(Handler handler) {
            super(handler);
        }


        @Override
        public void onChange(boolean selfChange) {
            // select * from person order by personid desc limit 1
            Uri uri = Uri.parse("content://cn.itcast.providers.personprovider/person");
           //通过ContentResolver查询出变化的数据
            Cursor cursor = getContentResolver().query(uri, null, null, null, "personid desc limit 1");//查找最近的一条数据
            if(cursor.moveToFirst()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                Log.i("MainActivity", name);
            }
        }

    }
}
