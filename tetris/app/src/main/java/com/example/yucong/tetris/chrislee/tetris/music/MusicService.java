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
import com.example.yucong.tetris.chrislee.tetris.util.LogUtil;


public class MusicService extends Service {

	private final IBinder binder = new LocalBinder();  //本地通讯
	public SoundPool soundPool; //音乐池
	public MediaPlayer  mediaPlay;//媒体类
	public HashMap<Integer, Integer> soundPoolMap;
	public Boolean isButtonMusic=true;
	public Boolean isBgMusic=true;
	private int position;//记录暂停的位置
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
			getGameSharedPreference();
		return binder;
	}
	
	public  class LocalBinder extends Binder{
		
		public  MusicService getService(){
			Log.d("TAg===>","getData ok");

			return MusicService.this;
		}
		
	}

	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		init();
		LogUtil.i("TAg===>","onCreate init");

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

		mediaPlay=new MediaPlayer();

	}
	
	public void playOne(){
		if(soundPool!=null){
		     if(isButtonMusic)
				soundPool.play(soundPoolMap.get(1), 1, 1,0, 0, 1);
			}

	}
	
	public void playTwo(){
		if(soundPool!=null){
		     if(isButtonMusic){
				soundPool.setVolume(soundPool.play(soundPoolMap.get(2), 1, 1,0, 0, 1), 1f, 1f);
			}
			
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
		if(soundPool!=null){
		       if(isButtonMusic) {

				   soundPool.play(soundPoolMap.get(6), 1, 1, 0, 0, 1);
			   }
		}

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
		if(soundPool!=null){
			if(isButtonMusic){
				soundPool.play(soundPoolMap.get(12), 1, 1,0, 0, 1);

			   }
		}


	}
	public void playThirteen(){
		if(soundPool!=null) {
			if (isButtonMusic){
				soundPool.play(soundPoolMap.get(13), 1, 1, 0, 0, 1);
			}
		}
	}
	
	

		
	


	public void freeResource(){
		if (mediaPlay!=null) {
			if (mediaPlay.isPlaying()) {
				mediaPlay.stop();
			}
			mediaPlay.release();
		}
	}

	public void pauseMusic(){
		if (mediaPlay!=null){
			if(mediaPlay.isPlaying()){
				position = mediaPlay.getCurrentPosition();
				mediaPlay.pause();
			}
		}

	}


	public void continueMusic(){

			if (position > 0) {
				play(position);
				position = 0;
			} else {
				play(0);
			}


	}


	public void satrtMusic(){

          if (isBgMusic){
	             play(0);
            }


	}


	public void play(int position) {
		Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.menubg);
		try {
			mediaPlay.reset();//重置播放
			mediaPlay.setDataSource(this,uri); //获取播放资源
			mediaPlay.prepare();//开始缓存
			mediaPlay.setOnPreparedListener(new PrepareListener(position));//监听缓存状态
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public final class PrepareListener implements MediaPlayer.OnPreparedListener {
		private int position;//成员类 保存播放进度
		public PrepareListener(int position) {
			this.position = position;
		}
		public void onPrepared(MediaPlayer mp) {
			mediaPlay.start();//开始播放
			if(position>0) mediaPlay.seekTo(position);//已经播放一段时间的话 从当前位置开始播放
		}
	}




	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		freeResource();
		soundPool.release();
		
	}
    public void getGameSharedPreference(){
		
		SharedPreferences sp = getSharedPreferences("jilu", Context.MODE_PRIVATE);
		isBgMusic = sp.getBoolean("isBgMusic", true);
		isButtonMusic=sp.getBoolean("isButtonMusic", true);
		LogUtil.e("TAg===>","getGameSharedPreference"+isBgMusic);
		
	}
	
	

}

