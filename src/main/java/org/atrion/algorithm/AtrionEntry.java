package org.atrion.algorithm;

import org.atrion.entity.MovingObject;
import org.atrion.geometry.Point;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 09/07/13
 * Time: 18:55
 * To change this template use File | Settings | File Templates.
 */
public class AtrionEntry implements Comparable<AtrionEntry> {

    private final MovingObject movingObject;
    private final CandidatePoint point;
    private final double objectCost;
    private final double walkingCost;
    private final double totalCost;

    public AtrionEntry(MovingObject movingObject, CandidatePoint point, double objectCost, double walkingCost, double totalCost) {
        this.movingObject = movingObject;
        this.point = point;
        this.objectCost = objectCost;
        this.walkingCost = walkingCost;
        this.totalCost = totalCost;
    }

    public CandidatePoint getPoint() {
        return point;
    }

    public MovingObject getMovingObject() {
        return movingObject;
    }

    public double getObjectCost() {
        return objectCost;
    }

    public double getWalkingCost() {
        return walkingCost;
    }

    public double getTotalCost() {
        return totalCost;
    }


    @Override
    public int compareTo(AtrionEntry o) {
        return Double.compare(this.totalCost,o.totalCost);
    }

    @Override
    public String toString() {
        return "AtrionEntry{" +
                "movingObject=" + movingObject +
                ", point=" + point +
                ", objectCost=" + objectCost +
                ", walkingCost=" + walkingCost +
                ", totalCost=" + totalCost +
                '}';
    }
}
