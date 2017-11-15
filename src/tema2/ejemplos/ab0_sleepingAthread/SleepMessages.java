package tema2.ejemplos.ab0_sleepingAthread;

/**
 * <p>Thread.sleep causes the current thread to suspend execution for a specified period. 
 * 
 * <p> Is can be used to:
 * 
 * <ul>
 *   <li><p>This is an efficient means of making processor time available to the other threads of
 *          an application or other applications that might be running on a computer system. 
 * 
 *   <li><p>The sleep method can also be used for pacing, as shown in the example that follows
 *       
 *   
 *   <li><p>and waiting for another thread with duties that are understood to have time requirements, 
 *          as with the SimpleThreads example in a later section.
 * </ul>
 * 
 * <p><code>sleep()</code> throws <code>InterruptedException</code>.
 *     This is an exception that sleep throws when another thread interrupts the current thread 
 *     while sleep is active
 */
public class SleepMessages {
    public static void main(String args[])
        throws InterruptedException {
        String importantInfo[] = {
            "Mares eat oats",
            "Does eat oats",
            "Little lambs eat ivy",
            "A kid will eat ivy too"
        };

        for (int i = 0;
             i < importantInfo.length;
             i++) {
            //Pause for 4 seconds
            Thread.sleep(4000);
            //Print a message
            System.out.println(importantInfo[i]);
        }
    }
}
