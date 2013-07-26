package org.atrion.entity;

import org.atrion.geometry.Point;

/**
 * Created with IntelliJ IDEA.
 * User: igobrilhante
 * Date: 25/07/13
 * Time: 23:11
 * To change this template use File | Settings | File Templates.
 */
public class MovingObject {
    private int id;
    private Point point;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "MovingObject{" +
                "id=" + id +
                ", point=" + point +
                '}';
    }
}
