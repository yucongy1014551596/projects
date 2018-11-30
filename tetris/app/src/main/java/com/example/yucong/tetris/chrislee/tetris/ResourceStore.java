package com.example.yucong.tetris.chrislee.tetris;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import android.graphics.drawable.Drawable;

import com.example.yucong.tetris.R;

/**
 * 图片对象有关的资源
 */
public class ResourceStore {
    private static Bitmap mBackground = null;
    private static Bitmap[] mBlocks = null;
    private static Bitmap mMenuBackground = null;
    private static Bitmap mMenu = null;
    private static Bitmap mSpeed = null;
    private static Bitmap mLine = null;
    private static Bitmap mScore = null;
    private static Bitmap mGameover = null;

    private Context mContext = null;
    private Resources mR = null;


    public ResourceStore(Context context) {
        mContext = context;
        mR = mContext.getResources();
        if (mBackground == null)
            mBackground = createImage(mR.getDrawable(R.drawable.courtbg), Court.COURT_WIDTH * Court.BLOCK_WIDTH, TetrisView.SCREEN_HEIGHT);
        if (mMenuBackground == null)
            mMenuBackground = createImage(mR.getDrawable(R.drawable.menubg), TetrisView.SCREEN_WIDTH, TetrisView.SCREEN_HEIGHT);
        if (mMenu == null)
            mMenu = createImage(mR.getDrawable(R.drawable.menu), 200, 100);
        if (mSpeed == null)
            mSpeed = createImage(mR.getDrawable(R.drawable.speed), 200, 100);
        if (mLine == null)
            mLine = createImage(mR.getDrawable(R.drawable.line), 200, 100);
        if (mScore == null)
            mScore = createImage(mR.getDrawable(R.drawable.score), 200, 100);
        if (mGameover == null)
            mGameover = createImage(mR.getDrawable(R.drawable.gameover), 200, 100);
        if (mBlocks == null) {
            mBlocks = new Bitmap[8];
            for (int i = 0; i < 8; i++) {
                mBlocks[i] = createImage(mR.getDrawable(R.drawable.block0 + i), Court.BLOCK_WIDTH, Court.BLOCK_WIDTH);
            }
        }
    }

    public Bitmap getCourtBackground() {
        return mBackground;
    }

    public Bitmap getMenuBackground() {
        return mMenuBackground;
    }

    public Bitmap getMenu() {
        return mMenu;
    }

    public Bitmap getBlock(int index) {
        return mBlocks[index];
    }

    public Bitmap getGameover() {
        return mGameover;
    }

    /**
     * 加载单个俄罗斯方块图片
     * @param tile
     * @param w
     * @param h
     * @return  Bitmap  对象
     */
    public static Bitmap createImage(Drawable tile, int w, int h) {
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        tile.setBounds(0, 0, w, h);
        tile.draw(canvas);
        return bitmap;
    }
}
