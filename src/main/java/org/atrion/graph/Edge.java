package org.atrion.graph;

import org.atrion.geometry.LineSegment;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 06/07/13
 * Time: 12:33
 * To change this template use File | Settings | File Templates.
 */
public class Edge implements Serializable {

    private final Node source;
    private final Node target;
    private final String label;
    private final float cost;
    private final LineSegment lineSegment;


    public Edge(Node source, Node target,float cost) {
        this.source      = source;
        this.target      = target;
        this.cost        = cost;
        this.label       = source.getId()+"-"+target.getId();
        this.lineSegment = new LineSegment(source.getPoint(),target.getPoint());
    }

    public Node getSource() {
        return source;
    }

    public String getLabel() {
        return label;
    }

    public LineSegment getLineSegment() {
        return lineSegment;
    }

    public Node getTarget() {
        return target;
    }

    public float getCost() {
        return cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (!label.equals(edge.label)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }

    @Override
    public String toString() {
        return "Edge{" +
                "label='" + label + '\'' +
                '}';
    }
}
