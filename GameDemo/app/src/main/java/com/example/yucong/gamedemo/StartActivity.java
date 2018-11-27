package com.example.yucong.gamedemo;

import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.yucong.gamedemo.minetetris.TetrisActivityAW;
import com.example.yucong.gamedemo.util.LogUtil;
import com.example.yucong.gamedemo.util.SPutil;

import java.util.Locale;

public class StartActivity extends BaseActivity  implements View.OnClickListener{
    private Button buttoncon;
    private Button buttonstart;
    private Button buttonrank;
    private Button buttonlan;
    private ImageButton ibmusic;
    private ImageButton ibsound;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        init();


    }

    protected void init(){
        buttoncon=this.findViewById(R.id.buttoncon);
        buttonstart=this.findViewById(R.id.buttonstart);
        buttonrank=this.findViewById(R.id.buttonrank);
        buttonlan=this.findViewById(R.id.buttonlan);
        ibmusic=this.findViewById(R.id.ib_music);
        ibsound=this.findViewById(R.id.ib_sound);
        buttonrank.setOnClickListener(this);
        buttonstart.setOnClickListener(this);
        buttoncon.setOnClickListener(this);
        buttonlan.setOnClickListener(this);
        ibmusic.setOnClickListener(this);
        ibsound.setOnClickListener(this);
    }


    /**
     * IsContinute 标志用户点击的是继续游戏还是开始游戏
     * @param
     */



    @Override
    public void onClick(View v) {
        Intent intent=new Intent(StartActivity.this,TetrisActivityAW.class);
        switch(v.getId()){
            case R.id.buttoncon:
               intent.putExtra("IsContinute","true");

                startActivity(intent);
                break;
            case R.id.buttonstart:
                intent.putExtra("IsContinute","false");
                startActivity(intent);
                break;
            case R.id.buttonrank:
//                intent=new Intent(StartActivity.this,RankActivity.class);
//                startActivity(intent);
                break;
            case R.id.buttonlan:
                saveLanguage();
                intent=new Intent(StartActivity.this,StartActivity.class);
                startActivity(intent);
                this.finish();
                break;

        }
    }


    private void clear(){
        editor.clear();
        editor.apply();

    }
    private void saveLanguage(){
        int lan= SPutil.getSPutil(this).getLan();
        LogUtil.i("lan",""+lan);
        lan=(++lan)%2;
        LogUtil.v("language","lan:"+lan);
        SPutil.getSPutil(this).saveLan(lan);
    }



    @Override
    protected void onResume() {
        super.onResume();

        boolean jilu=sp.getBoolean("jilu",false);
        if(jilu){
            Log.e("aa","visible");
            buttoncon.setVisibility(View.VISIBLE);
        }else{
            buttoncon.setVisibility(View.INVISIBLE);
            Log.e("aa","invisible");
        }
    }







}
