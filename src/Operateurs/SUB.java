package Operateurs;

public class SUB extends Operateur
{
    private Operateur val1, val2;

    public SUB(Operateur val1, Operateur val2)
    {
        this.val1 = val1;
        this.val2 = val2;
    }

    @Override
    public int evaluer(){ return val1.evaluer() - val2.evaluer(); }
}
