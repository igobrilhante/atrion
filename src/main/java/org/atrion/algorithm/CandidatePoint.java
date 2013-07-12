package org.atrion.algorithm;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 09/07/13
 * Time: 19:14
 * To change this template use File | Settings | File Templates.
 */
public class CandidatePoint {

    private final double distance;
    private final Object object;
    private double destinationDistance;

    public CandidatePoint(double distance, Object object) {
        this.distance = distance;
        this.object = object;
    }

    public double getDistance() {
        return distance;
    }

    public Object getObject() {
        return object;
    }

    public double getDestinationDistance() {
        return destinationDistance;
    }

    public void setDestinationDistance(double destinationDistance) {
        this.destinationDistance = destinationDistance;
    }

    @Override
    public String toString() {
        return "CandidatePoint{" +
                "distance=" + distance +
                ", object=" + object +
                ", destinationDistance=" + destinationDistance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CandidatePoint that = (CandidatePoint) o;

        if (!object.equals(that.object)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return object.hashCode();
    }
}
