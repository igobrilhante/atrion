package org.atrion.distance;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 26/07/13
 * Time: 14:11
 * To change this template use File | Settings | File Templates.
 */
public class Util {

    public static double meters2degree(double meters){
        double R_EARTH = 6371000;
        return (meters*(180/Math.PI/R_EARTH));
    }

    public static double degree2meters(double degree){
        double R_EARTH = 6371000;
        return (degree/(180/Math.PI/R_EARTH));
    }
}
