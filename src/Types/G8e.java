package Types;

public class G8e extends Voisinage{
    private static final Coords[] regle = { new Coords(-1,-1), new Coords(-1,0), new Coords(-1,1),
                                            new Coords( 0,-1), new Coords( 0,1),
                                            new Coords( 1,-1), new Coords( 1,0), new Coords( 1,1)};
    public G8e(TableauDynamiqueND grille, int ... index){
        super(regle, grille, index);
    }
}
