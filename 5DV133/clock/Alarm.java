package clock;

/*
* Object oriented programming with Java
* Spring 18
* Assignment 1  Second revision.
* File:         Alarm.java (MWE)
* Description:  Simple program that simulates a clock with alarm functionality.
* @author:      Svitri Magnusson
* CS username:  kv13smn
* Date:         2018-04-23
* Input:        void
* Output:       console prints
* Run: javac -cp /usr/share/java/junit4.jar <filenames>
* java -cp -cp .:/usr/share/java/junit4.jar org.junit.runner.JUnitCore <main>
*
*/


public class Alarm {

    public static void main(String[] args) throws LimitException {

        //Select when you want the alarm to go off.
        int hour = 13;
        int minute = 37;

        //Create alarm object and set alarm time
        AlarmClock alarm = new AlarmClock(hour, minute);

        //While the alarm isn't triggered, increase time and print int.
        while(!alarm.isTriggered()) {
                alarm.timeTick();
                alarm.getTime();
                System.out.println(alarm.getTime());

        }

        System.out.printf("*Rewinding the clock to %02d:%02d*\n", hour, minute-1);
        alarm.setTime(13,36);
        System.out.println(alarm.getTime());
        System.out.println("Is the alarm triggered?: " + alarm.isTriggered());
        System.out.println("*TICK*");
        alarm.timeTick();
        alarm.getTime();
        System.out.println("Is the alarm triggered?: " + alarm.isTriggered());

    }
}
