package Operateurs;

public class SI implements Operateur
{
    int val1, val2, val3;

    public SI(int val1, int val2, int val3)
    {
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
    }

    @Override
    public int evaluer(){ return (val1 != 0) ? val2 : val3; }
}