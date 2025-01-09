package src;

import java.util.Stack;

/**
 * 2018-05-11
 * A abctract class that is the superclass to the subclasses Agent and Query.
 * Keeps track of the nextPosition to be travelled and the maximum number of
 * steps for the agent and query.
 */
public abstract class Message {

    protected Environment domain;
    protected Stack<Position> pathHistory = new Stack<>();
    protected int currentSteps;
    protected int maxSteps;
    private Position nextPosition;

    /**
     * A constructor that keeps track of a position.
     *
     * @param nextPosition: The next Position yo travel.
     */
    public Message(Position nextPosition, Environment domain) {

        this.nextPosition = nextPosition;
        this.domain = domain;
    }

    /**
     * Returns the next Position to be travelled in the network.
     *
     * @return: A Position
     */
    public Position getNextPosition() {

        return this.nextPosition;
    }

    /**
     * Sets the next position to be travelled in the network.
     *
     * @param nextPosition: A Position
     */
    public void setNextPosition(Position nextPosition) {

        this.nextPosition = nextPosition;
    }

    /**
     * An abstract method that is implemented in the subclasses.
     */
    public abstract void executeStep();

    /**
     * A method that sets the maximum number of
     * steps that can be travelled in the network.
     *
     * @param maxSteps: An int
     */
    protected void setMaxSteps(int maxSteps) {

        this.maxSteps = maxSteps;
    }


    /**
     * Returns a queue of message path history.
     * Used for testing purposes.
     *
     * @return pathHistory.
     */
    public Stack<Position> getPathHistory() {

        return this.pathHistory;
    }

    /**
     * Returns a amount of steps taken by the message.
     * Used for testing purposes.
     *
     * @return int steps.
     */
    public int getStepsTaken() {

        return this.currentSteps;
    }
}