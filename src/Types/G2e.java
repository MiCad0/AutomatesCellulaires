package Types;

public class G2e extends Voisinage{
    private static final Coords[] regle = {new Coords(false, 1), new Coords(false, -1)};
    public G2e(TableauDynamiqueND grille, int ... index){
        super(regle, grille, index);
    }
}
