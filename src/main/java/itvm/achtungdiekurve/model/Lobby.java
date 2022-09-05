package itvm.achtungdiekurve.model;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    List<Kurve> kurveList = new ArrayList<>();
    private final static int MAX_ANZAHL_KURVEN = 10;
    private static int ID_CTR = 0;
    private int id;

    public Lobby(){
        id = ID_CTR++;
    }


    public void addKurve(Kurve kurve) {
        kurveList.add(kurve);
    }

    public int getId() {
        return id;
    }

    public void start() {

    }
}
