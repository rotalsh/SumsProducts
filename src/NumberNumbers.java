import java.util.ArrayList;
import java.util.HashMap;

public class NumberNumbers {
    private HashMap<Integer, ArrayList<Integer>> numNums;

    public NumberNumbers(){
        this.numNums = new HashMap<>();
    }

    public void addNum(int mainNum, int relNum){
        this.numNums.putIfAbsent(mainNum, new ArrayList<>());
        this.numNums.get(mainNum).add(relNum);
    }

    public HashMap<Integer, ArrayList<Integer>> getNumNums() {
        return numNums;
    }

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
