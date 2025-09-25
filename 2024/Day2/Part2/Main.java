import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.ArrayList;

public class Main {
    // How many bad levels are tolerated in a report
    private static final int FAULT_TOLERANCE = 1;

    public static void main(String[] args) {
        System.out.println("Advent of Code 2024 - Day 2 - Part 2");

        File inputFile = new File("2024/Day2/input.txt");
        int safeReports = 0;
        int lineNumber = 0;

        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine();
                ArrayList<Integer> levels = new ArrayList<>();
                for (String part : line.trim().split("\\s+")) {
                    levels.add(Integer.valueOf(part));
                }

                if (isReportSafe(levels, true)) {
                    safeReports++;
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }

        System.out.println("Number of Safe Reports: " + safeReports);
    }

    private static boolean isReportSafe(ArrayList<Integer> levels, boolean attemptDampen) {
        
        boolean isIncreasing = levels.get(1) > levels.get(0);
        
        for (int i = 1; i < levels.size(); i++) {

            if (!isLevelSafe(levels.get(i - 1), levels.get(i), isIncreasing)) {
                
                if (attemptDampen) {
                    
                    int[] problemIndices;
                    if (i >= 2) {
                        problemIndices = new int[] {i - 2, i - 1, i};
                    } else {
                        problemIndices = new int[] {i - 1, i};
                    }
                    
                    for (int index : problemIndices) {

                        ArrayList<Integer> modifiedLevels = new ArrayList<>(levels);
                        modifiedLevels.remove(index);
                        
                        if (isReportSafe(modifiedLevels, false)) {
                            return true;
                        }
                    }
                }

                return false; // Could not dampen the problem, report is unsafe
                
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