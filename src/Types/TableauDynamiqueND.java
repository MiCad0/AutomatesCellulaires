package Types;
import java.util.Arrays;

import Operateurs.*;


public class TableauDynamiqueND {
    private int dimension;
    private int taille;
    private TableauDynamiqueND[] tab;
    private Coords currentCoords;

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
        TableauDynamiqueND res = new TableauDynamiqueND(new Coords(true, this.currentCoords.getCoords().length), this.taille);
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

    public void setTab(TableauDynamiqueND[] tab){
        this.tab = tab;
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

    public void setState(Boolean state, int ... index){
        if(index.length != dimension){
            throw new IllegalArgumentException("Le nombre d'index ne correspond pas à la dimension");
        }
        for(int i = 0; i < index.length; i++){
            if(index[0] >= taille){
                throw new IllegalArgumentException("Index hors de la grille");
            }
        }
        if(index.length == 1){
            ((Cellule)tab[index[0]]).setEtat(state);
        }
        else{
            ((TableauDynamiqueND)tab[index[0]]).setState(state, Arrays.copyOfRange(index, 1, index.length));
        }
    }

    public void setState(int state, int ... coords){
        if(state == 1){
            setState(true, coords);
        }
        else if(state == 0){
            setState(false, coords);
        }
        else{
            throw new IllegalArgumentException("L'état doit être 0 ou 1");
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

    // Prend m coordonnées et renvoie le tableau dynamique n-m dimensionnel correspondant
    public TableauDynamiqueND slice(int ... coupe){
        if(coupe.length == 0){
            return this;
        }
        else{
            return tab[coupe[0]].slice(Arrays.copyOfRange(coupe, 1, coupe.length));
        }
    }

    public Voisinage voisinage(Coords[] regle, int ... index){
        return new Voisinage(regle, this, index);
    }

    public int[] getCoords(){
        return this.currentCoords.getCoords();
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
}
