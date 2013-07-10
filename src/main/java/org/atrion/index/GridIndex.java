package org.atrion.index;

import com.infomatiq.jsi.Rectangle;
import com.infomatiq.jsi.SpatialIndex;
import com.infomatiq.jsi.rtree.RTree;
import org.atrion.geometry.Point;
import org.atrion.graph.Graph;
import org.atrion.graph.Node;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 09/07/13
 * Time: 18:42
 * To change this template use File | Settings | File Templates.
 */
public class GridIndex {

    private SpatialIndex grids;

    public void init(Graph graph,int nGrids){
        grids = new RTree();




    }

    private Rectangle getRectangle(Collection<Node> nodes){

        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;

        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        for(Node node : nodes){

            double x = node.getPoint().getX();
            double y = node.getPoint().getY();

            if(x < minX){
                minX = x;
                continue;
            }
            if(x > maxX){
                maxX = x;
                continue;
            }
            if(y < minY){
                minY = y;
                continue;
            }
            if(y > maxY){
                maxY = y;
                continue;
            }
        }

//        Point bottomLeft = new Point(minX,minY);
//        Point upperRight = new Point(maxX,maxY);

        Rectangle rec = new Rectangle();
        rec.set((float)minX,(float)minY,(float)maxX,(float)maxY);

        return rec;

    }

}
