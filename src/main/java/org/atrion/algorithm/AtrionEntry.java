package org.atrion.algorithm;

import org.atrion.geometry.Point;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 09/07/13
 * Time: 18:55
 * To change this template use File | Settings | File Templates.
 */
public class AtrionEntry implements Comparable<AtrionEntry> {

    private final int objectID;
    private final CandidatePoint point;
    private final double objectCost;
    private final double walkingCost;
    private final double totalCost;

    public AtrionEntry(int objectID, CandidatePoint point, double objectCost, double walkingCost, double totalCost) {
        this.objectID = objectID;
        this.point = point;
        this.objectCost = objectCost;
        this.walkingCost = walkingCost;
        this.totalCost = totalCost;
    }

    public CandidatePoint getPoint() {
        return point;
    }

    public int getObjectID() {
        return objectID;
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
                "objectID=" + objectID +
                ", point=" + point +
                ", objectCost=" + objectCost +
                ", walkingCost=" + walkingCost +
                ", totalCost=" + totalCost +
                '}';
    }
}
