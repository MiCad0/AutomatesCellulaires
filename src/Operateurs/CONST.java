package Operateurs;

public class CONST extends Operateur{
    private int val;

    public CONST(int val){
        this.val = val;
    }

    @Override
    public int evaluer(){ return val; }
}
