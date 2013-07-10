package org.atrion.query;

import org.atrion.algorithm.AtrionQuery;
import org.atrion.algorithm.CandidatePoint;
import org.atrion.geometry.Point;
import org.atrion.graph.Edge;
import org.atrion.graph.Graph;
import org.atrion.graph.Node;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 09/07/13
 * Time: 19:16
 * To change this template use File | Settings | File Templates.
 */
public class CandidateQuery {

    public static Collection<CandidatePoint> query(Graph graph,AtrionQuery point){

        double distance = point.getWalkingDistance();

        Collection<Node> range = RangeNodeQuery.query(graph.getNodes(),point);
        Edge             edge  = ClosestEdgeQuery.query(graph.getEdges(),point.getPoint());




        return null;
    }
}
