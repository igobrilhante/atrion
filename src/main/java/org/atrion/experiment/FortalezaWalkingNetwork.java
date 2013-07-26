package org.atrion.experiment;

import org.atrion.astar.AStar;
import org.atrion.astar.Path;
import org.atrion.astar.PathCollection;
import org.atrion.graph.Graph;
import org.atrion.graph.Node;
import org.atrion.graph.io.GraphReader;
import org.atrion.graph.io.GraphWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 26/07/13
 * Time: 00:02
 * To change this template use File | Settings | File Templates.
 */
public class FortalezaWalkingNetwork {

    public static void main(String[] args) throws IOException, ClassNotFoundException {


        /*
                Read Graph Object
         */
        System.out.println("Loading Graph");
        Graph graph = GraphReader.readObject(args[0]);
        System.out.println("Nodes "+graph.nodeCount());
        System.out.println("Edges "+graph.edgeCount());

        boolean directed = false;
        PathCollection pathCollection = new PathCollection(directed);

        AStar astar = new AStar();

        List<Node> nodes = new ArrayList<Node>(graph.getNodes());

        System.out.println("Computing paths");
        for(int i=0;i<nodes.size();i++){
            System.out.println("Node: "+i);
            for(int j=i+1;j<nodes.size();j++){
                System.out.println("Node: "+j);
                if(i!=j){
                    astar.distance(graph, nodes.get(i), nodes.get(j));
                    Path path = astar.getPath();
                    if(path.getTotalCost() > 0)
                        pathCollection.addPath(path);
                }
            }
        }

        System.out.println("Writing Paths");
        PathCollection.write(args[1],pathCollection);

    }

}
