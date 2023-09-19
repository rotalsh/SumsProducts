import java.util.ArrayList;
import java.util.HashMap;

public class NumberNumbers {
    private HashMap<Integer, ArrayList<Integer>> numNums;

    // EFFECTS: makes new NumberNumbers with numNums an empty hashmap
    public NumberNumbers(){
        this.numNums = new HashMap<>();
    }

    // MODIFIES: this
    // EFFECTS: adds relNum to the list mapped to mainNum - if no list exists, create the list then add
    public void addNum(int mainNum, int relNum){
        this.numNums.putIfAbsent(mainNum, new ArrayList<>());
        this.numNums.get(mainNum).add(relNum);
    }

    // EFFECTS: return numNums
    public HashMap<Integer, ArrayList<Integer>> getNumNums() {
        return numNums;
    }

    // EFFECTS: returns string representation of NumberNumbers
    @Override
    public String toString(){
        StringBuilder message = new StringBuilder("");
        for(int num : this.numNums.keySet()){
            message.append(num + ", ");
            message.append(this.numNums.get(num));
            message.append("\n");
        }
        return String.valueOf(message);
    }
}
