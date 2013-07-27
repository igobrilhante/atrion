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

    private Point point;
    private Point destinationPoint;
    private float   walkingDistance;

    public AtrionQuery(){}

    public AtrionQuery(Point point, float walkingDistance) {
        this.point              = point;
        this.walkingDistance    = walkingDistance;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setWalkingDistance(float walkingDistance) {
        this.walkingDistance = walkingDistance;
    }

    public Point getPoint() {
        return point;
    }

    public float getWalkingDistance() {
        return walkingDistance;
    }

    public Point getDestinationPoint() {
        return destinationPoint;
    }

    public void setDestinationPoint(Point destinationPoint) {
        this.destinationPoint = destinationPoint;
    }
}

