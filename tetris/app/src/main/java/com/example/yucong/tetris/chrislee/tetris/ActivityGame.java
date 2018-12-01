package com.example.yucong.tetris.chrislee.tetris;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.yucong.tetris.R;
import com.example.yucong.tetris.chrislee.tetris.util.LogUtil;
import com.example.yucong.tetris.chrislee.tetris.util.Timeutils;

public class ActivityGame extends BaseActivity {

    private static final String TAG = "ActivityGame";
   public TetrisView mTetrisView;
    public Timeutils timeutils;

    public void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_tetris_activity_aw);
        init();
    }

    private void init() {
        mTetrisView = findViewById(R.id.tetrisViewAW1);
        Intent intent = getIntent();
        int level = intent.getIntExtra(ActivityMain.LEVEL, 1);
        mTetrisView.setLevel(level);
        TextView textView= (TextView)findViewById(R.id.time);
        timeutils =new Timeutils(textView);
        int flag = intent.getFlags();
        if (flag == ActivityMain.FLAG_CONTINUE_LAST_GAME) {
            LogUtil.i("ff","继续执行了 ");
            mTetrisView.restoreGame();
            timeutils.count=sp.getLong("time",0);
            timeutils.resumeTime();
            boolean isVoice = intent.getBooleanExtra(ActivityMain.VOICE, true);
            mTetrisView.setVoice(isVoice);
            mTetrisView.setFather(this);
        }else{
            // voice setting influence last game
            boolean isVoice = intent.getBooleanExtra(ActivityMain.VOICE, true);
            mTetrisView.setVoice(isVoice);
            mTetrisView.setFather(this);
            timeutils.startTimer();
        }






    }







    public void onPause() {
        mTetrisView.onPause();
        super.onPause();

    }

    public void onResume() {
        super.onResume();
        mTetrisView.onResume();


    }

    public void onStop() {
        super.onStop();


    }






    /**
     * 向左滑动
     */
    public void toLeft(View view) {
        LogUtil.i("ff","toLeft ");
        mTetrisView.left();
    }

    /**
     * 向右滑动
     */
    public void toRight(View view) {
        mTetrisView.right();


    }

    /**
     * 向右滑动
     */
    public void toRoute(View view) {
        mTetrisView.route();



    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
//		if (tetrisViewAW != null) {
//			tetrisViewAW.stopGame();
//		}
    }

    private long oldtime;
    @Override
    public void onBackPressed() {

        long backtime=System.currentTimeMillis();
        long time=backtime-oldtime;
        if(time<=1000){

            onclose();

        }else{
            oldtime=backtime;

        }
        Toast.makeText(this,getString(R.string.back_tishi), Toast.LENGTH_SHORT).show();

    }


    public  void onclose(){
        mTetrisView.saveGame();
        mTetrisView.freeResources();
        timeutils.stopTimer1();
        editor.putLong("time", timeutils.count);
          clear();
//        savebydb();
//        savebysp();
        super.onBackPressed();
    }

    private void clear(){
        editor.clear();
        editor.apply();

    }



}
