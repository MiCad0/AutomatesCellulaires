package Operateurs;

public class OU implements Operateur
{
    private int val1, val2;

    public OU(int val1, int val2)
    {
        this.val1 = val1;
        this.val2 = val2;
    }

    @Override
    public int evaluer(){ return ((val1 == 0) && (val2 == 0)) ? 0 : 1; }
}