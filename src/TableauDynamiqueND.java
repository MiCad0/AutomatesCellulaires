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

    public TableauDynamiqueND(int ... taille){
        if(taille.length == 0){
            this.taille = 1;
        }
        else{
            this.taille = taille[0];
            this.tab = new TableauDynamiqueND[this.taille];
        }
        this.dimension = taille.length;
        if(dimension == 1){
            for(int i = 0; i < this.taille; i++){
                tab[i] = new Cellule();
            }

        }
        else if (dimension > 1){
            for(int i = 0; i < this.taille; i++){
                tab[i] = new TableauDynamiqueND(Arrays.copyOfRange(taille, 1, dimension));
            }
        }
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
            TableauDynamiqueND res = new TableauDynamiqueND(newTailles);
            for(int i = coupe[0]; i <= coupe[1]; i++){
                res.tab[i-coupe[0]] = tab[i].slice(Arrays.copyOfRange(coupe, 2, coupe.length));
            }
            return res;
        }
    }

    public Voisinage voisinage(int type, int ... index){
        return new Voisinage(type, this, index);
    }


    public static void main(String[] args){
        int tailles[] = {3, 10, 5};
        TableauDynamiqueND tab = new TableauDynamiqueND(tailles);
        // tab.display();
        tab.changeState(2, 2, 2);
        System.out.println();
        // tab.slice( 3, 3, 3, 3).display();
        Voisinage voisinage = tab.voisinage(4, 2, 2, 2);
        voisinage.display();
    }
}
