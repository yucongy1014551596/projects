package com.example.yucong.gobang;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Rank extends AppCompatActivity {
    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        initUser();//得到数据
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        UserAdapter adapter = new UserAdapter(userList);
        recyclerView.setAdapter(adapter);
    }

    private void initUser(){
        Uri uri = Uri.parse("content://com.example.mrpeng.gobang.provider/User");
        Cursor cursor = getContentResolver().query(uri,new String[]{"id","account","win","lose","grade"},null,null,"grade desc");
        if(cursor.moveToFirst()){
            int rank = 0;
            do{
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String account = cursor.getString(cursor.getColumnIndex("account"));
                int win = cursor.getInt(cursor.getColumnIndex("win"));
                int lose = cursor.getInt(cursor.getColumnIndex("lose"));
                int grade = cursor.getInt(cursor.getColumnIndex("grade"));
                rank++;
                userList.add(new User(rank,id,account,win,lose,grade));
            }while(cursor.moveToNext());
        }
        cursor.close();
    }
}
