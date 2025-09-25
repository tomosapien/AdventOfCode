import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main {
    public static void main(String[] args) {
        System.out.println("Advent of Code 2024 - Day 3 - Part 1");

        String patternString = "mul\\((\\d+),(\\d+)\\)";
        Pattern pattern = Pattern.compile(patternString);
        File inputFile = new File("2024/Day3/input.txt");

        int sumTotal = 0;

        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                Matcher matcher = pattern.matcher(line);

                while (matcher.find()) {

                    int num1 = Integer.parseInt(matcher.group(1));
                    int num2 = Integer.parseInt(matcher.group(2));
                    int product = num1 * num2;

                    sumTotal += product;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        System.out.println("Sum of all products: " + sumTotal);
    }
}