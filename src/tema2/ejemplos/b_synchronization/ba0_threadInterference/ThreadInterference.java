package tema2.ejemplos.b_synchronization.ba0_threadInterference;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * <b>Interference</b> happens when two operations, running in different
 * threads, but acting on the same data, interleave. This means that the two
 * operations consist of multiple steps, and the sequences of steps overlap
 *
 * <p>
 * As example, Consider a simple class called Counter. Counter is designed so
 * that each invocation of <code>increment()</code> will add 1 to c, and each
 * invocation of <code>decrement()</code> will subtract 1 from c. However, if a
 * Counter object is referenced from multiple threads, interference between
 * threads may prevent this from happening as expected.
 *
 * <p>
 * It might not seem possible for operations on instances of Counter to
 * interleave, since both operations on c are single, simple statements.
 * However, even simple statements can translate to multiple steps by the
 * virtual machine. We won't examine the specific steps the virtual machine
 * takes � it is enough to know that the single expression c++ can be decomposed
 * into three steps:
 * <ol>
 * <li> Retrieve the current value of c.
 * <li> Increment the retrieved value by 1.
 * <li> Store the incremented value back in c.
 * </ol>
 *
 * <p>
 * Suppose Thread A invokes increment at about the same time Thread B invokes
 * decrement. If the initial value of c is 0, their interleaved actions might
 * follow this sequence:
 * <ol>
 * <li>Thread A: Retrieve c.
 * <li>Thread B: Retrieve c.
 * <li>Thread A: Increment retrieved value; result is 1.
 * <li>Thread B: Decrement retrieved value; result is -1.
 * <li>Thread A: Store result in c; c is now 1.
 * <li>Thread B: Store result in c; c is now -1.
 * </ol>
 *
 * <p>
 * Thread A's result is lost, overwritten by Thread B. This particular
 * interleaving is only one possibility. Under different circumstances it might
 * be Thread B's result that gets lost, or there could be no error at all.
 *
 * <p>
 * <b>Because they are unpredictable, thread interference bugs can be difficult
 * to detect and fix</b>.
 *
 */
public class ThreadInterference {

    final static int latency = 1000;

    static class Counter {

        private int c = 0;

        public void increment() {
            //c++;
            int caux = c;
            System.out.println("+retrieve: " + c + ", in " + Thread.currentThread() + ", Counter object is: " + this.toString());
            try {
                Thread.sleep((int) (Math.random() * latency));
            } catch (InterruptedException ie) {
                System.out.println(ie);
            }
            caux++;
            c = caux;
            System.out.println("+store   : " + c + ", in " + Thread.currentThread() + ", Counter object is: " + this.toString());
        }

        public void decrement() {
            //c--;
            int caux = c;
            System.out.println("-retrieve: " + c + ", in " + Thread.currentThread() + ", Counter object is: " + this.toString());
            try {
                Thread.sleep((int) (Math.random() * latency));
            } catch (InterruptedException ie) {
                System.out.println(ie);
            }
            caux--;
            c = caux;
            System.out.println("-store   : " + c + ", in " + Thread.currentThread() + ", Counter object is: " + this.toString());
        }

        public int value() {
            return c;
        }

    }

    static class IncrementCounter implements Runnable {

        private Counter c;

        IncrementCounter(Counter c) {
            this.c = c;
        }

        public void run() {
            c.increment();
        }
    };

    static class DecrementCounter implements Runnable {

        private Counter c;

        DecrementCounter(Counter c) {
            this.c = c;
        }

        public void run() {
            c.decrement();
        }
    };

    //------------------------------------Examples---------------------------------------------
    public static void exampleNoConcurrent_Increment_Increment(Counter counter) {

        Runnable taskIncrement = new IncrementCounter(counter);

        taskIncrement.run();
        taskIncrement.run();
    }

    public static void exampleIncrement_Increment(Counter counter) {

        Runnable taskIncrement = new IncrementCounter(counter);

        Thread tA = new Thread(taskIncrement);
        Thread tB = new Thread(taskIncrement);

        try {
            tA.start();
            Thread.sleep((int) (Math.random() * latency));
            tB.start();

            tA.join();
            tB.join();
        } catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }

    public static void exampleIncrement_Decrement(Counter counter) {

        Runnable taskIncrement = new IncrementCounter(counter);
        Runnable taskDecrement = new DecrementCounter(counter);

        Thread tA = new Thread(taskIncrement);
        Thread tB = new Thread(taskDecrement);

        try {
            tA.start();
            Thread.sleep((int) (Math.random() * latency));
            tB.start();

            tA.join();
            tB.join();
        } catch (InterruptedException ie) {
            System.out.println(ie);
        }
    }

    public static void extraExample(Counter counter) {

        Runnable taskIncrement = new IncrementCounter(counter);
        Runnable taskDecrement = new DecrementCounter(counter);
        Runnable taskIncrement2 = new IncrementCounter(counter);

        //Con un pool de 2 threads, �cada tarea parece tener su propio objeto 'counter'!.
        //Esta anomal�a probablemente se debe a optimizaciones que realiza el compilador
        //-Si se suministran dos tareas, cada una se asignar� a un thread y mantendr� su propio counter 
        //-Si se suministran tres tareas, ir�n cambiando de thread, asi que ir�n tomando el valor actual del 'counter' en ese thread
        // y lo actulizar�n
        ScheduledExecutorService se = Executors.newScheduledThreadPool(2);

        se.scheduleAtFixedRate(taskIncrement, 0, 100, TimeUnit.MILLISECONDS);
        se.scheduleAtFixedRate(taskDecrement, 0, 10, TimeUnit.MILLISECONDS);
        se.scheduleAtFixedRate(taskIncrement2, 0, 10, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) {

        Counter counter = new Counter();

        //1- Sequential Increment, Increment: the result is always 2
//        exampleNoConcurrent_Increment_Increment(counter);
        //2- Concurrent Increment, Increment: the result could be :
        //    2 (correctly                                        : +retrieve1 +store1    +retrieve1 +store2  or  +retrieve1 +store2    +retrieve1 +store1)
        //    1 (incorrectly, because the first increment is lost : +retrieve1 +retrieve1 +store1    +store2  or  +retrieve1 +retrieve1 +store1    +store2)
        //    1 (incorrectly, because the second increment is lost: +retrieve1 +retrieve1 +store2    +store1  or  +retrieve1 +retrieve1 +store2    +store1)
        exampleIncrement_Increment(counter);
        //3- Concurrent Increment, Decrement: the result could be:
        //    0 (correctly:                                  +retrieve1 +store     -retrieve1 -store  or -retrieve1 -store     +retrieve1 +store)
        //   -1 (incorrectly, because the increment is lost: +retrieve1 -retrieve1 +store     -store  or -retrieve1 +retrieve1 +store     -store)
        //    1 (incorrectly, because the decrement is lost: +retrieve1 -retrieve1 -store     +store  or -retrieve1 +retrieve1 -store     +store)
        //exampleIncrement_Decrement(counter);
        //4-The task are given to an executor
        //extraExample(counter);
        System.out.println("main     : " + counter.value() + ", in " + Thread.currentThread() + ", Counter object is: " + counter.toString());
    }

}
