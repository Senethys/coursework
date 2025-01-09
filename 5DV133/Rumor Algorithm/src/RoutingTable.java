package src;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This class keeps track of how many steps it is to an event (using id)
 * and to find a node position using the same id. Both are kept in a
 * hashmap. Used by message in executeMessage();
 */

public class RoutingTable {

    //steps = ID, steps
    private HashMap<Integer, Integer> steps = new HashMap<>();

    //nextNode = ID, pos
    private HashMap<Integer, Position> nextNode = new HashMap<>();

    /**
     * A constructor that creates a routingtable containing two hashmaps.
     */
    public RoutingTable() {
    }

    /**
     * Checks if the given object is the same object, is null and is same class.
     * if it's not the same object or null and is of same class then for all
     * entries check if they are equal.
     *
     * @param obj position to check.
     * @return boolean which return true if they are the same object or if they
     * are different object of same class with equal entries. Returns false if
     * object is null, of different class or entries which is not equal.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (this == null) return false;
        if (this.getClass() != obj.getClass()) return false;

        RoutingTable e = (RoutingTable) obj;

        Set<Integer> IDs = this.steps.keySet();
        if (!IDs.containsAll(e.getIDSet())) {
            return false;
        }

        for (Integer ID : IDs) {
            if (!steps.get(ID).equals(e.getSteps(ID))) {
                return false;
            }
            if (!nextNode.get(ID).equals(e.getNextNode(ID))) {
                return false;
            }
        }

        return true;
    }


    /**
     * Add a entry to both HashMaps.
     *
     * @param ID   key for both HashMaps.
     * @param step value in steps HashMap.
     * @param pos  value in nextNode HashMap.
     */
    public void addEntry(int ID, int step, Position pos) {
        steps.put(ID, step);
        nextNode.put(ID, pos);

    }

    /**
     * Returns the number of steps from an event.
     *
     * @param ID Identification corresponding to a position.
     * @return An Integer.
     */
    public Integer getSteps(int ID) {

        return steps.get(ID);
    }

    /**
     * Returns the position for next node to travel to.
     *
     * @param ID Identification corresponding to a position.
     * @return A position.
     */
    public Position getNextNode(Integer ID) {
        return nextNode.get(ID);
    }

    /**
     * Returns a set of keys (set of ID's)
     *
     * @return A set of keys
     */
    public Set<Integer> getIDSet() {
        return steps.keySet();
    }

    /**
     * Makes a copy of a RoutingTable and puts entries in to
     * the new RoutingTable.
     *
     * @return copy of this RoutingTable.
     */
    public RoutingTable copy() {

        RoutingTable e = new RoutingTable();
        for (Integer ID : steps.keySet()) {

            e.addEntry(ID, steps.get(ID), nextNode.get(ID));
        }
        return e;
    }

    /**
     * Updates the routingtable with the lowest number of steps from the event.
     *
     * @param e routingtable to sync this table with.
     */
    public void merge(RoutingTable e) {

        Set<Integer> s = new HashSet<>();
        s.addAll(steps.keySet());
        s.addAll(e.getIDSet());

        for (Integer ID : s) {

            if (steps.get(ID) == null) {
                steps.put(ID, e.getSteps(ID));
                nextNode.put(ID, e.getNextNode(ID));
            } else if (e.getSteps(ID) == null) {
                e.addEntry(ID, steps.get(ID), nextNode.get(ID));
            } else if (steps.get(ID) < e.getSteps(ID)) {
                e.addEntry(ID, steps.get(ID), nextNode.get(ID));
            } else {
                steps.put(ID, e.getSteps(ID));
                nextNode.put(ID, e.getNextNode(ID));
            }
        }
    }
}
