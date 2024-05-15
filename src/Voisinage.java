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
            // if(grille.getDimension() != 1){
            //     throw new IllegalArgumentException("Le type de voisinage ne correspond pas à la dimension de la grille");
            // }
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
            for(int i = 0; i < 3; ++i){
                for(int j = 0; j < 3; ++j){
                    this.voisinage[i*3 + j] = (Cellule)grille.getTab()[index[0] + i - 1].getTab()[index[1] + j - 1];
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
