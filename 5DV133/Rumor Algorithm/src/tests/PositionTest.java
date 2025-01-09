package src.tests;

import src.Configuration;
import src.Position;

import static org.junit.Assert.assertEquals;

public class PositionTest {

    @org.junit.Test
    public void setXRight() {
        Position p = new Position(1, 2);
        assertEquals(1, p.getX());
    }

    @org.junit.Test
    public void setYRight() {
        Position p = new Position(1, 2);
        assertEquals(2, p.getY());
    }

    @org.junit.Test
    public void equalsReturnsTrueWhenSame() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(1, 2);
        assertEquals(true, p1.equals(p2));
    }

    @org.junit.Test
    public void equalsReturnsFalseWhenDifferent() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(2, 1);
        assertEquals(false, p1.equals(p2));
    }

    @org.junit.Test
    public void hashcodeReturnsTrueWhenEqual() {
        Position p1 = new Position(5, 6);
        Position p2 = new Position(5, 6);
        assertEquals(true, p1.hashCode() ==
            p2.hashCode());
    }

    @org.junit.Test
    public void hashcodeReturnsTrueWhenUnequal() {
        Position p1 = new Position(5, 6);
        Position p2 = new Position(6, 5);
        assertEquals(false, p1.hashCode() ==
            p2.hashCode());
    }

    @org.junit.Test
    public void toStringReturnsRight() {
        Position p = new Position(5, 6);
        assertEquals("x: 5, y: 6", p.toString());
    }

    @org.junit.Test
    public void getPosToSouth() {
        Position p1 = new Position(0, 0);
        Position p2 = p1.getPosToSouth();
        assertEquals(true, p2.equals(new Position(
            0, -1 * new Configuration().distance)));
    }

    @org.junit.Test
    public void getPosToNorth() {
        Position p1 = new Position(0, 0);
        Position p2 = p1.getPosToNorth();
        assertEquals(true, p2.equals(new Position(
            0, new Configuration().distance)));
    }

    @org.junit.Test
    public void getPosToWest() {
        Position p1 = new Position(0, 0);
        Position p2 = p1.getPosToWest();
        assertEquals(true, p2.equals(new Position(
            -1 * new Configuration().distance, 0)));
    }

    @org.junit.Test
    public void getPosToEast() {
        Position p1 = new Position(0, 0);
        Position p2 = p1.getPosToEast();
        assertEquals(true, p2.equals(new Position(
            new Configuration().distance, 0)));
    }
}