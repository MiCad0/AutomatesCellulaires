package Operateurs;

public class SUB implements Operateur
{
    private int val1, val2;

    public SUB(int val1, int val2)
    {
        this.val1 = val1;
        this.val2 = val2;
    }

    @Override
    public int evaluer(){ return val1 - val2; }
}
