package com.example.yucong.lianliankan.dal;



import com.example.yucong.lianliankan.entity.Piece;
import com.example.yucong.lianliankan.entity.PieceImage;
import com.example.yucong.lianliankan.util.GameConf;
import com.example.yucong.lianliankan.util.ImageUtils;

import java.util.List;

public abstract class AbstractBoard {
    public abstract List<Piece> createPiece(Piece[][] piece);

    public Piece[][] create(GameConf gameConf){
        Piece[][] pieces=new Piece[gameConf.getXSize()][gameConf.getYSize()];
        List<Piece> piecelist=createPiece(pieces);
        List<PieceImage> pieceImageList= ImageUtils.getPlayImage(gameConf.getContext(),piecelist.size(),gameConf);
        int imagewidth=pieceImageList.get(0).getBitmap().getWidth();
        int imageheigth=pieceImageList.get(0).getBitmap().getHeight();
        //LogUtil.w("pieceImageList","imageheigth"+imageheigth);
        for(int i=0;i<piecelist.size();i++){
            Piece piece= piecelist.get(i);
            piece.setPieceImage(pieceImageList.get(i));
            piece.setX(gameConf.getBeginImageX()+piece.getArrayxx()*imagewidth);
            piece.setY(gameConf.getBeginImageY()+piece.getArrayxy()*imageheigth);
            pieces[piece.getArrayxx()][piece.getArrayxy()]=piece;
        }
        return pieces;
    }
}
