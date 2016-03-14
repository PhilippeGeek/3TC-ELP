package fr.insa.lyon.tc.elp.threads;

/**
 * Created by pvienne on 14/03/16.
 */
public class EvenCounterTest {

    public static void main(String... args){
        EvenCounter ec = new EvenCounter();
        new Thread(ec).start();
        System.out.println("Thread 1 started");
        new Thread(ec).start();
        System.out.println("Thread 2 started");
    }

}
