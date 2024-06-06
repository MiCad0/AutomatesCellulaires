package Operateurs;

public class NON extends Operateur
{
    private Operateur val;

    public NON(Operateur val)
    {
        this.val = val;
    }
    @Override
    public int evaluer(){ return (val.evaluer() == 0) ? 1 : 0; }
}
