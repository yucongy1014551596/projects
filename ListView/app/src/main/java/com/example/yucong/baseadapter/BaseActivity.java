package com.example.yucong.baseadapter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.yucong.listview.R;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {
    ListView mListView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);


        List<ItemBean> itemBeanList = new ArrayList<>();
        for (int i = 0;i < 20; i ++){
            itemBeanList.add(new ItemBean(R.mipmap.ic_launcher, "标题" + i, "内容" + i));
        }
        mListView = (ListView) findViewById(R.id.lv_main);
        //设置ListView的数据适配器
        mListView.setAdapter(new MyAdapter(this,itemBeanList));

    }

}
