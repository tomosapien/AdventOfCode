import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Main {
    private static ArrayList<Rule> rules = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Advent of Code 2024 - Day 5 - Part 1");

        File inputFile = new File("2024/Day5/input.txt");
        int sum = 0;
        
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
                    LinkedHashSet<Integer> input = new LinkedHashSet<>();
                    for (String s : line.split(",")) {

                        input.add(Integer.valueOf(s.trim()));
                    }
                    if (IsCorrectlyOrdered(input)) {
                        int middleIndex = input.size() / 2;
                        int middleValue = (int) input.toArray()[middleIndex];
                        sum += middleValue;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }

        System.out.println("Sum of the middle values: " + sum);
    }

    private static boolean IsCorrectlyOrdered(LinkedHashSet<Integer> input) {
        for (Rule r : rules) {
            if (!r.IsRuleMet(input)) {
                return false;
            }
        }

        return true;
    }
}