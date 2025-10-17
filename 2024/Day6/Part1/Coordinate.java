public final class Coordinate {
    private final int x;
    private final int y;

    public Coordinate() {
        this(0, 0);
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate other) {
        this.x = other.x;
        this.y = other.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /**
     * Return a new Coordinate translated by the given deltas.
     */
    public Coordinate move(int deltaX, int deltaY) {
        return new Coordinate(this.x + deltaX, this.y + deltaY);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}