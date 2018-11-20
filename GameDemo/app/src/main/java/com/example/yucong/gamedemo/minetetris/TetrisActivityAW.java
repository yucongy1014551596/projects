package com.example.yucong.gamedemo.minetetris;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import com.example.yucong.gamedemo.R;
import com.example.yucong.gamedemo.TimeSchedule;

public class TetrisActivityAW extends Activity {
	private NextBlockView nextBlockView;
	private TetrisViewAW tetrisViewAW;
	private TextView gameStatusTip;
	public TextView score;

	private TextView timerView;
	private long baseTimer;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tetris_activity_aw);

		timerView=   findViewById(R.id.timer);


		nextBlockView = (NextBlockView) findViewById(R.id.nextBlockView1);
		tetrisViewAW = (TetrisViewAW) findViewById(R.id.tetrisViewAW1);
		tetrisViewAW.setFather(this);
		gameStatusTip = (TextView) findViewById(R.id.game_staus_tip);

		score = (TextView) findViewById(R.id.score);











	}




	public  void getTime(final  TextView textView){
		TetrisActivityAW.this.baseTimer= SystemClock.elapsedRealtime(); //刚开机的时间



		final Handler startTimehandler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				if (null != textView) {
					textView.setText((String) msg.obj);
				}
			}
		};


		new Timer("开机计时器").scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				int time = (int)((SystemClock.elapsedRealtime() - TetrisActivityAW.this.baseTimer) / 1000);//现在时间减去开机时间
				String hh = new DecimalFormat("00").format(time / 3600);
				String mm = new DecimalFormat("00").format(time % 3600 / 60);
				String ss = new DecimalFormat("00").format(time % 60);
				String timeFormat = new String(hh + ":" + mm + ":" + ss);
				Message msg = new Message();
				msg.obj = timeFormat;
				startTimehandler.sendMessage(msg);//将时间格式化好发送给handler
			}

		}, 0, 1000L);//周期1秒   没有延时启动



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
//		gameStatusTip.setText("游戏运行中");


		gameStatusTip.setText(R.string.gameRunning);
		getTime(timerView);

	}

	/**
	 * 暂停游戏
	 */
	public void pauseGame(View view) {
		tetrisViewAW.pauseGame();
//		gameStatusTip.setText("游戏已暂停");
		gameStatusTip.setText(R.string.gamePause);
	}

	/**
	 * 继续游戏
	 */
	public void continueGame(View view) {
		tetrisViewAW.continueGame();
		//gameStatusTip.setText("游戏运行中");
		gameStatusTip.setText(R.string.gameRunning);
	}

	/**
	 * 停止游戏
	 */
	public void stopGame(View view) {
		tetrisViewAW.stopGame();
		score.setText("" + 0);
//		gameStatusTip.setText("游戏已停止");
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
		if (tetrisViewAW != null) {
			tetrisViewAW.stopGame();
		}
	}
}
