package Types;

public class G4e extends Voisinage{
    private static final Coords[] regle = {                              new Coords(-1,0),
                                            new Coords( 0,-1)                              , new Coords( 0,1),
                                                                         new Coords( 1,0)};
    public G4e(TableauDynamiqueND grille, int ... index){
        super(regle, grille, index);
    }
}
