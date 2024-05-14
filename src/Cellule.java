public class Cellule {
    
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

}
