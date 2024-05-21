public class Coords {
    int [] values;

    public Coords(int size){
        this.values = new int[size];
        for(int i = 0; i < size; i++){
            this.values[i] = 0;
        }
    }

    public Coords(int ... values){
        this.values = values;
    }

    public int[] getCoords(){
        return this.values;
    }

    public void setCoord(int index, int value){
        this.values[index] = value;
    }
}
