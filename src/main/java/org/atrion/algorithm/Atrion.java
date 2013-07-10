package org.atrion.algorithm;

import org.atrion.distance.PointNodeDistance;
import org.atrion.distance.PointPointDistance;
import org.atrion.geometry.Point;
import org.atrion.graph.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 09/07/13
 * Time: 18:53
 * To change this template use File | Settings | File Templates.
 */
public class Atrion {

    private static final double CAR_SPEED     = 20.0;
    private static final double WALKING_SPEED = 0.0002;

    public AtrionRecommendation execute(AtrionQuery query,Collection<Point> objects) throws Exception {

        /*
            Result
         */
        List<AtrionEntry> tentativeSolution = new ArrayList<AtrionEntry>();
        /*
            Find cadidate points
         */
           Collection<CandidatePoint> candidatePoints = null;
        /*
            Search for close objects
         */

        PointNodeDistance pnd  = new PointNodeDistance();
        PointPointDistance ppd = new PointPointDistance();

         for(Point object : objects){
             for(CandidatePoint candidatePoint : candidatePoints){
                 Double distance = null;
                 Object obj = candidatePoint.getObject();

                 if(obj instanceof Point){
                      distance = ppd.invoke(object,(Point)obj);
                 }
                 else{
                     if(candidatePoint.getObject() instanceof Node){
                          distance = pnd.invoke(object,(Node)obj);
                     }
                     else{
                         throw new Exception("Invalid class: "+obj.getClass());
                     }
                 }

                 double objectCost  = distance / CAR_SPEED;
                 double walkingCost = candidatePoint.getDistance() / WALKING_SPEED;

                 if(objectCost > walkingCost){
                     AtrionEntry entry = new AtrionEntry(1,candidatePoint,objectCost,walkingCost,objectCost+walkingCost);
                     tentativeSolution.add(entry);
                     break;
                 }
             }

             AtrionRecommendation finalSolution = new AtrionRecommendation(tentativeSolution,query.getK());
         }


        return null;

    }
}
