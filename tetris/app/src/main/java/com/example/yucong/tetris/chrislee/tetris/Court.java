package com.example.yucong.tetris.chrislee.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;


/**
 *
 * 游戏界面
 *
 */


public class Court {
    public final static int COURT_WIDTH = 11;
    public final static int COURT_HEIGHT = 23 + 4;

    public final static int BLOCK_WIDTH = 60;
    public final static int BLOCK_HEIGHT = 60;

    public final static int ABOVE_VISIBLE_TOP = 4;

    public final static int BEGIN_DRAW_X = 0;
    public final static int BEGIN_DRAW_Y = TetrisView.SCREEN_HEIGHT - Court.BLOCK_WIDTH * Court.COURT_HEIGHT;


    private int[][] mCourt = new int[COURT_WIDTH][COURT_HEIGHT];
    private Context mContext = null;

    private ResourceStore mRs = null;

    public Court(Context context) {
        mContext = context;

        mRs = new ResourceStore(context);

        clearCourt();

    }


    /**
     * 清空画布
     */

    public void clearCourt() {
        int i, j;
        for (i = 0; i < COURT_WIDTH; i++) {
            for (j = 0; j < COURT_HEIGHT; j++) {
                mCourt[i][j] = 0;
            }
        }
    }


    /**
     * 判断游戏结束
     * @return
     */

    public boolean isGameOver() {

        for (int i = 0; i < COURT_WIDTH; i++) {
            if (mCourt[i][ABOVE_VISIBLE_TOP] != 0)
                return true;    //到达顶部   游戏结束
        }
        return false;

    }


    /**
     * 画布是否为空
     * @param posX
     * @param posY
     * @return
     */

    public boolean isSpace(int posX, int posY) {
        if (posX < 0 || posX >= COURT_WIDTH)
            return false;
        if (posY < 0 || posY >= COURT_HEIGHT)
            return false;
        if (0 == mCourt[posX][posY])
            return true;
        return false;
    }

    public boolean availableForTile(int[][] tile, int x, int y) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tile[i][j] != 0) {
                    if (!isSpace(x + i, y + j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    /**
     * 将当前的俄罗斯方块添加到画布中
     * @param tile
     */
    public void placeTile(TetrisBlock tile) {
        int i, j;
        for (i = 0; i < 4; i++) {
            for (j = 0; j < 4; j++) {
                if (tile.mTile[i][j] != 0) {
                    mCourt[tile.getOffsetX() + i][tile.getOffsetY() + j] = tile.getColor();
                }
            }
        }
    }


    /**
     * 移除满行方块
     * @return
     */
    public int removeLines() {
        int high = 0;
        int low = COURT_HEIGHT;

        high = highestFullRowIndex();
        low = lowestFullRowIndex();
        int lineCount = low - high + 1;
        if (lineCount > 0) {
            eliminateRows(high, lineCount);
            return lineCount;
        }
        return 0;

    }

    /**
     * 消除多行的俄罗斯方块
     * @param highRow
     * @param rowAmount
     */
    private void eliminateRows(int highRow, int rowAmount) {
        int i, j;
        for (i = highRow + rowAmount - 1; i >= rowAmount; i--) {
            for (j = 0; j < COURT_WIDTH; j++) {
                mCourt[j][i] = mCourt[j][i - rowAmount];
            }
        }
    }

    /**
     * 获取多个满行的最上面一行的索引
     * @return
     */

    private int highestFullRowIndex() {
        int result = 0;
        boolean removeable = true;
        int i, j;
        for (i = 0; i < COURT_HEIGHT; i++) {
            removeable = true;
            for (j = 0; j < COURT_WIDTH && removeable; j++) {
                if (isSpace(j, i)) {
                    result++;
                    removeable = false;
                }
            }
            if (removeable)
                break;
        }
        return result;
    }
    /**
     * 获取多个满行的最下面一行的索引
     * @return
     */

    private int lowestFullRowIndex() {
        int result = COURT_HEIGHT - 1;
        boolean removeable = true;
        int i, j;
        for (i = COURT_HEIGHT - 1; i >= 0; i--) {
            removeable = true;
            for (j = 0; j < COURT_WIDTH && removeable; j++) {
                if (isSpace(j, i)) {
                    result--;
                    removeable = false;
                }
            }
            if (removeable)
                break;
        }
        return result;
    }


    /**
     * 移除一行
     * @param lineIndex
     * @param time
     */
    private void removeSingleLine(int lineIndex, int time) {
        int t, i, j;
        for (t = 0; t < time; t++) {
            for (i = lineIndex; i > 0; i--) {
                for (j = 0; j < COURT_WIDTH; j++) {
                    mCourt[j][i] = mCourt[j][i - 1];
                }
            }

        }
    }

    /**
     *
     * @param canvas
     */
    public void paintCourt(Canvas canvas) {

        Paint paint = new Paint();
        paint.setAlpha(0x60);
        canvas.drawBitmap(mRs.getCourtBackground(), 0, 0, paint);
        paint.setAlpha(0xee);
        for (int i = 0; i < Court.COURT_WIDTH; i++) {
            for (int j = 0; j < Court.COURT_HEIGHT; j++) {
                if (mCourt[i][j] != 0) {
                    canvas.drawBitmap(mRs.getBlock(mCourt[i][j] - 1), BEGIN_DRAW_X + i
                            * Court.BLOCK_WIDTH, BEGIN_DRAW_Y + j * Court.BLOCK_WIDTH, paint);
                }
            }
        }

    }

    /**
     * 获取画布范围内的矩形坐标
     * @return
     */

    public int[][] getMatrix() {
        return mCourt;
    }

}
