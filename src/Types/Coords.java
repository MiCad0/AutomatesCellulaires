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

    @Override
    public String toString(){
        String res = "[";
        int i = 0;
        for(int c : values){
            if(i++ == 0){
                res += c;
            }
            else{
                res += "," + c;
            
            }
        }
        res += "]";
        return res;
    }

    public void display(){
        System.out.print(this);
    }
}
