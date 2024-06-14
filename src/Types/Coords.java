package Types;
public class Coords {
    int [] values;

    public Coords(Boolean isSize, int size){
        if(isSize){
            this.values = new int[size];
            for(int i = 0; i < size; i++){
                this.values[i] = 0;
            }
        }
        else{
            this.values = new int[1];
            this.values[0] = size;
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

    public void display(){
        for(int c : values){
            System.out.print(c + ",");
        }
        System.out.print(" ");
    }
}
