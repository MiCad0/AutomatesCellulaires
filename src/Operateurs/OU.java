package Operateurs;

public class OU extends Operateur
{
    private Operateur val1, val2;

    public OU(Operateur val1, Operateur val2)
    {
        this.val1 = val1;
        this.val2 = val2;
    }

    @Override
    public int evaluer(){ return ((val1.evaluer() == 0) && (val2.evaluer() == 0)) ? 0 : 1; }
}