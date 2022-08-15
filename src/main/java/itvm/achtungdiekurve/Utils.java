package itvm.achtungdiekurve;

import itvm.achtungdiekurve.model.Kurve;

import java.awt.*;
import java.util.List;

public class Utils {

    /**
     * @author Vincenz Krüsmann
     * Überprüft jede
     * @return boolean
     */
    public boolean detectCollsion(List<Kurve> array){
        for (Kurve kurve: array) {
            //der neueste Punkt
            Point pointLast = kurve.getPoint().get(kurve.getPoint().size() - 1);
            Point pointFirst = kurve.getPoint().get(kurve.getPoint().size() - 2);

            //Neuester Punkt mit jeden Überprüfen
            for (Kurve elem: array) {
                for (int i = 0; i < elem.getPoint().size(); i++) {
                    double steigungOther = (elem.getPoint().get(i+1).getY()- elem.getPoint().get(i).getY())/
                            (elem.getPoint().get(i+1).getX() - elem.getPoint().get(i).getX());
                    double nOther =  elem.getPoint().get(i).getY() - elem.getPoint().get(i).getX() * steigungOther;

                    double steigungPlayer = (pointLast.getY()-pointFirst.getY())/(pointLast.getX()-pointFirst.getX());
                    double nPlayer =  pointLast.getY() - pointLast.getX() * steigungOther;

                    double x = (nPlayer - nOther) / steigungPlayer - steigungOther;

                    if(pointFirst.getX() < pointLast.getX() && elem.getPoint().get(i).getX() < elem.getPoint().get(i+1).getX() ){
                        if(x >= pointFirst.getX() && x <= pointLast.getX() && x >= elem.getPoint().get(i).getX() && x <= elem.getPoint().get(i+1).getX()){
                            return true;
                        }
                    }else if(pointFirst.getX() < pointLast.getX() && elem.getPoint().get(i).getX() >= elem.getPoint().get(i+1).getX()){
                        if(x >= pointFirst.getX() && x <= pointLast.getX() && x <= elem.getPoint().get(i).getX() && x >= elem.getPoint().get(i+1).getX()){
                            return true;
                        }
                    }
                    else if(pointFirst.getX() > pointLast.getX() && elem.getPoint().get(i).getX() >= elem.getPoint().get(i+1).getX()){
                        if(x <= pointFirst.getX() && x >= pointLast.getX() && x <= elem.getPoint().get(i).getX() && x >= elem.getPoint().get(i+1).getX()){
                            return true;
                        }
                    }else {
                        if(x <= pointFirst.getX() && x >= pointLast.getX() && x <= elem.getPoint().get(i).getX() && x >= elem.getPoint().get(i+1).getX()){
                            return true;
                        }
                    }



                }

            }
        }
        return false;
    }
}
