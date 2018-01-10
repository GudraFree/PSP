package tema2.ejemplos.ae0_join.a_joinning;

import tema2.ejemplos.aa0_definingAndStartingAthread.b_aSeparatedTaskClass.RunnableTask;

public class Joinning {

    static public class RunnableTask implements Runnable {

        public void run() {

            try {
                System.out.println("Hello from a thread!");
                Thread.sleep((int) (Math.random() * 4000));
                System.out.println("Good bye from a thread!");
            } catch (InterruptedException ie) {
                System.out.println(ie);
            }
        }

    }

    public static void main(String args[]) {

        // exampleJoin();  
        exampleJoinMillisNanos();
    }

    /**
     * <p>
     * <b><code>public final void join()</code></b>
     * <p>
     * Waits for this thread to die.
     * <p>
     * An invocation of this method behaves in exactly the same way as the
     * invocation <code>join(0)</code>
     *
     * @throws InterruptedException - if any thread has interrupted the current
     * thread.
     * <p>
     * The interrupted status of the current thread is cleared when this
     * exception is thrown.
     */
    static void exampleJoin() {
        //We create and start a thread object  	
        Thread t = new Thread(new RunnableTask());

        t.start();

        System.out.println("Hello from main's thread: before join");

        try {
            t.join();  //wait until t dead
        } catch (InterruptedException ie) {
            System.out.println(ie);
            return;
        }
        System.out.println("Hello from main's thread: after join");
    }

    /**
     * <p>
     * <b><code>public final void join(long millis,int nanos)</code></b>
     * <p>
     * Waits at most millis milliseconds plus nanos nanoseconds for this thread
     * to die.
     * <p>
     * This implementation uses a loop of this.wait calls conditioned on
     * this.isAlive. As a thread terminates the this.notifyAll method is
     * invoked. It is recommended that applications not use wait, notify, or
     * notifyAll on Thread instances.
     *
     * @param millis - the time to wait in milliseconds
     * @param nanos - 0-999999 additional nanoseconds to wait
     * @throws IllegalArgumentException if the value of millis is negative, or
     * the value of nanos is not in the range 0-999999
     * @throws InterruptedException if any thread has interrupted the current
     * thread.
     *
     * <p>
     * The interrupted status of the current thread is cleared when this
     * exception is thrown.
     *
     *
     */
    static void exampleJoinMillisNanos() {
        //We create and start a thread object  	
        Thread t = new Thread(new RunnableTask());

        t.start();

        System.out.println("Hello from main's thread: before join");

        try {

            t.join(2000, 1000); //wait (aprox.) 2.1 (2000 milliseconds and 1000 nanoseconds) seconds or until t dead  
            //t.join(2000);      //wait (aprox.) 2 seconds (2000 milliseconds) or until t dead 
        } catch (InterruptedException ie) {
            System.out.println(ie);
            return;
        } catch (IllegalArgumentException iae) {
            System.out.println("The value of millis is negative, or the value of nanos is not in the range 0-999999");
        }
        System.out.println("Hello from main's thread: after join");
    }

}
