import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.Collections;

public class Main {
    private static ArrayList<Rule> rules = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Advent of Code 2024 - Day 5 - Part 2");

        File inputFile = new File("2024/Day5/input.txt");
        ArrayList<ArrayList<Integer>> incorrectInputs = new ArrayList<>();
        
        try (Scanner scanner = new Scanner(inputFile)) {
            boolean readRules = true;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                
                if (line.isBlank()) {
                    readRules = false;
                    continue;
                }

                if (readRules) {
                    rules.add(new Rule(line));
                } else {
                    // process input
                    ArrayList<Integer> input = new ArrayList<>();
                    for (String s : line.split(",")) {

                        input.add(Integer.valueOf(s.trim()));
                    }
                    if (IsCorrectlyOrdered(input) != null) {

                        // add to list of incorrect inputs
                        incorrectInputs.add(input);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }

        int sumOfMiddleValues = 0;
        // Swap incorrect input to see if it can be made correct and then calculate the sum of the middle values
        for (ArrayList<Integer> input : incorrectInputs) {
            boolean corrected = false;
            int maxAttempts = 100; // Prevent infinite loop
            int attempts = 0;
            
            while(!corrected && attempts < maxAttempts) {
                Rule incorrectRule = IsCorrectlyOrdered(input);
                
                if (incorrectRule == null) {
                    corrected = true;
                    int middleIndex = input.size() / 2;
                    sumOfMiddleValues += input.get(middleIndex);
                    break;
                } else {
                    // Try to correct the input
                    int firstIndex = GetValueIndex(input, incorrectRule.GetFirstNum());
                    int secondIndex = GetValueIndex(input, incorrectRule.GetSecondNum());

                    if (firstIndex != -1 && secondIndex != -1) {
                        Collections.swap(input, firstIndex, secondIndex);
                    }
                }
                attempts++;
            }

            if (!corrected) {
                System.out.println("Could not correct input after " + maxAttempts + " attempts: " + input);
            }
        }

        System.out.println("Sum of middle values: " + sumOfMiddleValues);
    }

    private static Rule IsCorrectlyOrdered(ArrayList<Integer> input) {
        for (Rule r : rules) {
            
            if (!r.IsRuleMet(input)) {
                return r;
            }
        }

        return null;
    }

    private static int GetValueIndex(ArrayList<Integer> input, int value) {
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i) == value) {
                return i;
            }
        }

        return -1;
    }
}