package itvm.achtungdiekurve.model;

public class Field {

    private boolean field [][];

    public Field(int size){
        field = new boolean[size][size];
    }
    /**
     * @author Ben Borcherding
     * @param x
     * @param y
     * @return true bedeutet eine Kollision
     */
    public boolean detectCollision(int x, int y){
        //TODO
        return field[x][y];
    }

    /**
     * @author Vincenz Krüsmann
     * @param x
     * @param y
     * @return void
     */
    public void setPoint(int x, int y){
        field[x][y] = true;
    }



}



