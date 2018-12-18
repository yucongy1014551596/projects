package com.example.yucong.tetris.chrislee.tetris;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.yucong.tetris.R;
import com.example.yucong.tetris.chrislee.tetris.entity.Block;
import com.example.yucong.tetris.chrislee.tetris.entity.rank;
import com.example.yucong.tetris.chrislee.tetris.music.MusicService;
import com.example.yucong.tetris.chrislee.tetris.util.LogUtil;
import com.example.yucong.tetris.chrislee.tetris.util.Timeutils;

import org.litepal.crud.DataSupport;

public class ActivityGame extends BaseActivity {

    private static final String TAG = "ActivityGame";
   public TetrisView mTetrisView;
    public Timeutils timeutils;
    Intent intent ;
    public MusicService meidiaPlay;
    public void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.activity_tetris_activity_aw);
        init();
        Intent intent1 = new Intent(this,MusicService.class);
        bindService(intent1, conn, Context.BIND_AUTO_CREATE);

    }



    private ServiceConnection conn  =new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub

            MusicService.LocalBinder binder =  (MusicService.LocalBinder)service;
            meidiaPlay = binder.getService();

            Log.d("TAg===>","backPlay ok");

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub

        }


    };




    private void init() {

        mTetrisView = findViewById(R.id.tetrisViewAW1);
        intent = getIntent();
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
            mTetrisView.setFather(this);

        }else{

            mTetrisView.setFather(this);
            timeutils.startTimer();
        }




    }






    public void onPause() {
        mTetrisView.onPause();
        super.onPause();
        LogUtil.i("mmmm","捕获游戏异常时 保存当前游戏状态 ");

    }

    public void onResume() {
        super.onResume();
        mTetrisView.onResume();
        LogUtil.i("mmmm","恢复游戏状态 ");

    }

    public void onStop() {
        super.onStop();
    }



    //游戏异常退出时  保存游戏进度
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mTetrisView.mIsPaused=true;
//        LogUtil.i("mmmm","捕获游戏异常时 保存当前游戏状态 ");
//
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//           LogUtil.i("mmmm","恢复游戏状态 ");
//        mTetrisView.mIsPaused=false;
//
//    }


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

    /**
     * 向右滑动
     */
    public void tofastDrop(View view) {
        mTetrisView.fastDorpMaster();

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
        timeutils.stopTimer1();
        editor.putLong("time", timeutils.count);
          clear();
        super.onBackPressed();
    }





    private void clear(){
        editor.clear();
        editor.apply();

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unbindService(conn);
        LogUtil.i("mmmm","on destory");
    }


}
