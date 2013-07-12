package org.atrion.test.query;

import org.atrion.algorithm.AtrionQuery;
import org.atrion.algorithm.CandidatePoint;
import org.atrion.astar.AStar;
import org.atrion.astar.Path;
import org.atrion.astar.PathCollection;
import org.atrion.geometry.Point;
import org.atrion.graph.Graph;
import org.atrion.graph.Node;
import org.atrion.graph.io.GraphReader;
import org.atrion.query.CandidateQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 11/07/13
 * Time: 22:30
 * To change this template use File | Settings | File Templates.
 */
public class CandidateQueryTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException{
         /*
                Load from CSV
         */
        String nodeFile = "walking-nodes.txt";
        String edgeFile = "walking-edges.txt";

        Graph graph = GraphReader.readFromCSV(nodeFile, edgeFile);

        System.out.println(graph.nodeCount());
        System.out.println(graph.edgeCount());

        PathCollection pathCollection = PathCollection.read("walking-path-collection.ser");

         /*
                Load Road Graph from CSV
         */
        String roadNodeFile = "road-network-nodes.txt";
        String roadEdgeFile = "road-network-edges.txt";

        Graph roadGraph = GraphReader.readFromCSV(roadNodeFile, roadEdgeFile);

        PathCollection roadPaths = PathCollection.read("path-collection.ser");

        AtrionQuery queryPoint = new AtrionQuery(new Point(1,1.0),3);
        queryPoint.setDestinationPoint(new Point(4,1));

        Collection<CandidatePoint> candidatePointCollection = CandidateQuery.query(graph,pathCollection,roadPaths,queryPoint);

        System.out.println("Result:\n"+candidatePointCollection);
    }
}
