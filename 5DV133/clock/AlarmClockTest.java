
package clock;

import org.junit.Test;

import static org.junit.Assert.*;

/*
* Object oriented programming with Java
* Spring 18
* Assignment 1
* File:         AlarmClockTest.java (MWE)
* Description:  This class is inherited from Clock.java and adds functions to set a time
*               when the users wants the alarm to go off.
* Author:       Svitri Magnusson
* CS username:  kv13smn
* Date:         2018-04-23
* Input:        void
* Output:       console prints
* Run: javac -cp /usr/share/java/junit4.jar <filenames>
* java -cp -cp .:/usr/share/java/junit4.jar org.junit.runner.JUnitCore <main>
*
*/

public class AlarmClockTest {


    final int testIncrementTimes = 120;

    @Test
    public void shouldCreateAlarmClock() throws Exception {
        Clock n = new AlarmClock();
    }

    @Test(expected = LimitException.class)
    public void shouldThrowLimitExceptionHours() throws Exception {
        Clock n = new AlarmClock(32, 23);
    }

    @Test(expected = LimitException.class)
    public void shouldThrowLimitExceptionMinutes() throws Exception {
        Clock n = new AlarmClock(2, -23);
    }

    @Test(expected = LimitException.class)
    public void shouldThrowIllegalArgumentException() throws Exception {
        Clock n = new AlarmClock(-2, -23);
    }


    @Test
    public void initialStateTest() throws Exception {
        Clock n = new AlarmClock();
        assertEquals("00:00", n.getTime());
    }


    @Test
    public void setAlarmTimeTest0() throws Exception {
        AlarmClock n = new AlarmClock();
        n.setAlarm(13,37);
        n.setTime(13,36);
        assertFalse(n.isTriggered());
    }

    @Test
    public void setAlarmTimeTest1() throws Exception {
        AlarmClock n = new AlarmClock();
        n.setAlarm(13,37);
        n.setTime(13,36);
        n.timeTick();
        System.out.println(n.getTime());
        assertTrue(n.isTriggered());
        n.timeTick();
    }


    @Test
    public void alarmSwitchTest0() throws Exception {
        AlarmClock n = new AlarmClock();
        n.setAlarm(13,37);
        n.turnOff();
        n.setTime(13,36);
        assertFalse(n.isTriggered());
    }

    @Test
    public void alarmSwitchTest1() throws Exception {
        AlarmClock n = new AlarmClock();
        n.setAlarm(13,37);
        n.turnOff();
        n.setTime(13,36);
        n.timeTick();
        System.out.println(n.getTime());
        assertFalse(n.isTriggered());
    }

    @Test
    public void alarmSwitchTest2() throws Exception {
        AlarmClock n = new AlarmClock();
        n.setAlarm(13,37);
        n.turnOff();
        n.setTime(13,36);
        n.timeTick();
        System.out.println(n.getTime());
        n.setAlarm(13,37);
        n.setTime(13,36);
        n.timeTick();
        assertTrue(n.isTriggered());
    }


}
