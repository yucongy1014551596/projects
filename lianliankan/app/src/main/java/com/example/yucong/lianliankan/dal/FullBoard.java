package com.example.yucong.lianliankan.dal;




import com.example.yucong.lianliankan.entity.Piece;

import java.util.ArrayList;
import java.util.List;

public class FullBoard extends AbstractBoard {
    @Override
    public List<Piece> createPiece(Piece[][] piece) {
        List<Piece> pieceList=new ArrayList<Piece>();
        for(int i=0;i<piece.length;i++){
            for(int j=0;j<piece[i].length;j++){
                Piece p=new Piece(i,j);
                pieceList.add(p);
            }
        }
        return pieceList;
    }
}
