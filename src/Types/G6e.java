package Types;

public class G6e extends Voisinage{
    private static final Coords[] regle = { new Coords(0,0,-1), new Coords(0,-1,0), new Coords(-1,0,0),
                                            new Coords(0,0, 1), new Coords(0, 1,0), new Coords( 1, 0,0),
                                            };
    public G6e(TableauDynamiqueND grille, int ... index){
        super(regle, grille, index);
    }
}
