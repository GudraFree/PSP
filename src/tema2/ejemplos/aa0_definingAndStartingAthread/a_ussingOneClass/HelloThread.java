package tema2.ejemplos.aa0_definingAndStartingAthread.a_ussingOneClass;

/**
 * Each time this application is launched, the main's thread and
 * the three programmer's threads could end in different (and unknown in advance) order
 *
 */
public class HelloThread extends Thread {

    public void run() {
        System.out.println("Hello from a thread!");
    }

    public static void main(String args[]) {
        (new HelloThread()).start();
        (new HelloThread()).start();
        (new HelloThread()).start();
        System.out.println("Hello from main's thread");
    }

}
