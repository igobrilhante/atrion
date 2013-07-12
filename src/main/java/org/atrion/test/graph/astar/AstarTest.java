package org.atrion.test.graph.astar;

import org.atrion.astar.AStar;
import org.atrion.astar.Path;
import org.atrion.graph.Graph;
import org.atrion.graph.Node;
import org.atrion.graph.io.GraphReader;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 06/07/13
 * Time: 18:53
 * To change this template use File | Settings | File Templates.
 */
public class AstarTest {

    public static void main(String[] args) throws IOException {
         /*
                Load from CSV
         */
        String nodeFile = "road-network-nodes.txt";
        String edgeFile = "road-network-edges.txt";

        Graph graph = GraphReader.readFromCSV(nodeFile,edgeFile);

        System.out.println(graph.nodeCount());
        System.out.println(graph.edgeCount());


        Node start = graph.getNode(2);
        System.out.println("Start "+start);
        Node destination = graph.getNode(5);
        System.out.println("Destination "+destination);
        System.out.println(graph.getEdge(start,destination).getCost());

        AStar astar = new AStar();
        astar.distance(graph, start, destination);

        Path path = astar.getPath();
        System.out.println("Path "+path.getTotalCost()+ ": "+path.getReversedPath());
    }
}
