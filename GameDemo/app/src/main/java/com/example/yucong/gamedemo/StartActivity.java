package com.example.yucong.gamedemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.yucong.gamedemo.minetetris.GameConfig;
import com.example.yucong.gamedemo.minetetris.TetrisActivityAW;
import com.example.yucong.gamedemo.util.LogUtil;
import com.example.yucong.gamedemo.util.SPutil;

import java.util.Locale;

public class StartActivity extends AppCompatActivity  implements View.OnClickListener{
    private Button buttoncon;
    private Button buttonstart;
    private Button buttonrank;
    private Button buttonlan;
    private ImageButton ibmusic;
    private ImageButton ibsound;


    SharedPreferences pre;
    SharedPreferences.Editor editor;






    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //region 设置为用户所选语言，需在加载界面之前配置好
        pre = getSharedPreferences("language_config",MODE_PRIVATE);
        String str = pre.getString("language","zh");
        if(str.equals("zh")) GameConfig.language=1;
        else GameConfig.language =0;
        setConf(str);
        //endregion
        super.onCreate(savedInstanceState);
        initFullScreen();

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

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(StartActivity.this,TetrisActivityAW.class);
        switch(v.getId()){
            case R.id.buttoncon:
                startActivity(intent);
                break;
            case R.id.buttonstart:

                startActivity(intent);
                break;
            case R.id.buttonrank:
//                intent=new Intent(StartActivity.this,RankActivity.class);
//                startActivity(intent);
                break;
            case R.id.buttonlan:
                swtichLanguage();
                intent=new Intent(StartActivity.this,StartActivity.class);
                startActivity(intent);
                this.finish();
                break;

        }
    }


    private void clear(){
        editor.clear();
        editor.apply();
//        DataSupport.deleteAll(PieceImage.class);
//        DataSupport.deleteAll(Piece.class);
//        MainActivity.gameGlass=1;
    }
    private void saveLanguage(){
        int lan= SPutil.getSPutil(this).getLan();
        lan=(++lan)%2;
        LogUtil.v("language","lan:"+lan);
        SPutil.getSPutil(this).saveLan(lan);
    }




    //切换语言
    private void swtichLanguage(){
        editor = pre.edit();
        String  str = Locale.getDefault().getLanguage();
        if(str.equals("zh")) str = "en";
        else str = "zh";
        editor.putString("language",str);//保留语言配置
        editor.apply();
        setConf(str);
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        this.finish();
    }

    //保存语言设置，并更新
    private void setConf(String str) {
        Locale locale = new Locale(str);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, null);
    }






    public  void initFullScreen(){
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }
}
