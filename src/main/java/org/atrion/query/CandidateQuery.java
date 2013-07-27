package org.atrion.query;

import org.atrion.algorithm.AtrionQuery;
import org.atrion.algorithm.CandidatePoint;
import org.atrion.astar.AStar;
import org.atrion.astar.PathCollection;
import org.atrion.distance.EuclideanDistance;
import org.atrion.distance.Util;
import org.atrion.geometry.Point;
import org.atrion.graph.Edge;
import org.atrion.graph.Graph;
import org.atrion.graph.Node;
import org.atrion.index.SpatialIndex;

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

    public static Collection<CandidatePoint> query(SpatialIndex index,Graph walkingGraph,Graph roadGraph,AtrionQuery queryPoint){

        EuclideanDistance ed = new EuclideanDistance();
        AStar astart = new AStar();


        /*
            Find closest edge for the query point
         */
//        Map.Entry<Edge,Point> entry         = ClosestEdgeQuery.query(graph.getEdges(), queryPoint.getPoint());
        Map.Entry<Edge,Point> entry         = index.nearestEdge(queryPoint.getPoint());
        Edge closestEdge                    = entry.getKey();
        Point projectedPoint                = entry.getValue();

        /*
            Find closest edge for destination point
         */
//        Map.Entry<Edge,Point> destinationEntry  = ClosestEdgeQuery.query(graph.getEdges(), queryPoint.getDestinationPoint());
        Map.Entry<Edge,Point> destinationEntry    = index.nearestEdge(queryPoint.getDestinationPoint());
        Edge closestDestinationEdge               = destinationEntry.getKey();
        Point projectedDestinationPoint           = destinationEntry.getValue();
//        System.out.println("Destination "+destinationEntry);
//        System.out.println(closestDestinationEdge.getSource().getPoint());
//        System.out.println(closestDestinationEdge.getTarget().getPoint());

//        System.out.println("Closest edge: "+closestEdge);
//        System.out.println("Projected point: "+projectedPoint);

        Node source = closestEdge.getSource();
        Node target = closestEdge.getTarget();

        Node destinationSource = closestDestinationEdge.getSource();

        double sourceDistance = Util.degree2meters(ed.invoke(source.getPoint(), projectedPoint));
        double targetDistance = Util.degree2meters(ed.invoke(target.getPoint(), projectedPoint));
        
        double projectedDestinationDistance = Util.degree2meters(ed.invoke(projectedDestinationPoint,destinationSource.getPoint()));
//        System.out.println("projdes "+projectedDestinationDistance);

        Double distance = 0.0;

        /*
            Find candidate set
         */
        Set<CandidatePoint> candidatePoints = new HashSet<CandidatePoint>();

        // If projected point is not a Node
        if(!projectedPoint.equals(source.getPoint()) && !projectedPoint.equals(target.getPoint())){

            // Destination Distance
//            Double dd = roadPaths.getCost(target,destinationSource);

            astart.distance(roadGraph,target,destinationSource);
            Double dd = astart.getPath().getTotalCost();
            if(dd < 0){
                dd = null;
            }
//            System.out.println(dd);

            if(dd !=null) {
//                System.out.println(projectedPoint);
//                System.out.println(target.getPoint());
                dd = dd + Util.degree2meters(ed.invoke(projectedPoint,target.getPoint()));
//                System.out.println(dd);
                dd = dd + projectedDestinationDistance;
//                System.out.println(dd);
                CandidatePoint defaultPoint = new CandidatePoint(0.0,entry);
                defaultPoint.setDestinationDistance(dd);
                candidatePoints.add(defaultPoint);

            }
        }

        if( sourceDistance <= queryPoint.getWalkingDistance()){
//            System.out.println("Source Distance "+source +" is "+sourceDistance);

            // Destination Distance
//            Double dd = roadPaths.getCost(source,destinationSource);
            astart.distance(roadGraph,source,destinationSource);
            Double dd = astart.getPath().getTotalCost();
            if(dd < 0){
                dd = null;
            }
            if(dd != null){
                dd = dd + projectedDestinationDistance;
//                System.out.println("Source dd "+dd);
                CandidatePoint candidatePoint = new CandidatePoint(sourceDistance,source);
                candidatePoint.setDestinationDistance(dd);
                candidatePoints.add(candidatePoint);
            }

        }
        if( targetDistance <= queryPoint.getWalkingDistance()){
//            System.out.println("Target Distance "+target +" is "+targetDistance);

            // Destination Distance
//            Double dd = roadPaths.getCost(target,destinationSource);
            astart.distance(roadGraph,source,destinationSource);
            Double dd = astart.getPath().getTotalCost();
            if(dd < 0){
                dd = null;
            }
            if(dd !=null){
                dd += projectedDestinationDistance;
                CandidatePoint candidatePoint = new CandidatePoint(targetDistance,target);
                candidatePoint.setDestinationDistance(dd);
                candidatePoints.add(candidatePoint);
            }


        }

        /*
            If there is candidate, it means that from the query point we can reach a node.
            If we cannot reach a node, we do not need to look for other nodes
         */
        if(candidatePoints.size() > 1){

            Collection<Node> tentativeNodes = index.range(projectedPoint,queryPoint.getWalkingDistance());
//            System.out.println("Tentative Nodes "+tentativeNodes.size());

            for(Node node : tentativeNodes){
                /*
                    Source to Node
                 */
                if(!node.equals(source)){
                    astart.distance(walkingGraph,source,node);
                    distance = astart.getPath().getTotalCost();
                    if(distance < 0){
                        distance = null;
                    }
//                    distance = walkingPaths.getCost(source,node);
                    if(distance != null){
                        if(sourceDistance + distance <= queryPoint.getWalkingDistance()){

//                            System.out.println("Node "+node.getId());
                            // Destination Distance
//                            Double dd = roadPaths.getCost(node,destinationSource);
                            astart.distance(roadGraph,node,destinationSource);
                            Double dd = astart.getPath().getTotalCost();
                            if(dd < 0){
                                dd = null;
                            }
                            if(dd != null){
                                dd += projectedDestinationDistance;
                                CandidatePoint candidatePoint = new CandidatePoint(sourceDistance + distance,node);
                                candidatePoint.setDestinationDistance(dd);
                                candidatePoints.add(candidatePoint);
//                                System.out.println("\tok");
                                continue;
                            }


                        }
                    }
                }

                /*
                    Target to Node
                 */
                if(!node.equals(target)){
//                    distance = walkingPaths.getCost(target,node);
                    astart.distance(walkingGraph,source,node);
                    distance = astart.getPath().getTotalCost();
                    if(distance < 0){
                        distance = null;
                    }
                    if(distance !=null){
                        if(targetDistance + distance <= queryPoint.getWalkingDistance()){

//                            System.out.println("Node "+node.getId());
                            // Destination Distance
//                            Double dd = roadPaths.getCost(node,destinationSource);
                            astart.distance(roadGraph,node,destinationSource);
                            Double dd = astart.getPath().getTotalCost();
                            if(dd < 0){
                                dd = null;
                            }
                            if(dd != null){
                                dd += projectedDestinationDistance;
                                CandidatePoint candidatePoint = new CandidatePoint(targetDistance + distance,node);
                                candidatePoint.setDestinationDistance(dd);
                                candidatePoints.add(candidatePoint);
//                                System.out.println("\tok");
                            }

                        }
                    }
                }
            }
        }

        return candidatePoints;
    }

}
