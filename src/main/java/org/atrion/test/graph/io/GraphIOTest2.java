package org.atrion.test.graph.io;

import org.atrion.graph.Edge;
import org.atrion.graph.Graph;
import org.atrion.graph.Node;
import org.atrion.graph.io.GraphReader;
import org.atrion.graph.io.GraphWriter;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 25/07/13
 * Time: 20:41
 * To change this template use File | Settings | File Templates.
 */
public class GraphIOTest2 {

    public static void main(String[] args) throws IOException, ClassNotFoundException {


         /*
                Load from CSV
         */
        String nodeFile = "road-network-nodes.txt";
        String edgeFile = "road-network-edges.txt";

        Graph graph = GraphReader.readFromCSVWithDirection(nodeFile,edgeFile,",");

        System.out.println(graph.nodeCount());
        System.out.println(graph.edgeCount());
        for(Node node : graph.getNodes()){
            System.out.println(node);
        }
        for(Edge edge : graph.getEdges()){
            System.out.println(edge.getSource()+" "+edge.getTarget()+" "+edge.getCost());
        }

        /*
                Write Graph Object
         */
        GraphWriter.writeObject(graph, "graph.ser");

        /*
                Read Graph Object
         */
        graph = GraphReader.readObject("graph.ser");
        System.out.println(graph.nodeCount());
        System.out.println(graph.edgeCount());

    }
}
