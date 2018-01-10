package tema2.ejemplos.b_synchronization.bc0_synchronizedMethods;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p> The Java programming language provides two basic synchronization idioms: synchronized methods and synchronized statements.
 * 
 * <h3>SynchronizedMethods</h3>
 * 
 * <p> To make a method synchronized, simply add the synchronized keyword to its declaration:
 * 
 * <pre>
 * <code> public class SynchronizedCounter {
 *           private int c = 0;
 *
 *           public synchronized void increment() {
 *              c++;
 *           }
 *
 *           public synchronized void decrement() {
 *              c--;
 *           }
 *
 *           public synchronized int value() {
 *             return c;
 *           }
 *      }
 * </code> 
 * </pre>
 * 
 * 
 * If count is an instance of SynchronizedCounter, then making these methods synchronized has two effects:
 *<ul>
 * <li>First, it is not possible for two invocations of synchronized methods on the same object to interleave. 
 *     <b>When one thread is executing a synchronized method for an object, all other threads that invoke synchronized methods
 *     for the same object block (suspend execution) until the first thread is done with the object.</b>
 *     </li>
 * <li>Second, <b>when a synchronized method exits, it automatically establishes a happens-before relationship with any subsequent 
 *     invocation of a synchronized method for the same object. </b>
 *     This guarantees that changes to the state of the object are visible to all threads.
 *     </li>
 *</ul>
 * 
 * 
 * <p>Synchronized methods enable a simple strategy for preventing thread interference and memory consistency errors: 
 *    if an object is visible to more than one thread, all reads or writes to that object's variables are done through synchronized methods.
 *    (An important exception: final fields, which cannot be modified after the object is constructed,
 *    can be safely read through non-synchronized methods, once the object is constructed) 
 *    This strategy is effective, but can present problems with <i>liveness</i>
 *    
 *    
 * <p>Notes:
 * <ul>
 *   <li> Note that constructors cannot be synchronized � using the synchronized keyword with a constructor is a syntax error. 
 *        Synchronizing constructors doesn't make sense, because only the thread that creates an object 
 *        should have access to it while it is being constructed. </li>
 *        
 *   <li> When constructing an object that will be shared between threads, 
 *        be very careful that a reference to the object does not "leak" prematurely. 
 *        For example, suppose you want to maintain a List called instances containing every instance of class.
 *        You might be tempted to add the following line to your constructor:
 *         
 *           <p><code>     instances.add(this); </code>
 *
 *        But then other threads can use instances to access the object before construction of the object is complete.</li>
 * </ul>
 * 
 * 
 * 
 * 
 */





public class SynchronizedMethods {
	
	final static int latency=1000;
	
	static class SynchronizedCounter {
		private int c = 0;

		public synchronized void increment() {
			//c++;
			int caux=c;
			            System.out.println("+retrieve: "+ c +", in "+ Thread.currentThread()+", Counter object is: "+ this.toString());
						try{Thread.sleep((int)(Math.random()*latency));}catch(InterruptedException ie){System.out.println(ie);}
			caux++;       
			c=caux;
			            System.out.println("+store   : "+ c +", in "+ Thread.currentThread()+", Counter object is: "+ this.toString());   
		}

		public synchronized void decrement() {
			//c--;
			int caux=c;
			            System.out.println("-retrieve: "+ c +", in "+Thread.currentThread()+", Counter object is: "+ this.toString());
    	                try{Thread.sleep((int)(Math.random()*latency));}catch(InterruptedException ie){System.out.println(ie);}
    	    caux--;       
    	    c=caux;
    	                System.out.println("-store   : "+ c +", in "+Thread.currentThread()+", Counter object is: "+ this.toString());   
		}
    
		public synchronized int value() {
			return c;
		}

     }
	
	
	
	 static class IncrementCounter implements Runnable{
		 private SynchronizedCounter c;
        
		 IncrementCounter(SynchronizedCounter c){
		 		this.c=c;
		 }
        
		 public void run(){
		 	c.increment();        	  
      	}
	 };
	
	 
	 static class DecrementCounter implements Runnable{
         private SynchronizedCounter c;
         
         DecrementCounter(SynchronizedCounter c){
       	  this.c=c;
         }

         public void run(){
        	 c.decrement();
        }

}
	 



//------------------------------------Examples---------------------------------------------

public static void exampleNoConcurrent_Increment_Increment(SynchronizedCounter counter){
	 
	 Runnable taskIncrement= new IncrementCounter(counter); 
	 
	 taskIncrement.run();
	 taskIncrement.run();
}


public static void exampleIncrement_Increment(SynchronizedCounter counter){
    
	 Runnable taskIncrement= new IncrementCounter(counter);                                                                             

	 Thread tA= new Thread(taskIncrement);
	 Thread tB= new Thread(taskIncrement);

	 try{
		 tA.start(); 
		 Thread.sleep((int)(Math.random()*latency));
		 tB.start();

		 tA.join();
		 tB.join();
	 }catch(InterruptedException ie){
		 System.out.println(ie);
	 }
}


public static void exampleIncrement_Decrement(SynchronizedCounter counter){
    
	 Runnable taskIncrement= new IncrementCounter(counter);                                                
	 Runnable taskDecrement= new DecrementCounter(counter);	                             

	 Thread tA= new Thread(taskIncrement);
	 Thread tB= new Thread(taskDecrement);

	 try{
		 tA.start(); 
		 Thread.sleep((int)(Math.random()*latency));
		 tB.start();

		 tA.join();
		 tB.join();
	 }catch(InterruptedException ie){
		 System.out.println(ie);
	 }
}


public static void  main(String[] args){

	SynchronizedCounter synchronizedcounter= new SynchronizedCounter();
	 
	 //1- Sequential Increment, Increment: the result is always 2
//  exampleNoConcurrent_Increment_Increment(synchronizedcounter);
	 
	 //2- Concurrent Increment, Increment: the result could be : the result is always 2
exampleIncrement_Increment(synchronizedcounter);
	 
	 //3- Concurrent Increment, Decrement: the result could be: the result is always 0
//exampleIncrement_Decrement(synchronizedcounter);
	 	 
	 System.out.println("main     : "+synchronizedcounter.value()+", in "+Thread.currentThread()+", Counter object is: "+ synchronizedcounter.toString());
}

}




