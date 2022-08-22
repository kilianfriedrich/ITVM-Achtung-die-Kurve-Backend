package itvm.achtungdiekurve.model;

import org.springframework.web.socket.WebSocketSession;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Kurve {

    private final WebSocketSession session;
    private List<Point> points = new ArrayList<Point>();
    private Color color;
    private int id;
    private boolean isAlive = true;

    public Kurve(WebSocketSession _session, int _id, Color _color){
        this.session = _session;
        this.id = _id;
        this.color = _color;

    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Point> getPoint() {
        return points;
    }

    public void setPoint(List<Point> point) {
        this.points = point;
    }

    public void addPoint(Point p){
        this.points.add(p);
    }

    public WebSocketSession getSession(){
        return this.session;
    }

    public boolean getIsAlive(){
        return this.isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
