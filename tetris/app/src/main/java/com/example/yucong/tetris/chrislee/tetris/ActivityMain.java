package com.example.yucong.tetris.chrislee.tetris;
// Author: ChrisLee
// 2018

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.yucong.tetris.R;
import com.example.yucong.tetris.chrislee.tetris.music.MusicPage;
import com.example.yucong.tetris.chrislee.tetris.music.MusicService;
import com.example.yucong.tetris.chrislee.tetris.util.LogUtil;
import com.example.yucong.tetris.chrislee.tetris.util.SPutil;

import java.util.ArrayList;
import java.util.List;


public class ActivityMain extends BaseActivity  {

    public static final int FLAG_NEW_GAME = 0;
    public static final int FLAG_CONTINUE_LAST_GAME = 1;

    public static final String FILENAME = "settingInfo";
    public static final String LEVEL = "level";


    private int mLevel = 1;
    private Button btLan = null;
    private Button btNewgame = null;
    private Button btContinue = null;
    private Button btHelp = null;
    private Button btRank = null;
    private Button btPre = null;
    private Button btNext = null;
    private TextView tvLevel = null;
    private ImageButton about_page;
    private ImageButton music_page;
    public MusicService meidiaPlay;



    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        init();
        Intent intent = new Intent(this,MusicService.class);
		bindService(intent, conn, Context.BIND_AUTO_CREATE);

    }





	private ServiceConnection conn  =new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub

			MusicService.LocalBinder binder =  (MusicService.LocalBinder)service;
                meidiaPlay = binder.getService();
                meidiaPlay.satrtMusic();
                Log.d("TAg===>","ActivityMain  meidiaPlay.start");

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}


	};



    public void init(){

        btNewgame = (Button) findViewById(R.id.bt_new);
        btContinue = (Button) findViewById(R.id.bt_continue);
        btHelp = (Button) findViewById(R.id.bt_help);
        btRank = (Button) findViewById(R.id.bt_rank);
        btPre = (Button) findViewById(R.id.bt_pre);
        btNext = (Button) findViewById(R.id.bt_next);
        tvLevel = (TextView) findViewById(R.id.tv_speed);
        btLan=(Button)findViewById(R.id.buttonlan);
        about_page=(ImageButton) findViewById(R.id.about_game);
        music_page=(ImageButton) findViewById(R.id.music_page);



        btNewgame.setOnClickListener(buttonListener);
        btContinue.setOnClickListener(buttonListener);
        btRank.setOnClickListener(buttonListener);
        btPre.setOnClickListener(buttonListener);
        btNext.setOnClickListener(buttonListener);
        btLan.setOnClickListener(buttonListener);
        about_page.setOnClickListener(buttonListener);
        music_page.setOnClickListener(buttonListener);

        restoreSettings();
//         getDatas();
        getDatasByContentProvider();
    }


    private Button.OnClickListener buttonListener = new Button.OnClickListener() {

        @SuppressLint("WrongConstant")
        @Override
        public void onClick(View v) {
            if (v == btNewgame) {
                Intent intent = new Intent(ActivityMain.this, com.example.yucong.tetris.chrislee.tetris.ActivityGame.class);
                intent.setFlags(FLAG_NEW_GAME);
                android.util.Log.i("ljs88888", "mLevel = " +mLevel+", 103");
                intent.putExtra(LEVEL, mLevel);
                startActivity(intent);
                return;
            }
            if (v == btContinue) {
                Intent intent = new Intent(ActivityMain.this, com.example.yucong.tetris.chrislee.tetris.ActivityGame.class);
                intent.setFlags(FLAG_CONTINUE_LAST_GAME);
                startActivity(intent);

                return;
            }
            if (v == btHelp) {
                Intent intent = new Intent(ActivityMain.this, com.example.yucong.tetris.chrislee.tetris.ActivityHelp.class);
                startActivity(intent);
                finish();
                return;
            }
            if (v == btRank) {
                Intent intent = new Intent(ActivityMain.this, com.example.yucong.tetris.chrislee.tetris.ActivityRank.class);
                startActivity(intent);
                return;
            }
            if (v == about_page) {
                Intent intent = new Intent(ActivityMain.this, com.example.yucong.tetris.chrislee.tetris.AboutActivity.class);
                startActivity(intent);
                return;
            }
            if (v == music_page) {
                Intent intent = new Intent(ActivityMain.this, MusicPage.class);
                startActivity(intent);
                return;
            }
            if (v == btPre) {
                btPre.setBackgroundColor(0xffc0c0c0);
                String s = tvLevel.getText().toString();
                int level = Integer.parseInt(s);
                --level;
                level = (level - 1 + TetrisView.MAX_LEVEL) % TetrisView.MAX_LEVEL;
                ++level;
                s = String.valueOf(level);
                tvLevel.setText(s);
                mLevel = level;
                btPre.setBackgroundColor(0x80cfcfcf);
                return;
            }
            if (v == btNext) {
                btNext.setBackgroundColor(0xffc0c0c0);
                String s = tvLevel.getText().toString();
                int level = Integer.parseInt(s);
                --level;
                level = (level + 1) % TetrisView.MAX_LEVEL;
                ++level;
                s = String.valueOf(level);
                tvLevel.setText(s);
                mLevel = level;
                btNext.setBackgroundColor(0x80cfcfcf);
                return;
            }
//            if (v == btExit) {
////                ActivityMain.this.finish();
//                ActivityCollector.finishAll();
//            }

            if (v == btLan) {
                saveLanguage();
                Intent   intent=new Intent(ActivityMain.this,ActivityMain.class);
                startActivity(intent);
                finish();

            }
        }
    };

    private void saveSettings() {
        SharedPreferences settings = getSharedPreferences(FILENAME, 0);
        settings.edit()
                .putInt(LEVEL, mLevel)
                .commit();
    }

    private void restoreSettings() {
        SharedPreferences settings = getSharedPreferences(FILENAME, 0);
        mLevel = settings.getInt(LEVEL, 1);
        tvLevel.setText(String.valueOf(mLevel));

    }

    public void onStop() {
        super.onStop();
        saveSettings();

    }


    private void saveLanguage(){
        int lan= SPutil.getSPutil(this).getLan();
        LogUtil.i("lan",""+lan);
        lan=(++lan)%2;
        LogUtil.v("language","lan:"+lan);
        SPutil.getSPutil(this).saveLan(lan);
    }




    public void getDatasByContentProvider(){
        //全查
        Uri uriQuery = Uri.parse("content://com.example.yucong.provider.rank/rank");
        Cursor cursor=  getContentResolver().query(uriQuery,null,null,null,null);
        List<String> lists=new ArrayList<String>();

        Intent intent=  getIntent(); //接受从客户端传过来的数据
        String userName=intent.getStringExtra("userName");
        long time= intent.getLongExtra("time",0);
        int score=  intent.getIntExtra("score",0);

        if(!"".equals(userName)&&time!=0&&score!=0){
            if(cursor!=null){
                LogUtil.i("userName","ranks不为空");
                while (cursor.moveToNext()){
                    lists.add(cursor.getString(cursor.getColumnIndex("name")));
                }

            }
          if(lists.contains(userName)){
                LogUtil.i("userName","姓名已存在");
              Uri uriRowQuery = Uri.parse("content://com.example.yucong.provider.rank/rank/1");
              Cursor cursorName=  getContentResolver().query(uriRowQuery,null,"name=?",new String[]{userName},null);

              while (cursorName.moveToNext()){
                  LogUtil.i("userName","姓名已存在进入便历循环");
                 int dataScore= cursorName.getInt(cursor.getColumnIndex("score"));
                 int id= cursorName.getInt(cursor.getColumnIndex("id"));
                    if(dataScore<=score){
                           LogUtil.i("userName","分数高于之前  可以进行更新处理");

                            Uri uri = Uri.parse("content://com.example.yucong.provider.rank/rank");
                             ContentValues contentValues=new ContentValues();
                             contentValues.put("score",score);
                             contentValues.put("time",time);

                          getContentResolver().update(uri,contentValues,"id=?",new String[]{String.valueOf(id)});
                     }else {
                        LogUtil.i("userName","分数低于之前  不进行处理");
                    }
              }


          }else {
                  LogUtil.i("userName","姓名不存在");

                   Uri uri = Uri.parse("content://com.example.yucong.provider.rank/rank");
                    ContentValues contentValues=new ContentValues();
                    contentValues.put("name",userName);
                    contentValues.put("score",score);
                     contentValues.put("time",time);
                  getContentResolver().insert(uri,contentValues);


                }

        }else {
            LogUtil.i("userName","传入的值为空 ,不进行处理");
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

		unbindService(conn);
    }


}