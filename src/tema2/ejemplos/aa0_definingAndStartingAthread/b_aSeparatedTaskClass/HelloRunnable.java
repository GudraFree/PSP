package tema2.ejemplos.aa0_definingAndStartingAthread.b_aSeparatedTaskClass;

/**
 * Each time this application is launched, the main's thread and
 * the three programmer's threads could end in different (and unknown in advance) order
 *
 */

//Usually the class that represents a task and the class that creates and starts
//'Thread' objects are different
public class HelloRunnable {


    public static void main(String args[]) {
    	
    	//version 1: An instance of 'RunnableTask' class, that implements 'Runnable',
    	//           is created and a reference to such a object
    	//           is saved in a local variable that represents a 'task'
    	Runnable task=new RunnableTask();
           
    	
        //We use the former reference to a task object to create and start two more
        //thread objects
    	(new Thread(task)).start();
        (new Thread(task)).start();
        (new Thread(task)).start();

        System.out.println("Hello from main's thread");
    }

}
