package org.atrion.query;

import com.infomatiq.jsi.Rectangle;
import com.infomatiq.jsi.rtree.RTree;
import gnu.trove.TIntProcedure;
import org.atrion.astar.AStar;
import org.atrion.distance.EuclideanDistance;
import org.atrion.geometry.Point;
import org.atrion.graph.Graph;
import org.atrion.graph.Node;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 08/07/13
 * Time: 08:16
 * To change this template use File | Settings | File Templates.
 */
public class ClosestNodeQuery {

    public static Node query(Graph graph,Point point){

        double maxDistance = Double.POSITIVE_INFINITY;
        AStar astar = new AStar();
        EuclideanDistance ed = new EuclideanDistance();
        Node result = null;

        for(Node node : graph.getNodes()){
            double d = ed.invoke(point,node.getPoint());
            if(d < maxDistance){
                maxDistance = d;
                result = node;
            }
        }

        return result;
    }

    private static Rectangle createRectangle(Point p){
        return new Rectangle((float)p.getX(),(float)p.getY(),
                (float)p.getX(),(float)p.getY());
    }

    private static RTree init(Collection<Node> nodes){
        RTree tree = new RTree();
        tree.init(null);

        for(Node node : nodes){
            Rectangle rect = new Rectangle((float)node.getPoint().getX(),(float)node.getPoint().getY(),
                    (float)node.getPoint().getX(),(float)node.getPoint().getY());
            tree.add(rect,node.getId());
        }

        return tree;
    }

}
