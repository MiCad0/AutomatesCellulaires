package Types;

public class G0 extends Voisinage{
    private static final Coords[] regle = {new Coords(true, 1)};
    public G0(TableauDynamiqueND grille, int ... index){
        super(regle, grille, index);
    }
}
