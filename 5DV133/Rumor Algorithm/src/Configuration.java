package src;

/**
 * Contains all parameters in the simulation that should be able to change.
 * The parameters are:
 * chanceEvent       - Chance for an event to happen this timestep for a node.
 * Used by Environment.
 * chanceAgent       - Chance for an agent to be created for a node when it
 * receives an event. Used by Node.
 * distance          - Distance between to nodes, both horizontal and vertical.
 * Used by Environment and Position.
 * commRange         - Range which a node can communicate with surrounding
 * nodes. Used by Environment to add neighbours to nodes.
 * agentSteps        - Amount of steps an agent takes in its lifetime.
 * Used by Agent.
 * querySteps        - Max amount of steps an query can take before finding a
 * node with direction to the event. Used by Query.
 * rows              - How many rows of nodes exist in the network. Used by
 * Environment when creating the network.
 * cols              - How many columns of nodes exist in the network. Used by
 * Environment when creating the network.
 * stepsBetweenQuery - How many timesteps interval between creating queries.
 * Used by Environment to decide when creating queries.
 * amountOfQueries   - How many queries to create each time. Used by Environment
 * to create queries.
 */
public class Configuration {

    public static int queriesCreated = 0;
    public static int agentsCreated = 0;
    public static int eventsCreated = 0;
    public static int queriesDone = 0;
    public final int simulationSteps = 10000;
    public final double chanceEvent = 0.0001;
    public final double chanceAgent = 0.5;
    public final int distance = 10;
    public final int commRange = 15;
    public final int agentSteps = 50;
    public final int querySteps = 45;
    public final int rows = 50;
    public final int cols = 50;
    public final int stepsBetweenQuery = 400;
    public final int amountOfQueries = 4;
}
