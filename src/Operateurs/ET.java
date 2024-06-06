package Operateurs;

public class ET extends Operateur
{
    private Operateur val1, val2;

    public ET(Operateur val1, Operateur val2)
    {
        this.val1 = val1;
        this.val2 = val2;
    }

    @Override
    public int evaluer(){ return ((val1.evaluer() != 0) && (val2.evaluer() != 0)) ? 1 : 0; }
}
