package com.example.yucong.lianliankan.entity;

import android.graphics.Point;

import org.litepal.crud.DataSupport;


public class Piece extends DataSupport{
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private PieceImage pieceImage;
    private int x;
    private int y;
    private int arrayxx;//二维数组第一个值
    private int arrayxy;//二维数组第二个值

    public Piece() {

    }

    public Piece(int arrayxx, int arrayxy) {
        this.arrayxx = arrayxx;
        this.arrayxy = arrayxy;
    }


    public PieceImage getPieceImage() {
        return pieceImage;
    }

    public void setPieceImage(PieceImage pieceImage) {
        this.pieceImage = pieceImage;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getArrayxx() {
        return arrayxx;
    }

    public void setArrayxx(int arrayxx) {
        this.arrayxx = arrayxx;
    }

    public int getArrayxy() {
        return arrayxy;
    }

    public void setArrayxy(int arrayxy) {
        this.arrayxy = arrayxy;
    }
    public boolean isSamePiece(Piece otherP){
        if(pieceImage==null){
            if(otherP!=null)
                return false;
        }
        return this.pieceImage.getImageId()==otherP.pieceImage.getImageId();

    }
    public Point getCenterPoint(){
        return new Point(getX()+this.getPieceImage().getBitmap().getWidth()/2,getY()+this
                           .getPieceImage().getBitmap().getHeight()/2);
    }

    @Override
    public String toString() {
        return "Piece{" +
                "x=" + x +
                ", y=" + y +
                ", arrayxx=" + arrayxx +
                ", arrayxy=" + arrayxy +
                '}';
    }
}
