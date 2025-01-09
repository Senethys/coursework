package src;

public class Demo {

    public static void main(String[] args) {
        Environment domain = new Environment();
        int step = new Configuration().simulationSteps;

        System.out.println("Start!");
        long time = System.currentTimeMillis();
        for (int i = 0; i < step; i++) {
            domain.timeTick();
            if ((i + 1) % 1000 == 0) {
                System.out.println("Time: " + (i + 1));
            }
        }

        System.out.print("\nTime taken in milli seconds: ");
        System.out.println(System.currentTimeMillis() - time);
        System.out.print("Time taken in seconds: ");
        System.out.println((System.currentTimeMillis() - time) / 1000);
        System.out.println("Events created: " + Configuration.eventsCreated);
        System.out.println("Agents created: " + Configuration.agentsCreated);
        System.out.println("Queries created: " + Configuration.queriesCreated);
        System.out.println("Queries answered: " + Configuration.queriesDone);

    }
}
