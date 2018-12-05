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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yucong.tetris.R;
import com.example.yucong.tetris.chrislee.tetris.util.LogUtil;


public class GameOver extends BaseActivity  implements OnClickListener{

	public TextView textTimeView;
	public TextView textViewScore;
	public Button gameAgain;
	public Button button2;
	public int score;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_over);

		textTimeView = (TextView) findViewById(R.id.TimeView);
		textViewScore = (TextView) findViewById(R.id.gameScore);
		
		
		LinearLayout linear1 = (LinearLayout) findViewById(R.id.linear1);
		AnimationSet set =new AnimationSet(false);
		
		Animation animation1 = new ScaleAnimation(0, 1, 0, 1);
		animation1.setDuration(2000);//延时两秒
		set.addAnimation(animation1);
			
		linear1.setAnimation(set);


		gameAgain = (Button) findViewById(R.id.again);  //再来一次

		button2 = (Button) findViewById(R.id.Button2);  //上传分数


		Intent intent = getIntent();

		final long gameTime =intent.getLongExtra("gameTime",0);
		LogUtil.i("mm","gameTime"+gameTime);
		long time= gameTime * 1000;
		CharSequence sysTimeStr = DateFormat.format("mm:ss", time);
		textTimeView.setText(String.valueOf(sysTimeStr));


		score =intent.getIntExtra("gameScore",0);



		textViewScore.setText(String.valueOf(score));





		gameAgain.setOnClickListener(this);
		

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()){
			case R.id.again:
				Intent intent = new Intent(GameOver.this,ActivityMain.class);
				startActivity(intent);
				GameOver.this.finish();
				break;










		}





	}



	
	
	
	
	
	
	
}
