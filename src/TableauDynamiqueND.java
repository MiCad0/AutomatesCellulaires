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

    public TableauDynamiqueND(int dimension, int taille){
        this.dimension = dimension;
        this.taille = taille;
        for(int i = 0; i < dimension; i++){
            tab[i] = new TableauDynamiqueND(dimension - 1, taille);
        }
        
    }

    public int getDimension(){
        return this.dimension;
    }

    public int getTaille(){
        return this.taille;
    }

}
