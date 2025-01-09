package src;

/**
 * Class for events happening at nodes. Events are defined by a unique
 * identification (unique for this simulation), time of happening and position
 * of node happening at. Time is how many cycles have been done so far in the
 * simulation.
 * <p>
 * When using toString the event is written in a specific format:
 * "x, y, time, id"
 */
public class Event {

    private Position eventOrigin;
    private int ID;
    private int time;

    /**
     * Constructor for Event. Need an ID, time of creation and Position of node
     * happening in. Time is how many cycles have happend so far in the
     * simulation.
     *
     * @param ID          Unique identification. All events in same simulation have
     *                    diffrent.
     * @param time        How many cycles have been done.
     * @param eventOrigin Position of node happening in.
     * @throws IllegalArgumentException If given ID or time which is less
     *                                  than 1.
     * @throws NullPointerException     If given null pointer.
     */
    public Event(int ID, int time, Position eventOrigin) throws
        IllegalArgumentException, NullPointerException {

        if (ID < 1) {
            throw new IllegalArgumentException("ID not allowed under 1");
        }

        if (time < 1) {
            throw new IllegalArgumentException("Time can't be lower than 1");
        }

        if (eventOrigin == null) {
            throw new NullPointerException("Need origin position");
        }

        this.eventOrigin = eventOrigin;
        this.ID = ID;
        this.time = time;
    }

    /**
     * @return The ID of the event.
     */
    public int getEventID() {
        return this.ID;
    }

    /**
     * Creates a string which contains the events position, time, id-number.
     * Format:
     * "x, y, time, id"
     *
     * @return String following the format.
     */
    @Override
    public String toString() {
        return eventOrigin.toString() + ", time of creation: " + this.time +
            ", Event ID: " + this.ID;
    }
}