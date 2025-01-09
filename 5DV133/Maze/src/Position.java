/**
 * Object oriented programming, 5DV133
 * Spring '18
 * Obligatory assignment 2
 * CAS: svma0011
 * CS: kv13smn
 *
 * @author Svitri Magnusson
 */

import java.util.Objects;

public class Position {
    private int x;
    private int y;

    /**
     * Creates a position object with x and y coordinates.
     * Has functions to get neighbour positions.
     */
    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {

        return this.x;
    }

    public int getY() {

        return this.y;
    }

    public Position getPosToSouth() {

        return new Position(this.x, this.y + 1);
    }

    public Position getPosToNorth() {

        return new Position(this.x, this.y - 1);
    }

    public Position getPosToWest() {

        return new Position(this.x - 1, this.y);
    }

    public Position getPosToEast() {

        return new Position(this.x + 1, this.y);
    }

    /**
     * Defines equal function by same x and y coordinate.
     * @param o
     */
    @Override
    public boolean equals(Object o) {

        // Return true if same reference
        if (o == this) {
            return true;
        }
        // Return false if argument is not of Position instance.
        if (!(o instanceof Position)) {
            return false;
        }

        //Convert object to Position and check x and y.
        Position pos = (Position) o;
        boolean compX = this.x == pos.getX();
        boolean compY = this.y == pos.getY();

        // Return true if both
        return compX && compY;

    }

    //Uses Java native function hash to has the x and y values of the object.
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}


