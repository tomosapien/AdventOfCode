import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.HashSet;

// Keep a list of the visited coordinates and loop through this list to place an obstacle - 
// Then the guard route needs to be simulated from the guards starting position
// To detect a loop: For each simulated guard route store the guard position + orientation in a Set of a special class - 
// Override equals to check coordinates & orientation, if it ever returns true it means we have entered a loop
// Also keep checking if we left the grid
// Depending on the result add + 1 to possible positions to place an obstacle and continue the loop

public class Main {
    private static ArrayList<ArrayList<Character>> grid = new ArrayList<>();
    private static LinkedHashSet<Coordinate> visited = new LinkedHashSet<>();

    private static int loopCount = 0;
    private static Guard guard;
    private static Coordinate startCoordinate;
    private static char startOrientationChar;

    public static void main(String[] args) {
        System.out.println("Advent of Code 2024 - Day 6 - Part 2");

        File inputFile = new File("2024/Day6/input.txt");
        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                ArrayList<Character> row = new ArrayList<>();
                for (char c : line.toCharArray()) {
                    if (c == '^' || c == 'v' || c == '>' || c == '<') {
                        startCoordinate = new Coordinate(grid.size(), row.size());
                        startOrientationChar = c;
                        // Coordinate is immutable, pass the startCoordinate directly
                        guard = new Guard(startOrientationChar, startCoordinate);
                    }
                    row.add(c);
                }
                grid.add(row);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    Coordinate pos = guard.getPosition();
    // Coordinate is immutable; safe to store the reference
    visited.add(pos);

        while (!onOuterEdge(guard.getPosition())) {
            Orientation guardOrientation = guard.getOrientation();
            Coordinate guardPosition = guard.getPosition();
            int xTarget = guardPosition.getX() + getXDelta(guardOrientation);
            int yTarget = guardPosition.getY() + getYDelta(guardOrientation);

            if (isAccessable(xTarget, yTarget)) {
                guard.moveForward();
                // position is immutable, store reference
                visited.add(guard.getPosition());
            } else {
                guard.turnRight();
            }
        }

        System.out.println("Exit found! Amount of coordinates visited = " + visited.size());

        for (Coordinate c : visited) {
            // save original character so we can restore it after the simulation
            char original = grid.get(c.getX()).get(c.getY());
            setCharInGrid(c, 'O');

            boolean loopDetected = detectLoop();
            if (loopDetected) {
                loopCount++;
            }

            // restore the original grid character
            setCharInGrid(c, original);
        }

        System.out.println("Amount of positions to add an obstacle that would result in a loop: " + loopCount);
    }

    private static boolean detectLoop() {
        HashSet<VisitedPosition> simVisited = new HashSet<>();
    Guard simGuard = new Guard(startOrientationChar, startCoordinate);

        while (!onOuterEdge(simGuard.getPosition())) {
            Orientation simGuardOrientation = simGuard.getOrientation();
            Coordinate simGuardPosition = simGuard.getPosition();
            int xTarget = simGuardPosition.getX() + getXDelta(simGuardOrientation);
            int yTarget = simGuardPosition.getY() + getYDelta(simGuardOrientation);

            if (isAccessable(xTarget, yTarget)) {
                simGuard.moveForward();
                VisitedPosition vp = new VisitedPosition(simGuard.getPosition(), simGuard.getOrientation());

                boolean vpAdded = simVisited.add(vp);
                if (!vpAdded) {
                    // System.out.println("Loop detected!");
                    return true;
                }
            } else {
                simGuard.turnRight();
            }
        }

        // System.out.println("Exit found at " + simGuard.getPosition() + ". No loop detected.");
        return false;
    }

    private static void setCharInGrid(Coordinate coordinate, char c) {
        grid.get(coordinate.getX()).set(coordinate.getY(), c);
        // System.out.println("Set character at " + coordinate + " to '" + c + "'");
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
        char gridContent = grid.get(x).get(y);
        return gridContent != '#' && gridContent != 'O';
    }
}