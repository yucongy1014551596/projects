package com.example.yucong.gamedemo.contentResolver;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;

import com.example.yucong.gamedemo.entity.user;
import com.example.yucong.gamedemo.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class userActivity  extends AppCompatActivity {
    List<user> users=new ArrayList<>();



    public void testUpdate(){

        Uri uri=Uri.parse("content://com.example.yucong.gamedemostore.provider.UserContentProvider/user/1");
        ContentResolver resolver=getContentResolver();
        ContentValues contentValues=new ContentValues();
        contentValues.put("account", "zhangxiao");
        resolver.update(uri,contentValues,"password=?",new String[]{"12345"});
    }


    public void insertValues() {
        Uri uri=Uri.parse("content://com.example.yucong.gamedemostore.provider.UserContentProvider/user");
        ContentResolver resolver=getContentResolver();
        ContentValues contentValues=new ContentValues();
        contentValues.put("account", "yucong");
        contentValues.put("password", "123456");
        resolver.insert(uri,contentValues);
    }


    public void testDelete(){
        Uri uri=Uri.parse("content://com.example.yucong.gamedemostore.provider.UserContentProvider/user/3");

        ContentResolver resolver=getContentResolver();
        resolver.delete(uri,null,null);



    }




    public  List<user> testQuery(){
        Uri uri=Uri.parse("content://com.example.yucong.gamedemostore.provider.UserContentProvider/user");
        ContentResolver resolver=getContentResolver();
        Cursor cursor = resolver.query(uri, null, "account=?", new String[]{"yucong"}, "userid asc");

        while(cursor.moveToNext()){
             user u= new user();
            int  userid=  cursor.getInt(cursor.getColumnIndex("userid"));
            String account = cursor.getString(cursor.getColumnIndex("account"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
             u.setAccount(account);
             u.setPassword(password);
             u.setUserid(userid);
             users.add(u);

            LogUtil.i("userid","userid="+userid);
            LogUtil.i("account","account="+account);
            LogUtil.i("password","password="+password);

        }
        return users;


    }




}
