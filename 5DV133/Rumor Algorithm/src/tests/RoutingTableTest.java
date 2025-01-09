package src.tests;

import org.junit.Test;
import src.Position;
import src.RoutingTable;

import java.util.Set;

import static org.junit.Assert.*;

public class RoutingTableTest {


    @Test
    public void equalsTestDifferentObjects() {

        RoutingTable table = new RoutingTable();
        RoutingTable table2 = new RoutingTable();

        Integer[] intArray = new Integer[]{3, 8};
        Integer[] result = new Integer[2];
        Integer step = 5;
        Position pos = new Position(4, 4);
        Position pos2 = new Position(5, 6);

        table2.addEntry(intArray[0], step, pos);
        table.addEntry(intArray[1], step, pos2);

        assertFalse(table.equals(table2));

    }

    @Test
    public void equalsTestSameObject() {

        RoutingTable table = new RoutingTable();
        RoutingTable table2 = new RoutingTable();

        Integer[] intArray = new Integer[]{3, 8};
        Integer[] result = new Integer[2];
        Integer step = 5;
        Position pos = new Position(4, 4);


        table2.addEntry(intArray[1], step, pos);
        table.addEntry(intArray[1], step, pos);

        assertTrue(table.equals(table2));

    }

    @Test
    public void getSteps() {
        int ID = 6;
        Integer step = 3;
        Position pos = new Position(56, 67);

        RoutingTable table = new RoutingTable();

        table.addEntry(ID, step, pos);
        assertEquals(step, table.getSteps(6));

    }

    @Test
    public void addEntryTest() {

        int ID = 5;
        Integer step = 8;
        Position pos = new Position(32, 32);

        RoutingTable table = new RoutingTable();

        table.addEntry(ID, step, pos);
        assertEquals(step, table.getSteps(ID));
    }

    @Test
    public void getIDsetTest() {

        RoutingTable table = new RoutingTable();

        Integer[] intArray = new Integer[]{3, 8};
        Integer[] result = new Integer[2];
        Integer step = 5;
        Position pos = new Position(4, 4);
        Position pos2 = new Position(5, 6);

        table.addEntry(intArray[0], step, pos);
        table.addEntry(intArray[1], step, pos2);

        Set<Integer> s = table.getIDSet();

        assertArrayEquals(intArray, s.toArray(result));
    }

    @Test
    public void getNextNodeTest() {

        RoutingTable table = new RoutingTable();

        Integer[] intArray = new Integer[]{3, 8};
        Integer[] result = new Integer[2];
        Integer step = 5;
        Position pos = new Position(4, 4);
        Position pos2 = new Position(5, 6);

        table.addEntry(intArray[0], step, pos);
        table.addEntry(intArray[1], step, pos2);

        assertEquals(pos2, table.getNextNode(8));

    }

    @Test
    public void copyTest() {

        RoutingTable table = new RoutingTable();

        Integer[] intArray = new Integer[]{3, 8};
        Integer[] result = new Integer[2];
        Integer step = 5;
        Position pos = new Position(4, 4);
        Position pos2 = new Position(5, 6);

        table.addEntry(intArray[0], step, pos);
        table.addEntry(intArray[1], step, pos2);

        RoutingTable e = table.copy();

        assertEquals(true, e.equals(table));
    }

    @Test
    public void mergeTest() {

        RoutingTable table = new RoutingTable();

        Integer[] intArray = new Integer[]{3, 8};
        Integer[] result = new Integer[2];
        Integer step = 5;
        Position pos = new Position(4, 4);
        Position pos2 = new Position(5, 6);

        table.addEntry(intArray[0], step, pos);
        table.addEntry(intArray[1], step, pos2);

        RoutingTable farTable = new RoutingTable();

        Integer[] intArr = new Integer[]{12, 4};
        Integer[] res = new Integer[2];
        Integer steps = 12;
        Position position = new Position(34, 54);
        Position position2 = new Position(56, 65);

        farTable.addEntry(intArr[0], steps, position);
        farTable.addEntry(intArr[1], steps, position2);

        table.merge(farTable);

        assertEquals(true, table.equals(farTable));
    }

    @Test
    public void mergeTestShortestPath() {

        RoutingTable table = new RoutingTable();

        Integer[] intArray = new Integer[]{4, 8};
        Integer[] result = new Integer[2];
        Integer step = 5;
        Position pos = new Position(4, 4);
        Position pos2 = new Position(5, 6);

        table.addEntry(intArray[0], step, pos);
        table.addEntry(intArray[1], step, pos2);

        RoutingTable farTable = new RoutingTable();

        Integer[] intArr = new Integer[]{4, 8};
        Integer[] res = new Integer[2];
        Integer steps = 12;
        Position position = new Position(4, 4);
        Position position2 = new Position(5, 6);

        farTable.addEntry(intArr[0], steps, position);
        farTable.addEntry(intArr[1], steps, position2);

        table.merge(farTable);

        int mergedstep = farTable.getSteps(4);

        assertEquals(5, mergedstep);

    }

    @Test
    public void mergeTestEmptyRoutingTable() {

        RoutingTable table = new RoutingTable();

        Integer[] intArray = new Integer[]{4, 8};
        Integer[] result = new Integer[2];
        Integer step = 5;
        Position pos = new Position(4, 4);
        Position pos2 = new Position(5, 6);

        table.addEntry(intArray[0], step, pos);
        table.addEntry(intArray[1], step, pos2);

        RoutingTable farTable = new RoutingTable();

        table.merge(farTable);

        assertEquals(true, table.equals(farTable));
    }


}