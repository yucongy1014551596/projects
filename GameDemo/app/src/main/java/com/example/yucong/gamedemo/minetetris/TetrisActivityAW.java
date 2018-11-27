package com.example.yucong.gamedemo.minetetris;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yucong.gamedemo.BaseActivity;
import com.example.yucong.gamedemo.R;
import com.example.yucong.gamedemo.TimeSchedule;
import com.example.yucong.gamedemo.util.LogUtil;
import com.example.yucong.gamedemo.util.Timeutils;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

public class TetrisActivityAW extends BaseActivity {


	private NextBlockView nextBlockView;
	private TetrisViewAW tetrisViewAW;
	private TextView gameStatusTip;
	public TextView score;
	public TextView highScore;
	private TextView timerView;
	private long baseTimer;
	public  Timeutils timeutils;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tetris_activity_aw);

		String IsContinute=  getIntent().getStringExtra("IsContinute");

		if (IsContinute.equals("false")) {
			initGame();
			LogUtil.i("game","初始化game界面");

		}else {
			initContinuteGame();
		}


	}




    public void initContinuteGame(){

		         initGame();
//				tetrisViewAW.initGameMap();

		       getdatas();

	}




	  public  void   getdatas(){
	   List<BlockUnit>	  BlockUnits = DataSupport.findAll(BlockUnit.class);
		  tetrisViewAW.allBlockUnits=BlockUnits;
		  tetrisViewAW.xx=sp.getInt("xx",0);
		  tetrisViewAW.yy=sp.getInt("yy",0);
	}



	public void initGame(){
		LitePal.getDatabase();
		timerView=   findViewById(R.id.timer);
		nextBlockView = (NextBlockView) findViewById(R.id.nextBlockView1);
		tetrisViewAW = (TetrisViewAW) findViewById(R.id.tetrisViewAW1);
		tetrisViewAW.setFather(this);
		gameStatusTip = (TextView) findViewById(R.id.game_staus_tip);
		score = (TextView) findViewById(R.id.score);
		highScore = (TextView) findViewById(R.id.highscore);
		timeutils=new Timeutils(timerView);

	}

	private void savebysp(){
		editor.putBoolean("jilu",true);
		editor.putInt("xx",tetrisViewAW.xx);
		editor.putInt("yy",tetrisViewAW.yy);
		editor.apply();
	}


	private void savebydb(){

		DataSupport.deleteAll(BlockUnit.class);
		tetrisViewAW.initGameMap();

	}


	public void setNextBlockView(List<BlockUnit> blockUnits, int div_x) {
		nextBlockView.setBlockUnits(blockUnits, div_x);
	}

	/**
	 * 开始游戏
	 * 
	 * @param view
	 */
	public void startGame(View view) {
		tetrisViewAW.startGame();

		gameStatusTip.setText(R.string.gameRunning);

		timeutils.startTimer();  //开启计时
	}

	/**
	 * 暂停游戏
	 */
	public void pauseGame(View view) {
		tetrisViewAW.pauseGame();
		gameStatusTip.setText(R.string.gamePause);
		timeutils.puseTimer();
	}

	/**
	 * 继续游戏
	 */
	public void continueGame(View view) {
		tetrisViewAW.continueGame();
		gameStatusTip.setText(R.string.gameRunning);
		timeutils.resumeTime();
	}

	/**
	 * 停止游戏
	 */
	public void stopGame(View view) {
		tetrisViewAW.stopGame();

		score.setText("" + 0);

		gameStatusTip.setText(R.string.gameOver);
	}

	/**
	 * 向左滑动
	 */
	public void toLeft(View view) {

		tetrisViewAW.toLeft();
	}

	/**
	 * 向右滑动
	 */
	public void toRight(View view) {

		tetrisViewAW.toRight();
	}

	/**
	 * 向右滑动
	 */
	public void toRoute(View view) {

		tetrisViewAW.route();
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
			tetrisViewAW.pauseGame();
			gameStatusTip.setText(R.string.gamePause);
			timeutils.puseTimer();
			LogUtil.i("tt",""+tetrisViewAW.score);
				onclose();
		}else{
			oldtime=backtime;

		}
		Toast.makeText(this,getString(R.string.back_tishi),Toast.LENGTH_SHORT).show();

	}


	public  void onclose(){
		savebydb();
		savebysp();
		super.onBackPressed();
	}


	@Override
	protected void onResume() {
		super.onResume();

	}
}
