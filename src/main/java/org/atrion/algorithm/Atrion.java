package org.atrion.algorithm;

import org.atrion.astar.PathCollection;
import org.atrion.distance.EuclideanDistance;
import org.atrion.distance.PointNodeDistance;
import org.atrion.distance.PointPointDistance;
import org.atrion.geometry.Point;
import org.atrion.graph.Edge;
import org.atrion.graph.Graph;
import org.atrion.graph.Node;
import org.atrion.query.CandidateQuery;
import org.atrion.query.ClosestEdgeQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 09/07/13
 * Time: 18:53
 * To change this template use File | Settings | File Templates.
 */
public class Atrion {

    private static final double CAR_SPEED     = 3.0;
    private static final double WALKING_SPEED = 1;

    public AtrionRecommendation execute(AtrionQuery query,Collection<Point> objects,Graph roadGraph, Graph walkingGraph,PathCollection roadPaths, PathCollection walkingPaths,final int k) throws Exception {

        /*
            Result
         */
        List<AtrionEntry> tentativeSolution = new ArrayList<AtrionEntry>();
        /*
            Find cadidate points
         */
           Collection<CandidatePoint> candidatePoints = CandidateQuery.query(walkingGraph,walkingPaths,roadPaths,query);
        System.out.println("Candidate Points "+candidatePoints);
        /*
            Search for close objects
         */

        PointNodeDistance  pnd = new PointNodeDistance();
        PointPointDistance ppd = new PointPointDistance();
        EuclideanDistance  ed  = new EuclideanDistance();

         for(Point object : objects){
             System.out.println("Taxi "+object);
             Map.Entry<Edge,Point> projection = ClosestEdgeQuery.query(roadGraph.getEdges(),object);

             Edge edge              = projection.getKey();
             Point projectedPoint   = projection.getValue();

             System.out.println("From: "+edge.getTarget());

             for(CandidatePoint candidatePoint : candidatePoints){
                 System.out.println("To: "+candidatePoint);
                 Double distance = null;
                 Object obj = candidatePoint.getObject();

                 if(obj instanceof Map.Entry){
//                      distance = ppd.invoke(object,(Point)obj);
                 }
                 else{
                     if(candidatePoint.getObject() instanceof Node){
                         Node node = (Node)candidatePoint.getObject();

                         distance = roadPaths.getCost(edge.getTarget(),node);

                         if(distance != null){
                             distance += ed.invoke(edge.getTarget().getPoint(),projectedPoint);
                         }
                         else{
                             continue;
                         }

                     }
                     else{
                         throw new Exception("Invalid class: "+obj.getClass());
                     }
                 }

                 double objectCost  = distance                      / CAR_SPEED;
                 double walkingCost = candidatePoint.getDistance()  / WALKING_SPEED;

                 System.out.println("Taxi Cost "+objectCost);
                 System.out.println("Walking Cost "+walkingCost);

                 if(objectCost > walkingCost){
                     AtrionEntry entry = new AtrionEntry(1,candidatePoint,objectCost,walkingCost,objectCost+walkingCost);
                     tentativeSolution.add(entry);
                     break;
                 }
             }

         }

        AtrionRecommendation finalSolution = new AtrionRecommendation(tentativeSolution,k);

        return finalSolution;

    }
}
