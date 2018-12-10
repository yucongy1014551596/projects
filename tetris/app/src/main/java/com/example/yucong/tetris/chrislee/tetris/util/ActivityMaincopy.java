package com.example.yucong.tetris.chrislee.tetris.util;
// Author: ChrisLee
// 2010.3

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.yucong.tetris.R;
import com.example.yucong.tetris.chrislee.tetris.BaseActivity;
import com.example.yucong.tetris.chrislee.tetris.TetrisView;
import com.example.yucong.tetris.chrislee.tetris.entity.rank;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;


public class ActivityMaincopy extends BaseActivity  {

    public static final int FLAG_NEW_GAME = 0;
    public static final int FLAG_CONTINUE_LAST_GAME = 1;

    public static final String FILENAME = "settingInfo";
    public static final String LEVEL = "level";
    public static final String VOICE = "voice";


    private int mLevel = 1;
    private Button btLan = null;
    private Button btNewgame = null;
    private Button btContinue = null;
    private Button btHelp = null;
    private Button btRank = null;
    private Button btPre = null;
    private Button btNext = null;
    private Button btExit = null;
    private Button btHome = null;
    private TextView tvLevel = null;
    private CheckBox cbVoice = null;   //声音按钮
    /** 俄罗斯方块的最大坐标 */
    private static int max_x, max_y;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        init();

    }








    public void init(){


        btNewgame = (Button) findViewById(R.id.bt_new);
        btContinue = (Button) findViewById(R.id.bt_continue);
        btHelp = (Button) findViewById(R.id.bt_help);
        btRank = (Button) findViewById(R.id.bt_rank);
        btPre = (Button) findViewById(R.id.bt_pre);
        btNext = (Button) findViewById(R.id.bt_next);
        btExit = (Button) findViewById(R.id.bt_exit);
        tvLevel = (TextView) findViewById(R.id.tv_speed);
        cbVoice = (CheckBox) findViewById(R.id.cb_voice);
        btLan=(Button)findViewById(R.id.buttonlan);
        btHome = (Button) findViewById(R.id.bt_homePage);
        btNewgame.setOnClickListener(buttonListener);
        btContinue.setOnClickListener(buttonListener);
        btHelp.setOnClickListener(buttonListener);
        btRank.setOnClickListener(buttonListener);
        btPre.setOnClickListener(buttonListener);
        btNext.setOnClickListener(buttonListener);
        btExit.setOnClickListener(buttonListener);
        btLan.setOnClickListener(buttonListener);
        btHome.setOnClickListener(buttonListener);
        restoreSettings();
         getDatas();
    }


    private Button.OnClickListener buttonListener = new Button.OnClickListener() {

        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View v) {
            if (v == btNewgame) {
                Intent intent = new Intent(ActivityMaincopy.this, com.example.yucong.tetris.chrislee.tetris.ActivityGame.class);
                intent.setFlags(FLAG_NEW_GAME);
                intent.putExtra(VOICE, cbVoice.isChecked());
                android.util.Log.i("ljs88888", "mLevel = " +mLevel+", 103");
                intent.putExtra(LEVEL, mLevel);
                startActivity(intent);
                return;
            }
            if (v == btContinue) {
                Intent intent = new Intent(ActivityMaincopy.this, com.example.yucong.tetris.chrislee.tetris.ActivityGame.class);
                intent.setFlags(FLAG_CONTINUE_LAST_GAME);
                intent.putExtra(VOICE, cbVoice.isChecked());
                startActivity(intent);

                return;
            }
            if (v == btHelp) {
                Intent intent = new Intent(ActivityMaincopy.this, com.example.yucong.tetris.chrislee.tetris.ActivityHelp.class);
                startActivity(intent);
                finish();
                return;
            }
            if (v == btRank) {
                Intent intent = new Intent(ActivityMaincopy.this, com.example.yucong.tetris.chrislee.tetris.ActivityRank.class);
                startActivity(intent);
                return;
            }
            if (v == btHome) {
                Intent intent = new Intent(ActivityMaincopy.this, com.example.yucong.tetris.chrislee.tetris.HomePage.class);
                startActivity(intent);
                return;
            }
            if (v == btPre) {
                btPre.setBackgroundColor(0xffc0c0c0);
                String s = tvLevel.getText().toString();
                int level = Integer.parseInt(s);
                --level;
                level = (level - 1 + TetrisView.MAX_LEVEL) % TetrisView.MAX_LEVEL;
                ++level;
                s = String.valueOf(level);
                tvLevel.setText(s);
                mLevel = level;
                btPre.setBackgroundColor(0x80cfcfcf);
                return;
            }
            if (v == btNext) {
                btNext.setBackgroundColor(0xffc0c0c0);
                String s = tvLevel.getText().toString();
                int level = Integer.parseInt(s);
                --level;
                level = (level + 1) % TetrisView.MAX_LEVEL;
                ++level;
                s = String.valueOf(level);
                tvLevel.setText(s);
                mLevel = level;
                btNext.setBackgroundColor(0x80cfcfcf);
                return;
            }
            if (v == btExit) {
//                ActivityMain.this.finish();
                ActivityCollector.finishAll();
            }

            if (v == btLan) {
                saveLanguage();
                Intent   intent=new Intent(ActivityMaincopy.this,ActivityMaincopy.class);
                startActivity(intent);
                finish();

            }
        }
    };

    private void saveSettings() {
        SharedPreferences settings = getSharedPreferences(FILENAME, 0);
        settings.edit()
                .putInt(LEVEL, mLevel)
                .putBoolean(VOICE, cbVoice.isChecked())
                .commit();
    }

    private void restoreSettings() {
        SharedPreferences settings = getSharedPreferences(FILENAME, 0);
        mLevel = settings.getInt(LEVEL, 1);
        boolean hasVoice = settings.getBoolean(VOICE, true);
        tvLevel.setText(String.valueOf(mLevel));
        cbVoice.setChecked(hasVoice);
    }

    public void onStop() {
        super.onStop();
        saveSettings();

    }


    private void saveLanguage(){
        int lan= SPutil.getSPutil(this).getLan();
        LogUtil.i("lan",""+lan);
        lan=(++lan)%2;
        LogUtil.v("language","lan:"+lan);
        SPutil.getSPutil(this).saveLan(lan);
    }


    public void getDatas() {

        List<rank> ranks = DataSupport.findAll(rank.class); //获取数据库

        List<String> lists=new ArrayList<String>();

         Intent intent=  getIntent(); //接受从客户端传过来的数据
         String userName=intent.getStringExtra("userName");
         long time= intent.getLongExtra("time",0);
         int score=  intent.getIntExtra("score",0);

     if(!"".equals(userName)&&time!=0&&score!=0){
         if(ranks!=null){
             LogUtil.i("userName","ranks不为空");
             for (rank  r:ranks) {
                 lists.add(r.getName());
             }
         }

         if(lists.contains(userName)){
             LogUtil.i("userName","姓名已存在");
             String sql=" select * from rank where name= ?";
              Cursor r= DataSupport.findBySQL(sql,userName);
             while (r.moveToNext()){
                 LogUtil.i("userName","姓名已存在进入便历循环");
                 rank rank=new rank();
                 int dataScore= r.getInt(r.getColumnIndex("score"));
                 int id= r.getInt(r.getColumnIndex("id"));
                 LogUtil.i("userName","姓名更新"+id);
                 LogUtil.i("userName","姓名更新"+dataScore);
                 if(dataScore<=score){
                     LogUtil.i("userName","分数高于之前  可以进行更新处理");
                     rank.setScore(score);
                     rank.setTime(time);
                     rank.updateAll("id=?",String.valueOf(id));
                 }else {
                     LogUtil.i("userName","分数低于之前  不进行处理");
                 }

             }

         }else {
             LogUtil.i("userName","姓名不存在");
             rank rk=new rank();
             rk.setTime(time);
             rk.setScore(score);
             rk.setName(userName);
             rk.save();
         }


     }else {
         LogUtil.i("userName","传入的值为空 ,不进行处理");
     }


    }
}