package org.atrion.test.graph.astar;

import org.atrion.astar.AStar;
import org.atrion.astar.Path;
import org.atrion.astar.PathCollection;
import org.atrion.graph.Graph;
import org.atrion.graph.Node;
import org.atrion.graph.io.GraphReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 11/07/13
 * Time: 23:50
 * To change this template use File | Settings | File Templates.
 */
public class RoadPathCollectionTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
         /*
                Load from CSV
         */
        String nodeFile = "road-network-nodes.txt";
        String edgeFile = "road-network-edges.txt";

        Graph graph = GraphReader.readFromCSV(nodeFile, edgeFile);

        System.out.println(graph.nodeCount());
        System.out.println(graph.edgeCount());

        boolean directed = true;
        PathCollection pathCollection = new PathCollection(directed);

        AStar astar = new AStar();

        List<Node> nodes = new ArrayList<Node>(graph.getNodes());

        for(int i=0;i<nodes.size();i++){
            for(int j=0;j<nodes.size();j++){
                if(i!=j){
                    astar.distance(graph, nodes.get(i), nodes.get(j));
                    Path path = astar.getPath();
                    if(path.getTotalCost() > 0)
                        pathCollection.addPath(path);
                }
            }
        }

        System.out.println("Paths " + pathCollection.getMap().size());
        PathCollection.write("path-collection.ser",pathCollection);

        pathCollection = PathCollection.read("path-collection.ser");
        System.out.println("Paths " + pathCollection.getMap().size());
        System.out.println(pathCollection.getPath(graph.getNode(2),graph.getNode(4)));
    }
}
