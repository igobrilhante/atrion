package org.atrion.astar;

import org.atrion.graph.Edge;
import org.atrion.graph.Node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 07/07/13
 * Time: 10:46
 * To change this template use File | Settings | File Templates.
 */
public class Path implements Serializable {
    private List<Node> nodes;
    private double totalCost;
    private List<Edge> edges;
    private final Node source;
    private final Node target;

    public Path(Node source, Node target){
        this.nodes = new ArrayList<Node>();
        this.edges = new ArrayList<Edge>();
        this.source = source;
        this.target = target;
        this.totalCost = -1d;
    }

    public Node getStart(){
        return this.source;
    }

    public Node getDestination(){
        return this.target;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public Path getReversedPath(){
        Path reversedPath = new Path(source,target);

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
