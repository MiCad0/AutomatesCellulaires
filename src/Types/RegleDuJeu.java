package Types;
public class RegleDuJeu {
    private int[] naissance;
    private int[] survie;

    public RegleDuJeu(int[] naissance, int[] survie){
        this.naissance = naissance;
        this.survie = survie;
    }

    public int[] getNaissance(){
        return this.naissance;
    }

    public int[] getsurvie(){
        return this.survie;
    }

    public void display(){
        System.out.print("Naissance : ");
        for(int i : naissance){
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.print("survie : ");
        for(int i : survie){
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public boolean estNee(Voisinage cellules){
        for(int i: naissance){
            if(cellules.getNbAlive() == i)
                return true;
        }
        return false;
    }

    public boolean estSurvie(Voisinage cellules){
        for(int i: survie){
            if(cellules.getNbAlive() == i)
                return true;
        }
        return false;
    }
}
