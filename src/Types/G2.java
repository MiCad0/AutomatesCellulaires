package Types;

public class G2 extends Voisinage{
    private static final Coords[] regle = {new Coords(false, 1), new Coords(false, 0), new Coords(false, -1)};
    public G2(TableauDynamiqueND grille, int ... index){
        super(regle, grille, index);
    }
}
