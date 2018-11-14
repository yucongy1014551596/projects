package com.example.yucong.gobang;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Hall extends AppCompatActivity {
    private List<Player> players = new ArrayList<>();

    private PlayerAdapter adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall);

        //region 隐藏标题栏
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        //endregion

        initPlayers();//获得数据

        //region 恢复对局数据
        if(BothSide.isSaved){
            SharedPreferences pre = getSharedPreferences("SaveState",MODE_PRIVATE);
            int hid = pre.getInt("hid",0);
            int position = 0;
            for(;position<players.size();position++){
                if(hid==players.get(position).getId()){
                    break;
                }
            }
            Player player = players.get(position);
            setView(player);
        }
        //endregion

        //region 选定对手
        adapter = new PlayerAdapter(Hall.this,R.layout.player_item,players);
        listView = (ListView) findViewById(R.id.player_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                final Player player = players.get(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(Hall.this);
                dialog.setMessage(getString(R.string.hall_str1)  + player.getAccount() + getString(R.string.hall_str2));
                dialog.setCancelable(false);
                dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        setView(player);
                    }
                });

                dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                dialog.show();
            }
        });
        //endregion
    }

    //选择对手成功以后跳转进入对局
    private void setView(Player player){
        if(BothSide.isSaved == false) {
            if (player.getStatus() == 1) {
                Toast.makeText(Hall.this, player.getAccount() + getString(R.string.hall_str3), Toast.LENGTH_SHORT).show();

                return;
            }
        }

        //设置双方为对局中
        Uri uri = Uri.parse("content://com.example.mrpeng.gobang.provider/Online");
        ContentValues values = new ContentValues();
        values.put("isPlaying",1);
        getContentResolver().update(uri,values,"id=? or id=?",new String[]{BothSide.Me.getId()+"",player.getId()+""});

        //启动对局活动
        Intent intent = new Intent(Hall.this,GameDeal.class);
        intent.putExtra("id",player.getId());
        startActivity(intent);
    }

    private void initPlayers(){
        Uri uri = Uri.parse("content://com.example.mrpeng.gobang.provider/Online");
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String account = cursor.getString(cursor.getColumnIndex("account"));
                int status = cursor.getInt(cursor.getColumnIndex("isPlaying"));
                if(id!=BothSide.Me.getId()) {
                    players.add(new Player(id, account, status));
                }
            }while(cursor.moveToNext());
        }
        cursor.close();
    }

    public void onBackPressed(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(R.string.hall_str4);
        dialog.setCancelable(false);
        dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("content://com.example.mrpeng.gobang.provider/Online/" + BothSide.Me.getId());
                getContentResolver().delete(uri,null,null);
                Hall.this.finish();
            }
        });
        dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    @Override//对局结束，及时更新数据
    protected void onResume(){
        super.onResume();
        players.clear();//先清空
        initPlayers();
        adapter = new PlayerAdapter(Hall.this,R.layout.player_item,players);
        listView = (ListView) findViewById(R.id.player_list);
        listView.setAdapter(adapter);
    }
}
