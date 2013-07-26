package org.atrion.test.atrion;

import org.atrion.algorithm.Atrion;
import org.atrion.algorithm.AtrionQuery;
import org.atrion.algorithm.AtrionRecommendation;
import org.atrion.algorithm.CandidatePoint;
import org.atrion.astar.PathCollection;
import org.atrion.entity.MovingObject;
import org.atrion.geometry.Point;
import org.atrion.graph.Graph;
import org.atrion.graph.io.GraphReader;
import org.atrion.query.CandidateQuery;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 11/07/13
 * Time: 23:44
 * To change this template use File | Settings | File Templates.
 */
public class AtrionTest {

    public static void main(String[] args) throws Exception {
         /*
                Load Walking Graph from CSV
         */
        String nodeFile = "walking-nodes.txt";
        String edgeFile = "walking-edges.txt";

        Graph walkingGraph = GraphReader.readFromCSV(nodeFile, edgeFile);

        System.out.println("Walking Networks:");
        System.out.println("\tNodes: "+walkingGraph.nodeCount());
        System.out.println("\tEdges: "+walkingGraph.edgeCount());

        PathCollection walkingPaths = PathCollection.read("walking-path-collection.ser");

         /*
                Load Road Graph from CSV
         */
        String roadNodeFile = "road-network-nodes.txt";
        String roadEdgeFile = "road-network-edges.txt";

        Graph roadGraph = GraphReader.readFromCSVWithDirection(roadNodeFile, roadEdgeFile, ",");

        System.out.println("Road Networks:");
        System.out.println("\tNodes: "+roadGraph.nodeCount());
        System.out.println("\tEdges: "+roadGraph.edgeCount());

        PathCollection roadPaths = PathCollection.read("path-collection.ser");

        AtrionQuery queryPoint = new AtrionQuery(new Point(3.0,2.0),2);
        queryPoint.setDestinationPoint(new Point(4,1));

        Atrion atrion = new Atrion();

        Collection<MovingObject> taxis = new HashSet<MovingObject>();
        MovingObject taxi1 = new MovingObject();
        taxi1.setPoint(new Point(2,0));
        MovingObject taxi2 = new MovingObject();
        taxi2.setPoint(new Point(1,1));

        taxis.add(taxi1);taxis.add(taxi2);

        AtrionRecommendation ar = atrion.execute(queryPoint,taxis,roadGraph,walkingGraph,roadPaths,walkingPaths,3);

        System.out.println("Result "+ar);
    }

}
