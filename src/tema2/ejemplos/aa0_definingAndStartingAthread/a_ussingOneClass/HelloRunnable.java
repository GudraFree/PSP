package tema2.ejemplos.aa0_definingAndStartingAthread.a_ussingOneClass;

/**
 * Each time this application is launched, the main's thread and
 * the three programmer's threads could end in different (and unknown in advance) order
 *
 */
public class HelloRunnable implements Runnable {

	//Since 'HelloRunnable' implements the 'Runnable' interface, it must implement 'run()'
    public void run() {
        System.out.println("Hello from a thread!");
    }

    //The 'main' method is executed in its own thread, created by the JVM
    public static void main(String args[]) { 
    	
    	//We create a 'Thread' object; a 'task' need to be passed to the 'Thread' constructor
    	//A 'task' must be instance of a class that implements the 'Runnable' interface
    	//In this example, since 'HelloRunnable' implements 'Runnable', an instance
    	//of 'HelloRunnable' can be a task
    	
    	//version 1: one programmer thread
        (new Thread(new HelloRunnable())).start();
        
        //version 2: one more programmer thread
        (new Thread(new HelloRunnable())).start();
        
        //version 3: 3th thread, this time we first explicitly create a 'Thread' object
        //           and secondly we invoke the 'start()' method on the 'Thread' object
        //           to start the thread's execution
        Thread threadObject=new Thread(new HelloRunnable());
        threadObject.start();
        
        System.out.println("Hello from main's thread");
    }

}
