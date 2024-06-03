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

    public boolean estNee(Cellule[] cellules){
        int x = 0;
        for(int i: naissance){
            x = 0;
            for(Cellule c : cellules){
                if(c.getEtat()){
                    x++;
                }
            }
            if(x == i){
                return true;
            }
        }
        return false;
    }

    public boolean estSurvie(Cellule[] cellules){
        int x = 0;
        for(int i: survie){
            x = 0;
            for(Cellule c : cellules){
                if(c.getEtat()){
                    x++;
                }
            }
            if(x == i+1){
                return true;
            }
        }
        return false;
    }
}
