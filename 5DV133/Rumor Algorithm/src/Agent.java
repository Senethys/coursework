package src;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Agent is responsible to go semi-randomly in the network (between nodes)
 * and update routingtables of the network.
 * The purpose of agents is to update the network pathways so that
 * Query messages are to be able to reach its destination.
 * Agents are created based on chance.
 */


public class Agent extends Message {

    private RoutingTable routingTable = new RoutingTable();

    /**
     * Constructor of the Agents. Subclass of message.
     * Takes in position and the routing table of the node. Uses executeStep to
     * get position for next node.
     *
     * @param position    Position for starting node.
     * @param environment environment created in.
     */
    public Agent(Position position, Environment environment) {
        super(position, environment);
        setMaxSteps(new Configuration().agentSteps);
        executeStep();

    }

    /**
     * Syncronizes the routing tables of the agent and the node
     * and goes to another node working queue.
     * Takes in position and the routing table of the node.
     */
    public void executeStep() {

        //Get the next node (next node is also the initial one) and sync routingtables.
        Node targetNode = domain.getNode(getNextPosition());
        RoutingTable nodesTable = targetNode.getRoutingTable();
        routingTable.merge(nodesTable);

        //Check if agent has not expired, go to target node and save path history.
        if (this.currentSteps < this.maxSteps) {
            targetNode.addToQueue(this);
            this.pathHistory.add(targetNode.getPosition());
            this.currentSteps++;
        }

        updateRoutingTable();

        decideNextNode(targetNode);
    }

    /**
     * @return RoutingTable for agent.
     */
    public RoutingTable getRoutingTable() {
        return routingTable;
    }

    /**
     * Decide which node to visit next. Chooses a random neighbor
     * for targetNode. If possible chooses an unvisited node.
     *
     * @param targetNode Node which neighbors to decide from.
     */
    private void decideNextNode(Node targetNode) {

        //Get neighbour nodes and check for next unvisited position.
        ArrayList<Node> nodesNeighbors = targetNode.getNeighbors();
        HashSet<Integer> checkedNeighbors = new HashSet<>();


        //Set a random, unvisited node as next to visit.
        int randomNeighbor = 0;
        while (!checkedNeighbors.contains(randomNeighbor)) {
            randomNeighbor = (int) (Math.random() * nodesNeighbors.size());
            if (!pathHistory.contains(nodesNeighbors
                .get(randomNeighbor).getPosition())) {

                setNextPosition(nodesNeighbors.get(randomNeighbor)
                    .getPosition());
                return;
            }
            checkedNeighbors.add(randomNeighbor);
        }

        //Set a random, visited node as next if all neighbors been visited.
        randomNeighbor = (int) (Math.random() * nodesNeighbors.size());
        setNextPosition(nodesNeighbors.get(randomNeighbor).getPosition());
    }

    //Increase amount of steps to each routing entry.
    private void updateRoutingTable() {
        for (Integer ID : routingTable.getIDSet()) {
            routingTable.addEntry(ID, routingTable.getSteps(ID) + 1,
                getNextPosition());
        }
    }
}