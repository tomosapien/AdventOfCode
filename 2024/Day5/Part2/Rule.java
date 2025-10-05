import java.util.ArrayList;

public class Rule {
    private int firstNum = 0;
    private int secondNum = 0;

    public Rule(String ruleString) {
        String[] splitRules = ruleString.split("\\|");
        firstNum = Integer.parseInt(splitRules[0]);
        secondNum = Integer.parseInt(splitRules[1]);
    }

    // public boolean ContainsNum(int num) {
    //     return firstNum == num || secondNum == num;
    // }

    public boolean IsRuleMet(ArrayList<Integer> input) {
        if ((input.contains(firstNum) ^ input.contains(secondNum)) || 
            (!input.contains(firstNum) && !input.contains(secondNum))) {
            return true;
        }
        
        for (int i : input) {
            if (i == firstNum) {
                return true;
            }
            if (i == secondNum) {
                return false;
            }
        }

        return true;
    }

    public int GetFirstNum() {
        return firstNum;
    }

    public int GetSecondNum() {
        return secondNum;
    }

    @Override
    public String toString() {
        return firstNum + " | " + secondNum;
    }
}