package com.example.yucong.tetris.chrislee.tetris.music;

import java.io.IOException;
import java.util.HashMap;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.yucong.tetris.R;


public class MusicService extends Service {

	private final IBinder binder = new LocalBinder();  //本地通讯
	public SoundPool soundPool; //音乐池
	public MediaPlayer  mediaPlay;//媒体类
	public HashMap<Integer, Integer> soundPoolMap;
	public Boolean isButtonMusic=true;
	public Boolean isBgMusic=true;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return binder;
	}
	
	public  class LocalBinder extends Binder{
		
		public  MusicService getService(){
			Log.d("TAg===>","bgPlay1 ok");
			return MusicService.this;
		}
		
	}

	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		init();

	}



	public void init(){
		soundPool =new SoundPool(13, AudioManager.STREAM_SYSTEM, 2);
		soundPoolMap = new HashMap<Integer, Integer>();
		soundPoolMap.put(1, soundPool.load(this, R.raw.ka, 1));
		soundPoolMap.put(2, soundPool.load(this, R.raw.sa, 1));
		soundPoolMap.put(3, soundPool.load(this, R.raw.seekbar, 1));
		soundPoolMap.put(4, soundPool.load(this, R.raw.rotation, 1));
		soundPoolMap.put(5, soundPool.load(this, R.raw.down, 1));
		soundPoolMap.put(6, soundPool.load(this, R.raw.fastdown, 1));
		soundPoolMap.put(7, soundPool.load(this, R.raw.lost, 1));
		soundPoolMap.put(8, soundPool.load(this, R.raw.delete1, 1));
		soundPoolMap.put(9, soundPool.load(this, R.raw.delete2, 1));
		soundPoolMap.put(10, soundPool.load(this, R.raw.delete3, 1));
		soundPoolMap.put(11, soundPool.load(this, R.raw.delete4, 1));
		soundPoolMap.put(12, soundPool.load(this, R.raw.move, 1));
		soundPoolMap.put(13, soundPool.load(this, R.raw.bomb, 1));
		getGameSharedPreference();
		Log.d("TAg===>","backPlay11 ok");
	}
	
	public void playOne(){
		if(isButtonMusic)
		 soundPool.play(soundPoolMap.get(1), 1, 1,0, 0, 1); 
		
	}
	
	public void playTwo(){
		if(isButtonMusic){
		 Log.d("TAG","playTwo");
			
			soundPool.setVolume(soundPool.play(soundPoolMap.get(2), 1, 1,0, 0, 1), 1f, 1f);
		}
	}
	public void playThree(){
		if(isButtonMusic)
		 soundPool.play(soundPoolMap.get(3), 1, 1,0, 0, 1); 
		
	}
	
	public void playFour(){
		if(isButtonMusic)
		 soundPool.play(soundPoolMap.get(4), 1, 1,0, 0, 1); 
		
	}
	public void playFive(){
		if(isButtonMusic)
		 soundPool.play(soundPoolMap.get(5), 1, 1,0, 0, 1); 
		
	}
	public void playSix(){
		if(isButtonMusic)
		 soundPool.play(soundPoolMap.get(6), 1, 1,0, 0, 1); 
		
	}
	public void playSeven(){
		if(isButtonMusic)
		 soundPool.play(soundPoolMap.get(7), 1, 1,0, 0, 1); 
		
	}
	public void playEight(){
		if(isButtonMusic)
		 soundPool.play(soundPoolMap.get(8), 1, 1,0, 0, 1); 
		
	}
	public void playNine(){
		if(isButtonMusic)
		 soundPool.play(soundPoolMap.get(9), 1, 1,0, 0, 1); 
		
	}
	public void playTen(){
		if(isButtonMusic)
		 soundPool.play(soundPoolMap.get(10), 1, 1,0, 0, 1); 
		
	}
	public void playEleven(){
		if(isButtonMusic)
		 soundPool.play(soundPoolMap.get(11), 1, 1,0, 0, 1); 
		
	}
	public void playTwelve(){
		if(isButtonMusic)
			soundPool.play(soundPoolMap.get(12), 1, 1,0, 0, 1);

	}
	public void playThirteen(){
		if(isButtonMusic)
			soundPool.play(soundPoolMap.get(13), 1, 1,0, 0, 1);

	}
	
	
	public void CreateMediaSound(){
		
		mediaPlay = new MediaPlayer();
		mediaPlay.setVolume(0.4f, 0.4f);
		playMediaSound();
		}
		
	
	
	
	public void playMediaSound(){
		
		
		Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.menubg);
			try {
				
		    mediaPlay.setDataSource(this,uri);
			
			mediaPlay.prepare();
			mediaPlay.start();
			mediaPlay.setLooping(true);
			
			
			if(!isBgMusic){
				mediaPlay.pause();
			}
			
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	
	public void pauseBgMusic(){
		

		if(mediaPlay.isPlaying()){
			mediaPlay.pause();
			
		}
	}
	
    public void startBgMusic(){

		if(mediaPlay.getDuration()==0)
		{
           	CreateMediaSound();

		}else{
			mediaPlay.start();
		}
		
	}
	
	public void endBgSound(){
		
		if(mediaPlay.isPlaying()){
			mediaPlay.stop();
		}
		mediaPlay.release();

		
	}
	

	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		endBgSound();
		soundPool.release();
		
	}
    public void getGameSharedPreference(){
		
		SharedPreferences sp = getSharedPreferences("jilu", Context.MODE_PRIVATE);
		isBgMusic = sp.getBoolean("isBgMusic", true);
		isButtonMusic=sp.getBoolean("isButtonMusic", true);
		
	}
	
	

}

