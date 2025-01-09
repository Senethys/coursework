package clock;

/*
* Object oriented programming with Java
* Spring 18
* Assignment 1
* File:         AlarmClock.java
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

public class AlarmClock extends Clock {

    private String alarmTime;
    private boolean status = true;

    AlarmClock() throws LimitException { }

    /* Creates an alarm clock object at specified time
    * for the alarm to go off.
    * @param hour, minute When the alarm will go off.
    * @throws LimitException if the values are set outside interval.
    */
    AlarmClock(int hour, int minute) throws LimitException {
        setAlarm(hour, minute);
    }


    /* Sets a new time for the alarm to go off.
    * Also activates the alarm if it was shut off.
    * @param hour, minute When the alarm will go off.
    * @throws LimitException if the values are set outside interval.
    */
    public void setAlarm(int hour, int minute) throws LimitException {
        if (hour > 24 || hour < 0 || minute < 0 || minute > 59) {
            throw new LimitException("Min is bigger tha max limit!");
        }

        this.status = true;
        this.alarmTime = String.format("%02d:%02d", hour, minute);
    }


    /*Asks if an alarm has gone off. Compares current time with set time.
    * @return Triggered boolean.
    */
    public boolean isTriggered() {

        if(this.getTime().equals(this.alarmTime) && this.status) {
            System.out.println("alarm");
            return true;
        }
        return false;
    }


    //Turns an alarm on.
    public void turnOn() {
        this.status = true;

    }

    //Turns alarm off. Alarm can still be set, but won't work if it's off. 
    public void turnOff() {
        this.status = false;

    }
}
