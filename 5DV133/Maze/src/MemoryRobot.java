/**
 * Object oriented programming, 5DV133
 * Spring '18
 * Obligatory assignment 2
 * CAS: svma0011
 * CS: kv13smn
 *
 * @author Svitri Magnusson
 */


import java.util.*;

public class MemoryRobot extends Robot {
    private Stack unvisited = new Stack();
    private Stack treePath = new Stack();
    private ArrayList<Position> visited = new ArrayList<>();

    /*
     * This class implements a robot that solves the maze depth first.
     * Has two stacks for unvisited positions and for current path taken.
     * Keeps track of all visited positions in a ArrayList.
     * @param maze
     */


    public MemoryRobot(Maze maze) {
        super(maze);
        visited.add(getCurrentPosition());
        treePath.add(getCurrentPosition());
    }

    /*
     * This class implements a robot that solves the maze depth first.
     * Has two stacks for unvisited positions and for current path taken.
     * Keeps track of all visited positions in a ArrayList.
     * Puts step into a stack and and pops when there are no
     * unvisited positions nearby.
     */

    public void move() {
        try {
            //Get all neighbour positions relative to the robot.
            ArrayList<Position> neighbors = new ArrayList<>();
            neighbors.add(relativeNorth());
            neighbors.add(relativeEast());
            neighbors.add(relativeSouth());
            neighbors.add(relativeWest());

            boolean deadEnd = true;
            treePath.add(getCurrentPosition());

            for (int i = 0; i < 4; i++) {
                //If not visited at all.
                Position pos = neighbors.get(i);
                //Add a position to unvisited if it's not visited and if it's movable.
                if (maze.isMovable(pos) && !visited.contains(pos)) {
                    if (!unvisited.contains(pos)) {
                        unvisited.add(pos);
                    }
                    deadEnd = false;
                }
            }

            // If goal is found in the unvisited stack, go directly there.
            if (unvisited.contains(maze.goalPosition)) {
                setCurrentPosition(maze.goalPosition);
                return;

                // If the robot hits a dead end, use path history stack to go back.
            } else if (deadEnd) {

                //Remove itself from the stack so robot can go back.
                treePath.pop();
                setCurrentPosition((Position) treePath.pop());
            } else {

                //Else go to unvisited position.
                setCurrentPosition((Position) unvisited.pop());
            }
            // Add current position to all of the nodes visited. (Not the stack)
            visited.add(getCurrentPosition());
        } catch (EmptyStackException e) {
            System.out.println("Memory robot could not move.");
            System.exit(0);
        }
    }
}

