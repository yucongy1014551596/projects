package com.example.yucong.lianliankan.entity;

import android.graphics.Bitmap;

import org.litepal.crud.DataSupport;

public class PieceImage extends DataSupport{
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Bitmap bitmap;
    private int imageId;

    public PieceImage() {
    }

    public PieceImage(int id, int imageId) {
        this.id = id;
        this.imageId = imageId;
    }

    public PieceImage(Bitmap bitmap, int imageId) {
        super();
        this.bitmap=bitmap;
        this.imageId=imageId;
    }
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
