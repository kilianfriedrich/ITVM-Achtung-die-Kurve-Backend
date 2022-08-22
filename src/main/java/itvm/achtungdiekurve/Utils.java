package itvm.achtungdiekurve;

import itvm.achtungdiekurve.model.Kurve;

import java.awt.*;
import java.util.List;

public class Utils {

    private static final int MAX_X = 999;
    private static final int MAX_Y = 999;
    /**
     * @author Vincenz Krüsmann
     * Überprüft jede
     * @return boolean
     */
    public static Kurve detectCollsion(List<Kurve> array){
        for (Kurve kurve: array) {
            //der neueste Punkt
            Point pointLast = kurve.getPoint().get(kurve.getPoint().size() - 1);
            Point pointFirst = kurve.getPoint().get(kurve.getPoint().size() - 2);

            if(pointLast.getX() < 0 || pointLast.getX() > MAX_X || pointLast.getY() < 0 || pointLast.getY() > MAX_Y){
                return kurve;
            }

            //Neuester Punkt mit jeden Überprüfen
            for (Kurve elem: array) {
                for (int i = 0; i < elem.getPoint().size() - 1; i++) {

                    if(collides(pointFirst, pointLast, elem.getPoint().get(i), elem.getPoint().get(i + 1))) {
                        return kurve;
                    }

                }

            }
        }
        return null;
    }
    
    private static boolean collides(Point a1, Point a2, Point b1, Point b2) {
        // Um 90 Grad drehen, wenn beide senkrecht
        if(a1.getX() == a2.getX() || b1.getX() == b2.getX()) {
            a1.setLocation(a1.getY(), a1.getX());
            a2.setLocation(a2.getY(), a2.getX());
            b1.setLocation(b1.getY(), b1.getX());
            b2.setLocation(b2.getY(), b2.getX());
        }

        // FIXME wenn eine senkrechte und eine waagerechte Linie kommen crasht das

        double steigungOther = (b2.getY()- b1.getY())/
                (b2.getX() - b1.getX());
        double nOther =  b1.getY() - b1.getX() * steigungOther;

        double steigungPlayer = (a2.getY()-a1.getY())/(a2.getX()-a1.getX());
        double nPlayer =  a2.getY() - a2.getX() * steigungOther;

        double x = (nPlayer - nOther) / steigungPlayer - steigungOther;


        if(a1.getX() < a2.getX() && b1.getX() < b2.getX() ){
            if(x >= a1.getX() && x <= a2.getX() && x >= b1.getX() && x <= b2.getX()){
                return true;
            }
        }else if(a1.getX() < a2.getX() && b1.getX() >= b2.getX()){
            if(x >= a1.getX() && x <= a2.getX() && x <= b1.getX() && x >= b2.getX()){
                return true;
            }
        }
        else if(a1.getX() > a2.getX() && b1.getX() >= b2.getX()){
            if(x <= a1.getX() && x >= a2.getX() && x <= b1.getX() && x >= b2.getX()){
                return true;
            }
        }else {
            if(x <= a1.getX() && x >= a2.getX() && x <= b1.getX() && x >= b2.getX()){
                return true;
            }
        }
        return false;
    }
    
}
