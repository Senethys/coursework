package clock;

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

public class LimitException extends Exception {

  public LimitException(String message) {

      super(message);
  }
}