package com.example.yucong.tetris.chrislee.tetris;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import com.example.yucong.tetris.R;
import com.example.yucong.tetris.chrislee.tetris.util.LogUtil;

public class ActivityGame extends Activity {

    private static final String TAG = "ActivityGame";
   public TetrisView mTetrisView = null;

    public void onCreate(Bundle saved) {
        super.onCreate(saved);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        init();
    }

    private void init() {
        mTetrisView = new TetrisView(this);
        //setContentView(mTetrisView);

        Intent intent = getIntent();
        int level = intent.getIntExtra(ActivityMain.LEVEL, 1);
        mTetrisView.setLevel(level);
        int flag = intent.getFlags();


        if (flag == ActivityMain.FLAG_CONTINUE_LAST_GAME) {
            LogUtil.i("ff","继续执行了 ");
            mTetrisView.restoreGame();
        }

        // voice setting influence last game
        boolean isVoice = intent.getBooleanExtra(ActivityMain.VOICE, true);
        mTetrisView.setVoice(isVoice);
        mTetrisView.setFather(this);

        setContentView(R.layout.activity_tetris_activity_aw);


    }







    public void onPause() {
//		ranking();
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
//        savebydb();
//        savebysp();
        super.onBackPressed();
    }





}
