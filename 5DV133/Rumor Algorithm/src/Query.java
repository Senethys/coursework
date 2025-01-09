package src;

import java.util.ArrayList;

/**
 * Query, as it sounds, queries other nodes for specific events.
 * Once a query arrives to its destination, a response will be sent
 * to the node that initially sent the query.
 */
public class Query extends Message {

    private Integer eventID;
    private Event responseObject;

    /**
     * A constructor that keeps track of a position.
     *
     * @param eventID: The next Position yo travel.
     */

    public Query(int eventID, Position startPosition, Environment environment) {

        super(startPosition, environment);
        this.eventID = eventID;
        setMaxSteps(new Configuration().querySteps);
        executeStep();
    }

    /**
     * Searches for the target event. When the target event has been found,
     * A response object is created and returned the same
     * path to the origin node.
     */
    public void executeStep() {

        //Make sure the query does not live longer than specified.
        //Since query won't jump to another node, it will delete it.
        if (maxSteps <= currentSteps) {
            return;
        }

        Node nextNode;

        //While the target node has not been reached.
        if (responseObject == null) {

            //Recall the next position set from previous iteration.
            nextNode = domain.getNode(getNextPosition());
            nextNode.addToQueue(this);
            this.pathHistory.add(nextNode.getPosition());


            //Get routing table of next node.
            RoutingTable nextNodeTable = nextNode.getRoutingTable();

            //Search if next node knows anything about sought after event.
            Integer stepsToTargetEvent = nextNodeTable.getSteps(this.eventID);

            //Go to a random node (not prev. visited) if no information is found.
            if (stepsToTargetEvent == null) {
                boolean allVisited = true;
                //Find an unvisited node.
                ArrayList<Node> nodesNeighbors = nextNode.getNeighbors();
                for (Node node : nodesNeighbors) {
                    if (!pathHistory.contains(node.getPosition())) {
                        nextNode = node;
                        allVisited = false;
                        break;
                    }
                }

                //Set a random node to next out of the unvisited.
                if (allVisited) {
                    int randomNeighbor = (int) (Math.random() *
                        nodesNeighbors.size());

                    nextNode = nodesNeighbors.get(randomNeighbor);
                    setNextPosition(nodesNeighbors.get(
                        randomNeighbor).getPosition());

                }
                this.currentSteps++;
            }

            //If there is zero steps to target event, create a response object(Event),
            //because the target is reached.
            else if (stepsToTargetEvent.equals(0)) {
                for (Event event : nextNode.getEventList()) {
                    if (eventID.equals(event.getEventID())) {
                        responseObject = event;
                        pathHistory.pop();
                        setNextPosition(pathHistory.pop());
                        return;
                    }
                }
            }


            //Use information found about event to get closer.
            else {
                Position ToTargetEvent = nextNodeTable.getNextNode(eventID);
                nextNode = domain.getNode(ToTargetEvent);
            }

            //Put itself into designated node.
            setNextPosition(nextNode.getPosition());
        }

        //If target event has been found, use path history
        //to go back to origin node.
        else if (!this.pathHistory.empty()) {
            nextNode = domain.getNode(getNextPosition());
            nextNode.addToQueue(this);
            this.setNextPosition(this.pathHistory.pop());

            //If event has reached its destination...
        } else {
            Configuration.queriesDone++;
            Position finalDestinationPosition = this.getNextPosition();
            Node finalDestinationNode = domain.getNode(finalDestinationPosition);
            finalDestinationNode.queryResponseReceived(responseObject.toString());

        }
    }
}