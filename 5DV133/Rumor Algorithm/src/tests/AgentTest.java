package src.tests;

import org.junit.Test;
import src.*;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class AgentTest {

    private Agent setUp(Environment domain) {
        Position corner = new Position(0, 0);
        Event event = new Event(1, 1, corner);
        domain.getNode(corner).discoverEvent(event);
        return new Agent(corner, domain);
    }

    @Test
    public void GetRoutingTableCorrectlyWhenCreatedTest() {
        Environment domain = new Environment();
        Agent agent = setUp(domain);
        domain.timeTick();
        RoutingTable agentTable = agent.getRoutingTable();
        assertEquals(new Integer(2), agentTable.getSteps(1));
    }

    @Test
    public void UpdateRoutingTableStepsAreDoneTest() {
        Environment domain = new Environment();
        Agent agent = setUp(domain);
        Integer before = agent.getRoutingTable().getSteps(1);
        domain.timeTick();
        Integer after = agent.getRoutingTable().getSteps(1);
        assertEquals(true, before + 1 == after);
    }

    @Test
    public void UpdateRoutingTableNextNodeAreDoneFirstTimeTest() {
        Environment domain = new Environment();
        Agent agent = setUp(domain);
        Position after = agent.getRoutingTable().getNextNode(1);
        assertEquals(true, new Position(0, 0).equals(after));
    }


    @Test
    public void UpdateRoutingTableNextNodeAreDoneRestOfTimesTest() {
        Environment domain = new Environment();
        Agent agent = setUp(domain);
        Position before = agent.getNextPosition();
        domain.timeTick();
        Position after = agent.getRoutingTable().getNextNode(1);
        assertEquals(true, before.equals(after));
    }

    @Test
    public void ChecksSelectionOfNextPositionIsSemiRandomTest() {
        Environment domain = new Environment();
        ArrayList<Agent> agents = new ArrayList<>();
        agents.add(setUp(domain));
        int loop = 1000;

        // Create all agents
        for (int i = 0; i < loop - 1; i++) {
            agents.add(i, new Agent(new Position(0, 0), domain));
        }

        // Create array for saving how many agents are at specific neighbors.
        int size = domain.getNode(new Position(0, 0)).getNeighbors().size();
        ArrayList<Double> answers = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            answers.add(0.0);
        }

        // Insert how many agents are at specific neighbors.
        for (int i = 0; i < loop; i++) {
            Agent agent = agents.get(i);

            for (int j = 0; j < size; j++) {
                Position neighbor = domain.getNode(new Position(0, 0))
                    .getNeighbors()
                    .get(j)
                    .getPosition();
                if (agent.getNextPosition().equals(neighbor)) {
                    answers.set(j, answers.get(j) + 1);
                }

            }
        }

        // Assert if the amount of agents per node is around equal
        // between neighbors.
        double d = ((double) size / 10) * 1000;
        for (int i = 0; i < size; i++) {
            assertEquals(true, answers.get(i) >= d - 100);
            assertEquals(true, answers.get(i) <= d + 100);
        }
    }

    @Test
    public void OnlyTakesSpecifiedAmountOfStep() {
        Environment domain = new Environment();
        Agent agent = setUp(domain);

        for (int i = 0; i < new Configuration().agentSteps; i++) {
            domain.timeTick();
        }

        assertEquals(new Configuration().agentSteps, agent.getStepsTaken());
    }
}


