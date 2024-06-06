package Operateurs;

public class SI extends Operateur
{
    private Operateur val1, val2, val3;

    public SI(Operateur val1, Operateur val2, Operateur val3)
    {
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
    }

    @Override
    public int evaluer(){ return (val1.evaluer() != 0) ? val2.evaluer() : val3.evaluer(); }
}