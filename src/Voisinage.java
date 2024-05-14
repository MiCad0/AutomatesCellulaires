public class Voisinage extends TableauDynamiqueND{
    private int dimension;
    private TableauDynamiqueND voisinage[];
    private int type;

    public Voisinage(int type, TableauDynamiqueND grille, int ... index){
        this.type = type;

        if (type == 0){
            this.dimension = 0;
            this.voisinage[0] = new Cellule();
            if (grille.getState(index))
                this.voisinage[0].changeState(index);
        }
        else if(type == 2){
            this.dimension = 1;
            for(int i = 0; i < 3; i++){
                this.voisinage[i] = new Cellule();
            }
        }
        else if(type == 4){
            this.dimension = 2;
            for(int i = 0; i < 5; i++){
                this.voisinage[i] = new TableauDynamiqueND(5);
            }
        }
        else if(type == 8){
            this.dimension = 2;
            for(int i = 0; i < 9; i++){
                this.voisinage[i] = new TableauDynamiqueND(5, 5);
            }
        }
        else if(type == 6){
            this.dimension = 3;
            for(int i = 0; i < 7; i++){
                this.voisinage[i] = new TableauDynamiqueND(3, 3, 3);
            }
        }
        else if(type == 26){
            this.dimension = 3;
            for(int i = 0; i < 27; i++){
                this.voisinage[i] = new TableauDynamiqueND(3, 3, 3);
            }
        }
        else{
            throw new IllegalArgumentException("Type de voisinage non reconnu");
        }
    }
    
}
