package org.atrion.distance;

import org.atrion.geometry.LineSegment;
import org.atrion.geometry.Point;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 07/07/13
 * Time: 11:57
 * To change this template use File | Settings | File Templates.
 */


public class PerpendicularEuclideanDistance {

    private Point projectedPoint;

    public double invoke(Point point,LineSegment lineSegment){
        EuclideanDistance ed = new EuclideanDistance();

        Point p1 = lineSegment.getPoint1();
        Point p2 = lineSegment.getPoint2();

        double L =  ed.invoke(p1,p2);

        double a =
                (point.getX() - p1.getX())*(p2.getX() - p1.getX()) +
                (point.getY() - p1.getY())*(p2.getY()-p1.getY());
        double b = a / (L*L);

        double distance = 1d;
        if(b < 0.00000000001 || b > 1){
            double d1 = ed.invoke(p1,point);
            double d2 = ed.invoke(p2,point);

            if(d1>d2){
                distance = d2;
                this.projectedPoint = p2;
            }
            else{
                distance = d1;
                this.projectedPoint = p1;
            }
        }
        else{
            Point pc = interpolate(b, lineSegment);
            distance = ed.invoke(point,pc);

            this.projectedPoint = pc;
        }


        return distance;
    }

    public Point getProjectedPoint(){
        return this.projectedPoint;
    }

    private Point interpolate(double a, LineSegment lineSegment){
        double px = lineSegment.getPoint1().getX() + a*(lineSegment.getPoint2().getX() - lineSegment.getPoint1().getX());
        double py = lineSegment.getPoint1().getY() + a*(lineSegment.getPoint2().getY() - lineSegment.getPoint1().getY());

        return new Point(px,py);
    }

}
