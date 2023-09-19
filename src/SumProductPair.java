public class SumProductPair {
    private int sum;
    private int prod;

    // EFFECTS: makes new SumProductPair with fields sum and prod
    public SumProductPair(int sum, int prod){
        this.sum = sum;
        this.prod = prod;
    }

    // EFFECTS: returns string representation of SumProductPair
    @Override
    public String toString(){
        return "sum: " + sum + "; product: " + prod;
    }
}
