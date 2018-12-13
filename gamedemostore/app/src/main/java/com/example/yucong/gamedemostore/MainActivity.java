package com.example.yucong.gamedemostore;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
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
//        Uri uri = Uri.parse("content://com.example.yucong.provider.rank/rank");
//        ContentValues contentValues=new ContentValues();
//        contentValues.put("name","张云");
//        contentValues.put("score",200);
//        contentValues.put("time",18);
//
//
//        getContentResolver().insert(uri,contentValues);


        /**
         * 查询
         *
         */



//        String name="yangyang";
//
//        Uri uriQuery = Uri.parse("content://com.example.yucong.provider.rank/rank/1");
//      Cursor cursor=  getContentResolver().query(uriQuery,null,"name=?",new String[]{name},null);
//      while (cursor.moveToNext()){
//          Log.d(TAG, "onCreate: "+cursor.getString(cursor.getColumnIndex("name")));
//          Log.d(TAG, "onCreate: "+cursor.getInt(cursor.getColumnIndex("score")));
//          Log.d(TAG, "onCreate: "+cursor.getLong(cursor.getColumnIndex("time")));
//      }




//        Uri uriQuery = Uri.parse("content://com.example.yucong.provider.rank/rank");
//        Cursor cursor=  getContentResolver().query(uriQuery,null,null,null,null);
//        while (cursor.moveToNext()){
//            Log.d(TAG, "onCreate:id= "+cursor.getInt(cursor.getColumnIndex("id")));
//            Log.d(TAG, "onCreate: "+cursor.getString(cursor.getColumnIndex("name")));
//            Log.d(TAG, "onCreate: "+cursor.getInt(cursor.getColumnIndex("score")));
//            Log.d(TAG, "onCreate: "+cursor.getLong(cursor.getColumnIndex("time")));
//        }


//        Uri uriQuery = Uri.parse("content://com.example.yucong.provider.rank/path");
//        Cursor cursor=  getContentResolver().query(uriQuery,null,null,null,null);
//        while (cursor.moveToNext()){
//            Log.d(TAG, "onCreate:name= "+cursor.getString(cursor.getColumnIndex("name")));
//            Log.d(TAG, "onCreate:score= "+cursor.getInt(cursor.getColumnIndex("score")));
//
//        }









//        Uri uri = Uri.parse("content://com.example.yucong.provider.rank/rank");
//        ContentValues contentValues=new ContentValues();
//        contentValues.put("score",200);
//        contentValues.put("time",30);
//        contentValues.put("name","武庚");
//
//        getContentResolver().update(uri,contentValues,"id=?",new String[]{String.valueOf(2)});



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
