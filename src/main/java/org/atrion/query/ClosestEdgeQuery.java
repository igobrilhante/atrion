package org.atrion.query;

import org.atrion.distance.PerpendicularEuclideanDistance;
import org.atrion.geometry.Point;
import org.atrion.graph.Edge;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 07/07/13
 * Time: 13:11
 * To change this template use File | Settings | File Templates.
 */
public class ClosestEdgeQuery {

    public static Edge query(Collection<Edge> edges, Point point){

        double maxDistance = Double.POSITIVE_INFINITY;
        Edge result = null;
        PerpendicularEuclideanDistance ped = new PerpendicularEuclideanDistance();

        for(Edge edge : edges){
            double d = ped.invoke(point,edge.getLineSegment());
            if(d < maxDistance){
                maxDistance = d;
                result      = edge;
            }
        }
        System.out.println("Distance "+maxDistance);
        return result;
    }

}
