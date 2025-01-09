/**
 * Object oriented programming, 5DV133
 * Spring '18
 * Obligatory assignment 2
 * CAS: svma0011
 * CS: kv13smn
 *
 * @author Svitri Magnusson
 */

import java.io.*;
import java.util.HashMap;

public class Maze {
    private int mazeData;
    protected Position startPosition;
    protected Position goalPosition;
    private int xCoord = 0;
    private int yCoord = 0;
    protected HashMap<Position, String> maze = new HashMap<>();

    /**
     * This klass is responsible for reading in a txt file containg maze details.
     * also handles invalid states of the maze such as no start position.
     * Robot takes in a maze.
     *
     * @param freader
     * @throws IllegalArgumentException is thrown if there is an invalid symbol in the maze file.
     */

    public Maze(Reader freader) throws IllegalArgumentException, IOException {

        BufferedReader reader = new BufferedReader(freader);
        //For every character, create a position corresponding to it
        //and put the symbol as its value into a hashmap.
        //Also increment coordinates appropriately.
        while ((mazeData = reader.read()) != -1) {
            Position pos = new Position(xCoord, yCoord);
            char mazeChar = (char) mazeData;

            if (mazeChar == '*') {
                maze.put(pos, "*");
                xCoord++;

            } else if (mazeChar == 'G') {
                this.goalPosition = new Position(xCoord, yCoord);
                maze.put(goalPosition, "G");
                xCoord++;

            } else if (mazeChar == 'S') {
                startPosition = new Position(xCoord, yCoord);
                maze.put(startPosition, "S");
                xCoord++;

            } else if (mazeChar == '\n') {
                xCoord = 0;
                yCoord++;

            } else if (mazeChar == ' ') {
                maze.put(pos, " ");
                xCoord++;

            } else if ((int) mazeChar != 13) {
                System.out.println("File contains invalid symbols.");
                throw new IllegalArgumentException();
            }
        }
        //Control if the maze is doable by the robot.
        if (maze.get(startPosition) == null || maze.get(goalPosition) == null) {
            System.out.println("Invalid maze. Try again.");
            throw new IllegalArgumentException();
        }

    }

    /**
     * Checks if a certain position is movable in the maze, aka empty space, " ".
     *
     * @param pos
     * @return boolean, if space is empty or not.
     */
    public boolean isMovable(Position pos) {
        String mazeElement = maze.get(pos);
        if (mazeElement == " " || mazeElement == "S" || mazeElement == "G") {
            return true;
        }
        return false;
    }

    /**
     * Checks if a certain position is goal in the maze, aka "G".
     *
     * @param pos
     * @return boolean, if space is empty or not.
     */
    public boolean isGoal(Position pos) {
        if (maze.get(pos) == "G") {
            return true;
        }
        return false;
    }

    /**
     * Returns the goal position of the maze.
     *
     * @return position
     */
    public Position getStartPosition() {
        return startPosition;
    }
}