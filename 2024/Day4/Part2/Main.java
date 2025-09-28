import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.ArrayList;

import java.util.regex.Pattern;

public class Main {
    private static final ArrayList<ArrayList<Character>> grid = new ArrayList<>();

    private static int rowCount = 0;
    private static int colCount = 0;
    
    private static final Pattern pattern = Pattern.compile("MAS|SAM");

    public static void main(String[] args) {
        System.out.println("Advent of Code 2024 - Day 4 - Part 2");

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
        
        int xMasCount = 0;

        for (int row = 1; row < rowCount - 1; row++) {
            for (int col = 1; col < colCount - 1; col++) {
                if (grid.get(row).get(col) == 'A') {
                    if (checkForXMas(row, col)) {
                        xMasCount++;
                    }
                }
            }
        }

        System.out.println("Total number of XMases found: " + xMasCount);
    }

    private static boolean checkForXMas(int row, int col) {
        ArrayList<String> diagonals = getDiagonals(row, col);

        for (String diag : diagonals) {
            // System.out.println("Diagonal = " + diag);
            if (!pattern.matcher(diag).matches()) {
                return false;
            }
        }

        return true;
    }

    private static ArrayList<String> getDiagonals(int row, int col) {
        ArrayList<String> diagonals = new ArrayList<>();

        StringBuilder diag1 = new StringBuilder();
        StringBuilder diag2 = new StringBuilder();

        diag1.append(grid.get(row - 1).get(col - 1));
        diag1.append(grid.get(row ).get(col));
        diag1.append(grid.get(row + 1).get(col + 1));

        diag2.append(grid.get(row + 1).get(col - 1));
        diag2.append(grid.get(row).get(col));
        diag2.append(grid.get(row - 1).get(col + 1));

        diagonals.add(diag1.toString());
        diagonals.add(diag2.toString());

        return diagonals;
    }
}