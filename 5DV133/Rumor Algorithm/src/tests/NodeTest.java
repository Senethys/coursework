package src.tests;

import org.junit.Test;
import src.*;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class NodeTest {

    @Test
    public void createNode() {
        new Node(new Position(1, 1));
    }

    @Test
    public void createNegativePosNode() {
        new Node(new Position(-1, -21));
    }

    @Test
    public void addNeighbourWorks() {
        Node node = new Node(new Position(1, 1));
        node.addNeighbour((new Node(new Position(1, 2))));
        ArrayList testNodes = node.getNeighbors();
        Object testNode = testNodes.get(0);
        assertEquals(true, testNode instanceof Node);
    }

    @Test
    public void workingQueueIsNotEmpty() {
        Environment domain = new Environment();
        Node node = domain.getNode(new Position(0, 0));
        new Agent(new Position(0, 0), domain);
        assertEquals(false, node.getWorkingQueue().isEmpty());
    }

    @Test
    public void addToQueueWorks() {
        Environment domain = new Environment();
        Node node = domain.getNode(new Position(0, 0));
        Agent agent = new Agent(new Position(0, 0), domain);
        Message testAgent = node.getWorkingQueue().peek();
        assertEquals(true, testAgent.equals(agent));
    }

    @Test
    public void executeMessageExhaustsBothNodesTest() {
        Environment domain = new Environment();
        Node node = domain.getNode(new Position(0, 0));
        Node neighbor = domain.getNode(new Position(
            1 * new Configuration().distance,
            1 * new Configuration().distance));
        neighbor.discoverEvent(new Event(1, 1, neighbor.getPosition()));
        node.getRoutingTable().addEntry(1, 1, neighbor.getPosition());
        new Query(1, node.getPosition(), domain);
        node.executeMessage();
        assertEquals(true, node.getActionPerfomed()
            && neighbor.getActionPerfomed());
    }

    @Test
    public void MessageAreButBackInQueueIfNeighborHaveMadeActionTest() {
        Environment domain = new Environment();
        Node node = domain.getNode(new Position(0, 0));
        Node neighbor = domain.getNode(new Position(
            1 * new Configuration().distance,
            1 * new Configuration().distance));
        neighbor.discoverEvent(new Event(1, 1, neighbor.getPosition()));
        node.getRoutingTable().addEntry(1, 1, neighbor.getPosition());
        new Query(1, node.getPosition(), domain);
        neighbor.setActionPerformed(true);
        node.executeMessage();
        assertEquals(1, node.getWorkingQueue().size());
    }

    @Test
    public void MessageAreNotExecutedIfActionAlreadyPerformedTest() {
        Environment domain = new Environment();
        Node node = domain.getNode(new Position(0, 0));

        new Agent(node.getPosition(), domain);
        node.setActionPerformed(true);
        node.executeMessage();
        assertEquals(1, node.getWorkingQueue().size());
    }

    @Test
    public void executeMessageDoNotSendMessagesToExhaustedNodesTest() {
        Environment domain = new Environment();
        Node node = domain.getNode(new Position(0, 0));
        Node neighbor = domain.getNode(new Position(
            1 * new Configuration().distance,
            1 * new Configuration().distance));
        neighbor.discoverEvent(new Event(1, 1, neighbor.getPosition()));
        node.getRoutingTable().addEntry(1, 1, neighbor.getPosition());
        new Query(1, node.getPosition(), domain);
        neighbor.setActionPerformed(true);
        node.executeMessage();
        assertEquals(0, neighbor.getWorkingQueue().size());

    }
}
