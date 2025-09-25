import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Advent of Code 2024 - Day 2 - Part 1");

        File inputFile = new File("2024/Day2/input.txt");
        int safeReports = 0;

        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                ArrayList<Integer> levels = new ArrayList<>();
                for (String part : line.trim().split("\\s+")) {
                    levels.add(Integer.valueOf(part));
                }
                
                if (isReportSafe(levels)) {
                    safeReports++;
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }

        System.out.println("Number of Safe Reports: " + safeReports);
    }

    private static boolean isReportSafe(ArrayList<Integer> levels) {
        boolean isIncreasing = levels.get(1) > levels.get(0);
        for (int i = 1; i < levels.size(); i++) {
            if (!isLevelSafe(levels.get(i - 1), levels.get(i), isIncreasing)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isLevelSafe(int previousLevel, int currentLevel, boolean isIncreasing) {
        if (isIncreasing) {
            if (currentLevel <= previousLevel) {
                return false;
            }
            return isWithinRange(currentLevel, previousLevel + 1, previousLevel + 3);
        }

        // Decreasing
        if (currentLevel >= previousLevel) {
            return false;
        }
        return isWithinRange(currentLevel, previousLevel - 3, previousLevel - 1);
    }

    private static boolean isWithinRange(int level, int lower, int upper) {
        return level >= lower && level <= upper;
    }
}