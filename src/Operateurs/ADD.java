package Operateurs;

public class ADD implements Operateur
{
    private int val1, val2;

    public ADD(int val1, int val2)
    {
        this.val1 = val1;
        this.val2 = val2;
    }

    @Override
    public int evaluer(){ return val1 + val2; }
}
