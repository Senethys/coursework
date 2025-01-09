package src;

import java.util.HashMap;


/**
 * Main class of the simulation and/or the network. Environment class
 * is responsible for creation and safekeeping nodes. Furthermore also
 * has the ability to control the state of the network.agents and queries.
 * This class also creates IDs for events, creates agents and queries.
 * Has a configuration class.
 */
public class Environment {

    private Configuration config = new Configuration();
    private int nrOfTimeSteps;
    private HashMap<Position, Node> nodes = new HashMap<>();
    private int nextID;


    /**
     * Constructor for Environment. Creates node with positions with in a two
     * dimension plane. Number of columns, rows and distance between nodes are
     * taken from the Configuration class. After creating nodes uses
     * addNeighbors to give all nodes their neighbors.
     */
    public Environment() {

        nextID = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
        if (nextID < 1) {
            nextID = 1;
        }


        for (int col = 0; col < config.cols; col++) {
            for (int row = 0; row < config.rows; row++) {

                Node node = new Node(new Position(
                    col * config.distance, row * config.distance));
                nodes.put(node.getPosition(), node);
            }
        }

        addNeighbors();
    }

    /**
     * Advances the simulated network by on time unit.
     * Meaning that all nodes will perform one action
     * in the network. Same for agents.
     */
    public void timeTick() {
        nrOfTimeSteps++;
        ///Create queries every 400 timetick.
        if (nrOfTimeSteps % config.stepsBetweenQuery == 0) {
            createQueries();
        }
        rollEvent();
        runNodesActions();
        setAllNodesActionsPerformed(false);
    }

    /**
     * Randomly determines if a node will "discover" an event
     * proceeded by rolling if an agent is creating in the same node.
     */
    public void rollEvent() {
        for (Node current : nodes.values()) {
            if (config.chanceEvent > Math.random()) {
                Event event = new Event(createID(), nrOfTimeSteps,
                    current.getPosition());
                current.discoverEvent(event);
                Configuration.eventsCreated++;
                if (config.chanceAgent > Math.random()) {
                    Agent agent = new Agent(
                        current.getPosition(), this);

                    agent.setMaxSteps(config.agentSteps);
                    Configuration.agentsCreated++;
                    current.setActionPerformed(true);
                }
            }
        }
    }


    /**
     * After 400 simulation steps, creates four queries within random node
     * of a random event ID.
     */
    public void createQueries() {

        Integer randomEventIndex = -1;
        int targetID;
        Node targetNode = nodes.get(
            new Position(config.distance, config.distance));

        while (randomEventIndex.equals(-1)) {
            //Pick random position and node
            int targetY = (int) (Math.random() *
                ((config.cols - 1) * config.distance + 1)) / config.distance;
            targetY *= config.distance;

            int targetX = (int) (Math.random() *
                ((config.rows - 1) * config.distance + 1)) / config.distance;
            targetX *= config.distance;

            Position targetPosition = new Position(targetX, targetY);
            targetNode = this.nodes.get(targetPosition);

            //Get random event and eventID
            int eventListSize = targetNode.getEventList().size();
            randomEventIndex = ((int) (Math.random() + 1 * (eventListSize))) - 1;
        }

        Event targetEvent = targetNode.getEventList().get(randomEventIndex);
        targetID = targetEvent.getEventID();

        int i = 0;
        while (i < config.amountOfQueries) {
            int y = (int) (Math.random() *
                ((config.cols - 1) * config.distance + 1)) / config.distance;
            y *= config.distance;

            int x = (int) (Math.random() *
                ((config.rows - 1) * config.distance + 1)) / config.distance;
            x *= config.distance;
            Position position = new Position(x, y);
            if (!getNode(position).getEventList().contains(targetEvent)) {
                Query query = new Query(targetID, position, this);
                query.setMaxSteps(config.querySteps);

                this.getNode(position).setActionPerformed(true);
                Node node = this.getNode(position);
                node.setQueryEventID(targetID);
                Configuration.queriesCreated++;
                i++;
            }
        }
    }

    /**
     * Uses real time to create hash values. Also adds
     * some values to guarantee not 0.
     *
     * @return int ID.
     */
    public int createID() {
        nextID++;
        if (nextID < 1) {
            nextID = 1;
        }
        return nextID;

    }

    /**
     * Returns the amount of steps the simulation has been
     * goin on. Time meaning in the sense of steps.
     *
     * @return int number of steps.
     */
    public int getTime() {
        return nrOfTimeSteps;
    }


    /**
     * Returns a specific node by specifying position.
     *
     * @param position node position.
     * @return int number of steps.
     */
    public Node getNode(Position position) {
        return nodes.get(position);
    }


    /**
     * Defines the working state of the nodes. Usually used to
     * reset nodes for them to be able to perform actions.
     *
     * @param action desired working states of nodes.
     */
    public void setAllNodesActionsPerformed(boolean action) {
        for (Node current : nodes.values()) {
            current.setActionPerformed(action);
        }
    }

    /**
     * Runs all nodes executeMessage if they don't have acted this timeStep.
     */
    private void runNodesActions() {

        for (Node current : nodes.values()) {

            // If current node has not taken in or sent a way a message
            // and if a sent message has gone passed specified time,
            // send a new query.
            if (current.getWaitTime() >= config.querySteps * 8 &&
                current.getQueryEventID() != -1 &&
                !current.getRequestRepeated() && !current.getActionPerfomed()) {

                new Query(current.getQueryEventID(),
                    current.getPosition(), this);
                current.setRequestRepeated(true);
                current.setActionPerformed(true);
                current.resetWaitTime();
                Configuration.queriesCreated++;
            }

            // If current node has not taken in or sent away a message
            // Do it now.
            if (!current.getActionPerfomed()) {
                current.executeMessage();
            }

            //Increase wait time for nodes with active queries.
            if (current.getWaitTime() <= config.querySteps * 8 &&
                current.getQueryEventID() != -1) {

                current.incrementWaitTime();
            }
        }
    }

    /**
     * Adds all nodes neighbours to their neighbour lists. This is done by
     * traversing the matrix in a spiral around the node. Each new node is
     * check if its distance to the node is less or equal to the commRange for
     * the simulation.
     * <p>
     * If no nodes was neighbours in a cycle of the spiral then no more nodes
     * can be neighbours and the process stops for that node and goes to next
     * node. If atleast one node is a neighbour in a cycle then it continue to
     * the next cycle.
     */
    private void addNeighbors() {

        for (Node current : nodes.values()) {
            boolean done = false;
            int range = 1;
            Position pos = current.getPosition();

            while (!done) {
                done = true;
                pos = pos.getPosToNorth();
                if (checkIfNeighbour(pos, current)) {
                    current.addNeighbour(getNode(pos));
                    done = false;
                }

                for (int i = 0; i < range; i++) {
                    pos = pos.getPosToEast();
                    if (checkIfNeighbour(pos, current)) {
                        current.addNeighbour(getNode(pos));
                        done = false;
                    }
                }

                for (int i = 0; i < 2 * range; i++) {
                    pos = pos.getPosToSouth();
                    if (checkIfNeighbour(pos, current)) {
                        current.addNeighbour(getNode(pos));
                        done = false;
                    }
                }

                for (int i = 0; i < 2 * range; i++) {
                    pos = pos.getPosToWest();
                    if (checkIfNeighbour(pos, current)) {
                        current.addNeighbour(getNode(pos));
                        done = false;
                    }
                }
                for (int i = 0; i < 2 * range; i++) {
                    pos = pos.getPosToNorth();
                    if (checkIfNeighbour(pos, current)) {
                        current.addNeighbour(getNode(pos));
                        done = false;
                    }
                }

                for (int i = 0; i < range - 1; i++) {
                    pos = pos.getPosToEast();
                    if (checkIfNeighbour(pos, current)) {
                        current.addNeighbour(getNode(pos));
                        done = false;
                    }
                }

                pos = pos.getPosToEast();

                range++;
            }
        }
    }

    /**
     * @param pos Position to check.
     * @return false if position don't correspond with a node or
     * true if it does.
     */
    private boolean insideMatrix(Position pos) {
        return nodes.get(pos) != null;
    }

    /**
     * @param pos     Position for possible neighbour.
     * @param current Node for which neighbour is possible.
     * @return True if position correspond to a neighbour for the current node.
     * Returns false otherwise.
     */
    private boolean checkIfNeighbour(Position pos, Node current) {
        if (insideMatrix(pos)) {
            int distance = (int) Math.ceil(Math.sqrt(Math.pow(pos.getX() -
                current.getPosition().getX(), 2) + Math.pow(pos.getY() -
                current.getPosition().getY(), 2)));

            return distance <= config.commRange;
        }

        return false;
    }
}