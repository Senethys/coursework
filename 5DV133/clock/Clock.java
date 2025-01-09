package clock;

/*
* Object oriented programming with Java
* Spring 18
* Assignment 1
* File:         Clock.java
* Description:  Consists of two display classes. Basically a digital clock.
* @author:      Svitri Magnusson
* CS username:  kv13smn
* Email:        kv13smn@umu.se
* Date:         2018-04-23
* Input:        void
* Output:       console prints
*
* Run: javac -cp /usr/share/java/junit4.jar <filenames>
* java -cp -cp .:/usr/share/java/junit4.jar org.junit.runner.JUnitCore <main>
*
*/

public class Clock {

    private NumberDisplay minutes = new NumberDisplay(0, 59);
    private NumberDisplay hours = new NumberDisplay(0, 23);
    private String displayString;

    /* Creates a Clock object.
    * @param hour, minute
    *
    * Default value will be set to 0, 0 if not arguments given.
    * one display for hours and one for minutes.
    *
    * @throws LimitException if the display values are outside
    * of the set limits.
    * @see NumberDisplay
    */

    Clock() throws LimitException {
        if ( this.minutes == null || this.hours == null) {
            throw new IllegalArgumentException("Cannot assign null!");
        }
        this.minutes.setValue(0);
        this.hours.setValue(0);
    }

    Clock(int hour, int minute) throws LimitException {
        this.minutes.setValue(minute);
        this.hours.setValue(hour);
    }

    // Simulates the increase of one time unit.
    public void timeTick() {
        if(minutes.didWrapAround()) {
            this.hours.increment();
            this.minutes.increment();
        } else {
            minutes.increment();

        }

    }

    /* Sets the clock to provided time.
    * @param hour, minute
    * @throws LimitException if the values are set outside interval.
    */

    public void setTime(int hour, int minute) throws LimitException {
        this.minutes.setValue(minute);
        this.hours.setValue(hour);
    }

    //Gets the current time of the clock as a formatted string.
    public String getTime() {
        String min = minutes.getDisplayValue();
        String hour = hours.getDisplayValue();
        String result = String.format("%s:%s", hour, min);
        return result;
    }

    //Updates the display attribute.
    private void updateDisplay() {
        this.displayString = this.getTime();
    }
}
