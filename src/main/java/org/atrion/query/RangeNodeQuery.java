package org.atrion.query;

import org.atrion.algorithm.AtrionQuery;
import org.atrion.distance.EuclideanDistance;
import org.atrion.graph.Edge;
import org.atrion.graph.Graph;
import org.atrion.graph.Node;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 10/07/13
 * Time: 13:22
 * To change this template use File | Settings | File Templates.
 */
public class RangeNodeQuery {

    /**
     * Get the closest nodes given a walking distance in AtrionQuery
     * @param graph
     * @param query
     * @return
     */
    public static Collection<Node> query(Graph graph,AtrionQuery query){

//        EuclideanDistance ed = new EuclideanDistance();
//        Set<Node> candidateSet = new HashSet<Node>();
//
//        /*
//            Find closest edge
//         */
//        Edge closestEdge = ClosestEdgeQuery.query(graph.getEdges(), query.getPoint());
//
//        Node source = closestEdge.getSource();
//        Node target = closestEdge.getTarget();
//
//        double sourceDistance = ed.invoke(source.getPoint(),query.getPoint());
//        double targetDistance = ed.invoke(target.getPoint(),query.getPoint());
//
//        double distance = 0.0;
//
//        for(Node node : graph.getNodes()){
//            /*
//                Source to Node
//             */
//            distance = ed.invoke(node.getPoint(),source.getPoint());
//            if(sourceDistance + distance <= query.getWalkingDistance()){
//                candidateSet.add(node);
//                continue;
//            }
//
//            /*
//                Target to Node
//             */
//            distance = ed.invoke(node.getPoint(),target.getPoint());
//            if(targetDistance + distance <= query.getWalkingDistance()){
//                candidateSet.add(node);
//
//            }
//        }



        return null;

    }

}
