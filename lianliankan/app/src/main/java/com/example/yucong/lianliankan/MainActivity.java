package com.example.yucong.lianliankan;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.content.Intent;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import android.support.v4.app.NotificationCompat;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;

import android.widget.Toast;
import android.widget.ViewSwitcher;


import com.example.yucong.lianliankan.entity.Glass;
import com.example.yucong.lianliankan.entity.Piece;
import com.example.yucong.lianliankan.entity.PieceImage;
import com.example.yucong.lianliankan.entity.Rank;
import com.example.yucong.lianliankan.service.GameService;
import com.example.yucong.lianliankan.service.GameServiceImp;
import com.example.yucong.lianliankan.util.GameConf;
import com.example.yucong.lianliankan.util.LinkInfo;
import com.example.yucong.lianliankan.util.LogUtil;
import com.example.yucong.lianliankan.view.Gameview;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends BaseActivity {

    private GameConf config;

    private GameService gameService;

    private Gameview gameView;

    private Button searchButton;

    private Button refershButton;

    public TextView levelTextView;

    private Timer timer = new Timer();
    private  MTimerTask mTimerTask;

    private int gameTime;

    public static final int IS_PLAY = 250;

    public static final int IS_PAUSE = 251;

    public static final int IS_STOP =252;

    public static int isPlaying = IS_PLAY;


    private Piece selected =null;


    public static int gameGlass = 1;

    Piece[][] pieces=null;

    public static final int GAMEROW=6;
    private final int GAMECOLUMN=6;
    public TextView maxscoreText;
    public int thisscore;
    public int totalscore;
    public int oldtotscore;
    private long touchtime;
    private ProgressBar pbtime;
    private TextSwitcher scoreText;
    private Button addtime;
    public Rank rank;
    private List<Glass> gs=new ArrayList<>();
    private Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case 0x123:

                    pbtime.setProgress(gameTime);
                    Log.d("yyh","gameTime = " + gameTime);
                    gameTime--;
                    // 时间小于0, 游戏失败
                    if (gameTime < 0)
                    {
                        stopTimer();
                        MyDialog myDialog=new MyDialog(MainActivity.this,false,MainActivity.this);
                        if(!isFinishing())
                        myDialog.showDialog(totalscore,thisscore);
                        if(soundManager!=null)
                           soundManager.fail();
                        thisscore=0;
                        touchtime=0;
                        return;
                    }
                    break;
                case 0x124:
                    scoreText.setText(thisscore+"");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rank=new Rank();
        gameTime=GameConf.DEFAULT_TIME;
        boolean jilu=sp.getBoolean("jilu",false);
        LogUtil.v("jilu",""+jilu);



        pbtime=findViewById(R.id.pbTime);
        pbtime.setMax(GameConf.DEFAULT_TIME);
        if (jilu)
            initold();
        else
            init();
        startGame(gameTime, true);
    }



    protected void init(){

        LitePal.getDatabase();
        config=new GameConf(GAMEROW,GAMECOLUMN, 100000,this);
        gameService=new GameServiceImp(config) ;
        gameView = (Gameview) findViewById(R.id.gameView);

        levelTextView = (TextView) findViewById(R.id.tvLevel);
        levelTextView.setText(getString(R.string.llkd)+gameGlass+getString(R.string.guan));

        searchButton = (Button) this.findViewById(R.id.prompt);

        refershButton = (Button) findViewById(R.id.refresh);
        addtime=(Button)findViewById(R.id.addTime);
        maxscoreText = (TextView) findViewById(R.id.maxthisScore);
        String s=getmaxscore(gameGlass)+"";
        maxscoreText.setText(getString(R.string.jilu)+s);
        gameView.setGameService(gameService);
        scoreText=findViewById(R.id.scoreText);
        scoreText.setFactory(new ViewSwitcher.ViewFactory() {
            //这里 用来创建内部的视图，这里创建TextView，用来显示文字
            public View makeView() {
                TextView tv =new TextView(MainActivity.this);
                //设置文字大小
                tv.setTextSize(30);
                //设置文字 颜色
                tv.setTextColor(getResources().getColor(R.color.darkblue));
                tv.setGravity(Gravity.CENTER);
                return tv;
            }
        });
        scoreText.setInAnimation(this,R.anim.slide_in_bottom);
        scoreText.setOutAnimation(this,R.anim.slide_out_up);
        scoreText.setText(thisscore+"");
        new Thread(new Runnable() {
            int score=0;
            @Override
            public void run() {
                while (true) {

                    if (score != thisscore) {
                        handler.sendEmptyMessage(0x124);
                        score = thisscore;
                    }
                    try{
                        Thread.sleep(100);
                    }catch (Exception e){
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();
        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View source)
            {
                  search(new SearchListener() {
                      @Override
                      public void onSuccess(List<Piece> list) {
                          LogUtil.w("","");
                          selected=null;
                          gameView.setSelectedpiece(null);
                          gameView.setTishipiece(list);
                          gameView.postInvalidate();
                        //  gameTime=gameTime+1;
                          soundManager.promot();
                      }

                      @Override
                      public void onFail() {
                          //解决子线程无法弹出Toast问题
                          Looper.prepare();
                          Toast.makeText(MainActivity.this,getString(R.string.protishi),Toast.LENGTH_SHORT).show();
                          Looper.loop();
                      }
                  });
            }
        });
        addtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameTime<30)
                gameTime+=5;

            }
        });
        refershButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                soundManager.refresh();
                pieces=refresh();
                if(gameView.getTishipiece()!=null)
                    gameView.setTishipiece(null);
                gameView.postInvalidate();
            }
        });
        this.gameView.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View view, MotionEvent e)
            {

                if(isPlaying!=IS_PLAY)
                {
                    return false;
                }
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        gameViewTouchDown(e);
                        break;

                    case MotionEvent.ACTION_UP:
                        gameViewTouchUp(e);
                        break;
                }
                return true;
            }
        });
    }

    private interface SearchListener{
        public void onSuccess(List<Piece> list);
        public void onFail();
    }
    private int index=0;
    private void search(final SearchListener searchListener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Piece> list=null;
                list=find();
                if(list!=null)
                    searchListener.onSuccess(list);
                else
                    searchListener.onFail();

            }


        }).start();

    }
    private Piece p = null;
    //把数组变为list
    private List<Piece> getPl(){
        List<Piece> l=new ArrayList<>();
        if(pieces!=null)
        for (int i = 0; i < pieces.length; i++) {
            for (int j = 0; j < pieces[i].length; j++) {
                if(pieces[i][j]!=null){
                    l.add(pieces[i][j]);
                }
            }
        }
        return  l;
    }
    public List<Piece> find(){
        List<Piece> l= new ArrayList<>();
        List<Piece> ls=getPl();
        Random r1= new Random();
        if(ls!=null){
            int x=r1.nextInt(ls.size());
            p=ls.get(x);
        }
        if(index>pieces.length*3){
            return null;
        }else{
            index++;
            Piece pl=haslink(p,ls);
            if(pl==null){
                return  find();
            }
            else if(pl!=null){
                l.add(p);
                l.add(pl);
                index=0;
                return l;
            }
        }
        return null;
    }
    private Piece haslink(Piece p,List<Piece> ls){
        Piece otherpiece=null;
        for(int i=0;i<ls.size();i++){
            otherpiece=ls.get(i);
            LinkInfo linkInfo=this.gameService.link(p,otherpiece);
            if(linkInfo!=null){
                return otherpiece;
            }
        }
        return null;
    }

    private Piece[][] refresh() {
        List<Piece> list=getPl();
        List<Piece> nl=change(list);
        for(int i=0;i<nl.size();i++){
            pieces[nl.get(i).getArrayxx()][nl.get(i).getArrayxy()]=nl.get(i);
        }
        return pieces;
    }
    private  List<Piece> change(List<Piece> list){
        List<Piece> nl=new ArrayList<>();
        Random r=new Random();
        for(int i=0;i<list.size();i++){
            int x=r.nextInt(list.size());
            Piece p1=list.get(i);
            Piece p2=list.get(x);
            Piece p3=new Piece();
            p3.setPieceImage(p1.getPieceImage());
            p1.setPieceImage(p2.getPieceImage());
            p2.setPieceImage(p3.getPieceImage());
            nl.add(p1);
        }
        return nl;
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        stopTimer();


    }

    @Override
    protected void onStop() {
        super.onStop();
        stopTimer();
        createNotifi();


    }
    private static final int PUSH_NOTIFICATION_ID = (0x001);
    private static final String PUSH_CHANNEL_ID = "PUSH_NOTIFY_ID";
    private static final String PUSH_CHANNEL_NAME = "PUSH_NOTIFY_NAME";
    private NotificationManager notificationManager;
    protected void createNotifi(){
        notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(PUSH_CHANNEL_ID, PUSH_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        Intent intent=new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pi=PendingIntent.getActivity(this,0,intent,0);
        Notification notification=new NotificationCompat.Builder(this).setContentTitle(getString(R.string.app_name))
                .setContentText("点击回到游戏").setWhen(System.currentTimeMillis())
                .setContentIntent(pi).setChannelId(PUSH_CHANNEL_ID)
                .setAutoCancel(true).setSmallIcon(R.drawable.ic_launcher).build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(PUSH_NOTIFICATION_ID,notification);

    }

    private long oldtime;
    @Override
    public void onBackPressed() {

        long backtime=System.currentTimeMillis();
        long time=backtime-oldtime;
        if(time<=1000){
           onclose();
        }else{
            oldtime=backtime;
            //Toast.makeText(this,"点击太慢了",Toast.LENGTH_SHORT).show();
        }
          Toast.makeText(this,getString(R.string.back_tishi),Toast.LENGTH_SHORT).show();

    }
    public  void onclose(){
        saveAll();
        super.onBackPressed();
    }


    private void destorynot(){
        try {
            if (notificationManager != null) ;
            notificationManager.cancel(PUSH_NOTIFICATION_ID);
        }catch(Exception e){
            LogUtil.w("exception",e.toString());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destorynot();
    }
    //保存游戏
    private void saveAll(){
        DataSupport.deleteAll(PieceImage.class);
        DataSupport.deleteAll(Piece.class);
        savebysp();
        savebydb();
        if(!gameService.hasPieces()){
            if(gameGlass==3) {
                gameGlass = 1;
                editor.clear();
                editor.commit();
            }
            else
                gameGlass++;
            editor.putInt("gameGlass",gameGlass);
            editor.commit();

        }
    }
    private void savebysp(){
        editor.putBoolean("jilu",true);
        editor.putInt("gameGlass",gameGlass);
        editor.putInt("gameTime",gameTime);
        editor.putInt("thisscore", thisscore);
        editor.putInt("totalscore", totalscore);
        editor.putInt("oldtotscore", oldtotscore);
        if(rank.getGlassList()!=null) {
            for (int i = 1; i <= rank.getGlassList().size(); i++) {
                editor.putInt("gameGlass" + i, rank.getGlassList().get(i-1).getScore());
            }
        }
        editor.apply();
    }
    private void savebydb(){
        if(gameService.hasPieces()) {
            for (int i = 0; i < pieces.length; i++) {
                for (int j = 0; j < pieces[i].length; j++) {
                    if (pieces[i][j] != null) {
                        Piece piece = pieces[i][j];
                        PieceImage pieceImage = new PieceImage();
                        pieceImage.setBitmap(piece.getPieceImage().getBitmap());
                        pieceImage.setImageId(piece.getPieceImage().getImageId());
                        Piece newpiece = new Piece();
                        pieceImage.save();
                        newpiece.setPieceImage(pieceImage);
                        newpiece.setX(piece.getX());
                        newpiece.setY(piece.getY());
                        newpiece.setArrayxx(piece.getArrayxx());
                        newpiece.setArrayxy(piece.getArrayxy());
                        newpiece.save();
                    }
                }
            }
        }

    }
    private void initold(){
        gameGlass=sp.getInt("gameGlass",1);
        gameTime=sp.getInt("gameTime",GameConf.DEFAULT_TIME);
        thisscore=sp.getInt("thisscore",0);
        totalscore=sp.getInt("totalscore",0);
        oldtotscore=sp.getInt("oldtotscore",0);
        pbtime.setProgress(gameTime);
        gs.clear();
        for(int i=1;i<gameGlass;i++) {
            if(sp.contains("gameGlass"+i)){
                Glass g=new Glass();
                g.setGameglass(i);
                g.setScore(sp.getInt("gameGlass"+i,0));
                gs.add(g);
            }
        }
        rank.setGlassList(gs);
        init();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(timer==null&&mTimerTask==null) {
            this.timer = new Timer();
            this.mTimerTask = new MTimerTask();
            // 启动计时器 ， 每隔1秒发送一次消息
            this.timer.schedule(mTimerTask, 0, 1000);
        }
        destorynot();
    }


    private void gameViewTouchDown(MotionEvent event) //①
    {

        LogUtil.i("MainActivity","TouchDown:"+event.getAction()+"");
        if(gameView.getTishipiece()!=null)
            gameView.setTishipiece(null);
        float touchX = event.getX();
        float touchY = event.getY();
        Piece currentPiece = gameService.findPiece(touchX, touchY);  //②
        if (currentPiece == null)
            return;
        this.gameView.setSelectedpiece(currentPiece);
        soundManager.select();
        if (this.selected == null)
        {
            this.selected = currentPiece;
            this.gameView.postInvalidate();
            return;
        }
        if (this.selected != null)
        {
            LinkInfo linkInfo = this.gameService.link(this.selected,
                    currentPiece);   //③
            if (linkInfo == null)
            {
                this.selected = currentPiece;
                this.gameView.postInvalidate();
            }
            else
            {
             thisscore+=1;
             long firstime=System.currentTimeMillis();
             if(touchtime==0){
                 touchtime=firstime;
             }else{
                 long secondtime=firstime-touchtime;
                 if(secondtime<2000){
                     thisscore+=10;
                     soundManager.combo();
                 } else if (secondtime<3000){
                     thisscore+=5;
                     soundManager.combo();
                 }else if(secondtime<=4000){
                     thisscore+=2;
                 }
                 touchtime=firstime;
             }
                handleSuccessLink(linkInfo, this.selected
                        , currentPiece);
            }
        }
    }
    private void gameViewTouchUp(MotionEvent e)
    {
        this.gameView.postInvalidate();
    }

    public void startGame(int gameTime,boolean jilv)
    {

        if (this.timer != null)
        {
            stopTimer();
        }
        this.gameTime = gameTime;
        if(gameTime == GameConf.DEFAULT_TIME||jilv)
        {
            gameView.start();
            pieces = gameService.getPieces();
        }
        if(timer==null&&mTimerTask==null) {
            this.timer = new Timer();
            this.mTimerTask = new MTimerTask();
            this.timer.schedule(mTimerTask, 0, 1000);
        }
        isPlaying = IS_PLAY;
        this.selected = null;

    }
    private  class MTimerTask extends TimerTask{
        public void run()
        {

            handler.sendEmptyMessage(0x123);
        }
    }
    public MyDialog myDialog;
    public SuccessDialog successDialog;
    /**
     * 成功连接后处理
     *
     */
    private void handleSuccessLink(LinkInfo linkInfo, Piece prePiece,
                                   Piece currentPiece){
        this.gameView.setLinkInfo(linkInfo);

        this.gameView.setSelectedpiece(null);

        Log.d("yyyyy","prePiece.getArrayxx() = " + prePiece.getArrayxx());
        Log.d("yyyyy","prePiece.getArrayxy() = " + prePiece.getArrayxy());
        Log.d("yyyyy","currentPiece.getArrayxx() = " + currentPiece.getArrayxx());
        Log.d("yyyyy","currentPiece.getArrayxy() = " + currentPiece.getArrayxy());
        pieces[prePiece.getArrayxx()][prePiece.getArrayxy()] = null;
        pieces[currentPiece.getArrayxx()][currentPiece.getArrayxy()] = null;
        this.selected = null;
        soundManager.erase();
        this.gameView.postInvalidate();


        if (!this.gameService.hasPieces())
        {
            if (gameTime >= 0) {
                myDialog = new MyDialog(MainActivity.this, true, this);
                oldtotscore=totalscore;
                totalscore=totalscore+thisscore;
                setmaxscore(gameGlass);
                if(!isFinishing())
                myDialog.showDialog(totalscore,thisscore);
                soundManager.win();
                thisscore=0;
                touchtime=0;
                stopTimer();
            }
        }
    }
    public void setmaxscore(int glass){
        Glass g=new Glass();
        g.setScore(thisscore);
        g.setGameglass(glass);
        g.save();
        gs.add(g);
        rank.setGlassList(gs);
    }
    public int getmaxscore(int glass){

        int max=0;
          max=DataSupport.where("gameglass=?",glass+"").max(Glass.class,"score",int.class);
          LogUtil.v("maxscore","max是："+max);
        return max;
    }
    private void stopTimer()
    {
        Log.d("yyh","mTimerTask = " + mTimerTask + " timer = " + timer);
        if(this.mTimerTask!=null){
            Log.d("yyh","mTimerTask here stop timer");
            this.mTimerTask.cancel();
            this.mTimerTask=null;
        }
        if(this.timer!=null) {
            Log.d("yyh","timer here stop timer");
            this.timer.cancel();
            this.timer.purge();
            this.timer = null;
        }

    }



    @Override
    public void finish() {
        if(successDialog!=null) {
            successDialog.cancel();
            successDialog = null;
        }
        if(myDialog!=null){
            myDialog.dismiss();
            myDialog=null;
        }
        stopTimer();
        super.finish();
        //onDestroy();
    }

    @Override
    protected void playmusic() {
        if(musicManager!=null){
            musicManager.setMusres(R.raw.bgmusic);
            super.playmusic();
        }
    }
}

