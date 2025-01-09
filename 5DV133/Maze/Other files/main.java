
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        Maze maze = new Maze(new FileReader("maze.txt"));
        RightHandRuleRobot RRobot = new RightHandRuleRobot(maze);
        //   while (!maze.isGoal(RRobot.getCurrentPosition())) {
        Position pos = RRobot.getCurrentPosition();
        System.out.print("Position: " + pos.getX());
        System.out.println(" " + pos.getY());
        RRobot.move();
        pos = RRobot.getCurrentPosition();
        System.out.print("Position: " + pos.getX());
        System.out.println(" " + pos.getY());
        RRobot.move();
        pos = RRobot.getCurrentPosition();
        System.out.print("Position: " + pos.getX());
        System.out.println(" " + pos.getY());
        RRobot.move();
        pos = RRobot.getCurrentPosition();
        System.out.print("Position: " + pos.getX());
        System.out.println(" " + pos.getY());
        RRobot.move();
        pos = RRobot.getCurrentPosition();
        System.out.print("Position: " + pos.getX());
        System.out.println(" " + pos.getY());
        RRobot.move();
        pos = RRobot.getCurrentPosition();
        System.out.print("Position: " + pos.getX());
        System.out.println(" " + pos.getY());
        RRobot.move();
        pos = RRobot.getCurrentPosition();
        System.out.print("Position: " + pos.getX());
        System.out.println(" " + pos.getY());
        RRobot.move();
        pos = RRobot.getCurrentPosition();
        System.out.print("Position: " + pos.getX());
        System.out.println(" " + pos.getY());
        RRobot.move();
        pos = RRobot.getCurrentPosition();
        System.out.print("Position: " + pos.getX());
        System.out.println(" " + pos.getY());
        RRobot.move();
        pos = RRobot.getCurrentPosition();
        System.out.print("Position: " + pos.getX());
        System.out.println(" " + pos.getY());
        //  }
    }
}
