package src;

import java.util.Objects;

/**
 * A point representing a location in x- and y-coordinate space,
 * specified in integer precision.
 */
public class Position {


    private int x;
    private int y;

    /**
     * Constructs and initializes a point at the specified
     * coordinates in the coordinate space.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x-coordinate of this position
     * in integer precision.
     *
     * @return the x-coordinate of this position.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of this position
     * in integer precision.
     *
     * @return the y-coordinate of this position.
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the postion with same x-value and y-value minus one.
     *
     * @return Position to the south.
     */
    public Position getPosToSouth() {
        return new Position(x, y - new Configuration().distance);
    }

    /**
     * Returns the postion with same x-value and y-value plus one.
     *
     * @return Position to the north.
     */
    public Position getPosToNorth() {
        return new Position(x, y + new Configuration().distance);
    }

    /**
     * Returns the postion with x-value minus one and the same y-value.
     *
     * @return Position to the west.
     */
    public Position getPosToWest() {
        return new Position(x - new Configuration().distance, y);
    }

    /**
     * Returns the postion with x-value plus one and the same y-value.
     *
     * @return Position to the east.
     */
    public Position getPosToEast() {
        return new Position(x + new Configuration().distance, y);
    }

    /**
     * Checks if the given position has the same coordinates.
     *
     * @param o position to check
     * @return boolean if coordinates is same or not.
     */
    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

                /* Check if o is an instance of Complex returns false and
                   "null instanceof [type]" also returns false */
        if (!(o instanceof Position)) {
            return false;
        }

        // Typecast o to Position.
        Position p = (Position) o;

        // Compare the x- and y-coordinate.
        return Integer.compare(x, p.getX()) == 0 &&
            Integer.compare(y, p.getY()) == 0;
    }

    /**
     * Returns a hash value of a position.
     *
     * @return hash value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);

    }

    /**
     * @return Position in string form
     */
    @Override
    public String toString() {
        return "x: " + x + ", " + "y: " + y;
    }
}