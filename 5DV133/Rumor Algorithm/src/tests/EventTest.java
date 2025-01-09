package src.tests;

import org.junit.Test;
import src.Event;
import src.Position;

import static org.junit.Assert.assertEquals;


public class EventTest {

    @Test
    public void createEvent() {
        new Event(1, 1, new Position(1, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionIfNoID() {
        new Event(0, 1, new Position(1, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwExceptionIfNoTime() {
        new Event(1, 0, new Position(1, 1));
    }

    @Test(expected = NullPointerException.class)
    public void throwExceptionIfNoOrigin() {
        new Event(1, 1, null);
    }

    @Test
    public void eventGetsIDRight() {
        Event e = new Event(10, 1, new Position(1, 1));
        assertEquals(10, e.getEventID());
    }

    @Test
    public void createStringInRightFormat() {
        Event e = new Event(4, 5, new Position(6, 7));
        assertEquals("x: 6, y: 7, time of creation: 5, Event ID: 4",
            e.toString());
    }

}