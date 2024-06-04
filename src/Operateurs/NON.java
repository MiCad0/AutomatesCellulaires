package Operateurs;

public class NON implements Operateur
{
    private int val;

    public NON(int val)
    {
        this.val = val;
    }
    @Override
    public int evaluer(){ return (val == 0) ? 1 : 0; }
}
