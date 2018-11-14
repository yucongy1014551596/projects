package com.example.yucong.lianliankan.util;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

public class LinkInfo {
    private List<Point> pointlist=new ArrayList<Point>();
    public LinkInfo(Point p1,Point p2){
        pointlist.add(p1);
        pointlist.add(p2);
    }
    public LinkInfo(Point p1,Point p2,Point p3){
        pointlist.add(p1);
        pointlist.add(p2);
        pointlist.add(p3);
    }
    public LinkInfo(Point p1,Point p2,Point p3,Point p4){
        pointlist.add(p1);
        pointlist.add(p2);
        pointlist.add(p3);
        pointlist.add(p4);
    }
    public List<Point> getPointlist(){
        return pointlist;
    }
}
