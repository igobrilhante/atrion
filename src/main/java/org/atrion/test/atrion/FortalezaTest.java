package org.atrion.test.atrion;

import org.atrion.algorithm.Atrion;
import org.atrion.algorithm.AtrionQuery;
import org.atrion.algorithm.AtrionRecommendation;
import org.atrion.entity.MovingObject;
import org.atrion.geometry.Point;
import org.atrion.graph.Graph;
import org.atrion.graph.io.GraphReader;
import org.atrion.index.SpatialIndex;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 26/07/13
 * Time: 13:39
 * To change this template use File | Settings | File Templates.
 */
public class FortalezaTest {

    public static void main(String[] args) throws Exception {

        Graph walkingGraph = GraphReader.readObject("output/fortaleza-walking-network.ser");

        System.out.println("Walking Networks:");
        System.out.println("\tNodes: "+walkingGraph.nodeCount());
        System.out.println("\tEdges: "+walkingGraph.edgeCount());

        Graph roadGraph = GraphReader.readObject("output/fortaleza-road-network.ser");

        System.out.println("Road Networks:");
        System.out.println("\tNodes: "+roadGraph.nodeCount());
        System.out.println("\tEdges: "+roadGraph.edgeCount());

        AtrionQuery queryPoint = new AtrionQuery(new Point(-38.55137,-371671),500);
        queryPoint.setDestinationPoint(new Point(-38.51879,-3.71976));

        SpatialIndex si = new SpatialIndex();
        si.init(walkingGraph);

        Atrion atrion = new Atrion();
        atrion.init(si);

        Collection<MovingObject> taxis = new HashSet<MovingObject>();
        MovingObject taxi1 = new MovingObject();
        taxi1.setPoint(new Point(-38.6032927,-3.734921));
        MovingObject taxi2 = new MovingObject();
        taxi2.setPoint(new Point(-38.4600559,-3.7730028));

        taxis.add(taxi1);taxis.add(taxi2);

        AtrionRecommendation ar = atrion.execute(queryPoint,taxis,roadGraph,walkingGraph,3);

        System.out.println("Result "+ar);
    }
}
