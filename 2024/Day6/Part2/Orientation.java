public enum Orientation {
    UP, RIGHT, DOWN, LEFT;

    public Orientation turnRight() {
        return switch (this) {
            case UP -> RIGHT;
            case RIGHT -> DOWN;
            case DOWN -> LEFT;
            case LEFT -> UP;
        };
    }
}