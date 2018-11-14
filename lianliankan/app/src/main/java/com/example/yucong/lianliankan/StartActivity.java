package com.example.yucong.lianliankan;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


import com.example.yucong.lianliankan.entity.Piece;
import com.example.yucong.lianliankan.entity.PieceImage;
import com.example.yucong.lianliankan.util.GameConf;
import com.example.yucong.lianliankan.util.LogUtil;
import com.example.yucong.lianliankan.util.SPutil;

import org.litepal.crud.DataSupport;

public class StartActivity extends BaseActivity implements View.OnClickListener {
    private Button buttoncon;
    private Button buttonstart;
    private Button buttonrank;
    private Button buttonlan;
    private ImageButton ibmusic;
    private ImageButton ibsound;

//    private GameManager myGameManager = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
        init();
        initmusicsetting();

        Intent intent = new Intent();
        intent.setAction("com.example.yyh.aidlservice");
        intent.setPackage("com.example.yyh.aidlservice");
        Log.d("yangyunhai","bind service");
        bindService(intent,connection,BIND_AUTO_CREATE);

    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            myGameManager = GameManager.Stub.asInterface(service);
//            Log.d("yangyunhai","connect success " + myGameManager);
            try {
//                myGameManager.writeRank("this is from aidl service!");

            } catch (Exception e){
                //
                Log.d("yangyunhai","here error " + e.toString());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

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
    //初始化音乐配置
    protected void initmusicsetting(){
        if(musicManager!=null) {
            musicManager.setbgplay(GameConf.ismusicPlay);
        }
        if(soundManager!=null){
            soundManager.setplay(GameConf.issoundplay);

        }
        setmus();
        setsound();
    }

    @Override
    protected void playmusic() {
        if(musicManager!=null){
            musicManager.setMusres(R.raw.welcomebg);
            musicManager.startbgmusic();
        }
    }

    private void setmus(){
        boolean ismusicplay= GameConf.ismusicPlay;
        if(ismusicplay){
//            ibmusic.setBackground(getDrawable(R.drawable.music));
        }else{
//            ibmusic.setBackground(getDrawable(R.drawable.music_d));
        }
    }
    private void setsound(){
        boolean issoundplay=GameConf.issoundplay;
        if(issoundplay){}
//            ibsound.setBackground((getDrawable(R.drawable.sound)));
        else{}
//            ibsound.setBackground((getDrawable(R.drawable.sound_d)));

    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(StartActivity.this,MainActivity.class);
        switch(v.getId()){
            case R.id.buttoncon:
                startActivity(intent);
                break;
            case R.id.buttonstart:
                clear();
                startActivity(intent);
                break;
            case R.id.buttonrank:
                intent=new Intent(StartActivity.this,RankActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonlan:
                saveLanguage();
                intent=new Intent(StartActivity.this,StartActivity.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.ib_music:
                boolean ismusic=musicManager.isbgplay();
                musicManager.setbgplay(!ismusic);
                setmus();
                savemuc(!ismusic);
                break;
            case R.id.ib_sound:
                boolean issound=soundManager.isplay();
                soundManager.setplay(!issound);
                setsound();
                savesound(!issound);
                break;
        }
    }

    private void clear(){
        editor.clear();
        editor.apply();
        DataSupport.deleteAll(PieceImage.class);
        DataSupport.deleteAll(Piece.class);
        MainActivity.gameGlass=1;
    }
    private void saveLanguage(){
        int lan= SPutil.getSPutil(this).getLan();
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
    @Override
    protected void onStop(){
        super.onStop();


    }
    private void savemuc(boolean isplay){
        SPutil.getSPutil(this).savemusic(isplay);
    }
    private void savesound(boolean isplay){
        SPutil.getSPutil(this).savesound(isplay);
    }
}
