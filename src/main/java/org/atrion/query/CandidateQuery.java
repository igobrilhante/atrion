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

    public static Collection<CandidatePoint> query(Graph graph,PathCollection pathCollection,AtrionQuery queryPoint){

        EuclideanDistance ed = new EuclideanDistance();


        /*
            Find closest edge
         */
        Map.Entry<Edge,Point> entry = ClosestEdgeQuery.query(graph.getEdges(), queryPoint.getPoint());
        Edge closestEdge = entry.getKey();
        Point projectedPoint = entry.getValue();

        System.out.println("Closest edge: "+closestEdge);
        System.out.println("Projected point: "+projectedPoint);

        Node source = closestEdge.getSource();
        Node target = closestEdge.getTarget();

        double sourceDistance = ed.invoke(source.getPoint(), projectedPoint);
        double targetDistance = ed.invoke(target.getPoint(), projectedPoint);

        Double distance = 0.0;

        /*
            Find candidate set
         */
        Set<CandidatePoint> candidatePoints = new HashSet<CandidatePoint>();

        if(!projectedPoint.equals(source.getPoint()) && !projectedPoint.equals(target.getPoint())){
            CandidatePoint defaultPoint = new CandidatePoint(0.0,entry);
            candidatePoints.add(defaultPoint);
        }

        if( sourceDistance <= queryPoint.getWalkingDistance()){
            System.out.println("Source Distance "+source +" is "+sourceDistance);
            CandidatePoint candidatePoint = new CandidatePoint(sourceDistance,source);
            candidatePoints.add(candidatePoint);
        }
        if( targetDistance <= queryPoint.getWalkingDistance()){
            System.out.println("Target Distance "+target +" is "+targetDistance);
            CandidatePoint candidatePoint = new CandidatePoint(targetDistance,target);
            candidatePoints.add(candidatePoint);

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
                    distance = pathCollection.getCost(source,node);
                    if(distance != null){
                        if(sourceDistance + distance <= queryPoint.getWalkingDistance()){
                            CandidatePoint candidatePoint = new CandidatePoint(sourceDistance + distance,node);
                            candidatePoints.add(candidatePoint);
                            continue;
                        }
                    }
                }

                /*
                    Target to Node
                 */
                if(node != target){
                    distance = pathCollection.getCost(target,node);
                    if(distance !=null){System.out.println("Source Distance "+distance);
                        if(targetDistance + distance <= queryPoint.getWalkingDistance()){
                            CandidatePoint candidatePoint = new CandidatePoint(targetDistance + distance,node);
                            candidatePoints.add(candidatePoint);

                        }
                    }
                }
            }
        }

        return candidatePoints;
    }
}
