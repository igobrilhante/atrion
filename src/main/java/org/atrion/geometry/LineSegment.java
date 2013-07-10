package org.atrion.geometry;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 07/07/13
 * Time: 11:58
 * To change this template use File | Settings | File Templates.
 */
public class LineSegment {
    private final Point point1;
    private final Point point2;
//    private final float[] equation;

    public LineSegment(Point point1, Point point2) {
        this.point1   = point1;
        this.point2   = point2;
//        this.equation = findLineEquation(point1,point2);
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

//    private float[] findLineEquation(Point p1, Point p2){
//       float a = findA(p1,p2);
//       float c = findC(p1,a);
//       float b = findB(a,c,p1);
//
//       float[] equation = {a,b,c};
//
//       return equation;
//    }

//    private float findA(Point p1, Point p2){
//        float a = (float)(p2.getY()-p1.getY()) / (float)(p2.getX() - p1.getY());
//
//        return a;
//    }
//
//    private float findB(float a, float c, Point p){
//        float b = (float)(c*(-1) - a*p.getX())/(float)p.getY();
//
//        return b;
//    }
//
//    private float findC(Point p, float a){
//        float c = (float)p.getY() - a*(float)p.getX();
//        return c;
//    }
//
//    public float[] getEquation() {
//        return equation;
//    }
}
