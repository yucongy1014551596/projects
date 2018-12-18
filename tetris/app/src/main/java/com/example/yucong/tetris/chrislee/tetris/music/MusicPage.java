package com.example.yucong.tetris.chrislee.tetris.music;



import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import android.os.Bundle;
import android.os.IBinder;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;


import com.example.yucong.tetris.R;
import com.example.yucong.tetris.chrislee.tetris.BaseActivity;
import com.example.yucong.tetris.chrislee.tetris.util.LogUtil;


public class MusicPage extends BaseActivity implements OnClickListener {

	public CheckBox bgMusic; //背景音乐复选框
	public CheckBox buttonMusic;//按钮音乐复选框
	public Button setting;
	public Button cancel;
	
	public Boolean isBgMusic;  //背景音乐默认打开
	public Boolean isButtonMusic;//按钮音乐默认打开

	public MusicService musicService;  //音乐服务类
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.music_page);
		init();

		Intent intent = new Intent(this,MusicService.class);
		bindService(intent, conn, Context.BIND_AUTO_CREATE);

	}



	public void init(){
		bgMusic = (CheckBox) findViewById(R.id.bgmusic);
		buttonMusic =  (CheckBox) findViewById(R.id.buttonmusic);
		setting = (Button)findViewById(R.id.setting);
		cancel = (Button) findViewById(R.id.cancel);

		getMusicSetting();

		bgMusic.setChecked(isBgMusic);
		buttonMusic.setChecked(isButtonMusic);

		setting.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}






	
	private ServiceConnection conn  =new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			
			MusicService.LocalBinder binder =  (MusicService.LocalBinder)service;
			musicService = binder.getService();
			Log.d("TAg===>",(musicService==null)+ "ok");

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
		
	};
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
	
			
        case R.id.setting:
			
        	musicService.playTwo();
        	isButtonMusic = buttonMusic.isChecked();//查看是否勾选按钮 并返回结果
        	isBgMusic = bgMusic.isChecked();
        	musicService.isButtonMusic= isButtonMusic;

			if(!isBgMusic){
				musicService.pauseMusic();
				LogUtil.i("TAg===>","tttt");
			}else{
				musicService.continueMusic();
				LogUtil.i("TAg===>","pppp");

			}
			musicService.isBgMusic=isBgMusic;
			setMusicSetting();
			finish();
			break;
			
			
			case R.id.cancel:
				musicService.playTwo();
				finish();
				break;

		default:
			break;
		}
		
		
		
	}
	
	
	
	public void setMusicSetting(){
		editor.putBoolean("isBgMusic", isBgMusic);
		editor.putBoolean("isButtonMusic", isButtonMusic);
		editor.commit();
	}
	
    public void getMusicSetting(){
		sp=getSp();
		isBgMusic = sp.getBoolean("isBgMusic", true);
		isButtonMusic = sp.getBoolean("isButtonMusic", true);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unbindService(conn);
		finish();
	}
	

	
	
	
}
