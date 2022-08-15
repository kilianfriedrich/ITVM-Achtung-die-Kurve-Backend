package itvm.achtungdiekurve.model;

import java.awt.*;
import java.util.List;

public class Kurve {

    private List<Point> point;
    private String color;
    private int id;



    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Point> getPoint() {
        return point;
    }

    public void setPoint(List<Point> point) {
        this.point = point;
    }
}
