package org.atrion.algorithm;

import org.atrion.astar.PathCollection;
import org.atrion.distance.EuclideanDistance;
import org.atrion.entity.MovingObject;
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
    private static final double WALKING_SPEED = 1.0;

    public AtrionRecommendation execute(AtrionQuery query,Collection<MovingObject> movingObjects,Graph roadGraph, Graph walkingGraph,PathCollection roadPaths, PathCollection walkingPaths,final int k) throws Exception {

        /*
            Result
         */
        List<AtrionEntry> tentativeSolution = new ArrayList<AtrionEntry>();
        /*
            Find cadidate points
         */
        Collection<CandidatePoint> candidatePoints = CandidateQuery.query(walkingGraph,walkingPaths,roadPaths,query);
        System.out.println("Candidate Points "+candidatePoints);
        System.out.println("Candidate Points "+candidatePoints.size());
        /*
            Search for close movingObjects
         */

        EuclideanDistance  ed  = new EuclideanDistance();

        int objectId = 0;
         for(MovingObject movingObject : movingObjects){
             System.out.println("##############################");
             System.out.println("Taxi "+movingObject);
             Map.Entry<Edge,Point> projection = ClosestEdgeQuery.query(roadGraph.getEdges(),movingObject.getPoint());

             Edge movingObjectEdge              = projection.getKey();
             Point movingObjectprojectedPoint   = projection.getValue();

             System.out.println("From: " + movingObjectEdge.getTarget());

             for(CandidatePoint candidatePoint : candidatePoints){
                 System.out.println("--------------------------");
                 System.out.println("\tTo: "+candidatePoint.getObject());
                 // Distance is the distance of the moving object to reach the candidate point
                 Double distance = null;
                 Object obj = candidatePoint.getObject();

                 if(obj instanceof Map.Entry){
                     Map.Entry entry = (Map.Entry)obj;
                     Edge edge = (Edge)entry.getKey();
                     Point projectedPoint = (Point)entry.getValue();

                     // Distance from the moving object (TARGET NODE) to the point (SOURCE)
                     // Moving object can reach the Point
                     distance = roadPaths.getCost(movingObjectEdge.getTarget(),edge.getSource());
                     if(distance != null){
                        distance += ed.invoke(movingObjectprojectedPoint,movingObjectEdge.getTarget().getPoint());
                        distance += ed.invoke(edge.getTarget().getPoint(),projectedPoint);
                     }

                 }
                 else{
                     if(candidatePoint.getObject() instanceof Node){
                         Node node = (Node)candidatePoint.getObject();

                         distance = roadPaths.getCost(movingObjectEdge.getTarget(),node);
                         System.out.println("Path from "+movingObjectEdge.getTarget().getId()+" to "+node.getId()+" is "+distance);

                         if(distance != null){
                             distance += ed.invoke(movingObjectEdge.getTarget().getPoint(),movingObjectprojectedPoint);
                         }
                         else{
                             System.out.println("Path from "+movingObjectEdge.getTarget().getId()+" to "+node.getId()+" do not exit");
                             continue;
                         }

                     }
                     else{
                         throw new Exception("Invalid class: "+obj.getClass());
                     }
                 }

                 // Cost of the moving object to reach the candidate point
                 double objectCost  = distance                                              / CAR_SPEED;
                 // Cost of the person to reach the candidate point
                 double walkingCost = candidatePoint.getWalkingDistance()                   / WALKING_SPEED;
                 // Total cost of the trip:
                 //                         Cost of the moving object to reach the candidate point +
                 //                         Cost of the moving object to reach the destination
                 double totalCost   = (distance+candidatePoint.getDestinationDistance())    / CAR_SPEED;

                 System.out.println("\tTaxi Cost "+objectCost);
                 System.out.println("\tWalking Cost "+walkingCost);
                 System.out.println("\tTotal Cost "+totalCost);

                 if(objectCost > walkingCost){
                     System.out.println("\tValid Solution");
                     AtrionEntry entry = new AtrionEntry(movingObject,candidatePoint,objectCost,walkingCost,totalCost);
                     tentativeSolution.add(entry);
                 }
             }
             objectId++;
         }

        AtrionRecommendation finalSolution = new AtrionRecommendation(tentativeSolution,k);

        return finalSolution;

    }
}
