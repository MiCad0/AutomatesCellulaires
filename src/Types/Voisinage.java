package Types;

import java.util.ArrayList;

public class Voisinage{
    private ArrayList<Cellule> voisinage; // Liste des cellules voisines
    private Coords[] regle; // Règles de voisinage

    public Voisinage(Coords[] regle, TableauDynamiqueND grille, int ... index){
        this.regle = regle;
        this.voisinage = new ArrayList<Cellule>(); // Initialisation de la liste des cellules voisines
        int n = grille.getDimension(); // Dimension de la grille
        int[] newVoisin = new int[n]; // Tableau pour stocker les coordonnées du voisin à ajouter
        Boolean oob = false; // Variable pour vérifier si le voisin est en dehors de la grille
        for(Coords e : regle){ // Pour chaque règle de voisinage
            for(int i = 0; i<n; i++){ // Pour chaque dimension de la grille
                oob = false;
                newVoisin[i] = index[i]; // On initialise les coordonnées du voisin avec les coordonnées de la cellule courante
                if(newVoisin[i] + e.getCoords()[i] < 0 || newVoisin[i] + e.getCoords()[i] >= grille.getTaille()){ // Si le voisin est en dehors de la grille
                    this.voisinage.add(new Cellule(false)); // On ajoute une cellule morte au voisinage
                    oob = true; // On indique que le voisin est en dehors de la grille
                    break; // On arrête la boucle pour passer à la règle de voisinage suivante
                }
                if(i < e.getCoords().length){ // Tant que la coordonnée de la règle de voisinage a une dimension inférieure à celle de la grille 
                    newVoisin[i] += e.getCoords()[i]; // On ajoute les coordonnées aux coordonnées du voisin
                }
                if(grille.getTab()[newVoisin[i]] == null){ // Si la cellule du voisin est vide
                    this.voisinage.add(new Cellule(false)); // On ajoute une cellule morte au voisinage
                    oob = true; // On indique que le voisin est en dehors de la grille
                    break; // On arrête la boucle pour passer à la règle de voisinage suivante
                }
            }
            if(!oob) // Si le voisin n'est pas en dehors de la grille
                this.voisinage.add(new Cellule(grille.getState(newVoisin))); // On ajoute la cellule du voisin au voisinage
        }
    }

    public ArrayList<Cellule> getVoisinage(){
        return this.voisinage; // Retourne la liste des cellules voisines
    }

    public Coords[] getRegle(){
        return this.regle; // Retourne les règles de voisinage
    }

    public int getNbAlive(){
        int res = 0; // Compteur pour le nombre de cellules vivantes
        for(Cellule c : voisinage){ // Pour chaque cellule du voisinage
            if(c.getEtat()){ // Si la cellule est vivante
                res++; // On incrémente le compteur
            }
        }
        return res; // Retourne le nombre de cellules vivantes
    }

    public void display(){
        for(Cellule c : voisinage){ // Pour chaque cellule du voisinage
            System.out.print(c.getEtat() + " "); // Affiche l'état de la cellule (vivante ou morte)
        }
        System.out.println(); // Passe à la ligne suivante
    }

}
