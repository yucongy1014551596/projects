package com.example.yucong.listview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.yucong.baseadapter.ItemBean;
import com.example.yucong.baseadapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    public void base(View view){
        Intent intent=new Intent();
        intent.setClassName("com.example.yucong.listview","com.example.yucong.baseadapter.BaseActivity");
        startActivity(intent);
    }


}
