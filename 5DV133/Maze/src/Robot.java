/**
 * Object oriented programming, 5DV133
 * Spring '18
 * Obligatory assignment 2
 * CAS: svma0011
 * CS: kv13smn
 *
 * @author Svitri Magnusson
 */


public abstract class Robot {

    private Position position;
    protected Maze maze;

    public Robot() {
    }

    /**
     * Abstract klass robots that implements all other versions.
     * Has functionality for basic navigation.
     * Also includes helper functions that returns robots relative position to N,E,W,S.
     *
     * @param maze
     */

    public Robot(Maze maze) {
        this.maze = maze;
        this.setCurrentPosition(maze.startPosition);

    }

    /**
     * Abstract klass that is implemented in specialized klasses.
     */
    public abstract void move();

    /**
     * Returns the current position of the robot.
     *
     * @return current position.
     */
    public Position getCurrentPosition() {

        return this.position;
    }

    /**
     * Set the current position of the robot to a specified one.
     *
     * @param position
     */
    protected void setCurrentPosition(Position position) {

        this.position = position;
    }

    /**
     * Checks of the current position is the same as goal position.
     *
     * @return boolean, if robot has reached its goal.
     */
    public boolean hasReachedGoal() {
        if (maze.isGoal(this.position)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * These are helpers functions that use current position
     * to get neighbouring positions.
     */
    protected Position relativeNorth() {
        return getCurrentPosition().getPosToNorth();
    }

    protected Position relativeEast() { return getCurrentPosition().getPosToEast(); }

    protected Position relativeWest() {
        return getCurrentPosition().getPosToWest();
    }

    protected Position relativeSouth() {
        return getCurrentPosition().getPosToSouth();
    }
}
