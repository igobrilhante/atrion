package org.atrion.astar;

import org.atrion.graph.Edge;
import org.atrion.graph.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 07/07/13
 * Time: 10:46
 * To change this template use File | Settings | File Templates.
 */
public class Path {
    private List<Node> nodes;
    private float totalCost;
    private List<Edge> edges;

    public Path(){
        this.nodes = new ArrayList<Node>();
        this.edges = new ArrayList<Edge>();
        this.totalCost = -1f;
    }

    public Node getStart(){
        return this.nodes.get(nodes.size()-1);
    }

    public Node getDestination(){
        return this.nodes.get(0);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public Path getReversedPath(){
        Path reversedPath = new Path();

        for(int i=nodes.size()-1;i>=0;i--){
            reversedPath.getNodes().add(this.nodes.get(i));
        }
        reversedPath.setTotalCost(this.totalCost);
        reversedPath.setEdges(this.edges);

        return reversedPath;

    }

    @Override
    public String toString() {
        return "Path{" +
                "nodes=" + nodes +
                ", totalCost=" + totalCost +
                '}';
    }
}
