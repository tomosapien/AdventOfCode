public class Coordinate {
    private int x = 0;
    private int y = 0;
    
    public Coordinate() {
        this(0, 0);
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void move(int deltaX, int deltaY) {
        this.x += deltaX;
        this.y += deltaY;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}