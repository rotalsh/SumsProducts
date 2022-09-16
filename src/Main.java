import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {


    public static void main(String[] args) {
        NumberNumbers sums = new NumberNumbers();
        NumberNumbers prods = new NumberNumbers();
        for(int i = 2; i <= 50; i++){ //change to 50
            for(int j = i; i + j <=100; j++){ // change to 100
                int sum = i + j;
                int prod = i * j;
                sums.addNum(sum, prod);
                prods.addNum(prod, sum);
            }
        }
        ArrayList<Integer> singleProducts = getSingleProducts(prods);
        ArrayList<Integer> validSums = getValidSums(sums, singleProducts);
        ArrayList<Integer> validProds = getValidProds(validSums, sums, prods);
        ArrayList<SumProductPair> pairs = getValidPairs(validSums, validProds, sums);
        System.out.println(pairs);
    }

    private static ArrayList<SumProductPair> getValidPairs(ArrayList<Integer> validSums,
                                                           ArrayList<Integer> validProds, NumberNumbers sums) {
        ArrayList<SumProductPair> validPairs = new ArrayList<>();
        HashMap<Integer, ArrayList<Integer>> currSums = sums.getNumNums();

        for(int sum: validSums){
            AtomicInteger numOfValidProds = new AtomicInteger();
            AtomicInteger correctProd = new AtomicInteger();
            for(int prod : currSums.get(sum)){
                if(validProds.contains(prod)){
                    correctProd.set(prod);
                    numOfValidProds.getAndIncrement();
                }
            }
            if(numOfValidProds.get() == 1){
                validPairs.add(new SumProductPair(sum, correctProd.get()));
            }
        }

        return validPairs;
    }

    private static ArrayList<Integer> getValidProds(ArrayList<Integer> validSums,
                                                    NumberNumbers sums, NumberNumbers prods) {
        ArrayList<Integer> validProds = new ArrayList<>();
        HashMap<Integer, ArrayList<Integer>> currSums = sums.getNumNums();
        HashMap<Integer, ArrayList<Integer>> currProds = prods.getNumNums();

        for(int validSum : validSums){
            for(int prod : currSums.get(validSum)){
                AtomicBoolean canAdd = new AtomicBoolean(true);
                for(int checkSum : currProds.get(prod)){
                    if(checkSum != validSum && validSums.contains(checkSum)){
                        canAdd.set(false);
                        break;
                    }
                }
                if(canAdd.get()){
                    validProds.add(prod);
                }
            }
        }

        return validProds;
    }

    public static ArrayList<Integer> getSingleProducts(NumberNumbers prods){
        ArrayList<Integer> singleProducts = new ArrayList<>();
        HashMap<Integer, ArrayList<Integer>> currProds = prods.getNumNums();
        for (int prod: currProds.keySet()){
            if (currProds.get(prod).size() == 1){
                singleProducts.add(prod);
            }
        }
        return singleProducts;
    }

    public static ArrayList<Integer> getValidSums(NumberNumbers sums, ArrayList<Integer> prods){
        HashMap<Integer, ArrayList<Integer>> currSums = sums.getNumNums();
        ArrayList<Integer> validSums = new ArrayList<>();
        for(int sum : currSums.keySet()){
            AtomicBoolean canAdd = new AtomicBoolean(true);
            for (int prod : currSums.get(sum)){
                if(prods.contains(prod)){
                    canAdd.set(false);
                    break;
                }
            }
            if(canAdd.get()){
                validSums.add(sum);
            }
        }
        return validSums;
    }


}