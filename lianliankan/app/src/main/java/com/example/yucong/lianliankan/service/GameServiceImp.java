package com.example.yucong.lianliankan.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;


import com.example.yucong.lianliankan.MainActivity;
import com.example.yucong.lianliankan.dal.AbstractBoard;
import com.example.yucong.lianliankan.dal.FullBoard;
import com.example.yucong.lianliankan.dal.HorizontalBoard;
import com.example.yucong.lianliankan.dal.VerticalBoard;
import com.example.yucong.lianliankan.entity.Piece;
import com.example.yucong.lianliankan.entity.PieceImage;
import com.example.yucong.lianliankan.util.GameConf;
import com.example.yucong.lianliankan.util.ImageUtils;
import com.example.yucong.lianliankan.util.LinkInfo;
import com.example.yucong.lianliankan.util.LogUtil;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class GameServiceImp implements GameService {
    private Piece[][] pieces;
    private GameConf gameConfl;

    public GameConf getGameConfl() {
        return gameConfl;
    }

    public GameServiceImp(GameConf gameConfl) {
        this.gameConfl = gameConfl;
    }

    @Override
    public void start() {
// 定义一个AbstractBoard对象
        AbstractBoard board = null;

        // 根据当前的级别，选择不同的布局。

       // int index = MainoneActivity.gameGlass;
       int index = MainActivity.gameGlass;

        // 随机生成AbstractBoard的子类实例
        switch (index)
        {
            case 1://第一关
                // 0返回VerticalBoard(竖向)
                board = new VerticalBoard();
                break;
            case 2://第二关
                // 1返回HorizontalBoard(横向)
                board = new HorizontalBoard();
                break;
            case 3 ://第三关
                // 默认返回FullBoard()
                board = new FullBoard();
                break;
        }
        // 初始化Piece[][]数组
        SharedPreferences sharedPreferences=gameConfl.getContext().getSharedPreferences("jilu", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        boolean jilu=sharedPreferences.getBoolean("jilu",false);
        if(jilu==false) {
            this.pieces = board.create(gameConfl);
        }
        else {
            this.pieces =restart(gameConfl,index);
            LogUtil.w("aaaa","aaaaaa"+hasPieces());
            if(!hasPieces())
                this.pieces = board.create(gameConfl);
            editor.putBoolean("jilu",false);
            editor.apply();
        }
    }
    private Piece[][] restart(GameConf gameConf,int index){
        //true很重要，关联查询关键
        List<Piece> repieces= DataSupport.findAll(Piece.class,true);
        Piece[][] oldpieces=new Piece[gameConf.getXSize()][gameConf.getYSize()];
        if(repieces!=null){
            for(int i=0;i<repieces.size();i++){
                if(repieces.get(i)!=null) {
                    Piece newpi = repieces.get(i);
                    PieceImage pieceImage=newpi.getPieceImage();
                    ImageUtils.setSkinImages(gameConf.getContext());
                    Bitmap bm= ImageUtils.skinImages.get(pieceImage.getImageId());
                    bm=ImageUtils.getnewBitmap(bm,gameConf.PIECE_WIDTH,gameConf.PIECE_HEIGHT);
                    pieceImage.setBitmap(bm);
                    newpi.setPieceImage(pieceImage);
                    oldpieces[newpi.getArrayxx()][newpi.getArrayxy()]=newpi;
                }
            }
        }
        return oldpieces;
    }

    @Override
    public Piece[][] getPieces() {
        return this.pieces;
    }

    public void setPieces(Piece[][] pieces) {
        this.pieces = pieces;
    }

    public void setGameConfl(GameConf gameConfl) {
        this.gameConfl = gameConfl;
    }

    @Override
    public boolean hasPieces() {
        // 遍历Piece[][]数组的每个元素
        for (int i = 0; i < pieces.length; i++)
        {
            for (int j = 0; j < pieces[i].length; j++)
            {
                // 只要任意一个数组元素不为null，也就是还剩有非空的Piece对象
                if (pieces[i][j] != null)
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Piece findPiece(float touchX, float touchY) {
        int relativex=(int)touchX-gameConfl.getBeginImageX();
        int relativey=(int)touchY-gameConfl.getBeginImageY();
        if(relativex<0||relativey<0){
            return null;
        }
        int indexx=getIndex(relativex,gameConfl.PIECE_WIDTH);
        int indexy=getIndex(relativey,gameConfl.PIECE_HEIGHT);
        if(indexx<0||indexy<0)
            return null;
        if(indexx>=gameConfl.getXSize()||indexy>=gameConfl.getYSize())
            return null;
        return this.pieces[indexx][indexy];
    }
    public int getIndex(int relative,int size){
        int index=-1;
        //点到边界线算前一个
        if(relative%size==0)
            index=relative/size-1;
        else
            index=relative/size;
        return index;
    }

    @Override
    public LinkInfo link(Piece p1, Piece p2) {
        if(p1.equals(p2))
            return null;
        if(p1.getPieceImage().getImageId()!=p2.getPieceImage().getImageId())
            return  null;
        if(p1.getX()>p2.getX())
            return link(p2,p1);
        Point po1=p1.getCenterPoint();
        Point po2=p2.getCenterPoint();
        //没有转折点
        //处于同一行
        if(p1.getY()==p2.getY()){
            //可以直接链接
            if(!isdirconY(po1,po2,gameConfl.PIECE_WIDTH))
                return new LinkInfo(po1,po2);
        }
        //处于同一列
        if(p1.getX()==p2.getX()){
            //可以直接连接
            if(!isdirconX(po1,po2,gameConfl.PIECE_HEIGHT))
                return new LinkInfo(po1,po2);
        }
        //有一个转折点
        Point onepoint=getOnePoint(po1,po2,gameConfl.PIECE_WIDTH,gameConfl.PIECE_HEIGHT);
        if(onepoint!=null)
            return new LinkInfo(po1,onepoint,po2);
        //有两个转折点
        Map<Point,Point> map=getTwoPoints(po1,po2,gameConfl.PIECE_WIDTH,gameConfl.PIECE_HEIGHT);
        if (map.size()!=0){
           LinkInfo twol=getShortLink(map,po1,po2);
            return twol;
        }

        return null;
    }
    //处于同一行是否可以直接连接
    private boolean isdirconY(Point p1,Point p2,int piecewidth){
        //p1在p2右边换位置
        if(p1.x>p2.x){
            return isdirconY(p2,p1,piecewidth);
        }

        for(int i=p1.x+piecewidth;i<p2.x;i=i+piecewidth) {
            //有障碍
            if (hasPiece(i,p1.y))
                return true;
        }
        return false;
    }
    //处于同一列是否可以直接连接
    private boolean isdirconX(Point p1,Point p2,int pieceheight){
        //p2在p1上边换位置
        if(p1.y>p2.y){
            return isdirconX(p2,p1,pieceheight);
        }
        //屏
        for(int i=p1.y+pieceheight;i<p2.y;i=i+pieceheight) {
            //有障碍
            if (hasPiece(p1.x,i))
                return true;
        }
        return false;
    }
    private boolean hasPiece(int x,int y){
        if(findPiece(x,y)==null)
            return false;
        return true;
    }
    /**
     * 判断point2是否在point1的左上角
     *
     * @param point1
     * @param point2
     * @return p2位于p1的左上角时返回true，否则返回false
     */
    private boolean isLeftUp(Point point1, Point point2)
    {
        return (point2.x < point1.x && point2.y < point1.y);
    }

    /**
     * 判断point2是否在point1的左下角
     *
     * @param point1
     * @param point2
     * @return p2位于p1的左下角时返回true，否则返回false
     */
    private boolean isLeftDown(Point point1, Point point2)
    {
        return (point2.x < point1.x && point2.y > point1.y);
    }

    /**
     * 判断point2是否在point1的右上角
     *
     * @param point1
     * @param point2
     * @return p2位于p1的右上角时返回true，否则返回false
     */
    private boolean isRightUp(Point point1, Point point2)
    {
        return (point2.x > point1.x && point2.y < point1.y);
    }

    /**
     * 判断point2是否在point1的右下角
     *
     * @param point1
     * @param point2
     * @return p2位于p1的右下角时返回true，否则返回false
     */
    private boolean isRightDown(Point point1, Point point2)
    {
        return (point2.x > point1.x && point2.y > point1.y);
    }

    private Point getOnePoint(Point p1,Point p2,int piecewidth,int pieceheight){
        if(isLeftDown(p1,p2)||isLeftUp(p1,p2))
            return getOnePoint(p2,p1,piecewidth,pieceheight);
        //获取p1右上下的通道
        List<Point> rightpoints1=getRightPoints(p1,p2.x,piecewidth);
        List<Point> uppoints1=getUpPoints(p1,p2.y,pieceheight);
        List<Point> downpoints1=getDownPoints(p1,p2.y,pieceheight);

        //获取p2左上下的通道
        List<Point> leftpoints2=getLeftPoints(p2,p1.x,piecewidth);
        List<Point> uppoints2=getUpPoints(p2,p1.y,pieceheight);
        List<Point> downpoints2=getDownPoints(p2,p1.y,pieceheight);
        //当p2位于p1左上角时
        if(isRightUp(p1,p2)){
            Point po1=getWrapPoint(rightpoints1,downpoints2);
            Point po2=getWrapPoint(uppoints1,leftpoints2);
            return (po1==null)?po2:po1;
        }
        //当p2位于p1右下角时
        if(isLeftDown(p1,p2)){
            Point po1=getWrapPoint(rightpoints1,uppoints2);
            Point po2=getWrapPoint(downpoints1,leftpoints2);
            return (po1==null)?po2:po1;
        }
        return null;
    }
    //获取向右的点的集合
    private List<Point> getRightPoints(Point point,int maxwidth,int piecewidth){
        List<Point> result=new ArrayList<Point>();
        for(int i=point.x+piecewidth;i<=maxwidth;i=i+piecewidth){
            if(hasPiece(i,point.y))
                return result;
            result.add(new Point(i,point.y));
        }
        return result;
    }
    //获取向左的点的集合
    private List<Point> getLeftPoints(Point point,int minwidth,int piecewidth){
        List<Point> result=new ArrayList<Point>();
        for(int i=point.x-piecewidth;i>=minwidth;i=i-piecewidth){
            if(hasPiece(i,point.y))
                return result;
            result.add(new Point(i,point.y));
        }
        return result;
    }
    //获取向上的点的集合
    private List<Point> getUpPoints(Point point,int minheight,int pieceheight){
        List<Point> result=new ArrayList<Point>();
        for(int i=point.y-pieceheight;i>=minheight;i=i-pieceheight){
            if(hasPiece(point.x,i))
                return result;
            result.add(new Point(point.x,i));
        }
        return result;
    }
    //获取向右的点的集合
    private List<Point> getDownPoints(Point point,int maxheight,int pieceheight){
        List<Point> result=new ArrayList<>();
        for(int i=point.y+pieceheight;i<=maxheight;i=i+pieceheight){
            if(hasPiece(point.x,i))
                return result;
            result.add(new Point(point.x,i));
        }
        return result;
    }
    private Point getWrapPoint(List<Point> points1,List<Point> points2){
        for(int i=0;i<points1.size();i++){
            Point p1=points1.get(i);
            for(int j=0;j<points2.size();j++){
                Point p2=points2.get(j);
                if(p1!=null&&p2!=null){
                    if(p1.y==p2.y&&p1.x==p2.x)
                        return p1;
                }
            }
        }
        return null;
    }
    /**
     * 获取两个转折点的情况
     *
     * @param point1
     * @param point2
     * @return Map对象的每个key-value对代表一种连接方式，
     *   其中key、value分别代表第1个、第2个连接点
     */
    private Map<Point, Point> getTwoPoints(Point point1, Point point2,
                                            int pieceWidth, int pieceHeight)
    {
        Map<Point, Point> result = new HashMap<Point, Point>();
        // 获取以point1为中心的向上, 向右, 向下的通道
        List<Point> p1UpChanel = getUpPoints(point1, point2.y, pieceHeight);
        List<Point> p1RightChanel = getRightPoints(point1, point2.x, pieceWidth);
        List<Point> p1DownChanel = getDownPoints(point1, point2.y, pieceHeight);
        // 获取以point2为中心的向下, 向左, 向上的通道
        List<Point> p2DownChanel = getDownPoints(point2, point1.y, pieceHeight);
        List<Point> p2LeftChanel = getLeftPoints(point2, point1.x, pieceWidth);
        List<Point> p2UpChanel = getUpPoints(point2, point1.y, pieceHeight);
        // 获取Board的最大高度
        int heightMax = (this.gameConfl.getYSize() + 1) * pieceHeight
                + this.gameConfl.getBeginImageY();
        // 获取Board的最大宽度
        int widthMax = (this.gameConfl.getXSize() + 1) * pieceWidth
                + this.gameConfl.getBeginImageX();
        // 先确定两个点的关系
        // point2在point1的左上角或者左下角
        if (isLeftUp(point1, point2) || isLeftDown(point1, point2))
        {
            // 参数换位, 调用本方法
            return getTwoPoints(point2, point1, pieceWidth, pieceHeight);
        }
        // p1、p2位于同一行不能直接相连
        if (point1.y == point2.y)
        {
            // 在同一行
            // 向上遍历
            // 以p1的中心点向上遍历获取点集合
            p1UpChanel = getUpPoints(point1, 0, pieceHeight);
            // 以p2的中心点向上遍历获取点集合
            p2UpChanel = getUpPoints(point2, 0, pieceHeight);
            Map<Point, Point> upLinkPoints = getXLinkPoints(p1UpChanel,
                    p2UpChanel, pieceHeight);
            // 向下遍历, 不超过Board(有方块的地方)的边框
            // 以p1中心点向下遍历获取点集合
            p1DownChanel = getDownPoints(point1, heightMax, pieceHeight);
            // 以p2中心点向下遍历获取点集合
            p2DownChanel = getDownPoints(point2, heightMax, pieceHeight);
            Map<Point, Point> downLinkPoints = getXLinkPoints(p1DownChanel,
                    p2DownChanel, pieceHeight);
            result.putAll(upLinkPoints);
            result.putAll(downLinkPoints);
        }
        // p1、p2位于同一列不能直接相连
        if (point1.x == point2.x)
        {
            // 在同一列
            // 向左遍历
            // 以p1的中心点向左遍历获取点集合
            List<Point> p1LeftChanel = getLeftPoints(point1, 0, pieceWidth);
            // 以p2的中心点向左遍历获取点集合
            p2LeftChanel = getLeftPoints(point2, 0, pieceWidth);

            Map<Point, Point> leftLinkPoints = getYLinkPoints(p1LeftChanel,
                    p2LeftChanel, pieceWidth);
            // 向右遍历, 不得超过Board的边框（有方块的地方）
            // 以p1的中心点向右遍历获取点集合
            p1RightChanel = getRightPoints(point1, widthMax, pieceWidth);
            // 以p2的中心点向右遍历获取点集合
            List<Point> p2RightChanel = getRightPoints(point2, widthMax,
                    pieceWidth);
            Map<Point, Point> rightLinkPoints = getYLinkPoints(p1RightChanel,
                    p2RightChanel, pieceWidth);
            result.putAll(leftLinkPoints);
            result.putAll(rightLinkPoints);

        }
        // point2位于point1的右上角
        if (isRightUp(point1, point2))
        {
            // 获取point1向上遍历, point2向下遍历时横向可以连接的点
            Map<Point, Point> upDownLinkPoints = getXLinkPoints(p1UpChanel,
                    p2DownChanel, pieceWidth);
            // 获取point1向右遍历, point2向左遍历时纵向可以连接的点
            Map<Point, Point> rightLeftLinkPoints = getYLinkPoints(
                    p1RightChanel, p2LeftChanel, pieceHeight);
            // 获取以p1为中心的向上通道
            p1UpChanel = getUpPoints(point1, 0, pieceHeight);
            // 获取以p2为中心的向上通道
            p2UpChanel = getUpPoints(point2, 0, pieceHeight);
            // 获取point1向上遍历, point2向上遍历时横向可以连接的点
            Map<Point, Point> upUpLinkPoints = getXLinkPoints(p1UpChanel,
                    p2UpChanel, pieceWidth);
            // 获取以p1为中心的向下通道
            p1DownChanel = getDownPoints(point1, heightMax, pieceHeight);
            // 获取以p2为中心的向下通道
            p2DownChanel = getDownPoints(point2, heightMax, pieceHeight);
            // 获取point1向下遍历, point2向下遍历时横向可以连接的点
            Map<Point, Point> downDownLinkPoints = getXLinkPoints(p1DownChanel,
                    p2DownChanel, pieceWidth);
            // 获取以p1为中心的向右通道
            p1RightChanel = getRightPoints(point1, widthMax, pieceWidth);
            // 获取以p2为中心的向右通道
            List<Point> p2RightChanel = getRightPoints(point2, widthMax,
                    pieceWidth);
            // 获取point1向右遍历, point2向右遍历时纵向可以连接的点
            Map<Point, Point> rightRightLinkPoints = getYLinkPoints(
                    p1RightChanel, p2RightChanel, pieceHeight);
            // 获取以p1为中心的向左通道
            List<Point> p1LeftChanel = getLeftPoints(point1, 0, pieceWidth);
            // 获取以p2为中心的向左通道
            p2LeftChanel = getLeftPoints(point2, 0, pieceWidth);
            // 获取point1向左遍历, point2向右遍历时纵向可以连接的点
            Map<Point, Point> leftLeftLinkPoints = getYLinkPoints(p1LeftChanel,
                    p2LeftChanel, pieceHeight);
            result.putAll(upDownLinkPoints);
            result.putAll(rightLeftLinkPoints);
            result.putAll(upUpLinkPoints);
            result.putAll(downDownLinkPoints);
            result.putAll(rightRightLinkPoints);
            result.putAll(leftLeftLinkPoints);
        }
        // point2位于point1的右下角
        if (isRightDown(point1, point2))
        {
            // 获取point1向下遍历, point2向上遍历时横向可连接的点
            Map<Point, Point> downUpLinkPoints = getXLinkPoints(p1DownChanel,
                    p2UpChanel, pieceWidth);
            // 获取point1向右遍历, point2向左遍历时纵向可连接的点
            Map<Point, Point> rightLeftLinkPoints = getYLinkPoints(
                    p1RightChanel, p2LeftChanel, pieceHeight);
            // 获取以p1为中心的向上通道
            p1UpChanel = getUpPoints(point1, 0, pieceHeight);
            // 获取以p2为中心的向上通道
            p2UpChanel = getUpPoints(point2, 0, pieceHeight);
            // 获取point1向上遍历, point2向上遍历时横向可连接的点
            Map<Point, Point> upUpLinkPoints = getXLinkPoints(p1UpChanel,
                    p2UpChanel, pieceWidth);
            // 获取以p1为中心的向下通道
            p1DownChanel = getDownPoints(point1, heightMax, pieceHeight);
            // 获取以p2为中心的向下通道
            p2DownChanel = getDownPoints(point2, heightMax, pieceHeight);
            // 获取point1向下遍历, point2向下遍历时横向可连接的点
            Map<Point, Point> downDownLinkPoints = getXLinkPoints(p1DownChanel,
                    p2DownChanel, pieceWidth);
            // 获取以p1为中心的向左通道
            List<Point> p1LeftChanel = getLeftPoints(point1, 0, pieceWidth);
            // 获取以p2为中心的向左通道
            p2LeftChanel = getLeftPoints(point2, 0, pieceWidth);
            // 获取point1向左遍历, point2向左遍历时纵向可连接的点
            Map<Point, Point> leftLeftLinkPoints = getYLinkPoints(p1LeftChanel,
                    p2LeftChanel, pieceHeight);
            // 获取以p1为中心的向右通道
            p1RightChanel = getRightPoints(point1, widthMax, pieceWidth);
            // 获取以p2为中心的向右通道
            List<Point> p2RightChanel = getRightPoints(point2, widthMax,
                    pieceWidth);
            // 获取point1向右遍历, point2向右遍历时纵向可以连接的点
            Map<Point, Point> rightRightLinkPoints = getYLinkPoints(
                    p1RightChanel, p2RightChanel, pieceHeight);
            result.putAll(downUpLinkPoints);
            result.putAll(rightLeftLinkPoints);
            result.putAll(upUpLinkPoints);
            result.putAll(downDownLinkPoints);
            result.putAll(leftLeftLinkPoints);
            result.putAll(rightRightLinkPoints);
        }
        return result;
    }
    /**
     * 遍历两个集合, 先判断第一个集合的元素的x座标与另一个集合中的元素x座标相同(纵向),
     * 如果相同, 即在同一列, 再判断是否有障碍, 没有则加到结果的Map中去
     *
     * @param p1Chanel
     * @param p2Chanel
     * @param pieceHeight
     * @return
     */
    private Map<Point, Point> getYLinkPoints(List<Point> p1Chanel,
                                             List<Point> p2Chanel, int pieceHeight)
    {
        Map<Point, Point> result = new HashMap<Point, Point>();
        for (int i = 0; i < p1Chanel.size(); i++)
        {
            Point temp1 = p1Chanel.get(i);
            for (int j = 0; j < p2Chanel.size(); j++)
            {
                Point temp2 = p2Chanel.get(j);
                // 如果x座标相同(在同一列)
                if (temp1.x == temp2.x)
                {
                    // 没有障碍, 放到map中去
                    if (!isdirconX(temp1, temp2, pieceHeight))
                    {
                        result.put(temp1, temp2);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 遍历两个集合, 先判断第一个集合的元素的y座标与另一个集合中的元素y座标相同(横向),
     * 如果相同, 即在同一行, 再判断是否有障碍, 没有 则加到结果的map中去
     *
     * @param p1Chanel
     * @param p2Chanel
     * @param pieceWidth
     * @return 存放可以横向直线连接的连接点的键值对
     */
    private Map<Point, Point> getXLinkPoints(List<Point> p1Chanel,
                                             List<Point> p2Chanel, int pieceWidth)
    {
        Map<Point, Point> result = new HashMap<>();
        for (int i = 0; i < p1Chanel.size(); i++)
        {
            // 从第一通道中取一个点
            Point temp1 = p1Chanel.get(i);
            // 再遍历第二个通道, 看下第二通道中是否有点可以与temp1横向相连
            for (int j = 0; j < p2Chanel.size(); j++)
            {
                Point temp2 = p2Chanel.get(j);
                // 如果y座标相同(在同一行), 再判断它们之间是否有直接障碍
                if (temp1.y == temp2.y)
                {
                    if (!isdirconY(temp1, temp2, pieceWidth))
                    {
                        // 没有障碍则直接加到结果的map中
                        result.put(temp1, temp2);
                    }
                }
            }
        }
        return result;
    }

    private int getDistance(Point p1,Point p2){
        int xdis=Math.abs(p1.x-p2.x);
        int ydis=Math.abs(p1.y-p2.y);
        return xdis+ydis;
    }
    private LinkInfo countshortDis(List<LinkInfo> linkInfos){
        int temp=0;
        LinkInfo shortlink=null;
        for(int i=0;i<linkInfos.size();i++){
            LinkInfo linkInfo=linkInfos.get(i);
            List<Point> pointList=linkInfo.getPointlist();
            int shortdis=0;
            for(int j=0;j<pointList.size()-1;j++){
                shortdis+=getDistance(pointList.get(j),pointList.get(j+1));
            }
            if(i==0){
                temp=shortdis;
                shortlink=linkInfo;
            }else{
                if(temp>shortdis){
                    temp=shortdis;
                    shortlink=linkInfo;
                }
            }
        }
        return shortlink;
    }
    private LinkInfo getShortLink(Map<Point,Point> map,Point p1,Point p2){
        LinkInfo shortlink=null;
        List<LinkInfo> linkInfos=new ArrayList<>();
        Iterator<Map.Entry<Point,Point>> its=map.entrySet().iterator();
        while(its.hasNext()){
            Map.Entry<Point,Point> it = its.next();
            Point po1=it.getKey();
            Point po2=it.getValue();
            LinkInfo linkInfo=new LinkInfo(p1,po1,po2,p2);
            linkInfos.add(linkInfo);
        }

        shortlink=countshortDis(linkInfos);

        return shortlink;
    }

}

