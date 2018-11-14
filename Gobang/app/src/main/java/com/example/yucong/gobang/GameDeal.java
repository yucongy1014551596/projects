package com.example.yucong.gobang;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;


public class GameDeal extends AppCompatActivity implements Chronometer.OnChronometerTickListener,Runnable {

    private Game game;

    private long duration;
    private int standpoint;//代表用户选择的执方

    private Chronometer timer;
    //记录棋手用时，不能设为静态变量
    private long blackTime = 0;
    private long whiteTime = 0;

    private final int GAME_OVER = 1;
    private final int BLACK = 1;
    private final int WHITE =2;

    private TextView myTimer;
    private TextView otherTimer;
    private TextView myMessage;
    private TextView otherMessage;
    
    private User userHim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_deal);

        WindowManager manager = getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        BothSide.width = metrics.widthPixels;  //以要素为单位,得到的值应该比看到的大
        BothSide.height = metrics.heightPixels;



        getPlayer();

        //region 得到控件
        timer = (Chronometer) findViewById(R.id.timer);
        //timer.setVisibility(View.GONE);//这里使用Ｇone在真机上会没有计时，虽然模拟器上可以。真机运行只能让其为可见模式。
        timer.setOnChronometerTickListener(this);

        myTimer = (TextView) findViewById(R.id.my_timer);
        otherTimer = (TextView) findViewById(R.id.other_timer);
        myMessage = (TextView) findViewById(R.id.my_message);
        otherMessage = (TextView) findViewById(R.id.other_message);
        //endregion

        //region 加载对局视图
        if(BothSide.isSaved){
            //恢复对局数据
            @SuppressLint("WrongConstant") SharedPreferences pre = getSharedPreferences("SaveState",MODE_APPEND);
            StringBuilder builder = new StringBuilder(pre.getString("chessboard",""));
            game = new Game(this,builder);
        }
        else{
            game = new Game(this);
        }

        FrameLayout fm = (FrameLayout) findViewById(R.id.game_view);
        fm.addView(game.getGameView());
        //endregion

        //region 设置对局
        if(BothSide.isSaved) {
            @SuppressLint("WrongConstant") SharedPreferences pre = getSharedPreferences("SaveState",MODE_APPEND);
            duration = pre.getLong("duration",0);
            standpoint = pre.getInt("standpoint",0);
            blackTime = pre.getLong("blackTime",0);
            whiteTime = pre.getLong("whiteTime",0);
            game.setBlackTurn(pre.getBoolean("isBlackTurn",false));

            setLogical();
            SharedPreferences.Editor edit =pre.edit();
            edit.putBoolean("isSaved",false);
            edit.apply();
            BothSide.isSaved = false;
        }else{
            //弹出弹窗进行对局设置
            setting();
            //看看弹窗是否阻塞了进程？并没有
            //timer.start();//计时器开启
        }
        //endregion
    }
    
    //得到对手的信息
    private void getPlayer(){
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        Uri uri = Uri.parse("content://com.example.mrpeng.gobang.provider/User/" + id);
        Cursor cursor = getContentResolver().query(uri,new String[]{"account","win","lose","grade"},null,null,null);
        
        cursor.moveToFirst();//转到开头
        String account = cursor.getString(cursor.getColumnIndex("account"));
        int win = cursor.getInt(cursor.getColumnIndex("win"));
        int lose = cursor.getInt(cursor.getColumnIndex("lose"));
        int grade = cursor.getInt(cursor.getColumnIndex("grade"));
        cursor.close();
        userHim = new User(id,account,win,lose,grade);
        BothSide.Him = userHim;
    }

    //进行对局设置
    private void setting(){
        //下拉列表设置对局时间
        Spinner spinner = new Spinner(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new  AdapterView.OnItemSelectedListener(){
            @Override
            public  void onItemSelected(AdapterView<?> parent, View view,
                                        int pos, long id) {
                switch (pos){
                    case 0:duration = 30;
                        break;
                    case 1:duration = 1*60;
                        break;
                    case 2:duration = 5*60;
                        break;
                    case 3:duration = 10*60;
                        break;
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        TextView textTime = new TextView(this);
        textTime.setText(R.string.duration);

        LinearLayout layoutTime = new LinearLayout(this);
        layoutTime.setOrientation(LinearLayout.HORIZONTAL);
        layoutTime.addView(textTime);
        layoutTime.addView(spinner);
        layoutTime.setPadding(10,10,10,10);

        //选择执方
        final RadioButton radioBlack = new RadioButton(this);//执黑
        radioBlack.setText(getString(R.string.black));
        RadioButton radioWhite = new RadioButton(this);//执白
        radioWhite.setText(getString(R.string.white));

        RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        radioGroup.addView(radioBlack,0);
        radioGroup.addView(radioWhite,1);

        //注意这句一定要在group添加以后再写，否则就会把黑方一直设置为checked
        radioBlack.setChecked(true);//默认为黑


        TextView textStandpoint = new TextView(this);
        textStandpoint.setText(R.string.standpoint);

        LinearLayout layoutStandpoint = new LinearLayout(this);
        layoutTime.setOrientation(LinearLayout.HORIZONTAL);
        layoutStandpoint.addView(textStandpoint);
        layoutStandpoint.addView(radioGroup);
        layoutStandpoint.setPadding(10,10,10,10);

        //弹窗设置信息,并添加上面的视图
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.game_setting);
        dialog.setCancelable(false);
        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(radioBlack.isChecked()){
                    standpoint = BLACK;
                }else{
                    standpoint = WHITE;
                }
                setLogical();
            }
        });

        DividerManager manager = new DividerManager("#8B814C",this);

        LinearLayout myView = new LinearLayout(this);
        myView.setOrientation(LinearLayout.VERTICAL);
        myView.addView(manager.getDivider());
        myView.addView(layoutTime);
        myView.addView(manager.getDivider());
        myView.addView(layoutStandpoint);
        myView.addView(manager.getDivider());

        dialog.setView(myView);
        dialog.show();
    }

    //逻辑上设置，方便恢复对局
    private void setLogical(){
        game.initSetting(duration,standpoint,getResources().getInteger(R.integer.lineNum));

        if(game.getStandpoint() == BLACK){
            myTimer.setText(transferIntoTime(blackTime));
            otherTimer.setText(transferIntoTime(whiteTime));
            myMessage.setText(BothSide.Me.getAccount() + "：" + getString(R.string.black));
            otherMessage.setText(userHim.getAccount() + "：" + getString(R.string.white));
        }else{
            myTimer.setText(transferIntoTime(whiteTime));
            otherTimer.setText(transferIntoTime(blackTime));
            myMessage.setText(BothSide.Me.getAccount() + "：" + getString(R.string.white));
            otherMessage.setText(userHim.getAccount() + "：" + getString(R.string.black));
        }

        timer.start();//开启计时器
        new Thread(GameDeal.this).start();//超时判断线程
    }

    //转化成倒计时的标准时间格式
    private String transferIntoTime(long time){
        time = game.getDuration() - time;
        String hh = new DecimalFormat("00").format(time / 3600);
        String mm = new DecimalFormat("00").format(time % 3600 / 60);
        String ss = new DecimalFormat("00").format(time % 60);

        return hh + ":" + mm + ":" + ss;
    }

    @Override//即时显示剩余时间
    public void onChronometerTick(Chronometer chronometer){

        if(game.isBlackTurn()){
            blackTime++;
            if(game.getStandpoint() == BLACK){
                myTimer.setText(transferIntoTime(blackTime));
            }else{
                otherTimer.setText(transferIntoTime(blackTime));
            }
        }else{
            whiteTime++;
            if(game.getStandpoint() == BLACK){
                otherTimer.setText(transferIntoTime(whiteTime));
            }else{
                myTimer.setText(transferIntoTime(whiteTime));
            }
        }
    }

    //region 超时判断
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.what == GAME_OVER){
                timer.stop();
                game.showResult();
            }
        }
    };

    @Override
    public void run(){
        Message message = new Message();
        message.what = GAME_OVER;
        while(game.isPlaying()){
            if(game.isBlackTurn()){
                //代表黑方回合
                if(blackTime>=duration){
                    game.setWinner(WHITE);
                    game.setIsPlaying(false);
                    handler.sendMessage(message);
                }
            }else{
                if(whiteTime>=duration){
                    game.setWinner(BLACK);
                    game.setIsPlaying(false);
                    handler.sendMessage(message);
                }
            }
        }
    }
    //endregion

    @Override//遇到中断，保存对局数据
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        int[][] chessboard = game.getChessboard();
        StringBuilder builder = new StringBuilder("");
        for(int i=0;i<getResources().getInteger(R.integer.lineNum)+2;i++){
            for(int j=0;j<getResources().getInteger(R.integer.lineNum)+2;j++){
                builder.append(chessboard[i][j]+"");
            }
        }

        SharedPreferences pre = getSharedPreferences("SaveState",MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putBoolean("isSaved",true);
        editor.putString("account",BothSide.Me.getAccount());
        editor.putString("password",BothSide.password);
        editor.putInt("mid",BothSide.Me.getId());
        editor.putInt("hid",BothSide.Him.getId());
        editor.putLong("duration",duration);
        editor.putInt("standpoint",standpoint);
        editor.putLong("blackTime",blackTime);
        editor.putLong("whiteTime",whiteTime);
        editor.putBoolean("isBlackTurn",game.isBlackTurn());
        editor.putString("chessboard",builder.toString().replace("-",""));
        editor.apply();
    }

    public void onBackPressed(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(R.string.hall_str4);
        dialog.setCancelable(false);
        dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("content://com.example.mrpeng.gobang.provider/Online");
                ContentValues values = new ContentValues();
                values.put("isPlaying",0);
                getContentResolver().update(uri,values,"id=? or id=?",new String[]{BothSide.Me.getId()+"",BothSide.Him.getId()+""});

                Intent intent = new Intent(GameDeal.this,Hall.class);
                GameDeal.this.startActivity(intent);
            }
        });
        dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }
}
