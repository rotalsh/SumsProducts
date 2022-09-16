public class SumProductPair {
    private int sum;
    private int prod;

    public SumProductPair(int sum, int prod){
        this.sum = sum;
        this.prod = prod;
    }

    @Override
    public String toString(){
        return "sum: " + sum + "; product: " + prod;
    }
}
