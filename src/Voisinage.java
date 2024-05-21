public class Voisinage{
    private Cellule [] voisinage;
    private int type;

    public Voisinage(int type, TableauDynamiqueND grille, int ... index){
        this.type = type;

        if (type == 0){
            this.voisinage = new Cellule[1];
            this.voisinage[0] = (Cellule)grille.getTab()[index[0]];
        }
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
                        this.voisinage[x++] = new Cellule(false);
                    }
                }
            }
        }
        else if(type == 8){
            this.voisinage = new Cellule[9];
            for(int i = 0; i < 3; ++i){
                for(int j = 0; j < 3; ++j){
                    if ((index[0]+i-1 < 0 ) || (index[0]+i-1 >= grille.getTaille()) || (index[1]+j-1 < 0)){
                        this.voisinage[i*3 + j] = new Cellule(false);
                    }
                    else if (index[1]+j-1 < grille.getTab()[index[0]+i-1].getTaille()){
                        this.voisinage[i*3 + j] = (Cellule)grille.getTab()[index[0] + i - 1].getTab()[index[1] + j - 1];
                    }
                    else{
                        this.voisinage[i*3 + j] = new Cellule(false);
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
