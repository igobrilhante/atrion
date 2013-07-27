package org.atrion.index;

import com.infomatiq.jsi.Rectangle;
import com.infomatiq.jsi.rtree.RTree;
import gnu.trove.TIntProcedure;
import org.atrion.distance.Util;
import org.atrion.geometry.Point;
import org.atrion.graph.Edge;
import org.atrion.graph.Graph;
import org.atrion.graph.Node;
import org.atrion.query.ClosestEdgeQuery;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 26/07/13
 * Time: 10:04
 * To change this template use File | Settings | File Templates.
 */
public class SpatialIndex {
    private RTree nodeRtree;
    private RTree edgeRtree;
    private Map<Integer,Node> nodeMap;
    private Map<Integer,Edge> edgeMap;
    private Rectangle[] nodeRecs;
    private Rectangle[] edgeRecs;


    public void init(Graph graph){
        this.nodeRtree = new RTree();
        this.nodeRtree.init(null);
        this.edgeRtree = new RTree();
        this.edgeRtree.init(null);
        this.nodeRecs = new Rectangle[graph.nodeCount()];
        this.edgeRecs = new Rectangle[graph.edgeCount()];
        this.nodeMap = new HashMap<Integer, Node>();
        this.edgeMap = new HashMap<Integer, Edge>();

        int i =0;
        for(Node node : graph.getNodes()){
            Rectangle rec = new Rectangle();
            Point p = node.getPoint();
            rec.set((float)p.getX(),(float)p.getY(),(float)p.getX(),(float)p.getY());
            nodeRecs[i] = rec;
            nodeRtree.add(rec, i);
            nodeMap.put(i, node);
            i++;
        }
        int j =0;
        for(Edge edge : graph.getEdges()){
            Point p1 = edge.getSource().getPoint();
            Point p2 = edge.getTarget().getPoint();

            Rectangle rec = new Rectangle((float)p1.getX(),(float)p1.getY(),(float)p2.getX(),(float)p2.getY());
            edgeRecs[j] = rec;
            edgeRtree.add(rec,j);
            edgeMap.put(j,edge);
            j++;
        }

    }

    public Collection<Node> range(Point point,float maxDistance){
        final Collection<Node> result = new HashSet<Node>();


        this.nodeRtree.nearestN(
                new com.infomatiq.jsi.Point((float) point.getX(), (float) point.getY()),      // the point for which we want to find nearby rectangles
                new TIntProcedure() {         // a procedure whose execute() method will be called with the results
                    public boolean execute(int i) {
                        result.add(nodeMap.get(i));
                        return true;              // return true here to continue receiving results
                    }
                },
                Integer.MAX_VALUE,                // the number of nearby rectangles to find
                (float)Util.meters2degree(maxDistance)                       // Don't bother searching further than this. MAX_VALUE means search everything
        );

        return result;
    }

    public Map.Entry<Edge,Point> nearestEdge(Point point){
//        final Collection<Edge> edges = new HashSet<Edge>();
//
//        this.nodeRtree.contains(
//                new Rectangle((float) point.getX(), (float) point.getY(), (float) point.getX(), (float) point.getY()),      // the point for which we want to find nearby rectangles
//                new TIntProcedure() {         // a procedure whose execute() method will be called with the results
//                    public boolean execute(int i) {
//                        System.out.println("Edge "+edgeMap.get(i));
//                        edges.add(edgeMap.get(i));
//                        return true;              // return true here to continue receiving results
//                    }
//                }                      // Don't bother searching further than this. MAX_VALUE means search everything
//        );

        Map.Entry<Edge,Point> edge = ClosestEdgeQuery.query(edgeMap.values(),point);

        return edge;
    }

}
