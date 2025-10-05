import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.ArrayList;

public class Main {
    private static ArrayList<ArrayList<Character>> grid = new ArrayList<>();
    private static int xCount = 0;
    private static Guard guard;

    public static void main(String[] args) {
        System.out.println("Advent of Code 2024 - Day 6 - Part 1");

        File inputFile = new File("2024/Day6/input.txt");
        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                ArrayList<Character> row = new ArrayList<>();
                for (char c : line.toCharArray()) {
                    if (c == '^' || c == 'v' || c == '>' || c == '<') {
                        guard = new Guard(c, new Coordinate(grid.size(), row.size()));
                    }
                    row.add(c);
                }
                grid.add(row);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
        Coordinate pos = guard.getPosition();
        markX(pos.getX(), pos.getY());

        while (!onOuterEdge(guard.getPosition())) {
            Orientation guardOrientation = guard.getOrientation();
            Coordinate guardPosition = guard.getPosition();
            int xTarget = guardPosition.getX() + getXDelta(guardOrientation);
            int yTarget = guardPosition.getY() + getYDelta(guardOrientation);

            if (isAccessable(xTarget, yTarget)) {
                guard.moveForward();
                markX(guardPosition.getX(), guardPosition.getY());
            } else {
                System.out.println("Ran into obstacle on coordinates: (" + guardPosition.getX() + "," + guardPosition.getY() + ")");
                System.out.println("Turning right");
                guard.turnRight();
            }
        }

        System.out.println("Exit found! Amount of X's = " + xCount);
    }

    private static void markX(int x, int y) {
        if (grid.get(x).get(y) == 'x') {
            // Already visited this position
            return;
        }

        grid.get(x).set(y, 'x');
        xCount++;
    }

    private static boolean onOuterEdge(Coordinate position) {
        int x = position.getX();
        int y = position.getY();
        return x == 0 || y == 0 || y == grid.get(0).size() - 1 || x == grid.size() - 1;
    }

    private static int getYDelta(Orientation orientation) {
        return switch (orientation) {
            case Orientation.LEFT -> -1;
            case Orientation.RIGHT -> 1;
            default -> 0;
        };
    }

    private static int getXDelta(Orientation orientation) {
        return switch (orientation) {
            case Orientation.UP -> -1;
            case Orientation.DOWN -> 1;
            default -> 0;
        };
    }

    private static boolean isAccessable(int x, int y) {
        return grid.get(x).get(y) != '#';
    }
}