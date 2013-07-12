package org.atrion.test.query;

import org.atrion.query.ClosestEdgeQuery;
import org.atrion.geometry.Point;
import org.atrion.graph.Edge;
import org.atrion.graph.Graph;
import org.atrion.graph.io.GraphReader;

import java.io.IOException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 07/07/13
 * Time: 13:14
 * To change this template use File | Settings | File Templates.
 */
public class ClosestEdgeQueryTest {

    public static void main(String[] args) throws IOException {
        /*
                Load from CSV
         */
        String nodeFile = "road-network-nodes.txt";
        String edgeFile = "road-network-edges.txt";

        Graph graph = GraphReader.readFromCSV(nodeFile, edgeFile);

        System.out.println(graph.nodeCount());
        System.out.println(graph.edgeCount());

        Point queryPoint = new Point(2,0.5);

        Map.Entry<Edge,Point> edge = ClosestEdgeQuery.query(graph.getEdges(),queryPoint);
        System.out.println("Result "+edge);

    }

}
