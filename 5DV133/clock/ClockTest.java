
package clock;

import org.junit.Test;

import static org.junit.Assert.*;

/*
* Object oriented programming with Java
* Spring 18
* Assignment 1
* File:         ClockTest.java
* Description:  Tests Clock class.
* @author:      Svitri Magnusson
* CS username:  kv13smn
* Email:        kv13smn@umu.se
* Date:         2018-04-23
* Input:        void
* Output:       console prints
* Run: javac -cp /usr/share/java/junit4.jar <filenames>
* java -cp -cp .:/usr/share/java/junit4.jar org.junit.runner.JUnitCore <main>
*
*/


public class ClockTest {

    final int testIncrementTimes = 120;

    @Test
    public void shouldCreateClock() throws Exception {
        Clock n = new Clock();
    }

    @Test(expected = LimitException.class)
    public void shouldThrowLimitExceptionHours() throws Exception {
        Clock n = new Clock(32, 23);
    }

    @Test(expected = LimitException.class)
    public void shouldThrowLimitExceptionMinutes() throws Exception {
        Clock n = new Clock(2, -23);
    }


    @Test
    public void initialStateTest() throws Exception {
        Clock n = new Clock();
        assertEquals("00:00", n.getTime());
    }

    @Test
    public void initialStateTestIncrement() throws Exception {
        Clock n = new Clock();
        n.timeTick();
        assertEquals("00:01", n.getTime());
    }

    @Test
    public void setTimeTest() throws Exception {
        Clock n = new Clock();
        n.setTime(23, 59);
        assertEquals("23:59", n.getTime());
    }

    @Test(expected = LimitException.class)
    public void setTimeTestLimit() throws Exception {
        Clock n = new Clock();
        n.setTime(233, 529);
    }

    @Test
    public void setTimeTestIncrement0() throws Exception {
        Clock n = new Clock();
        n.setTime(23, 59);
        n.timeTick();
        assertEquals("00:00", n.getTime());


    }

    @Test
    public void setTimeTestIncrement1() throws Exception {
        Clock n = new Clock();
        n.timeTick();
        for (int i = 0; i < testIncrementTimes; i++) {
            n.timeTick();
        }
        assertEquals("02:01", n.getTime());
    }


    @Test
    public void setTimeTestIncrement2() throws Exception {
        Clock n = new Clock();
        n.timeTick();
        for (int i = 0; i < testIncrementTimes; i++) {
            n.timeTick();
        }
        for (int i = 0; i < testIncrementTimes * 4 + 23; i++) {
            n.timeTick();
        }
        assertEquals("10:24", n.getTime());

        n.setTime(13, 42);
        assertEquals("13:42", n.getTime());

    }


    @Test
    public void setTimeTestIncrement3() throws Exception {
        Clock n = new Clock();
        n.timeTick();

        for (int i = 0; i < testIncrementTimes; i++) {
            n.timeTick();
        }
        for (int i = 0; i < testIncrementTimes * 4 + 23; i++) {
            n.timeTick();
        }

        n.setTime(13, 42);
        assertEquals("13:42", n.getTime());

    }


    @Test
    public void TotalWrapAroundTest0() throws Exception {
        Clock n = new Clock();
        n.setTime(13, 42);
        assertEquals("13:42", n.getTime());

    }

    @Test
    public void TotalWrapAroundTest1() throws Exception {
        Clock n = new Clock();
        n.setTime(13, 42);

        for (int i = 0; i < 1440; i++) {
            n.timeTick();
        }
        assertEquals("13:42", n.getTime());
        n.timeTick();


    }

    @Test
    public void TotalWrapAroundTestFull() throws Exception {
        Clock n = new Clock();
        n.setTime(13, 42);

        for (int i = 0; i < 1440; i++) {
            n.timeTick();
        }
        n.timeTick();
        for (int i = 0; i < 617; i++) {
            n.timeTick();
        }
        assertEquals("00:00", n.getTime());
    }
}
