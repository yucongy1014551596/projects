package com.example.yucong.lianliankan.util;

import android.content.Context;

import com.example.yucong.lianliankan.R;


public class GameConf {

    //图片宽度px
    public  int PIECE_WIDTH;
    //图片高度px
    public  int PIECE_HEIGHT;


    public static int DEFAULT_TIME =60;

    private  int xSize;

    private  int ySize;

    private int beginImageX;

    private int beginImageY;

    private long gameTime;

    private Context context;

    public static boolean ismusicPlay=true;

    public static boolean issoundplay=true;

    public static int[] TopOrderImages = new int[] { R.drawable.top_1, R.drawable.top_2, R.drawable.top_3, R.drawable.top_4, R.drawable.top_5,
            R.drawable.top_6};

    public static String SkinName = "fish";
    public static final int ImageXCount=4;
    public static final int ImageYCount=4;

    public GameConf(int xSize, int ySize,  long gameTime, Context context)
    {
        this.xSize = xSize;
        this.ySize = ySize;
        this.gameTime = gameTime;
        this.context = context;
        this.PIECE_HEIGHT = DensityUtil.getPiecewidth(context);
        LogUtil.v("Density","PIECE_HEIGHT:"+PIECE_HEIGHT);
        this.PIECE_WIDTH = DensityUtil.getPiecewidth(context);
        LogUtil.v("Density","PIECE_WIDTH:"+PIECE_WIDTH);
        int[] cents=DensityUtil.getCenter(context,PIECE_WIDTH*(xSize),PIECE_HEIGHT*(ySize));
        this.beginImageX = cents[0];
        LogUtil.v("Density","beginImageX :"+cents[0]);
        this.beginImageY = cents[1];
        LogUtil.v("Density","beginImageY :"+cents[1]);

    }
    public GameConf(Context context)
    {
        this.PIECE_HEIGHT = DensityUtil.getPiecewidth(context);
        LogUtil.v("Density","PIECE_HEIGHT:"+PIECE_HEIGHT);
        this.PIECE_WIDTH = DensityUtil.getPiecewidth(context);
        LogUtil.v("Density","PIECE_WIDTH:"+PIECE_WIDTH);
    }

    public long getGameTime()
    {
        return gameTime;
    }

    public int getXSize()
    {
        return xSize;
    }

    public int getYSize()
    {
        return ySize;
    }

    public int getBeginImageX()
    {
        return beginImageX;
    }

    public int getBeginImageY()
    {
        return beginImageY;
    }

    public Context getContext()
    {
        return context;
    }
}
