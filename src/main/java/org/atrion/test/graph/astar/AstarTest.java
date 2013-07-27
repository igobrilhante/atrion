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

    public static void main(String[] args) throws IOException, ClassNotFoundException {
         /*
                Load from CSV
         */
        String nodeFile = "road-network-nodes.txt";
        String edgeFile = "road-network-edges.txt";

        Graph graph = GraphReader.readObject("output/fortaleza-road-network.ser");

        System.out.println(graph.nodeCount());
        System.out.println(graph.edgeCount());

//        100426519;-38.4996635;-3.7663746
//        100426594;-38.5917601;-3.6983037   100426519;100451059

        Node start = graph.getNode(100426519);
        System.out.println("Start "+start);
        Node destination = graph.getNode(100426594);
        System.out.println("Destination "+destination);

        AStar astar = new AStar();
        astar.distance(graph, start, destination);

        Path path = astar.getPath();
        System.out.println("Path "+path.getTotalCost()+ ": "+path.getReversedPath());

        String a ="[";
        for(Node node : path.getReversedPath().getNodes()){
            a = a + ",["+node.getPoint().getX()+","+node.getPoint().getY()+"]";
        }
        a = a + "]";
        System.out.println(a);
    }
}
