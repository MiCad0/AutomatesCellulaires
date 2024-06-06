package Operateurs;
import Types.Voisinage;

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
        return v.getNbAlive();
    }
}
