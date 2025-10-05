public class Guard {
    private Coordinate position = new Coordinate();
    private Orientation orientation = Orientation.UP;

    public Guard(char currentOrientation, Coordinate startPosition) {

        switch (currentOrientation) {
            case '^' -> this.orientation = Orientation.UP; 
            case '>' -> this.orientation = Orientation.RIGHT;
            case 'v' -> this.orientation = Orientation.DOWN;
            case '<' -> this.orientation = Orientation.LEFT;
            default -> throw new IllegalArgumentException("Invalid orientation character: " + currentOrientation);
        }

        this.position = startPosition;

        System.out.println("Guard starts out at position (" + startPosition.getX() + "," + startPosition.getY() + ") facing " + this.orientation.toString());
    }

    public Coordinate getPosition() {
        return position;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void turnRight() {
        this.orientation = this.orientation.turnRight();
        System.out.println("Guard is now facing " + this.orientation.toString());
    }

    public void moveForward() {
        switch (this.orientation) {
            case UP -> position.move(-1, 0);
            case RIGHT -> position.move(0, 1);
            case DOWN -> position.move(1, 0);
            case LEFT -> position.move(0, -1);
        }
    }
}