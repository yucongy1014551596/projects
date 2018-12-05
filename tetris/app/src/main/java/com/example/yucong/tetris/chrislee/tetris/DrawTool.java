package com.example.yucong.tetris.chrislee.tetris;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class DrawTool {

    /**
     *  加载图片工具类
     * @param canvas
     * @param bitmap
     * @param x
     * @param y
     */
    public static void paintImage(Canvas canvas, Bitmap bitmap, int x, int y) {
        canvas.drawBitmap(bitmap, x, y, null);
    }


}
