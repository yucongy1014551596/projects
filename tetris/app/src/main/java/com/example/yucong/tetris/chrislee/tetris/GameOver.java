package com.example.yucong.tetris.chrislee.tetris;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yucong.tetris.R;
import com.example.yucong.tetris.chrislee.tetris.util.LogUtil;


public class GameOver extends BaseActivity  implements OnClickListener{

	public TextView textTimeView;
	public TextView textViewScore;
	public EditText   userName;
	public Button gameAgain;
	public Button button2;
	public Button cancel;
	public int score;
	 long gameTime=0 ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_over);
		init();

	}




	public void init(){


		textTimeView = (TextView) findViewById(R.id.TimeView);
		textViewScore = (TextView) findViewById(R.id.gameScore);
		userName = (EditText) findViewById(R.id.userName);
		gameAgain = (Button) findViewById(R.id.again);  //再来一次
		cancel = (Button) findViewById(R.id.cancel);  //不想玩了
		button2 = (Button) findViewById(R.id.Button2);  //上传分数
		LinearLayout linear1 = (LinearLayout) findViewById(R.id.linear1);
		AnimationSet set =new AnimationSet(false);//创建动画  参数表示他的子动画是否共用一个插值器

		//动画缩放 动画开始时x,y坐标的缩放为0   动画结束时  x,y坐标结束时,在x,y伸缩1
		Animation animation1 = new ScaleAnimation(0, 1, 0, 1);

		animation1.setDuration(2000);//延续两秒
		set.addAnimation(animation1);
		linear1.setAnimation(set);

		Intent intent = getIntent();

		gameTime =intent.getLongExtra("gameTime",0);
		LogUtil.i("mm","gameTime"+gameTime);
		long time= gameTime * 1000;
		CharSequence sysTimeStr = DateFormat.format("mm:ss", time);
		textTimeView.setText(String.valueOf(sysTimeStr));

		score =intent.getIntExtra("gameScore",0);
		textViewScore.setText(String.valueOf(score));

		gameAgain.setOnClickListener(this);
		cancel.setOnClickListener(this);



	}

	@Override
	public void onClick(View v) {

		switch (v.getId()){
			case R.id.again:
				Intent intent = new Intent(GameOver.this,ActivityMain.class);
				intent.putExtra("userName",userName.getText().toString().trim());
				intent.putExtra("time",gameTime);
				intent.putExtra("score",score);
				startActivity(intent);
				GameOver.this.finish();
				break;

			case R.id.cancel:
				Intent intent1 = new Intent(GameOver.this,ActivityMain.class);
				startActivity(intent1);
				GameOver.this.finish();
				break;

		}





	}



	
	
	
	
	
	
	
}
