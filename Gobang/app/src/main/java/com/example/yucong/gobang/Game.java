package com.example.yucong.gobang;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by mrpeng on 18-9-28.
 */

public class Game {
    private long duration;//时长
    private int standpoint;//代表用户的执方
    private int lineNum;//可以用于设定棋盘大小
    private boolean blackTurn;//标志，true代表该黑方下棋
    private boolean isPlaying;//是否对局中

    private int winner;//最后的胜者
    private int[][] chessboard; //棋盘

    private final int COUNT = 5;//五子棋检查棋子连线的数量

    private final int WHITE = 2;
    private final int BLACK = 1;
    private final int EMPTY = 0;
    private final int INVALID = -1;

    private GameView gameView;
    private Context context;

    //显示设置前的对局视图
    public Game(Context context){
        gameView = new GameView(context);
        lineNum = 15;
        this.context = context;
        initBoard();
        blackTurn = true;//默认黑棋先行
    }

    //初始化棋盘
    private void initBoard() {
        //可以初始化的时候在实例化，不必要声明的时候就指定长度
        chessboard = new int[lineNum + 2][lineNum + 2];//四周加上一圈用于判断是都出界

        //真正的棋盘设为空
        for (int i = 1; i <= lineNum; i++)
            for (int j = 1; j <= lineNum; j++) {
                chessboard[i][j] = EMPTY;
            }

        //左右两边设为越界
        for (int i = 0; i < lineNum + 2; i++) {
            chessboard[i][0] = INVALID;
            chessboard[i][lineNum + 1] = INVALID;
        }

        //上下两边设为越界
        for (int j = 0; j < lineNum + 2; j++) {
            chessboard[0][j] = INVALID;
            chessboard[lineNum + 1][j] = INVALID;
        }
    }

    //恢复对局数据将要调用的构造函数，主要显示设置前的对局视图
    public Game(Context context, StringBuilder builder){
        gameView = new GameView(context);
        lineNum = 15;
        this.context = context;
        initBoard(builder);
    }

    //恢复对局数据时对棋盘的初始化
    private void initBoard(StringBuilder builder){
        chessboard = new int[lineNum + 2][lineNum + 2];//四周加上一圈用于判断是都出界
        for(int i=0;i<lineNum+2;i++){
            for(int j=0;j<lineNum+2;j++){
                chessboard[i][j] = Integer.parseInt(builder.charAt(i*(lineNum+2)+j)+"");
            }
        }
    }

    //初始化设置
    public void initSetting(long duration, int standpoint, int lineNum) {
        this.duration = duration;
        this.lineNum = lineNum;
        this.standpoint = standpoint;
        isPlaying = true;
        winner = EMPTY;
    }

    //region 内部类： GameView
    class GameView extends View {

        private float boardSide = BothSide.width;//棋盘的边长,获得的屏幕的值都比看得到的大
        private int gridNum = 14;//边长上的网格数
        private float gridSide;//网格边长
        private float startX = 0;
        private float startY = 0;//定位棋盘
        private float chessRadius;//棋子半径


        public GameView(Context context) {
            super(context);
            gridSide = boardSide / (gridNum + 2);
            chessRadius = (gridSide - 5) / 2;
        }

        @Override
        protected void onDraw(Canvas canvas) {

            canvas.drawColor(Color.parseColor("#CD9B1D"));//设置背景色

        /*画棋盘*/
            Paint paintBoard = new Paint();
            paintBoard.setColor(Color.BLACK);
            paintBoard.setStrokeWidth(2);
            paintBoard.setStyle(Paint.Style.STROKE);
            //画竖线
            for (int i = 1; i <= lineNum; i++) {
                canvas.drawLine(startX + i * gridSide, startY + gridSide, startX + i * gridSide, startY + boardSide -gridSide, paintBoard);
            }

            //画横线
            for (int j = 1; j <= lineNum; j++) {
                canvas.drawLine(startX + gridSide, startY + j * gridSide, startX + boardSide - gridSide, startY + j * gridSide, paintBoard);
            }

        /*画棋子*/
            Paint paintChess = new Paint();
            paintChess.setStyle(Paint.Style.FILL);
            paintChess.setStrokeWidth(2);

            for (int i = 1; i <= lineNum; i++) {
                for (int j = 1; j <= lineNum; j++) {
                    if (chessboard[i][j] == BLACK) {
                        paintChess.setColor(Color.BLACK);
                        canvas.drawCircle(startX + i * gridSide, startY + j * gridSide, chessRadius, paintChess);
                    }
                    if (chessboard[i][j] == WHITE){
                        paintChess.setColor(Color.WHITE);
                        canvas.drawCircle(startX + i * gridSide, startY + j * gridSide, chessRadius, paintChess);
                    }

                }
            }

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            //点击一次，执行两次，所以要取消一次
            int action = event.getAction();
            if(action!=MotionEvent.ACTION_DOWN)return true;

            float x = event.getX();
            float y = event.getY();

            //未在棋盘上落子
            if (x < startX || x > startX + 2 * gridSide + boardSide) return false;
            if (y < startY || y > startY + 2 * gridSide + boardSide) return false;

            //将棋盘上的位置转换为数组中的下标
            int putX = getPosition(x, startX);
            int putY = getPosition(y, startY);

            //落子不准确
            if (putX == 0 || putY == 0) return false;

            //落子
            if (chessboard[putX][putY] == EMPTY) {
                if (blackTurn) {
                    //放黑子
                    if(isWin(putX,putY,BLACK)){
                        winner = BLACK;
                        isPlaying = false;
                    }else{
                        blackTurn = false;
                    }
                } else {
                    //放白子
                    if(isWin(putX,putY,WHITE)){
                        winner = WHITE;
                        isPlaying = false;
                    }else{
                        blackTurn = true;
                    }
                }
            }

            //重绘
            invalidate();
            //重绘完再show结果
            if(winner!=EMPTY){
                showResult();
            }

            return true;
        }

        private int getPosition(float m, float start) {
            int pos = (int) ((m - start) / gridSide);
            float l1 = start + pos * gridSide;
            float l2 = l1 + gridSide;
            if (m - l1 > chessRadius) {
                if (l2 - m > chessRadius) return 0;
                else return pos + 1;
            } else {
                return pos;
            }
        }

    }
    //endregion

    //落子并判断是否五子成线
    public boolean isWin(int x, int y, int who) {

        int offset = 1;
        int count = 1;

        //落子
        chessboard[x][y] = who;

        //判断横
        while (chessboard[x + offset][y] == who) {
            count++;
            offset++;
        }
        offset = 1;
        while (chessboard[x - offset][y] == who) {
            count++;
            offset++;
        }
        if (count == COUNT) return true;
        else {
            count = 1;
            offset = 1;
        }

        //判断纵
        while (chessboard[x][y + offset] == who) {
            count++;
            offset++;
        }
        offset = 1;
        while (chessboard[x][y - offset] == who) {
            count++;
            offset++;
        }
        if (count == COUNT) return true;
        else {
            count = 1;
            offset = 1;
        }

        //判断撇
        while (chessboard[x + offset][y + offset] == who) {
            count++;
            offset++;
        }
        offset = 1;
        while (chessboard[x - offset][y - offset] == who) {
            count++;
            offset++;
        }
        if (count == COUNT) return true;
        else {
            count = 1;
            offset = 1;
        }

        //判断捺
        while (chessboard[x - offset][y + offset] == who) {
            count++;
            offset++;
        }
        offset = 1;
        while (chessboard[x + offset][y - offset] == who) {
            count++;
            offset++;
        }
        if (count == COUNT) return true;

        return false;
    }

    //显示最终结果
    public void showResult(){
        //对局结束
        String message ="";
        if(standpoint == winner){
            //说明用户获胜
            message += BothSide.Me.getAccount() + context.getString(R.string.winner);
            //上传游戏数据
            int grade = BothSide.Me.getGrade() +3;
            int win = BothSide.Me.getWin() +1;
            BothSide.Me.setGrade(grade);
            BothSide.Me.setWin(win);

            grade = BothSide.Him.getGrade() -1;
            int lose = BothSide.Him.getLose() +1;
            BothSide.Him.setGrade(grade);
            BothSide.Him.setLose(lose);
            uploadData(BothSide.Me.getId(),BothSide.Him.getId());
        }else{
            message += BothSide.Him.getAccount() + context.getString(R.string.loser);
            int grade = BothSide.Me.getGrade() -1;
            int lose = BothSide.Me.getLose() +1;
            BothSide.Me.setGrade(grade);
            BothSide.Me.setLose(lose);

            grade = BothSide.Him.getGrade() +3;
            int win = BothSide.Him.getLose() +1;
            BothSide.Him.setGrade(grade);
            BothSide.Him.setWin(win);
            uploadData(BothSide.Him.getId(),BothSide.Me.getId());
        }

        //为什么在这里使用context.this不行，使用context就可以？
        //因为context其实就是一个活动类的实例相当于活动类名.class
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(R.string.game_result);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //对局中的状态改变
                changeStatus();
                Intent intent = new Intent(context,Hall.class);
                context.startActivity(intent);
            }
        });
        dialog.show();
    }

    //对局结束后，恢复成观望中的状态
    private void changeStatus(){
        Uri uri = Uri.parse("content://com.example.mrpeng.gobang.provider/Online");
        ContentValues values = new ContentValues();
        values.put("isPlaying",0);
        context.getContentResolver().update(uri,values,"id=? or id=?",new String[]{BothSide.Me.getId()+"",BothSide.Him.getId()+""});
    }

    //对局结束把相关数据上传数据库
    private void uploadData(int winId, int loseId){
        {
            //插入棋盘数据
            Uri uri = Uri.parse("content://com.example.mrpeng.gobang.provider/Chess");
            ContentValues values = new ContentValues();
            if (standpoint == BLACK) {
                values.put("blackId", BothSide.Me.getId());
                values.put("whiteId", BothSide.Him.getId());
            } else {
                values.put("blackId", BothSide.Him.getId());
                values.put("whiteId", BothSide.Me.getId());
            }
            values.put("winId", winId);

            context.getContentResolver().insert(uri, values);
        }

        {
            //更改user积分情况
            Uri uri = Uri.parse("content://com.example.mrpeng.gobang.provider/User/" + BothSide.Me.getId());
            ContentValues values = new ContentValues();
            values.put("lose",BothSide.Me.getLose());
            values.put("win", BothSide.Me.getWin());
            values.put("grade", BothSide.Me.getGrade());
            context.getContentResolver().update(uri, values, null, null);
        }

        {
            //更改otherUser者积分情况
            Uri uri = Uri.parse("content://com.example.mrpeng.gobang.provider/User/" + BothSide.Him.getId());
            ContentValues values = new ContentValues();
            values.put("lose",BothSide.Him.getLose());
            values.put("win", BothSide.Him.getWin());
            values.put("grade", BothSide.Him.getGrade());
            context.getContentResolver().update(uri, values, null, null);
        }
    }

    public GameView getGameView() {
        return gameView;
    }

    //region set,get方法
    public void setWinner(int winner) {
        this.winner = winner;
    }

    public int getStandpoint() {
        return standpoint;
    }

    public void setStandpoint(int standpoint) {
        this.standpoint = standpoint;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public boolean isBlackTurn() {
        return blackTurn;
    }

    public void setBlackTurn(boolean blackTurn) {
        this.blackTurn = blackTurn;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public int[][] getChessboard() {
        return chessboard;
    }

    public void setChessboard(int[][] chessboard) {
        this.chessboard = chessboard;
    }
    //endregion
}
