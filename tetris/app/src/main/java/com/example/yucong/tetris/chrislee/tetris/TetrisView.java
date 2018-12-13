package com.example.yucong.tetris.chrislee.tetris;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.yucong.tetris.R;
import com.example.yucong.tetris.chrislee.tetris.entity.Block;
import com.example.yucong.tetris.chrislee.tetris.util.GameConf;
import com.example.yucong.tetris.chrislee.tetris.util.LogUtil;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

/**
 * 游戏主界面
 *
 *
 *
 * TetrisView
 */
public class TetrisView extends View implements Runnable {
    /** 调用此对象的Activity对象 */
    private ActivityGame father = null;
//     public  final static int SCREEN_WIDTH = 960;
//     public final static int SCREEN_HEIGHT = 455 * 3;
    /** 俄罗斯方块的最大坐标 */
    public static int SCREEN_WIDTH ;
    public  static int SCREEN_HEIGHT;
    private TextView timeView;
    /** 俄罗斯方块的最大坐标 */
    private static int max_x, max_y;

    final int STATE_MENU = 0;
    final int STATE_PLAY = 1;  //游戏开始标志位
    final int STATE_PAUSE = 2;//游戏暂停标志位
    final int STATE_OVER = 3; //游戏结束标志位

    public static final int MAX_LEVEL = 6;  //游戏等级

    public static final String TAG = "TetrisView";
    public static final String DATAFILE = "save.dt";

   public int mGamestate = STATE_PLAY;  //游戏默认状态为开始游戏

   public int mScore = 0;   // 得分
    public int mSpeed = 1;  //等级
    public int mDeLine = 0; //消去的行数

    public boolean mIsCombo = false; //方块是否落地标志
    public boolean mIsPaused = false;//游戏默认状态
    public boolean mIsVoice = true;  //音乐是否播放

    public long mMoveDelay = 600;  //移动延迟为0.6s
    public  long mLastMove = 0;


    public Context mContext = null;
    public Paint mPaint = new Paint();//游戏界面绘制

    public RefreshHandler mRefreshHandler = null;


    public TetrisBlock mCurrentTile = null;  //当前俄罗斯方块
    public TetrisBlock mNextTile = null;   //下一个出现的俄罗斯方块
    public TetrisBlock mFallingTile = null;   //正在下落的俄罗斯方块
    public  Court mCourt = null; //游戏进行区域
    public ResourceStore mResourceStore = null;

    /**
     * 设置当前游戏页面的父类activity
     *
     * @param tetrisActivityAW
     */
    public void setFather(ActivityGame tetrisActivityAW) {

        father = tetrisActivityAW;
    }

    public   MusicPlayer mMPlayer = null;

    public TetrisView(Context context) {
        super(context);
        init(context);
    }




    public TetrisView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);


    }


    public int getScreenHeight(){
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        return screenHeight;
    }

    public int getScreenWidth(){
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        return screenWidth;
    }

    protected void init(Context context) {
        mContext = context;

        SCREEN_WIDTH=  getScreenWidth();
        SCREEN_HEIGHT=  getScreenHeight();

        if (SCREEN_WIDTH<=600){
            SCREEN_WIDTH=  getScreenWidth();
            SCREEN_HEIGHT=  getScreenHeight()-240;

            Court.COURT_WIDTH= GameConf.small_width;
            Court.COURT_HEIGHT =  GameConf.small_height;
            Court.BLOCK_WIDTH=GameConf.small_block_width;

            LogUtil.i("sss","屏幕宽度小于600 ");

        }else {

            SCREEN_HEIGHT=  getScreenHeight()-500;


            Court. COURT_WIDTH=GameConf.big_width;
            Court.COURT_HEIGHT =GameConf.big_height;
            Court.BLOCK_WIDTH=GameConf.big_block_width;
            LogUtil.i("sss","屏幕宽度大于600 ");

        }

        LogUtil.i("screen","screenWidth"+SCREEN_WIDTH);




        LitePal.getDatabase();

        timeView=new TextView(context);


        mFallingTile=new TetrisBlock(context);  //正在下落的方块
        mNextTile = new TetrisBlock(context);   //下一个方块
        mCurrentTile = new TetrisBlock(context);




        mCourt = new Court(context);
        mRefreshHandler = new RefreshHandler(this);
        mResourceStore = new ResourceStore(context);

        ////////////////////////////////////////
        mMPlayer = new MusicPlayer(context);
        //
        setLevel(1);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        setFocusable(true);

        new Thread(this).start();
    }


    /**
     * RefreshHandler.handleMessage()方法会调用
     */
    public void logic() {
        switch (mGamestate) {
            case STATE_MENU:
                //
                mGamestate = STATE_PLAY;
                break;
            case STATE_PLAY:
                playGame();
                break;
            case STATE_PAUSE:
                break;
            case STATE_OVER:
                break;
            default:
                ;
        }
    }


    /**
     * 开始游戏
     */
    public void playGame() {
        long now = System.currentTimeMillis();
        if (now - mLastMove > mMoveDelay) {
            if (mIsPaused) {   //标志位是游戏暂停 的话   就直接返回
                return;
            }


            if (mIsCombo) {  //俄罗斯方块是否落地
                LogUtil.i("ff","方块落地"+mIsCombo);
                mCourt.placeTile(mCurrentTile);
                //方块落地播放音乐
                mMPlayer.playMoveVoice();

                if (mCourt.isGameOver()) {
                    mGamestate = STATE_OVER;
                    return;
                }

                //判断是否满行   如果满行的话就进行消除   返回满行的行数
                int line = mCourt.removeLines();
                if (line > 0) {
                    mMPlayer.playBombVoice();
                }
                mDeLine += line;//更新界面消除的行数
                countScore(line);//计算分数
                mCurrentTile = mNextTile;//将下一个方块赋值给当前界面要下落的方块
                mNextTile = new TetrisBlock(mContext);
                mIsCombo = false;
            }else {
                mFallingTile = mCurrentTile;//保存当前正在下落的方块
            }
            moveDown();
            mLastMove = now;
        }
    }


    /**
     * 计分
     * @param line  表示消去的行数
     */
    private void countScore(int line) {
        switch (line) {
            case 1:
                mScore += 100;
                break;
            case 2:
                mScore += 300;
                break;
            case 3:
                mScore += 600;
                break;
            case 4:
                mScore += 1000;
                break;
            default:
                break;
        }
        if (mScore >= 2000 && mScore < 4000) {
            setLevel(2);
        } else if (mScore >= 4000 && mScore < 6000) {
            setLevel(3);
        } else if (mScore >= 6000 && mScore < 8000) {
            setLevel(4);
        } else if (mScore >= 8000 && mScore < 10000) {
            setLevel(5);
        } else if (mScore >= 10000) {
            setLevel(6);
        }
    }


    /**
     * 默认执行
     * 绘制界面
     * @param canvas
     */
    protected void onDraw(Canvas canvas) {
        LogUtil.i("ff","Canvas"+mGamestate);
        switch (mGamestate) {
            case STATE_MENU:
//                paintMenu(canvas);
                break;
            case STATE_PLAY:

                paintGame(canvas);
                break;
            case STATE_PAUSE:
                paintPause(canvas);
                break;
            case STATE_OVER:
//                paintOver(canvas);
                gameOver();
                break;
            default:
                break;
        }
    }

    public boolean isGameOver() {

        return mCourt.isGameOver();
    }





    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                if (mGamestate == STATE_PLAY) {
                    if (!mIsPaused) {
                        rotate();
                        mMPlayer.playMoveVoice();
                    }
                } else if (mGamestate == STATE_PAUSE) {
                } else if (mGamestate == STATE_MENU) {

                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (mGamestate == STATE_PLAY) {
                    if (!mIsPaused) {
                        moveDown();
                        mMPlayer.playMoveVoice();
                    }
                } else if (mGamestate == STATE_PAUSE) {
                } else if (mGamestate == STATE_MENU) {

                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (mGamestate == STATE_PLAY) {
                    if (!mIsPaused) {
                        moveLeft();
                        mMPlayer.playMoveVoice();
                    }
                } else if (mGamestate == STATE_PAUSE) {
                } else if (mGamestate == STATE_MENU) {

                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (mGamestate == STATE_PLAY) {
                    if (!mIsPaused) {
                        moveRight();
                        mMPlayer.playMoveVoice();
                    }
                } else if (mGamestate == STATE_PAUSE) {
                } else if (mGamestate == STATE_MENU) {

                }
                break;
            case KeyEvent.KEYCODE_ENTER:
                ;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                if (mGamestate == STATE_PLAY) {
                    if (!mIsPaused) {
                        fastDrop();
                        mMPlayer.playMoveVoice();
                    }
                } else if (mGamestate == STATE_PAUSE) {
                } else if (mGamestate == STATE_MENU) {
                }
                break;
            //
            case KeyEvent.KEYCODE_S:
                if (mGamestate == STATE_PLAY) {
                    mIsPaused = true;
                } else if (mGamestate == STATE_PAUSE) {
                    mIsPaused = false;
                } else if (mGamestate == STATE_MENU) {

                }
                break;
            case KeyEvent.KEYCODE_SPACE:
                mIsPaused = !mIsPaused;
                if (mIsPaused) {
                    mRefreshHandler.pause();
                } else {
                    mRefreshHandler.resume();
                }
                break;

            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }












    /**
     * 用户类调用的旋转类
     */

    public void route(){

        if (mGamestate == STATE_PLAY) {
            if (!mIsPaused) {
                rotate();
                mMPlayer.playMoveVoice();
            }
        }
    }




    public void left(){

        if (mGamestate == STATE_PLAY) {
            if (!mIsPaused) {
                moveLeft();
                mMPlayer.playMoveVoice();
            }
        }
        postInvalidate();

    }


    public void right()
    {

        if (mGamestate == STATE_PLAY) {
            if (!mIsPaused) {
                moveRight();
                mMPlayer.playMoveVoice();
            }

        }
        postInvalidate();
    }


    /**
     * 用户类调用的快速下落功能
     */
    public void fastDorpMaster() {

        if (mGamestate == STATE_PLAY) {
            if (!mIsPaused) {
                fastDrop();
                mMPlayer.playMoveVoice();
            }

        }
    }





    public void rotate() {
        // check
        if (!mIsCombo)
            mCurrentTile.rotateOnCourt(mCourt);
        LogUtil.i("ff",""+mIsCombo);
    }

    public void moveDown() {
        if (!mIsCombo) {
            if (!mCurrentTile.moveDownOnCourt(mCourt))
                mIsCombo = true;  //

        }
    }

    public void moveLeft() {

        if (!mIsCombo) {
            mCurrentTile.moveLeftOnCourt(mCourt);
        }
    }

    public void moveRight() {
        if (!mIsCombo) {
            mCurrentTile.moveRightOnCourt(mCourt);

        }

    }


    private void fastDrop() {
        if (!mIsCombo) {
            mCurrentTile.fastDropOnCourt(mCourt);
            mIsCombo = true;
        }
    }

    private void paintMenu(Canvas canvas) {
        DrawTool.paintImage(canvas, mResourceStore.getMenuBackground(), 0, 0);
        DrawTool.paintImage(canvas, mResourceStore.getMenu(), 0, SCREEN_HEIGHT / 2 - mResourceStore.getMenu().getHeight() / 2);

    }

    /**
     * 绘制游戏界面区
     * @param canvas
     */
    private void paintGame(Canvas canvas) {
        mCourt.paintCourt(canvas);
        mCurrentTile.paintTile(canvas);   //主游戏的俄罗斯方块
//        mNextTile.paintTile(canvas);

        mPaint.setTextSize(20);
        paintNextTile(canvas);  //右上角的俄罗斯方块
        paintSpeed(canvas);
        paintScore(canvas);
        paintDeLine(canvas);
    }


    /**
     * 绘制右上角的俄罗斯方块
     * @param canvas
     */
    private void paintNextTile(Canvas canvas) {
        int i, j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                if (mNextTile.mTile[i][j] != 0) {
                    DrawTool.paintImage(canvas, mResourceStore.getBlock(mNextTile.getColor() - 1),
                            (int) (Court.BEGIN_DRAW_X + getBlockDistance(Court.COURT_WIDTH) + getBlockDistance((float) (i + 0.5))),
                            (int) (getBlockDistance((float) (j + 0.5)))
                    );
                }
            }
        }
    }

    private void paintSpeed(Canvas canvas) {
        mPaint.setColor(Color.BLUE);
        canvas.drawText("等级：", getBlockDistance(Court.COURT_WIDTH) + getRightMarginToCourt(), getBlockDistance(9), mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawText(String.valueOf(mSpeed), getBlockDistance(Court.COURT_WIDTH) + 2 * getRightMarginToCourt(), getBlockDistance(11), mPaint);
    }

    private void paintScore(Canvas canvas) {
        mPaint.setColor(Color.BLUE);
        canvas.drawText("得分:", getBlockDistance(Court.COURT_WIDTH) + getRightMarginToCourt(), getBlockDistance(13), mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawText(String.valueOf(mScore), getBlockDistance(Court.COURT_WIDTH) + 2 * getRightMarginToCourt(), getBlockDistance(15), mPaint);
    }

    private void paintDeLine(Canvas canvas) {


        mPaint.setColor(Color.BLUE);
        canvas.drawText("消去行数:", getBlockDistance(Court.COURT_WIDTH) + getRightMarginToCourt(), getBlockDistance(17), mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawText(String.valueOf(mDeLine), getBlockDistance(Court.COURT_WIDTH) + 2 * getRightMarginToCourt(), getBlockDistance(19), mPaint);
//        canvas.drawText(time, getBlockDistance(Court.COURT_WIDTH) + 2 * getRightMarginToCourt(), getBlockDistance(19), mPaint);


    }







    private float getBlockDistance(float blockNum) {

        return blockNum * Court.BLOCK_WIDTH;
    }

    private float getRightMarginToCourt()
    {
        return (float) 10.0;
    }

    private void paintPause(Canvas canvas) {

    }

    private void paintOver(Canvas canvas) {
        paintGame(canvas);

        father.timeutils.puseTimer();

        Paint paint = new Paint();
        paint.setTextSize(40);
        paint.setAntiAlias(true);
        paint.setARGB(0xe0, 0xff, 0x00, 0x00);
        canvas.drawText("Game Over", getBlockDistance(1), getBlockDistance(Court.COURT_HEIGHT / 2 - 2), paint);
        //DrawTool.paintImage(canvas,mResourceStore.getGameover(),0,SCREEN_HEIGHT/2 - mResourceStore.getGameover().getHeight()/2 );




    }





    private void gameOver(){
        father.timeutils.puseTimer();

        Intent intent=new Intent(father,GameOver.class);
        intent.putExtra("gameTime",father.timeutils.count);
        LogUtil.i("mm",""+father.timeutils.count);
        intent.putExtra("gameScore",mScore);
        father.startActivity(intent);



        father.finish();

    }





    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (!Thread.currentThread().isInterrupted()) {
            Message ms = new Message();
            ms.what = RefreshHandler.MESSAGE_REFRESH;
            this.mRefreshHandler.sendMessage(ms);
            try {
                Thread.sleep(/*RefreshHandler.DELAY_MILLIS*/mMoveDelay);  //即休眠时间   越小其速度感觉就越快
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }

    }


    public void setLevel(int level) {
        mSpeed = level;
        mMoveDelay = (long) (600 * (1.0 - (double) mSpeed / 7.0));
    }

    public void setVoice(boolean isVoice) {
        mIsVoice = isVoice;
        mMPlayer.setMute(!mIsVoice);
    }














    public void restoreGame() {
        Properties pro = new Properties();
        try {
            FileInputStream in = mContext.openFileInput(DATAFILE);
            pro.load(in);
            in.close();
        } catch (IOException e) {
            Log.i(TAG, "file open failed in restoreGame()");
            return;
        }

        mGamestate = Integer.valueOf(pro.get("gamestate").toString());
        mSpeed = Integer.valueOf(pro.get("speed").toString());
        setLevel(mSpeed);
        mScore = Integer.valueOf(pro.get("score").toString());
        mDeLine = Integer.valueOf(pro.get("deLine").toString());
        mIsVoice = Boolean.valueOf(pro.get("isVoice").toString());
        mIsCombo = Boolean.valueOf(pro.get("isCombo").toString());
        mIsPaused = Boolean.valueOf(pro.get("isPaused").toString());

        restoreCourt(pro);
        restoreTile(pro, mCurrentTile);
        restoreTile(pro, mNextTile);
        mNextTile.setOffsetY(0);
        mNextTile.setOffsetX((Court.COURT_WIDTH - 4) / 2 + 1);
        restoreTile(pro, mFallingTile);
    }

    private void restoreCourt(Properties pro) {
        int[][] matrix = mCourt.getMatrix();
        int i, j;
        for (i = 0; i < Court.COURT_WIDTH; i++) {
            for (j = 0; j < Court.COURT_HEIGHT; j++) {
                matrix[i][j] = Integer.valueOf(pro.get("courtMatrix" + i + j).toString());
            }
        }
    }

    private void restoreTile(Properties pro, TetrisBlock tile) {
        int[][] matrix = tile.getMatrix();
        int i, j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                matrix[i][j] = Integer.valueOf(pro.get("tileMatrix" + i + j).toString());
            }
        }
        tile.setColor(Integer.valueOf(pro.get("tileColor").toString()));
        tile.setShape(Integer.valueOf(pro.get("tileShape").toString()));
        tile.setOffsetX(Integer.valueOf(pro.get("tileOffsetX").toString()));
        tile.setOffsetY(Integer.valueOf(pro.get("tileOffsetY").toString()));
    }







    public void saveGame() {
        Properties pro = new Properties();

        pro.put("gamestate", String.valueOf(mGamestate));
        pro.put("speed", String.valueOf(mSpeed));
        pro.put("score", String.valueOf(mScore));
        pro.put("deLine", String.valueOf(mDeLine));
        Boolean b = new Boolean(mIsVoice);
        pro.put("isVoice", b.toString());
        b = new Boolean(mIsCombo);
        pro.put("isCombo", b.toString());
        b = new Boolean(mIsPaused);
        pro.put("isPaused", b.toString());

        saveCourt(pro);
        saveTile(pro, mCurrentTile);
        saveTile(pro, mNextTile);
        saveTile(pro, mFallingTile);

        try {
            FileOutputStream stream = mContext.openFileOutput(DATAFILE, Context.MODE_PRIVATE);
            pro.store(stream, "");
            stream.close();
        } catch (IOException e) {
            Log.i(TAG, "ioexeption in saveGame()");
            return;

        }

    }

    /**
     *
     * @param pro
     */

    private void saveCourt(Properties pro) {
        int[][] court = mCourt.getMatrix();
        int i, j;
        for (i = 0; i < Court.COURT_WIDTH; i++) {
            for (j = 0; j < Court.COURT_HEIGHT; j++) {
                pro.put("courtMatrix" + i + j, String.valueOf(court[i][j]));
            }
        }
    }

    /**
     *
     *  保存一个俄罗斯方块的所有坐标
     *
     * @param pro
     * @param tile
     */
    //getMatrix  返回当前存储俄罗斯方块的坐标
    private void saveTile(Properties pro, TetrisBlock tile) {
        int[][] matrix = tile.getMatrix();
        int i, j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                pro.put("tileMatrix" + i + j, String.valueOf(matrix[i][j]));
            }
        }
        pro.put("tileColor", String.valueOf(tile.getColor()));
        pro.put("tileShape", String.valueOf(tile.getShape()));
        pro.put("tileOffsetX", String.valueOf(tile.getOffsetX()));
        pro.put("tileOffsetY", String.valueOf(tile.getOffsetY()));
    }




    public void onPause() {
        mRefreshHandler.pause();
        mIsPaused = true;

    }


    public void onResume() {
        mRefreshHandler.resume();
        mIsPaused = false;
    }

    public void freeResources() {
        mMPlayer.free();
    }

































    /**
     * DB存储可以废弃   因为读写大量数据需要消耗手机大量内存
     * 同时保存的数据还是临时数据  需要随时删除 填写
     * 所以 文件存储更适合
     *
     * @param tile
     */


    private void saveTileByDb(Block block,TetrisBlock tile){

        int[][] matrix = tile.getMatrix();
        int i, j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
//                pro.put("tileMatrix" + i + j, String.valueOf(matrix[i][j]));
                Block   block1=new Block();
                block1.setTileMatrix(String.valueOf(matrix[i][j]));
                block1.setTitle(true);
                block1.save();

            }
        }

        block.setTileColor(tile.getColor());
        block.setTileShape(tile.getShape());
        block.setTileOffsetX(tile.getOffsetX());
        block.setTileOffsetY(tile.getOffsetY());
        block.setFlag(true);
        block.save();


    }


    private void saveCourtByDb(Block block){
        int[][] court = mCourt.getMatrix();
        int i, j;
        for (i = 0; i < Court.COURT_WIDTH; i++) {
            for (j = 0; j < Court.COURT_HEIGHT; j++) {
                Block   block1=new Block();
                block1.setCourtMatrix(String.valueOf(court[i][j]));
                block1.setCourt(true);
                block1.save();

            }
        }

    }

    public void saveGameByDb(){

        Block block=new Block();

        block.setGamestate(mSpeed);
        block.setSpeed(mGamestate);
        block.setScore(mScore);
        block.setDeLine(mDeLine);
        block.setCombo(mIsCombo);
        block.setVoice(mIsVoice);
        block.setPaused(mIsPaused);

        saveCourtByDb(block);
        saveTileByDb(block,mCurrentTile);
        saveTileByDb(block,mNextTile);
        saveTileByDb(block,mFallingTile);
        block.setFlag(true);
        block.save();
    }



    private void restoreTileByDb(Block block,TetrisBlock tile) {


        int[][] matrix = tile.getMatrix();
        int i, j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                matrix[i][j] =  Integer.valueOf(block.getTileMatrix());
                LogUtil.i("db",block.getTileMatrix());
            }
        }


        tile.setColor(block.getTileColor());
        tile.setShape(block.getTileShape());
        tile.setOffsetX(block.getTileOffsetX());
        tile.setOffsetY(block.getTileOffsetY());

    }


    private void restoreCourtByDb(Block block){

        int[][] matrix = mCourt.getMatrix();
        int i, j;
        for (i = 0; i < Court.COURT_WIDTH; i++) {
            for (j = 0; j < Court.COURT_HEIGHT; j++) {
                matrix[i][j] = Integer.valueOf(block.getCourtMatrix());
                LogUtil.i("db",block.getCourtMatrix());
            }
        }

    }


    public void restoreGameByDb() {
//           List<Block> blocks= DataSupport.findAll(Block.class);


        List<Block> Tileblocks=  DataSupport.select("tileMatrix").where("IsTitle=?","true").find(Block.class);

        List<Block> Courtblocks= DataSupport.select("courtMatrix").where("IsCourt=?","true").find(Block.class);

        List<Block> Mainblocks= DataSupport.where("Flag=?","true").find(Block.class);


        for ( Block      block   :Tileblocks){
            restoreTileByDb(block,mFallingTile);
            restoreTileByDb(block,mCurrentTile);
            restoreTileByDb(block,mNextTile);
        }

        for ( Block      block   :Courtblocks){

            restoreCourtByDb(block);
        }

        for ( Block      block   :Mainblocks) {

            mGamestate =block.getGamestate();
            mSpeed = block.getSpeed();
            setLevel(mSpeed);
            mScore =  block.getScore();
            mDeLine =  block.getDeLine();
            mIsVoice = block.isVoice();
            mIsCombo = block.isCombo();
            mIsPaused = block.isPaused();
            mNextTile.setOffsetY(0);
            mNextTile.setOffsetX((Court.COURT_WIDTH - 4) / 2 + 1);

//            LogUtil.i("db",block.getCourtMatrix());
//            LogUtil.i("db",block.getCourtMatrix());
//            LogUtil.i("db",""+block.getGamestate());
//            LogUtil.i("db",""+block.isCombo());
//            LogUtil.i("db",""+block.getSpeed());
//            LogUtil.i("db",block.getCourtMatrix());




        }




    }







}
