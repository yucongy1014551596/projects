package com.example.yucong.tetris.chrislee.tetris;
// Author: ChrisLee
// 2010.3

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.yucong.tetris.R;
import com.example.yucong.tetris.chrislee.tetris.util.LogUtil;
import com.example.yucong.tetris.chrislee.tetris.util.SPutil;


public class ActivityMain extends BaseActivity  {

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

    private TextView tvLevel = null;
    private CheckBox cbVoice = null;   //声音按钮

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



        btNewgame.setOnClickListener(buttonListener);
        btContinue.setOnClickListener(buttonListener);
        btHelp.setOnClickListener(buttonListener);
        btRank.setOnClickListener(buttonListener);
        btPre.setOnClickListener(buttonListener);
        btNext.setOnClickListener(buttonListener);
        btExit.setOnClickListener(buttonListener);
        btLan.setOnClickListener(buttonListener);
        restoreSettings();
    }












    private Button.OnClickListener buttonListener = new Button.OnClickListener() {

        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View v) {
            if (v == btNewgame) {
                Intent intent = new Intent(ActivityMain.this, com.example.yucong.tetris.chrislee.tetris.ActivityGame.class);
                intent.setFlags(FLAG_NEW_GAME);
                intent.putExtra(VOICE, cbVoice.isChecked());
                android.util.Log.i("ljs88888", "mLevel = " +mLevel+", 103");
                intent.putExtra(LEVEL, mLevel);
                startActivity(intent);
                return;
            }
            if (v == btContinue) {
                Intent intent = new Intent(ActivityMain.this, com.example.yucong.tetris.chrislee.tetris.ActivityGame.class);
                intent.setFlags(FLAG_CONTINUE_LAST_GAME);
                intent.putExtra(VOICE, cbVoice.isChecked());
                startActivity(intent);
                return;
            }
            if (v == btHelp) {
                Intent intent = new Intent(ActivityMain.this, com.example.yucong.tetris.chrislee.tetris.ActivityHelp.class);
                startActivity(intent);
                return;
            }
            if (v == btRank) {
                Intent intent = new Intent(ActivityMain.this, com.example.yucong.tetris.chrislee.tetris.ActivityRank.class);
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
                ActivityMain.this.finish();
            }

            if (v == btLan) {
                saveLanguage();
                Intent   intent=new Intent(ActivityMain.this,ActivityMain.class);
                startActivity(intent);

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
}