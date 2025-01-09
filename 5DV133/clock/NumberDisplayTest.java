package clock;

import org.junit.Test;

import static org.junit.Assert.*;

/*
* Object oriented programming with Java
* Spring 18
* Assignment 1
* File:         NumberDisplayTest.java
* Description:
* Author:       Svitri Magnusson
* CS username:  kv13smn
* Date:         2018-04-23
* Input:        void
* Output:       console prints
* Run: javac -cp /usr/share/java/junit4.jar <filenames>
* java -cp -cp .:/usr/share/java/junit4.jar org.junit.runner.JUnitCore <main>
*
*/

public class NumberDisplayTest {

    final int testMinLimit = 0;
    final int testMaxLimit = 59;
    final int testIncrementTimes = 120;

    @Test
    public void shouldCreateDisplay() throws Exception {
        new NumberDisplay(1, 2);
    }

    @Test(expected = LimitException.class)
    public void shouldThrowLimitExceptionAgain() throws Exception {
        new NumberDisplay(10, 0);
    }

    @Test(expected = LimitException.class)
    public void shouldThrowLimitException0() throws Exception {
        new NumberDisplay(0, 0);
    }


    @Test
    public void initialStateTestValue() throws Exception {
        NumberDisplay n = new NumberDisplay(testMinLimit, testMaxLimit);
        assertEquals(testMinLimit, n.getValue());

    }

    @Test
    public void initialStateTestDisplay() throws Exception {
        NumberDisplay n = new NumberDisplay(testMinLimit, testMaxLimit);
        assertEquals("00", n.getDisplayValue());

    }

    @Test
    public void initialStateTestIncrementValue() throws Exception {
        NumberDisplay n = new NumberDisplay(testMinLimit, testMaxLimit);
        n.increment();
        assertEquals(1, n.getValue());
    }

    @Test
    public void initialStateTestIncrementDisplay() throws Exception {
        NumberDisplay n = new NumberDisplay(testMinLimit, testMaxLimit);
        n.increment();
        assertEquals("01", n.getDisplayValue());
    }

    @Test
    public void initialStateTestDisplayIncrementValue() throws Exception {
        NumberDisplay n = new NumberDisplay(testMinLimit, testMaxLimit);
        n.increment();
        assertNotEquals(0, n.getValue());
    }

    @Test
    public void initialStateTestDisplayIncrementDisplay() throws Exception {
        NumberDisplay n = new NumberDisplay(testMinLimit, testMaxLimit);
        n.increment();
        assertNotEquals("00", n.getDisplayValue());
    }

    @Test
    public void initialStateTestNoIncrementValue() throws Exception {
        NumberDisplay n = new NumberDisplay(testMinLimit, testMaxLimit);
        assertNotEquals(1, n.getValue());
    }

    @Test
    public void initialStateTestNoIncrementDisplay() throws Exception {
        NumberDisplay n = new NumberDisplay(testMinLimit, testMaxLimit);
        assertNotEquals("01", n.getDisplayValue());
    }

    @Test
    public void setValueTestDisplay() throws Exception {
        NumberDisplay n = new NumberDisplay(0, 59);
        n.setValue(59);
        assertEquals("59", n.getDisplayValue());
    }

    @Test
    public void setValueTestValue() throws Exception {
        NumberDisplay n = new NumberDisplay(0, 59);
        n.setValue(59);
        assertEquals(59, n.getValue());
    }

    @Test
    public void WrapAroundTestValue() throws Exception {
        NumberDisplay n = new NumberDisplay(testMinLimit, testMaxLimit);
        n.setValue(59);
        n.increment();
        n.increment();
        assertEquals(1, n.getValue());
    }

    @Test
    public void WrapAroundTestDisplay() throws Exception {
        NumberDisplay n = new NumberDisplay(testMinLimit, testMaxLimit);
        n.setValue(59);
        n.increment();
        n.increment();
        assertEquals("01", n.getDisplayValue());
    }


    @Test
    public void testIncrementTimesValue() throws Exception {
        NumberDisplay n = new NumberDisplay(testMinLimit, testMaxLimit);
        for (int i = 0; i < testIncrementTimes; i++) {
            n.increment();
        }
        assertEquals(0, n.getValue());

    }

    @Test
    public void testIncrementTimesDisplay() throws Exception {
        NumberDisplay n = new NumberDisplay(testMinLimit, testMaxLimit);
        for (int i = 0; i < testIncrementTimes; i++) {
            n.increment();
        }
        assertEquals("00", n.getDisplayValue());

    }


    @Test
    public void WrapAroundTestMiddleValue() throws Exception {
        NumberDisplay n = new NumberDisplay(testMinLimit, testMaxLimit);
        for (int i = 0; i < testMaxLimit / 2; i++) {
            n.increment();
        }
        assertEquals(testMaxLimit / 2, n.getValue());

    }

    @Test
    public void WrapAroundTestMiddle() throws Exception {
        NumberDisplay n = new NumberDisplay(testMinLimit, testMaxLimit);
        for (int i = 0; i < testMaxLimit / 2; i++) {
            n.increment();
        }
        assertEquals("29", n.getDisplayValue());

    }
}