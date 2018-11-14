package com.example.yucong.lianliankan;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import com.example.yucong.lianliankan.entity.Rank;
import com.example.yucong.lianliankan.util.GameConf;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class RankActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        List<Rank> list=getList();
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        RankAdapter rankAdapter=new RankAdapter(list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(rankAdapter);

    }
    private List<Rank> getList(){
        List<Rank> list= DataSupport.order("score desc").find(Rank.class,false);
        if(list!=null){
            for(int i=0;i<list.size();i++){
               Rank r= list.get(i);
               r.setImageid(GameConf.TopOrderImages[i]);
            }
        }
        return list;
    }
    public void onBackPressed() {
        super.onBackPressed();


    }
    @Override
    protected void playmusic() {
        if(musicManager!=null){
            musicManager.setMusres(R.raw.welcomebg);
            super.playmusic();
        }
    }
}
