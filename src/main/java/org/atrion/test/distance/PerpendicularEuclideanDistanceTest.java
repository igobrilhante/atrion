package org.atrion.test.distance;

import org.atrion.distance.PerpendicularEuclideanDistance;
import org.atrion.geometry.LineSegment;
import org.atrion.geometry.Point;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 07/07/13
 * Time: 12:20
 * To change this template use File | Settings | File Templates.
 */
public class PerpendicularEuclideanDistanceTest {

    public static void main(String[] args){
//        # p1 = [1,1]
//        # p2 = [5,5]
//
//        # p = [1,5]
//
//        # l = createLine(p1,p2)
//
//        # print perpendicularDistance(p,l)

        Point p1 = new Point(1,1);
        Point p2 = new Point(5,5);

        Point p = new Point(10,10);

        LineSegment lineSegment = new LineSegment(p1,p2);

        PerpendicularEuclideanDistance d = new PerpendicularEuclideanDistance();
        System.out.println(d.invoke(p, lineSegment));

    }
}
