package org.atrion.query;

import org.atrion.algorithm.AtrionQuery;
import org.atrion.algorithm.CandidatePoint;
import org.atrion.astar.PathCollection;
import org.atrion.distance.EuclideanDistance;
import org.atrion.geometry.Point;
import org.atrion.graph.Edge;
import org.atrion.graph.Graph;
import org.atrion.graph.Node;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 09/07/13
 * Time: 19:16
 * To change this template use File | Settings | File Templates.
 */
public class CandidateQuery {

    public static Collection<CandidatePoint> query(Graph graph,PathCollection walkingPaths,PathCollection roadPaths,AtrionQuery queryPoint){

        EuclideanDistance ed = new EuclideanDistance();


        /*
            Find closest edge for the query point
         */
        Map.Entry<Edge,Point> entry         = ClosestEdgeQuery.query(graph.getEdges(), queryPoint.getPoint());
        Edge closestEdge                    = entry.getKey();
        Point projectedPoint                = entry.getValue();

        /*
            Find closest edge for destination point
         */
        Map.Entry<Edge,Point> destinationEntry  = ClosestEdgeQuery.query(graph.getEdges(), queryPoint.getDestinationPoint());
        Edge closestDestinationEdge             = entry.getKey();
        Point projectedDestinationPoint         = entry.getValue();

//        System.out.println("Closest edge: "+closestEdge);
//        System.out.println("Projected point: "+projectedPoint);

        Node source = closestEdge.getSource();
        Node target = closestEdge.getTarget();

        Node destinationSource = closestDestinationEdge.getSource();

        double sourceDistance = ed.invoke(source.getPoint(), projectedPoint);
        double targetDistance = ed.invoke(target.getPoint(), projectedPoint);
        
        double projectedDestinationDistance = ed.invoke(projectedDestinationPoint,destinationSource.getPoint());

        Double distance = 0.0;

        /*
            Find candidate set
         */
        Set<CandidatePoint> candidatePoints = new HashSet<CandidatePoint>();

        // If projected point is not a Node
        if(!projectedPoint.equals(source.getPoint()) && !projectedPoint.equals(target.getPoint())){

            // Destination Distance
            Double dd = roadPaths.getCost(target,destinationSource);

            if(distance !=null) {
                dd += ed.invoke(projectedPoint,target.getPoint());
                dd += projectedDestinationDistance;
                CandidatePoint defaultPoint = new CandidatePoint(0.0,entry);
                defaultPoint.setDestinationDistance(dd);
                candidatePoints.add(defaultPoint);

            }
        }

        if( sourceDistance <= queryPoint.getWalkingDistance()){
//            System.out.println("Source Distance "+source +" is "+sourceDistance);

            // Destination Distance
            Double dd = roadPaths.getCost(source,destinationSource);
            if(dd != null){
                dd += projectedDestinationDistance;
                CandidatePoint candidatePoint = new CandidatePoint(sourceDistance,source);
                candidatePoint.setDestinationDistance(dd);
                candidatePoints.add(candidatePoint);
            }

        }
        if( targetDistance <= queryPoint.getWalkingDistance()){
//            System.out.println("Target Distance "+target +" is "+targetDistance);

            // Destination Distance
            Double dd = roadPaths.getCost(target,destinationSource);
            if(dd !=null){
                dd += projectedDestinationDistance;
                CandidatePoint candidatePoint = new CandidatePoint(targetDistance,target);
                candidatePoints.add(candidatePoint);
            }


        }

        /*
            If there is candidate, it means that from the query point we can reach a node.
            If we cannot reach a node, we do not need to look for other nodes
         */
        if(candidatePoints.size() > 0){

            for(Node node : graph.getNodes()){
                /*
                    Source to Node
                 */
                if(node != source){
                    distance = walkingPaths.getCost(source,node);
                    if(distance != null){
                        if(sourceDistance + distance <= queryPoint.getWalkingDistance()){

                            // Destination Distance
                            Double dd = roadPaths.getCost(node,destinationSource);
                            if(dd != null){
                                dd += projectedDestinationDistance;
                                CandidatePoint candidatePoint = new CandidatePoint(sourceDistance + distance,node);
                                candidatePoints.add(candidatePoint);
                                candidatePoint.setDestinationDistance(dd);
                                continue;
                            }


                        }
                    }
                }

                /*
                    Target to Node
                 */
                if(node != target){
                    distance = walkingPaths.getCost(target,node);
                    if(distance !=null){
                        if(targetDistance + distance <= queryPoint.getWalkingDistance()){
                            // Destination Distance
                            Double dd = roadPaths.getCost(node,destinationSource);
                            if(dd != null){
                                dd += projectedDestinationDistance;
                                CandidatePoint candidatePoint = new CandidatePoint(targetDistance + distance,node);
                                candidatePoints.add(candidatePoint);
                                candidatePoint.setDestinationDistance(dd);
                            }

                        }
                    }
                }
            }
        }

        return candidatePoints;
    }
}
