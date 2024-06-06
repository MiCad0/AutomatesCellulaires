package Types;
public class Cellule extends TableauDynamiqueND{
    
    private Boolean etat;
    private Coords Coords;

    public Cellule(Boolean etat, int ... coords){
        super(new Coords( coords));
        this.etat = etat;
        this.Coords = new Coords(true, coords.length);
        for(int i = 0; i < coords.length; i++){
            this.Coords.setCoord(i, coords[i]);
        }
    }

    public Cellule(Boolean etat){
        super(new Coords(true, 0));
        this.etat = etat;
    }

    public Cellule clone(){
        return new Cellule(this.etat, this.Coords.getCoords());
    }

    public Boolean getEtat(){
        return this.etat;
    }

    public void setEtat(Boolean etat){
        this.etat = etat;
    }

    // public void display(){
    //     for(int c : Coords.getCoords()){
    //         System.out.print(c + ",");
    //     }
    //     System.out.print(" ");
    // }
    
    public void display(){
        if(this.etat){
            System.out.print("X");
        }
        else{
            System.out.print("O");
        }
    }

    public void display(int ... index){
        throw new IllegalArgumentException("Impossible d'afficher la coupe d'une cellule");
    }

    public int[] getCoords(){
        return this.Coords.getCoords();
    }


}
