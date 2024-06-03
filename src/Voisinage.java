/*Dans certaines r`egles d’´evolution des cellules, pour calculer la nouvelle valeur
d’une cellule, il est n´ecessaire d’explorer certaines cellules situ´ees autour de la
cellule cible. Ces cellules composent ce que l’on appelle le voisinage de la cellule,
et il existe diff´erents voisinages.
On souhaitera pouvoir d´efinir, dans le fichier de configuration, nos propres voisinages, mais certains voisinages consid´er´es basiques seront d´efinis par
avance dans votre code. Dans tous les cas (afin de simplifier votre programme),
un voisinage sera toujours nomm´e par un G majuscule suivi d’un num´ero et
´eventuellement d’une ´etoile. Voici les voisinages que nous souhaiterons d´efinir
de base dans le code :
• G0 : repr´esente la cellule examin´ee seule.
• G2 : valable en 1D, ce voisinage repr´esente la cellule examin´ee, ainsi que
sa voisine de droite et sa voisine de gauche.
• G4 : valable en 2D, ce voisinage repr´esente la cellule examin´ee, ainsi que
sa voisine du dessus, du dessous, de droite et de gauche.
• G8 : valable en 2D, ce voisinage repr´esente G4, ainsi que la voisine en
haut `a droite, en haut `a gauche, en bas `a droite et en bas `a gauche.
• G6 : valable en 3D, ce voisinage repr´esente la cellule examin´ee ainsi que
sa voisine du dessus, du dessous, de droite, de gauche, de devant et de
derri`ere.
• G26 : valable en 3D, ce voisinage repr´esente la cellule examin´ee ainsi que
toutes se voisines qui touchent une face, une arˆete ou un coin de la cellule
3D.
Nous d´efinirons aussi les Gk*, avec k ∈ {2, 4, 8, 6, 26}, qui sont ´egaux au
voisinage Gk mais o`u la cellule examin´ee n’est pas prise en compte.
Afin de repr´esenter un voisinage, il vous est possible (mais pas obligatoire)
de regarder les interface Iterator et Iterable de Java. Dans votre impl´ementation
5
des classes de voisinage, il sera important de montrer qu’un voisinage n’est rien
d’autre qu’une r`egle de parcours de cellules voisines d’une certaine cellule.
Il sera possible, dans le fichier de configuration, de d´efinir ses propres voisinages. Pour ce faire, il suffira de lister les coordonn´ees des voisins d’une cellule
si cette derni`ere a pour coordonn´ees l’origine. Ainsi, le voisinage G4* pourrait
s’´ecrire :
G4∗ = {(−1, 0),(1, 0),(0, −1),(0, 1)}
Si on souhaitait d´efinir un voisinage G3 en 3d, avec la cellule examin´ee en
elle-mˆeme, sa voisine en haut `a droite et sa voisine du dessous, on ´ecrirait :
G3 = {(0, 0, 0),(1, 1, 0),(−1, 0, 0)}
 */

import java.util.ArrayList;

public class Voisinage{
    private ArrayList<Cellule> voisinage;
    private Coords[] regle;

    public Voisinage(Coords[] regle, TableauDynamiqueND grille, int ... index){
        this.regle = regle;
        this.voisinage = new ArrayList<Cellule>();
        // On crée le voisinage en fonction des règles de voisinage
        int n = grille.getDimension();
        int[] tmp = new int[n]; // On crée un tableau de taille n pour stocker les coordonnées du voisin à ajouter
        for(Coords e : regle){
            for(int i = 0; i<n; i++){
                tmp[i] = index[i];
                if(tmp[i] + e.getCoords()[i] < 0 || tmp[i] + e.getCoords()[i] >= grille.getTaille()){ // WARNING A CHANGER
                    this.voisinage.add(new Cellule(false));
                    break;
                }
                if(i < e.getCoords().length){
                    tmp[i] += e.getCoords()[i];
                }
            }
            this.voisinage.add(new Cellule(grille.getState(tmp)));
            // System.out.print("Voisinage ajouté : " + grille.getState(tmp) + " en " );
            // for(int i = 0; i < n; i++){
            //     System.out.print(tmp[i] + " ");
            // }
            // System.out.println("");
        }
        // System.out.println("");
    }

    public ArrayList<Cellule> getVoisinage(){
        return this.voisinage;
    }

    public Coords[] getRegle(){
        return this.regle;
    }

    public void display(){
        for(Cellule c : voisinage){
            System.out.print(c.getEtat() + " ");
        }
        System.out.println();
    }

}
