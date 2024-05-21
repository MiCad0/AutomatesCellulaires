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


public class Voisinage{
    private Cellule [] voisinage;
    private int type;

    public Voisinage(int type, TableauDynamiqueND grille, int ... index){
        this.type = type;

        // On crée le voisinage en fonction du type de voisinage
        
        // Soi-même
        
        if (type == 0){
            this.voisinage = new Cellule[1];
            this.voisinage[0] = (Cellule)grille.getTab()[index[0]];
        }
        
        // Ses deux voisins 1D
        else if(type == 2){
            this.voisinage = new Cellule[3];
            for(int i = 0; i < 3; ++i){
                int n = index[0] + i - 1;
                if(n<0 || n>=grille.getTaille()){
                    this.voisinage[i] = new Cellule(false);
                }
                else{
                    TableauDynamiqueND tmpTab = grille.getTab()[n];
                    while(tmpTab.getDimension() > 1){
                        tmpTab = tmpTab.getTab()[0];
                    }
                    this.voisinage[i] = (Cellule)tmpTab.getTab()[n];
                }
            }
        }

        // Ses voisins directs en 2D
        else if(type == 4){
            this.voisinage = new Cellule[5];
            int x = 0;
            for(int i = 0; i < 3; ++i){
                int n = index[0]+i-1;
                for(int j = 0; j < 3; ++j){
                    if((i == 0 && j == 0) || (i == 2 && j == 0) || (i == 0 && j == 2) || (i == 2 && j == 2)){
                        continue;
                    }
                    if ((n < 0 ) || (n >= grille.getTaille()) || (index[1]+j-1 < 0)){
                        this.voisinage[x++] = new Cellule(false);
                    }
                    else if (index[1]+j-1 < grille.getTab()[n].getTaille()){
                        TableauDynamiqueND tmpTab = grille.getTab()[n];
                        while(tmpTab.getDimension() > 2){
                            tmpTab = tmpTab.getTab()[0];
                        }
                        this.voisinage[x++] = (Cellule)tmpTab.getTab()[n].getTab()[index[1] + j - 1];
                    }
                    else{
                        this.voisinage[x++] = new Cellule(false, new Coords(i, j));
                    }
                }
            }
        }

        // Ses voisins directs + en diagonale en 2D
        else if(type == 8){
            this.voisinage = new Cellule[9];
            for(int i = 0; i < 3; ++i){
                int n = index[0]+i-1;
                for(int j = 0; j < 3; ++j){
                    if ((index[0]+i-1 < 0 ) || (index[0]+i-1 >= grille.getTaille()) || (index[1]+j-1 < 0)){
                        this.voisinage[i*3 + j] = new Cellule(false);
                    }
                    else if (index[1]+j-1 < grille.getTab()[index[0]+i-1].getTaille()){
                        TableauDynamiqueND tmpTab = grille.getTab()[n];
                        while(tmpTab.getDimension() > 2){
                            tmpTab = tmpTab.getTab()[0];
                        }
                        this.voisinage[i*3 + j] = (Cellule)tmpTab.getTab()[index[0] + i - 1].getTab()[index[1] + j - 1];
                    }
                    else{
                        this.voisinage[i*3 + j] = new Cellule(false, new Coords(i, j));
                    }
                }
            }
        }

        // Ses voisins directs en 3D
        else if(type == 6){
            this.voisinage = new Cellule[7];
            int x = 0;
            for(int i = 0; i < 3; ++i){
                for(int j = 0; j < 3; ++j){
                    for(int k = 0; k < 3; ++k){
                        if ((index[0]+i-1 < 0 ) || (index[0]+i-1 >= grille.getTaille()) || 
                            (index[1]+j-1 < 0) || (index[1]+j-1 >= grille.getTab()[index[0]+i-1].getTaille()) ||
                            (index[2]+k-1 < 0) || (index[2]+k-1 >= grille.getTab()[index[0]+i-1].getTab()[index[1]+j-1].getTaille())){
                            this.voisinage[x++] = new Cellule(false, new Coords(i, j, k));
                        }
                        else{
                            TableauDynamiqueND tmpTab = grille.getTab()[index[0] + i - 1];
                            while(tmpTab.getDimension() > 3){
                                tmpTab = tmpTab.getTab()[0];
                            }
                            this.voisinage[x++] = (Cellule)tmpTab.getTab()[index[0] + i - 1].getTab()[index[1] + j - 1].getTab()[index[2] + k - 1];
                        }
                    }
                }
            }
        }
        else{
            throw new IllegalArgumentException("Type de voisinage non reconnu");
        }
        System.out.println("");
    }

    public Cellule[] getVoisinage(){
        return this.voisinage;
    }

    public int getType(){
        return this.type;
    }

    public void display(){
        for(int i = 0; i < this.voisinage.length; i++){
            this.voisinage[i].display();
        }
        System.out.println();
    }

}
