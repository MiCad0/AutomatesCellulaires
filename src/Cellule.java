public class Cellule extends TableauDynamiqueND{
    
    private Boolean etat;

    public Cellule(Boolean etat){
        this.etat = etat;
    }

    public Cellule(){
        this.etat = false;
    }

    public Boolean getEtat(){
        return this.etat;
    }

    public void setEtat(Boolean etat){
        this.etat = etat;
    }

    public void display(){
        System.out.print(this.etat + " ");
    }

    public void display(int ... index){
        if(index.length != 0){
            throw new IllegalArgumentException("Le nombre d'index ne correspond pas à la dimension");
        }
        System.out.print(this.etat + " ");
    }

}
