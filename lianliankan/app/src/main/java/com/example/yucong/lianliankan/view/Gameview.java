package com.example.yucong.lianliankan.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import com.example.yucong.lianliankan.R;
import com.example.yucong.lianliankan.entity.Piece;
import com.example.yucong.lianliankan.service.GameService;
import com.example.yucong.lianliankan.util.GameConf;
import com.example.yucong.lianliankan.util.ImageUtils;
import com.example.yucong.lianliankan.util.LinkInfo;

import org.litepal.crud.DataSupport;

import java.util.List;

public class Gameview extends View{
    /**
     * 游戏逻辑类
     */
    private GameService gameService;
    /**
     * 画笔
     */
    private GameConf gameConf=new GameConf(this.getContext());
    private Paint paint;
    /**
     * 链接信息
     */
    private LinkInfo linkInfo;



    private  List<Piece> tishipiece;

    public List<Piece> getTishipiece() {
        return tishipiece;
    }

    public void setTishipiece(List<Piece> tishipiece) {
        this.tishipiece = tishipiece;
    }

    /**
     * 保存当前已被选中的方块
     */
    private Piece selectedpiece;
    public Piece getSelectedpiece() {
        return selectedpiece;
    }

    public void setSelectedpiece( Piece selectedpiece) {
        this.selectedpiece= selectedpiece;
    }
    /*
     * 被选中的图片
     */
    private Bitmap selectedMap;
     private Piece[][] pieces=null;

    public Piece[][] getPieces() {
        return pieces;
    }

    public void setPieces(Piece[][] pieces) {
        this.pieces = pieces;
    }

    public void setLinkInfo(LinkInfo linkInfo) {
        this.linkInfo = linkInfo;
    }

    public void setSelectedMap(Bitmap selectedMap) {
        this.selectedMap = selectedMap;
    }

    public Gameview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(context.getResources(), R.drawable.heart),
                Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        paint.setStrokeWidth(9);
        selectedMap = ImageUtils.getselectedima(context,gameConf);
    }

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        pieces = this.gameService.getPieces();
        if (this.gameService == null)
            return;
        if (pieces != null) {
            for (int i = 0; i < pieces.length; i++) {
                for (int j = 0; j < pieces[i].length; j++) {
                    if (pieces[i][j] != null) {
                        Piece p = pieces[i][j];
                        Log.d("density"," Piece = " + p.toString());
                        canvas.drawBitmap(p.getPieceImage().getBitmap(), p.getX(), p.getY(), null);
                    }
                }
            }


        }
        if (linkInfo != null) {
            drawLine(linkInfo, canvas);
            linkInfo = null;
        }
        if (selectedpiece!= null) {
            canvas.drawBitmap(selectedMap, selectedpiece.getX(), selectedpiece.getY(), null);
        }
        if(tishipiece!=null){
            for(Piece p:tishipiece){
                canvas.drawBitmap(selectedMap, p.getX(), p.getY(), null);
            }
        }

    }

    private void drawLine(LinkInfo linkInfo, Canvas canvas) {
        List<Point> pointList = linkInfo.getPointlist();
        if (pointList != null) {
            for (int i = 0; i < pointList.size() - 1; i++) {
                Point firstp = pointList.get(i);
                Point lastp = pointList.get(i + 1);
                canvas.drawLine(firstp.x, firstp.y, lastp.x, lastp.y, paint);
            }
        }
    }
    public void start(){
        gameService.start();
        postInvalidate();
    }


}
