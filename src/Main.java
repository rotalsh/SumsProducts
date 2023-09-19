import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    // main function for output
    public static void main(String[] args) {
        NumberNumbers sums = new NumberNumbers();   // a NumberNumbers is HashMap<Integer, ArrayList<Integer>>
        NumberNumbers prods = new NumberNumbers();
        int n = 100;      // x + y <= n
        for(int x = 2; x <= n/2; x++){ 
            for(int y = x; x + y <= n; y++){ 
                int sum = x + y;
                int prod = x * y;
                sums.addNum(sum, prod);    // for a given sum s = x+y, map all its possible products of x*y
                prods.addNum(prod, sum);   // for a given product p = x*y, map all its possible sums of x+y
            }
        }
        ArrayList<Integer> singleProducts = getSingleProducts(prods);                 // get a list of products with only one mapped sum (single factor pair)
        ArrayList<Integer> validSums = getValidSums(sums, singleProducts);            // get a list of sums that aren't mapped to any products in singleProducts
        ArrayList<Integer> validProds = getValidProds(validSums, sums, prods);        // get a list of products where only one of its mapped sums appear in validSums
        ArrayList<SumProductPair> pairs = getValidPairs(validSums, validProds, sums); // get a list of sum-product pairs where for each sum, only one of its mapped product appear in validProds
                                                                                      //   and this is the product the sum is paired with
        System.out.println(pairs);
    }

    // Return a list of SumProductPairs, where 
    private static ArrayList<SumProductPair> getValidPairs(ArrayList<Integer> validSums,
                                                           ArrayList<Integer> validProds, NumberNumbers sums) {
        ArrayList<SumProductPair> validPairs = new ArrayList<>();
        HashMap<Integer, ArrayList<Integer>> currSums = sums.getNumNums();

        // Returns a list of SumProductPair where the each sum that appears only has one product in its mapped products that appear in validProds, and is paired with this product
        for(int sum: validSums){
            AtomicInteger numOfValidProds = new AtomicInteger(); // track number of validProds
            AtomicInteger correctProd = new AtomicInteger();     // track the most recent product that's contained in validProds
            // for each product mapped to sum
            for(int prod : currSums.get(sum)){
                if (numOfValidProds.get() > 1)  {
                    break;
                } else if (validProds.contains(prod)){  // if validProds contains prod set correctProd to prod and increment numOfValidProds
                    correctProd.set(prod);
                    numOfValidProds.getAndIncrement();
                }
            }
            // if numOfValidProds == 1 add new SumProductPair of sum and correctProd
            // numOfValidProds equals 1 if there is only one product in the list of products mapped to sum that's in validProds
            if(numOfValidProds.get() == 1){
                validPairs.add(new SumProductPair(sum, correctProd.get()));
            }
        }

        return validPairs;
    }

    // Returns a list of products where only one sum in its mapped sums appear in validSums
    private static ArrayList<Integer> getValidProds(ArrayList<Integer> validSums,
                                                    NumberNumbers sums, NumberNumbers prods) {
        ArrayList<Integer> validProds = new ArrayList<>();
        HashMap<Integer, ArrayList<Integer>> currSums = sums.getNumNums();
        HashMap<Integer, ArrayList<Integer>> currProds = prods.getNumNums();

        // for each sum in validSums
        for(int validSum : validSums){
            // go through its mapped products, and for each product,
            for(int prod : currSums.get(validSum)){
                AtomicBoolean canAdd = new AtomicBoolean(true);
                // go through its mapped sums,
                for(int checkSum : currProds.get(prod)){
                    // and if more than one sum in its mapped sums is in validSums,
                    if(checkSum != validSum && validSums.contains(checkSum)){
                        // set canAdd to false and leave loop
                        canAdd.set(false);
                        break;
                    }
                }
                // if canAdd is true, add product to validProds
                // canAdd is true if within the sums mapped to a product, there is only one of these sums in validSums
                if(canAdd.get()){
                    validProds.add(prod);
                }
            }
        }

        return validProds;
    }

    // Returns a list of products in prods that are mapped to only one sum (the product only has one valid factor pair)
    public static ArrayList<Integer> getSingleProducts(NumberNumbers prods){
        ArrayList<Integer> singleProducts = new ArrayList<>();
        HashMap<Integer, ArrayList<Integer>> currProds = prods.getNumNums();
        // for each product mapped to sums,
        for (int prod: currProds.keySet()){
            // if the product is mapped to only one sum, add it to singleProducts
            if (currProds.get(prod).size() == 1){
                singleProducts.add(prod);
            }
        }
        return singleProducts;
    }

    // Returns a list of sums that aren't mapped to any of the products in prods
    public static ArrayList<Integer> getValidSums(NumberNumbers sums, ArrayList<Integer> prods){
        HashMap<Integer, ArrayList<Integer>> currSums = sums.getNumNums();
        ArrayList<Integer> validSums = new ArrayList<>();
        // for each sum mapped to products, 
        for(int sum : currSums.keySet()){
            AtomicBoolean canAdd = new AtomicBoolean(true);
            // go through its list of mapped products
            for (int prod : currSums.get(sum)){
                // and check if any of them are in prods
                if(prods.contains(prod)){
                    // if so, set canAdd to false and leave the loop
                    canAdd.set(false);
                    break;
                }
            }
            // if canAdd is true, add sum to validSums
            // canAdd is true if every product mapped to a sum is not in the list prods
            if(canAdd.get()){
                validSums.add(sum);
            }
        }
        return validSums;
    }
}
