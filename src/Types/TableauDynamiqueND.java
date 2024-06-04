package Types;
import java.util.Arrays;

    /* En java, mˆeme si la taille d’un tableau peut ˆetre initialis´ee dynamiquement, sa
    dimension (int [][] tab, ici tab est de dimension 2) est fix´ee `a l’impl´ementation.
    Pour pouvoir g´en´erer des grilles de dimension d o`u d est donn´ee en entr´ee du
    programme, vous devrez impl´ementer votre propre structure de donn´ee. Pour
    cela, il sera n´ecessaire de proposer une classe TableauDynamiqueND (vous
    serez libres de choisir le noms qui vous conviendra) dont la d´efinition sera
    r´ecursive : un tableau dynamique n-dimensionnel de taille k1 × k2 × ... × kn
    r´ealisera l’allocation de k1 tableaux dynamiques (n−1)-dimensionnels de tailles
    k2 × ... × kn (vous devrez peut-ˆetre vous renseigner sur les m´ethodes `a nombre
    variable de param`etres). Vous pourrez ´egalement proposer une classe Cellule
    qui repr´esentera les cellules de la grille.
    Les automates cellulaires sont ´etudi´es dans le cadre o`u la grille est de taille
    infinie, le support physique qu’est l’ordinateur nous obligera `a donner une taille
    maximale. Toute cellule ”en-dehors” de la grille sera consid´er´ee comme toujours
    ´eteinte si sa valeur doit ˆetre prise en compte dans une r`egle.*/

public class TableauDynamiqueND {
    private int dimension;
    private int taille;
    private TableauDynamiqueND[] tab;
    Coords currentCoords;

    public TableauDynamiqueND(Coords coords, int ... taille){
        this.currentCoords = coords;
        if(taille.length == 0){
            this.taille = 1;
            return;
        }
        else{
            this.taille = taille[0];
            this.dimension = taille.length;
            this.tab = new TableauDynamiqueND[this.taille];
        }
        if(dimension == 1){
            for(int i = 0; i < this.taille; i++){
                currentCoords.setCoord(currentCoords.getCoords().length - 1, i);
                this.tab[i] = new Cellule(false, currentCoords.getCoords());
            }
        }
        else if (dimension > 1){
            for(int i = 0; i < this.taille; i++){
                currentCoords.setCoord(currentCoords.getCoords().length - dimension, i);
                this.tab[i] = new TableauDynamiqueND(currentCoords, Arrays.copyOfRange(taille, 1, taille.length));
            }
        }
    }

    public TableauDynamiqueND clone(){
        TableauDynamiqueND res = new TableauDynamiqueND(new Coords(this.currentCoords.getCoords().length), this.taille);
        res.dimension = this.dimension;
        for(int i = 0; i < this.taille; i++){
            res.tab[i] = this.tab[i].clone();
        }
        return res;
    }

    public int getDimension(){
        return this.dimension;
    }

    public int getTaille(){
        return this.taille;
    }

    public TableauDynamiqueND[] getTab(){
        return this.tab;
    }

    public void changeState(int ... index){
        if(index.length != dimension){
            throw new IllegalArgumentException("Le nombre d'index ne correspond pas à la dimension");
        }
        for(int i = 0; i < index.length; i++){
            if(index[0] >= taille){
                throw new IllegalArgumentException("Index hors de la grille");
            }
        }
        if(index.length == 1){
            ((Cellule)tab[index[0]]).setEtat(!((Cellule)tab[index[0]]).getEtat());
        }
        else{
            ((TableauDynamiqueND)tab[index[0]]).changeState(Arrays.copyOfRange(index, 1, index.length));
        }
    }

    public Boolean getState(int ... index){
        if(index.length != dimension){
            throw new IllegalArgumentException("Le nombre d'index ne correspond pas à la dimension");
        }
        for(int i = 0; i < index.length; i++){
            if(index[0] >= taille){
                throw new IllegalArgumentException("Index hors de la grille");
            }
        }
        if(index.length == 1){
            return ((Cellule)tab[index[0]]).getEtat();
        }
        else{
            return ((TableauDynamiqueND)tab[index[0]]).getState(Arrays.copyOfRange(index, 1, index.length));
        }
    }

    public void display(){
        for(int i = 0; i < taille; i++){
            tab[i].display();
        }
        System.out.println();
    }

    public void display(int ... coupe){
        if(coupe.length == 1){
            tab[coupe[0]].display();
        }
        else{
            tab[coupe[0]].display(Arrays.copyOfRange(coupe, 1, coupe.length));
        }
    }

    // Prend un couple de m coordonnées et renvoie le tableau dynamique n-m dimensionnel correspondant
    public TableauDynamiqueND slice(int ... coupe){
        if (coupe.length %2 != 0){
            throw new IllegalArgumentException("Le nombre de coordonnées doit être pair");
        }

        if(coupe.length == 0){
            return this;
        }
        else{
            int[] newTailles = new int[coupe.length/2];
            for(int i = 0; i < coupe.length; i+=2){
                newTailles[i/2] = coupe[i+1] - coupe[i]+1;
            }
            TableauDynamiqueND res = new TableauDynamiqueND(new Coords(newTailles.length), newTailles);
            for(int i = coupe[0]; i <= coupe[1]; i++){
                res.tab[i-coupe[0]] = tab[i].slice(Arrays.copyOfRange(coupe, 2, coupe.length));
            }
            return res;
        }
    }

    public Voisinage voisinage(Coords[] regle, int ... index){
        return new Voisinage(regle, this, index);
    }

    // récupère les coordonnées des cellules vivantes
    public Coords[] getAlive(){
        Coords[] res = new Coords[taille^dimension];
        int index = 0;
        if(dimension == 1){
            for(int i = 0; i < taille; i++){
                if(((Cellule)tab[i]).getEtat()){
                    res[index++] = new Coords(((Cellule)tab[i]).getCoords());
                }
            }
        }
        else{
            for(int i = 0; i < taille; i++){
                Coords[] alive = ((TableauDynamiqueND)tab[i]).getAlive();
                for(Coords c : alive){
                    if(c != null){
                        res[index++] = c;
                    }
                }
            }
        }
        return res;
    }


    public static void main(String[] args){
        int tailles[] = {5, 5};
        TableauDynamiqueND tab = new TableauDynamiqueND(new Coords(tailles.length), tailles);
        // tab.display();
		tab.changeState(1,1);
		tab.changeState(2,1);
		tab.changeState(3,1);
        System.out.println();
        tab.slice( 1, 1).display();
        Coords[] alive = tab.getAlive();
        for(Coords c : alive){
            if(c != null){
                c.display();
            }
        }
        System.out.println();
        System.out.println();
        System.out.println();
        Coords[] regle = {new Coords(0, 0), new Coords(-1, -1), new Coords(-1, 0), new Coords(-1, 1), new Coords(0, -1), new Coords(0, 1), new Coords(1, -1), new Coords(1, 0), new Coords(1, 1)};
        Voisinage voisinage = tab.voisinage(regle, 2, 2);
        voisinage.display();

        TableauDynamiqueND tmp = tab.clone();
        RegleDuJeu regleDuJeu = new RegleDuJeu(new int[]{3}, new int[]{4, 3});
        System.out.println();
        System.out.println();
        System.out.println();
        tab.display();
        System.out.println();
        System.out.println();
        System.out.println();

        for(int n = 0; n<30; ++n){
			for(int i = 0; i < 5; i++){
				for(int j = 0; j < 5; j++){
					if(tab.getState(i,j)){
						if(!regleDuJeu.estSurvie(tab.voisinage(regle,i,j))){
							tmp.changeState(i,j);
						}
					}
					else{
						if(regleDuJeu.estNee(tab.voisinage(regle,i,j))){
							tmp.changeState(i,j);
						}
					}
				}
			}
			tab = tmp.clone();
            tab.display();
			try{
				Thread.sleep(300);
			}
			catch(InterruptedException e){
				e.printStackTrace();
			}
		}
    }
}
