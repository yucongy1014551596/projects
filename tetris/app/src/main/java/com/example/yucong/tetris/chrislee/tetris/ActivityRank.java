package com.example.yucong.tetris.chrislee.tetris;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.yucong.tetris.R;
import com.example.yucong.tetris.chrislee.tetris.entity.rank;
import com.example.yucong.tetris.chrislee.tetris.util.GameConf;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * RankAdapter  适配器   适配数据资源
 */
public class ActivityRank extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rank);
//        List<rank> list=getList();
        List<rank> list=getListByProvider();
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        RankAdapter rankAdapter=new RankAdapter(list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(rankAdapter);

    }



    private List<rank> getListByProvider(){
        List<rank> lists=new ArrayList<>();
        Uri uriQuery = Uri.parse("content://com.example.yucong.provider.rank/path");
        Cursor cursor=  getContentResolver().query(uriQuery,null,null,null,null);
        while (cursor.moveToNext()){
            rank rank=new rank();
            String name=  cursor.getString(cursor.getColumnIndex("name"));
            int scores=   cursor.getInt(cursor.getColumnIndex("score"));
            rank.setName(name);
            rank.setScore(scores);
            lists.add(rank);

           }

        if(lists!=null){
            for (int i=0;i<lists.size();i++){
                rank r= lists.get(i);
                r.setImageid(GameConf.TopOrderImages[i]);
            }
        }

        return lists;
    }




//
//    private List<rank> getList(){
//
//
//        List<rank> lists=new ArrayList<>();
//
//       Cursor datas= DataSupport.findBySQL("select  name, score from rank  group by name order by score desc ,time asc ");
//
//     while(datas.moveToNext()){
//         rank rank=new rank();
//       String name=   datas.getString(datas.getColumnIndex("name"));
//       int scores=  datas.getInt(datas.getColumnIndex("score"));
//         rank.setName(name);
//         rank.setScore(scores);
//         lists.add(rank);
//     }
//
//
//     if(lists!=null){
//         for (int i=0;i<lists.size();i++){
//             rank r= lists.get(i);
//             r.setImageid(GameConf.TopOrderImages[i]);
//         }
//     }
//
//        return lists;
//    }



    public void onBackPressed() {
        super.onBackPressed();


    }


}
