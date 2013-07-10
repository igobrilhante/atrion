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
}
