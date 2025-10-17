public class VisitedPosition {
    private final Coordinate coordinate;
    private final Orientation orientation;

    public VisitedPosition(Coordinate coordinate, Orientation orientation) {
        this.coordinate = coordinate;
        this.orientation = orientation;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { 
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        VisitedPosition that = (VisitedPosition) obj;
        return coordinate.equals(that.coordinate) && orientation == that.orientation;
    }

    @Override
    public int hashCode() {
        return 31 * coordinate.hashCode() + orientation.hashCode();
    }
}