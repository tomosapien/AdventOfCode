import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.ArrayList;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {
    private static final ArrayList<ArrayList<Character>> grid = new ArrayList<>();

    private static int rowCount = 0;
    private static int colCount = 0;
    
    private static final Pattern pattern = Pattern.compile("XMAS|SAMX");

    public static void main(String[] args) {
        System.out.println("Advent of Code 2024 - Day 4 - Part 1");

        File inputFile = new File("2024/Day4/input.txt");
        
        try (Scanner scanner = new Scanner(inputFile)) {
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                ArrayList<Character> row = new ArrayList<>();
                
                for (char c : line.toCharArray()) {
                    row.add(c);
                }

                grid.add(row);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }

        rowCount = grid.size();
        colCount = grid.get(0).size();

        int rowOccurences = countOccurences(true, false);
        int colOccurences = countOccurences(false, true);
        int diagOccurences = countOccurences(true, true);

        int total = rowOccurences + colOccurences + diagOccurences;
        System.out.println("Total occurences: " + total);
    }

    private static int countOccurences(boolean rowIncrement, boolean colIncrement) {
        int count = 0;

        if (rowIncrement && ! colIncrement) {
            // Create a string from each character in the row
            for (ArrayList<Character> r : grid) {

                StringBuilder sb = new StringBuilder();
                for (char c : r) {
                    sb.append(c);
                }

                count += countMatches(sb.toString());
            }
        
        } else if (!rowIncrement && colIncrement) {
            // Create a string from each character in the column
            for (int c = 0; c < colCount; c++) {
                StringBuilder sb = new StringBuilder();
                for (int r = 0; r < rowCount; r++) {
                    sb.append(grid.get(r).get(c));
                }

                count += countMatches(sb.toString());
            }
        } else if (rowIncrement && colIncrement) {
            count += countDiagonals();
        } else {
            System.err.println("Invalid increment combination");
        }

        return count;
    }

    private static int countDiagonals() {
        int count = 0;
        ArrayList<String> diagonals = new ArrayList<>();

        // Left to right diagonals
        for (int r = 0; r < rowCount; r++) {
            diagonals.add(constructDiagonalString(r, 0, 1, 1));
        }
        for (int c = 1; c < colCount; c++) {
            diagonals.add(constructDiagonalString(0, c, 1, 1));
        }

        // Right to left diagonals
        for (int r = 0; r < rowCount; r++) {
            diagonals.add(constructDiagonalString(r, colCount - 1, 1, -1));
        }
        for (int c = colCount - 2; c >= 0; c--) {
            diagonals.add(constructDiagonalString(0, c, 1, -1));
        }

        for (String diag : diagonals) {
            if (diag.length() < 4) continue;

            count += countMatches(diag);
        }

        return count;
    }

    private static String constructDiagonalString(int startRow, int startCol, int rowIncrement, int colIncrement) {
        StringBuilder sb = new StringBuilder();

        while (startRow >= 0 && startRow < rowCount && startCol >= 0 && startCol < colCount) {
            sb.append(grid.get(startRow).get(startCol));    

            startRow += rowIncrement;
            startCol += colIncrement;
        }

        return sb.toString();
    }

    private static int countMatches(String str) {
        int count = 0;
        for (int i = 0; i <= str.length() - 4; i++) { // 4 is the pattern length
            String sub = str.substring(i, i + 4);
            if (pattern.matcher(sub).matches()) {
                count++;
            }
        }
        return count;
    }
}