import java.util.ArrayList;
import java.util.Collections;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Advent of Code 2024 - Day 1 - Part 1");

        ArrayList<Integer> leftList = new ArrayList<>();
        ArrayList<Integer> rightList = new ArrayList<>();

        File inputFile = new File("2024/Day1/input.txt");

        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.trim().split("\\s+");
                for (int i = 0; i < parts.length; i++) {
                    int num = Integer.parseInt(parts[i]);
                    if (i % 2 == 0) {
                        rightList.add(num);
                    } else {
                        leftList.add(num);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }

        Collections.sort(leftList);
        Collections.sort(rightList);

        int similarityScore = 0;
        for (int num : leftList) {
            int count = 0;
            for (int rightNum : rightList) {
                if (rightNum == num) {
                    count++;
                }

                if (num < rightNum) {
                    break;
                }
            }
            similarityScore += (count * num);
        }
        System.out.println("Total Similarity Score: " + similarityScore);
    }
}



