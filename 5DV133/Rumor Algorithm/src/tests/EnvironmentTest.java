package src.tests;

import org.junit.Test;
import src.Configuration;
import src.Environment;
import src.Position;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class EnvironmentTest {

    @Test
    public void timeTickRuns() {
        new Environment().timeTick();
    }

    @Test
    public void createQueriesRuns() {

        Environment domain = new Environment();
        for (int i = 0; i < 500; i++) {
            domain.timeTick();
        }
        domain.createQueries();
    }

    @Test
    public void createIDGivesIDs() {
        Object ID = new Environment().createID();
        assertEquals(true, ID instanceof Integer);
    }

    @Test
    public void idCanNotBeZero() {
        Object ID = new Environment().createID();
        assertEquals(false, ID.equals(0));
    }

    @Test
    public void idAreUnique() {
        Environment domain = new Environment();
        HashSet<Integer> IDs = new HashSet<>();

        for (int i = 0; i < 100; i++) {
            Integer ID = domain.createID();
            assertEquals(false, IDs.contains(ID));
            IDs.add(ID);
        }
    }

    @Test
    public void getTimeGivesTime() {
        Environment domain = new Environment();
        domain.timeTick();
        assertEquals(1, domain.getTime());
    }

    @Test
    public void getNodeReturnsNode() {
        Configuration config = new Configuration();
        Object node = new Environment().getNode(new Position(
            config.distance, config.distance));
        assertEquals(true, node != null);
    }

    @Test
    public void setAllNodesActionsPerformedWorks() {
        new Environment().setAllNodesActionsPerformed(true);

    }

    @Test
    public void checkNodesGetNeighbors() {
        Environment domain = new Environment();
        Configuration config = new Configuration();

        for (int col = 1; col < config.cols; col++) {
            for (int row = 1; row < config.rows; row++) {
                Integer size = domain.getNode(new Position(
                    col * config.distance, row * config.distance))
                    .getNeighbors()
                    .size();
                assertEquals(true,
                    (size.equals(3) || size.equals(5) || size.equals(8)));
            }
        }
    }
}