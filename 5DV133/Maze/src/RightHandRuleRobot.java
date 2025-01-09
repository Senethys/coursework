/**
 * Object oriented programming, 5DV133
 * Spring '18
 * Obligatory assignment 2
 * CAS: svma0011
 * CS: kv13smn
 *
 * @author Svitri Magnusson
 */


public class RightHandRuleRobot extends Robot {

    private enum robotPointingAt {NORTH, SOUTH, EAST, WEST}

    ;
    private robotPointingAt direction;

    /**
     * This klass implements a robot that only goes to its (relative) right.
     * Class uses enums to codify directions.
     *
     * @param maze
     */

    public RightHandRuleRobot(Maze maze) {
        super(maze);
        setInitialDirection();
    }

    /**
     * A helper class that sets the initial drection of the robot.
     */
    private void setInitialDirection() {

        if (maze.isMovable(relativeNorth())) {
            direction = robotPointingAt.NORTH;

        } else if (maze.isMovable(relativeEast())) {
            direction = robotPointingAt.EAST;

        } else if (maze.isMovable(relativeSouth())) {
            direction = robotPointingAt.SOUTH;

        } else if (maze.isMovable(relativeWest())) {
            direction = robotPointingAt.WEST;
        }
    }

    /**
     * Moves the robot once step. Uses direction and a switch statement
     * to find a relative direction in a spacific order.
     * 1. Go right
     * 2. Go Straight
     * 3. Go left
     * 4. Go back
     */
    public void move() {

        try {
            switch (direction) {

                case NORTH:

                    //RIGHT

                    //If it's movable to robot's relative east,
                    //set it to current position and put robot's
                    //direction to corresponding move.
                    if (maze.isMovable(relativeEast())) {
                        setCurrentPosition(relativeEast());
                        this.direction = robotPointingAt.EAST;

                        //STRAIGHT
                    } else if (maze.isMovable(relativeNorth())) {
                        setCurrentPosition(relativeNorth());
                        this.direction = robotPointingAt.NORTH;

                        //LEFT
                    } else if (maze.isMovable(relativeWest())) {
                        setCurrentPosition(relativeWest());
                        this.direction = robotPointingAt.WEST;

                        //BACK
                    } else if (maze.isMovable(relativeSouth())) {
                        setCurrentPosition(relativeSouth());
                        this.direction = robotPointingAt.SOUTH;
                    }
                    break;

                case EAST:

                    //RIGHT
                    if (maze.isMovable(relativeSouth())) {
                        setCurrentPosition(relativeSouth());
                        this.direction = robotPointingAt.SOUTH;

                        //STRAIGHT
                    } else if (maze.isMovable(relativeEast())) {
                        setCurrentPosition(relativeEast());
                        this.direction = robotPointingAt.EAST;

                        //LEFT
                    } else if (maze.isMovable(relativeNorth())) {
                        setCurrentPosition(relativeNorth());
                        this.direction = robotPointingAt.NORTH;

                        //BACK
                    } else if (maze.isMovable(relativeWest())) {
                        setCurrentPosition(relativeWest());
                        this.direction = robotPointingAt.WEST;
                    }
                    break;

                case SOUTH:

                    //RIGHT
                    if (maze.isMovable(relativeWest())) {
                        setCurrentPosition(relativeWest());
                        this.direction = robotPointingAt.WEST;

                        //STRAIGHT
                    } else if (maze.isMovable(relativeSouth())) {
                        setCurrentPosition(relativeSouth());
                        this.direction = robotPointingAt.SOUTH;

                        //LEFT
                    } else if (maze.isMovable(relativeEast())) {
                        setCurrentPosition(relativeEast());
                        this.direction = robotPointingAt.EAST;

                        //BACK
                    } else if (maze.isMovable(relativeNorth())) {
                        setCurrentPosition(relativeNorth());
                        this.direction = robotPointingAt.NORTH;

                    }
                    break;

                case WEST:

                    //RIGHT
                    if (maze.isMovable(relativeNorth())) {
                        setCurrentPosition(relativeNorth());
                        this.direction = robotPointingAt.NORTH;
                        //STRAIGHT
                    } else if (maze.isMovable(relativeWest())) {
                        setCurrentPosition(relativeWest());
                        this.direction = robotPointingAt.WEST;
                        //LEFT
                    } else if (maze.isMovable(relativeSouth())) {
                        setCurrentPosition(relativeSouth());
                        this.direction = robotPointingAt.SOUTH;
                        //BACK
                    } else if (maze.isMovable(relativeEast())) {
                        setCurrentPosition(relativeEast());
                        this.direction = robotPointingAt.EAST;
                    }
                    break;
            }
            
        } catch (NullPointerException e) {
            System.out.println("The robot cannot move.");
            System.exit(0);
        }
    }
}
