package Operateurs;
import Types.Voisinage;
import Types.Cellule;

import java.util.ArrayList;

public class COMPTER implements Operateur
{
    private Voisinage v;

    public COMPTER(Voisinage v)
    {
        this.v = v;
    }

    @Override
    public int evaluer()
    {
        int r = 0;
        ArrayList<Cellule> voisinages = v.getVoisinage();
        for(Cellule c : voisinages)
            if(c.getEtat())
                r += 1;
        return r;
    }
}
