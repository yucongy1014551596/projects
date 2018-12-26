package com.example.yucong.tetris.chrislee.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.yucong.tetris.chrislee.tetris.util.GameConf;
import com.example.yucong.tetris.chrislee.tetris.util.LogUtil;

import org.litepal.crud.DataSupport;


/**
 * 組成俄罗斯方块
 *
 *
 * Tile 表示一个俄罗斯方块
 * mTile 相当于存储了一个俄罗斯方块的所有坐标信息
 *
 */



public class TetrisBlock {
    int[][] mTile = new int[4][4];
    Random mRand = null;
    int mColor = 1;   //那种颜色的方块
    int mShape = 0;   //类型
    int mOffsetX = (Court.COURT_WIDTH - 4) / 2 + 1;//在画布的中间位置
    int mOffsetY = 0;//Y坐标默认从0开始

    private Context mContext = null;
    private ResourceStore mRs = null;

    public TetrisBlock(Context context) {
        mContext = context;

        mRs = new ResourceStore(context);   //初始化加载图片资源

        init();
        Log.e("TetrisResource", "Courtblock->clearCourt()" + Court.COURT_HEIGHT + " width:"+Court.COURT_WIDTH+"size"+Court.BLOCK_WIDTH);
        Log.i("tetris", "init in TetrisBlock OK");
        // TODO Auto-generated constructor stub
    }

    private void init() {

        mRand = new Random();
        mShape = Math.abs(mRand.nextInt() % 28);// 0-27

        mColor = Math.abs(mRand.nextInt() % 8) + 1;//1 -8

        if (mTile == null) {
            return;
        }
        int i, j;
        //初始化俄罗斯方块样式
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                mTile[i][j] = TileStore.store[mShape][i][j];
            }
        }
    }


    /**
     * 判断是否可以旋转
     * @param court
     * @return
     */
    public boolean rotateOnCourt(Court court) {
        int tempX = 0, tempY = 0;
        int tempShape;
        int[][] tempTile = new int[4][4];

        tempShape = mShape;//当前方块的类型  0-27

      //每次旋转时  只选择当前类型的上一个类型的坐标进行旋转   上一个类型在这已经实例化好之后在将其坐标全部赴给将要旋转的这个俄罗斯方块 ,则实现了旋转
        if (tempShape % 4 > 0) {
            tempShape--;
        } else {
            tempShape += 3;
        }
        //初始化加载当前类型的所有坐标
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tempTile[i][j] = TileStore.store[tempShape][i][j];
            }
        }

        tempX = mOffsetX;
        tempY = mOffsetY;
        boolean canTurn = false;

        if (court.availableForTile(tempTile, tempX, tempY)) {
            canTurn = true;
        } else if (court.availableForTile(tempTile, tempX - 1, tempY)) {
            canTurn = true;
            tempX--;
        } else if (court.availableForTile(tempTile, tempX - 2, tempY)) {
            canTurn = true;
            tempX -= 2;
        } else if (court.availableForTile(tempTile, tempX + 1, tempY)) {
            canTurn = true;
            tempX++;
        } else if (court.availableForTile(tempTile, tempX + 2, tempY)) {
            canTurn = true;
            tempX += 2;
        }

        if (canTurn) {
            mShape = tempShape;
            mOffsetX = tempX;
            mOffsetY = tempY;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    mTile[i][j] = tempTile[i][j];
                }
            }
            return true;
        }
        return false;
    }


    /**
     * 向右移动
     * @param court
     * @return
     */

    public boolean moveRightOnCourt(Court court) {
        Log.i("tetris", "here is moveRightOnCourt");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (mTile[i][j] != 0) {
                    if (!court.isSpace(mOffsetX + i + 1, mOffsetY + j)) {
                        return false;//判断当前俄罗斯方块是否和其他方块接触
                    }
                }
            }
        }
        ++mOffsetX;
        return true;
    }

    /**
     * 向左移动
     * @param court
     * @return
     */


    public boolean moveLeftOnCourt(Court court) {
        LogUtil.i("ff","moveLeftOnCourt()方法执行了 ");
        int i, j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                if (mTile[i][j] != 0) {
                    if (!court.isSpace(mOffsetX + i - 1, mOffsetY + j)) {
                        return false;
                    }
                }
            }
        }

        LogUtil.i("ff","moveLeftOnCourtfinish()方法执行了 ");
        mOffsetX--;
        return true;
    }


    /**
     * 向下移动
     * 1没有接触到其他方块
     * 2 已到达底部不能移动
     * @param court
     * @return
     */
    public boolean moveDownOnCourt(Court court) {
        int i, j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                if (mTile[i][j] != 0) {
                    if (!court.isSpace(mOffsetX + i, mOffsetY + j + 1)
                            || isUnderBaseline(mOffsetY + j + 1)) {

                        return false;    //不能移动  已到达底部或者与其他相遇
                    }
                }
            }
        }
        mOffsetY++;
        return true;
    }


    /**
     * 快速下落
     * 1 如果当前画布为空   则将y的位置直接加COURT_HEIGHT  即可实现落地
     * 2 如果当前画布不为空  则减去已存在的正下方坐标 即可实现快速下落
     *
     *
     * @param court
     * @return
     */
    public boolean fastDropOnCourt(Court court) {
        int i, j, k;
        int step = Court.COURT_HEIGHT;  //获取画布的高度
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                if (mTile[i][j] != 0) {
                    for (k = mOffsetY + j; k < Court.COURT_HEIGHT; k++) {
                        if (!court.isSpace(mOffsetX + i, k + 1)
                                || isUnderBaseline(k + 1)) {
                            if (step > k - mOffsetY - j) {
                                step = k - mOffsetY - j;
                            }
                        }
                    }
                }
            }
        }
        mOffsetY += step;
        if (step > 0)
            return true;
        return false;
    }


    /**
     * 是否到达界面底部
     * @param posY
     * @return
     */
    private boolean isUnderBaseline(int posY) {
        if (posY >= Court.COURT_HEIGHT)
            return true;
        return false;

    }

    public int getOffsetX() {
        return mOffsetX;
    }

    public void setOffsetX(int offsetX) {
        mOffsetX = offsetX;
    }

    public int getOffsetY() {
        return mOffsetY;
    }

    public void setOffsetY(int offsetY) {
        mOffsetY = offsetY;
    }

    public int getColor() {
        return mColor;
    }


    /**
     * 画一种颜色的俄罗斯方块
     * @param canvas
     */
    public void paintTile(Canvas canvas) {
        ResourceStore rs = new ResourceStore(mContext);
        Log.e("ff", "TetrisBlock->paintTile()"+mOffsetY);
        Paint paint = new Paint();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (mTile[i][j] != 0) {
                    canvas.drawBitmap(mRs.getBlock(mColor - 1), Court.BEGIN_DRAW_X
                            + (i + mOffsetX) * Court.BLOCK_WIDTH, Court.BEGIN_DRAW_Y
                            + (j + mOffsetY) * Court.BLOCK_WIDTH, paint);
                }
            }
        }

    }

    /**
     * mTile 相当于存储了一个俄罗斯方块的所有坐标信息
     * 获取矩阵
     * @return
     */
    public int[][] getMatrix() {

        return mTile;
    }

    public int getShape() {
        return mShape;
    }

    public void setColor(int color) {
        mColor = color;

    }

    public void setShape(int shape) {
        mShape = shape;
    }

}
