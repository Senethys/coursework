package src.tests;

import org.junit.Test;
import src.*;

import static org.junit.Assert.assertEquals;

//That it uses routingtables correctly to move.
//Does not visit visited nodes twice or more if not nessecary.
//Lives as long as specified.                   - Done
//That it takes the same path back as to target.- It has no choice.
//Picks random nodes randomly.                  - Used same method as Agent.
//Knows when it reaches the target.             - Tested with print statement.
//Knows when it reaches origin.                 - Tested with print statement.
//That it carries correct info with it.         - Tested with print statement.

public class QueryTest {

    @Test
    public void findsTargetInNeighbourBySteps() {
        int distance = 1 * new Configuration().distance;
        Environment domain = new Environment();
        Position corner = new Position(0, 0);
        Event event = new Event(1, 1, new Position(distance, distance));
        domain.getNode(new Position(2 * distance, 2 * distance)).discoverEvent(event);
        Query query = new Query(1, corner, domain);
        domain.timeTick();
        int stepsTaken = query.getStepsTaken();
        //One step to target, one step back.
        assertEquals(2, stepsTaken);
    }

    @Test
    public void findsTargetInNeighbourByWaitTime() {
        //WaitTime gets reset after destination is reached.
        int distance = 1 * new Configuration().distance;
        Environment domain = new Environment();
        Position corner = new Position(0, 0);
        Event event = new Event(1, 1, new Position(distance, distance));
        Node node = domain.getNode(new Position(2 * distance, 2 * distance));
        node.discoverEvent(event);
        new Query(1, corner, domain);
        domain.timeTick();
        assertEquals(0, node.getWaitTime());
    }

    @Test
    public void livesAsSpecified() {
        int distance = 1 * new Configuration().distance;
        Environment domain = new Environment();
        Position LeftUpperCorner = new Position(0, 0);
        Position RightLowerCorner = new Position(49 * distance, 49 * distance);

        Event event = new Event(1, 1, RightLowerCorner);
        domain.getNode(RightLowerCorner).discoverEvent(event);
        Query query = new Query(1, LeftUpperCorner, domain);
        for (int i = 0; i <= new Configuration().querySteps + 10; i++) {
            domain.timeTick();
        }
        int stepsTaken = query.getStepsTaken();
        assertEquals(new Configuration().querySteps, stepsTaken);
    }

    @Test
    public void testIfQueryResendAfterExpire() {
        int distance = 1 * new Configuration().distance;
        Environment domain = new Environment();
        Position LeftUpperCorner = new Position(0, 0);
        Position RightLowerCorner = new Position(49 * distance, 49 * distance);

        Node OriginNode = domain.getNode(LeftUpperCorner);
        Node TargetNode = domain.getNode(RightLowerCorner);
        Event event = new Event(1, 1, RightLowerCorner);
        TargetNode.discoverEvent(event);
        OriginNode.setQueryEventID(1);
        Query query = new Query(1, LeftUpperCorner, domain);
        for (int i = 0; i <= new Configuration().querySteps * 8 + 10; i++) {
            domain.timeTick();
        }
        System.out.println(OriginNode.getWaitTime());
        assertEquals(true, OriginNode.getRequestRepeated());
    }

}
