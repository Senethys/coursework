package src;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Class for the most atomic part of the network, a node.
 * Nodes are responsible for recording its events and keeping a routing table
 * of its nearest neighboring nodes. Nodes are also responsible for the
 * execution its messages, such as queries or agents.
 */
public class Node {

    private ArrayList<Node> Neighbors = new ArrayList<>();
    private Queue<Message> WorkQueue = new LinkedList<>();
    private ArrayList<Event> EventObjectList = new ArrayList<>();
    private RoutingTable routingTable = new RoutingTable();
    private Position position;
    private boolean actionPerformed = false;
    private int waitTime = 0;
    private int queryEventID = -1;
    private boolean requestRepeated = false;

    /**
     * Constructor for Node. Need a position class.
     *
     * @param position Position of node.
     * @throws NullPointerException If given null pointer.
     */

    public Node(Position position) throws NullPointerException {

        if (position == null) {
            throw new NullPointerException("Need position for node.");
        }

        this.position = position;
    }

    /**
     * Add a neighbor node to current node container.
     *
     * @param node
     */
    public void addNeighbour(Node node) {

        this.Neighbors.add(node);
    }


    /**
     * This methods executes an Agent or Query action from the
     * front of node's working queue. Exhausts node's action for
     * one iteration.
     */
    public void executeMessage() {

        //If the nodes working queue is not empty...
        if (!WorkQueue.isEmpty()) {

            //While the nodes does not already have an action performed...
            int j = 0;
            while (j < WorkQueue.size() && !this.actionPerformed) {

                Node nextNode = null;
                int i = 0;
                while (i < Neighbors.size() && nextNode == null) {

                    //If the message in the working queue of current node
                    //has next position the same as the nodes neighbor, add it has next node.
                    if (Neighbors.get(i).getPosition().equals(
                        WorkQueue.peek().getNextPosition())) {
                        nextNode = Neighbors.get(i);
                    }
                    i++;
                }

                // If next node has not exhausted itself this turn,
                // execute the first message in the queue, and set itself as action performed.
                if (!nextNode.getActionPerfomed()) {
                    WorkQueue.peek().executeStep();
                    WorkQueue.remove();
                    actionPerformed = true;
                    nextNode.setActionPerformed(true);

                    // else put the front message back to queue.
                } else {
                    WorkQueue.add(WorkQueue.peek());
                    WorkQueue.remove();
                }

                j++;
            }
        }
    }

    /**
     * Adds an event to neighbor's list of events.
     *
     * @param event
     */
    public void discoverEvent(Event event) {

        this.EventObjectList.add(event);
        this.routingTable.addEntry(event.getEventID(), 0, position);
    }

    /**
     * Getter for node's action state.
     *
     * @return if node has performed an action.
     */
    public boolean getActionPerfomed() {

        return this.actionPerformed;
    }

    /**
     * Setter for node's action state.
     *
     * @param action
     */
    public void setActionPerformed(boolean action) {
        this.actionPerformed = action;

    }

    /**
     * Getter for the node's requested Event by id.
     *
     * @return eventID.
     */
    public int getQueryEventID() {
        return this.queryEventID;
    }

    /**
     * Used when node receives a query. Event ID is used by the node
     * so it knows where to resend the Query in case it does node respond
     * in designated time.
     *
     * @param eventID
     */
    public void setQueryEventID(int eventID) {
        this.queryEventID = eventID;
        this.resetWaitTime();
    }

    /**
     * Increments the node has waited for a query response.
     */
    public void incrementWaitTime() {
        this.waitTime++;
    }

    /**
     * Getter for the node's waited time for query response.
     *
     * @return time node has waited.
     */
    public int getWaitTime() {
        return this.waitTime;
    }

    /**
     * Used when node receives a query. Event ID is used by the node
     * so it knows where to resend the Query in case it does node respond
     * in designated time.
     */
    private void resetQueryEventID() {
        this.queryEventID = -1;
    }


    /**
     * This function is used when a node has received a response from a query.
     */
    public void resetWaitTime() {
        this.waitTime = 0;
    }

    /**
     * This function is used when a node has received a response from a query.
     *
     * @return boolean that tells if an extra query has been sent by the node.
     */
    public boolean getRequestRepeated() {
        return this.requestRepeated;
    }

    /**
     * A setter that tells the node if it had sent an extra query request or not.
     *
     * @param repeatedCondition
     */
    public void setRequestRepeated(boolean repeatedCondition) {
        this.requestRepeated = repeatedCondition;
    }

    /**
     * When a node receives a successful query response, it resets requested event ID,
     * time it has waited, and query repeat status. Also prints auto the requested
     * Event's data.
     *
     * @param queryResponseInformation
     */
    public void queryResponseReceived(String queryResponseInformation) {
        this.resetQueryEventID();
        this.resetWaitTime();
        this.setRequestRepeated(false);
        System.out.println(queryResponseInformation);
    }

    /**
     * Gets the routing table of the node.
     * Contains event IDs, time the events happened and
     * steps to its position;
     *
     * @return routing table of the node as a hashmap.
     */
    public RoutingTable getRoutingTable() {
        return this.routingTable;
    }

    /**
     * Adds a message type (Agent or Query) class
     * to the nodes queue to be performed on.
     *
     * @return Agent or Query that was added to node's
     * working queue.
     */
    public Message addToQueue(Message message) {

        this.WorkQueue.add(message);
        return message;
    }

    /**
     * Returns a list of all the Event objects
     * that the node has discovered.
     * Event objects contain position, time and id.s
     *
     * @return Node's events.
     */
    public ArrayList<Event> getEventList() {

        return this.EventObjectList;
    }

    /**
     * Returns position of the node.
     *
     * @return position of node.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Returns neighbours of the node.
     *
     * @return ArrayList of Neighbors.
     */
    public ArrayList<Node> getNeighbors() {
        return Neighbors;
    }

    /**
     * A getter for testing purposes.
     *
     * @return queue of messages.
     */
    public Queue<Message> getWorkingQueue() {
        return this.WorkQueue;
    }
}