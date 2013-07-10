package org.atrion.distance;

import org.atrion.geometry.Point;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 07/07/13
 * Time: 12:51
 * To change this template use File | Settings | File Templates.
 */
public class EuclideanDistance {

    public double invoke(Point p1, Point p2){
        return  Math.sqrt(Math.pow(p1.getX()-p2.getX(),2) +
                Math.pow(p1.getY()-p2.getY(),2));
    }
}
