package com.example.yucong.terist.base;


/**
 * 时分秒
 */
public class GameTime {




	public int hour;
	public int minute;    
	public int sec;
	   

	public static String getTime(int hour,int minute,int sec ){
		hour=Math.abs(hour);
		minute=Math.abs(minute);
		sec=Math.abs(sec);
		return  (hour>=10?hour+"":"0"+hour+"")+":"+(minute>=10?minute+"":"0"+minute+"")+":"+(sec>=10?sec+"":"0"+sec+"");
	}

}
