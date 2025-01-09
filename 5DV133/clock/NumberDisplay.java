package clock;

import java.lang.String;

/*
* Object oriented programming with Java
* Spring 18
* Assignment 1
* File:         NumberDisplay.java
* Description:  Display numbers between min and max interval. Wraps around.
* Author:       Svitri Magnusson
* CS username:  kv13smn
* Email:        kv13smn@umu.se
* Date:         2018-04-23
* Input:        void
* Output:       console prints
* Run: javac -cp /usr/share/java/junit4.jar <filenames>
* java -cp -cp .:/usr/share/java/junit4.jar org.junit.runner.JUnitCore <main>
*
*/


public class NumberDisplay {
    private int minLimit;
    private int maxLimit;
    private int value;

    /* Creates a new NumberDisplay class with a given min max interval.
     * @param min, max Interval of numbers that will be displayed by the class.
     * @throws LimitException if min is bigger than max {@link LimitException}.

     */

    NumberDisplay(int min, int max) throws LimitException {
        if (min > max || min == max) {
            throw new LimitException("Min is bigger tha max limit!");
        }
        this.minLimit = min;
        this.maxLimit = max;
    }

    /*
     * Getter method for value of the display.
     * @return value The current value of the display as int.
     */

    public int getValue() {
        return value;
    }


    /* Setter method used to set the display value.
    * @param newValue
    * @throws LimitException if the param outside set value.
    */
    public void setValue(int newValue) throws LimitException {
        if (newValue < minLimit || newValue > maxLimit) {
            throw new LimitException("Wrong Limit!");
        }
        this.value = newValue;
    }

    //Gets the value as a string format.

    public String getDisplayValue() {
        String result = String.format("%02d", this.value);
        return result;
    }

    //Simulates the increase of a time unit.
    public void increment() {
        if (didWrapAround()) {
            this.value = minLimit;
        } else {
            this.value++;
        }
    }

    /* This if statement checks if the clock starts at 0 or not.
     * otherwise it would never increment form starting position.
     * @return wrapAround boolean. Says if it the display overshot its limit.
     */
    public boolean didWrapAround() {

        if (this.value != 0) {
            return (this.value % this.maxLimit == 0);
        } else {
            return false;
        }
    }

}