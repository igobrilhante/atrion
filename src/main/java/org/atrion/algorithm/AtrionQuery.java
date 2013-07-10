package org.atrion.algorithm;

import org.atrion.geometry.Point;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 09/07/13
 * Time: 18:54
 * To change this template use File | Settings | File Templates.
 */
public class AtrionQuery {

    private final Point point;
    private final int   walkingDistance;
    private final int k;

    public AtrionQuery(Point point, int walkingDistance,int k) {
        this.point              = point;
        this.walkingDistance    = walkingDistance;
        this.k                  = k;
    }

    public Point getPoint() {
        return point;
    }

    public int getWalkingDistance() {
        return walkingDistance;
    }

    public int getK() {
        return k;
    }
}

