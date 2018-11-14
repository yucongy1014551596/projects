package com.example.yucong.lianliankan.util;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import android.util.Log;


import com.example.yucong.lianliankan.R;
import com.example.yucong.lianliankan.entity.PieceImage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class ImageUtils {

    public static List<Bitmap> skinImages = new ArrayList<Bitmap>(1);
    public static void setSkinImages(Context context){
        if(skinImages.size()==0)
        loadSkinImages(context);
        LogUtil.v("skinImages","skinImages.size()"+skinImages.size());
    }
    /**
     * 获取连连看图片ID资源s
     * @return
     */
    public static List<Integer> getResourced(Context context){
        setSkinImages(context);
        List<Integer> list=new ArrayList<Integer>();
        for (int i=0;i<skinImages.size();i++){
            list.add(i);
        }
        //LogUtil.i("idlist.size()","idlist.size():"+list.size()+"");
        return list;
    }

    //随机获取图片资源
    public static List<Integer> getRandomId(List<Integer> idlist,int size){
        List<Integer> list=new ArrayList<Integer>();
        Random random=new Random();
        for(int i=0;i<size;i++){
            int randomint=random.nextInt(idlist.size());
            Integer image=idlist.get(randomint);
            list.add(image);
        }
        return list;
    }
    //生成游戏所需图片集合
    public static List<Integer> getPlayValues(int size,Context context){
        if(size/2!=0)
            size+=1;
        List<Integer> list=getRandomId(getResourced(context),size/2);
        list.addAll(list);
        Collections.shuffle(list);
        return list;
    }
    //将图片资源转换成pieceimages
    public static List<PieceImage> getPlayImage(Context context, int size, GameConf gameConf){
        List<Integer> list=getPlayValues(size,context);
        List<PieceImage> pieceImages=new ArrayList<PieceImage>(size);
        for(Integer value:list){
            Bitmap bm=skinImages.get(value);
            bm=getnewBitmap(bm,gameConf.PIECE_WIDTH,gameConf.PIECE_HEIGHT);
            if(bm!=null) {
                PieceImage pieceImage = new PieceImage(bm, value);
                pieceImages.add(pieceImage);
            }
        }
        return pieceImages;
    }
    public static Bitmap getnewBitmap(Bitmap bm,int wsize,int hsize){
        int width=bm.getWidth();
        LogUtil.v("bitmap","bm.width:"+width);
        int height=bm.getHeight();
        LogUtil.v("bitmap","bm.height:"+height);
        float scaleWidth = ((float) wsize )/ width;
        float scaleHeight = ((float) hsize) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap mbitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return mbitmap;

    }

    //获取选中标识图片
    public static Bitmap getselectedima(Context context,GameConf gameConf){
        Bitmap bitmap=BitmapFactory.decodeResource(context.getResources(), R.drawable.selected);
        bitmap=getnewBitmap(bitmap,gameConf.PIECE_WIDTH,gameConf.PIECE_HEIGHT);
        return bitmap;
    }
    /**
     * 分割bitmap
     *
     * @param bm
     *            源图片
     * @param xcount
     *            水平个数
     * @param ycount
     *            垂直个数
     * @return 小图片数组
     */
    public static List<Bitmap> cutImage(Bitmap bm, int xcount, int ycount) {
        if (bm == null) {
            return null;
        }
        int width = bm.getWidth();
        int height = bm.getHeight();
        int imageWidth = width / xcount;
        int imageHeight = height / ycount;
        List<Bitmap> images = new ArrayList<Bitmap>(xcount * ycount);
        for (int i = 0; i < xcount; i++) {
            for (int j = 0; j < ycount; j++) {
                try {
                    images.add(Bitmap.createBitmap(bm, i * imageWidth, j * imageHeight, imageWidth, imageHeight));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return images;
    }
    protected static void loadSkinImages(Context context) {
        InputStream is = null;
        String imageFile = String.format("fishs.dat", GameConf.SkinName);
        try {
            is = context.getResources().getAssets().open(imageFile);
            Bitmap bm = BitmapFactory.decodeStream(is);
            skinImages = cutImage(bm, GameConf.ImageXCount, GameConf.ImageYCount);

        } catch (Exception e) {
            Log.d("loadSkinImages", e.getMessage());
        }


        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
