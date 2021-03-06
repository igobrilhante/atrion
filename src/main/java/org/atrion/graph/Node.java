package org.atrion.graph;

import org.atrion.geometry.Point;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 06/07/13
 * Time: 12:33
 * To change this template use File | Settings | File Templates.
 */
public class Node implements Serializable {
    private final int id;
    private Point point;
    private Neighbors neighbors;


    public Node(int id) {
        this.id = id;
    }

    public Neighbors getNeighbors() {
        return neighbors;
    }

    public Edge getNeighbor(Node node){
        return neighbors.get(node);
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void setNeighbors(Neighbors neighbors) {
        this.neighbors = neighbors;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (id != node.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                '}';
    }
}
