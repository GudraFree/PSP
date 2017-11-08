package tema2.ejemplos.aa0_definingAndStartingAthread.c_annonymousTaskClass;

/**
 * Each time this application is launched, the main's thread and
 * the three programmer's threads could end in different (and unknown in advance) order
 *
 */
public class HelloRunnable {


    public static void main(String args[]) {
    	
    	//In this example, the class 'HelloRunnable' creates and starts thread,
    	//but it is not a task (it doesn't implement the 'Runnable' interface)
    	
    	//version 1: The task is created by instantiating an anonymous class
    	//           that implements the 'Runnable' interface, so the 'run()'
    	//           method must be defined
    	(new Thread(new Runnable(){
    		                       public void run(){
    		                    	   System.out.println("Hello from a thread!");   
    		                       }
    	                          })).start();
    	
    	//version 2: An instance of an anonymous class that implements 'Runnable'
    	//           is created again, but this time a reference to such a object
    	//           is saved in a local variable that represents a 'task'
    	Runnable task=new Runnable(){
            public void run(){
         	   System.out.println("Hello from a thread!");   
            }
           };
    	
        //We use the former reference to a task object to create and start two more
        //thread objects
        (new Thread(task)).start();
        (new Thread(task)).start();

        System.out.println("Hello from main's thread");
    }

}
