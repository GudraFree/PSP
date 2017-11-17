package tema2.ejemplos.ad0_interrupts.b_interrupting;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CancellationException;

public class Interrupting {

	static Runnable task;
	static 	Thread t;
	
	

	public static void main (String [] args)
	{

		task= new Runnable(){
			                  public void run()
			                  {
				               System.out.println("task's thread: beginning task");
				               try{
					              Thread.sleep((int)(Math.random()*5000));
				               }catch(InterruptedException ee){
					             System.out.println("task's thread: sleep() has thrown an InterruptedException");
					             return;
				               }
			                   System.out.println("task's thread: finishing task, so I'm about to die");
			                  }
                             };
           
	 //firstExample();
	 
	 //secondExample();
                             
     //thridExample();  No hace lo que esperaba: que join lanzara la excepci�n InterruptedException
     
     //extraExample();
	 	
		
	}
	
	
	private static void isAliveOrInterrupted(Thread aThreadObject)
	{
		if(aThreadObject.isAlive())
			System.out.println("main's thread: task's thread is alive");
		else
			System.out.println("main's thread: task's thread is dead");
		
		if (aThreadObject.isInterrupted())
			System.out.println("main's thread: task's thread has been interrupted");
		else
			System.out.println("main's thread: task's thread has NOT been interrupted");
	}
	
	/**
	 * start a thread and join to it
	 */
	public static void firstExample()
	{
		Thread threadToJoin= new Thread(task);
		threadToJoin.start();
		
		
		isAliveOrInterrupted(threadToJoin);
				
		System.out.println("main's thread: about joining to threadToJoin's thread");
		
		try{
			threadToJoin.join();
		}catch(InterruptedException ee){
			System.out.println("main's thread: join() method has thrown an InterruptedException");
		}
		
		System.out.println("main's thread: after join()");
		
		
		isAliveOrInterrupted(threadToJoin);      
	
	}
	
	/**
	 * <p>start a thread, interrupt it and (try to) join to it.
	 * <p>The interruption is probably received by task's thread when has yet execute 'sleep()', 
	 * so sleep() throws InterruptedException, that is couch in 'run()' method that simply 
	 * -show a message and- returns.
	 * <p>note: <code>run()</code> CAN'T throws <code>InterruptedException</code>, �so <code>join()</code> 
	 * can't throw this exception?
	 */
	public static void secondExample()
	{
		
		Thread threadToJoin= new Thread(task);
		threadToJoin.start();
		
		//----------->
		isAliveOrInterrupted(threadToJoin);
		threadToJoin.interrupt();
		//----------->
		
		isAliveOrInterrupted(threadToJoin);
		
        System.out.println("main's thread: about joining to threadToJoin's thread");
		
		try{
			threadToJoin.join();
		}catch(InterruptedException ee){
			System.out.println("main's thread: join() method has thrown an InterruptedException");
		}
		
		System.out.println("main's thread: after join()");
		
		
		isAliveOrInterrupted(threadToJoin);      	
	}
	
	public static void thridExample()
	{
		
		Thread intermediateThread= new Thread(task);
		
		t=intermediateThread;
		
	    Thread threadToJoin=new Thread(new Runnable(){
			                      public void run(){
			                    	  
			                    	  System.out.println("intermediateThread: beginning"); 
			                    	  t.start();
			                    	  try{
			                    		  Thread.sleep((int)(Math.random()*5000));
			                    	  }catch(InterruptedException ie){
			                    		  System.out.println("intermediateThread: sleep() has thrown an InterruptedException"); 
			                    	  }
			                    	  t.interrupt();
			                    	  System.out.println("intermediateThread: finishing");
			                    	  
			                      }
			                 
		                         });
	    
		threadToJoin.start();
		
		try{
  		  Thread.sleep((int)(Math.random()*5000));
  	    }catch(InterruptedException ie){
  		  System.out.println("main's Thread: sleep() has thrown an InterruptedException"); 
  	  }
		
		
		
		System.out.println("main's thread: about joining to threadToJoin's thread");
		
		try{
			t.join();
		}catch(InterruptedException ie){
			System.out.println("main's thread: join() method has thrown an InterruptedException");
		}
		
		System.out.println("main's thread: after join()");	
	}
	
	/**
	 * <p>If we execute this method several times, before invoking <code>cancel()</code>, will happend:
	 * 
	 * <ul>
	 *    <li><p> if task's thread has been scheduled yet, the <code>sleep()</code> method in the task code,
	 *            will throw an <code>ExecutionException</code>, and
	 *            in main's thread <code>get()</code> method will throw an <code>CancelationException</code>
	 *    <li><p> if task's thread hasn't been scheduled yet, only in main's thread <code>get()</code> method
	 *            will throw an <code>CancelationException</code>
	 * </ul>
	 * 
	 * <p> If <code>shutdown()</code> is placed <i>after</i> <code>cancel()</code>, only <code>CancelationException</code>
	 *     will be thrown, because <code>shutdown()</code> waits until the scheduled task finish.
	 * <p> <code>shutdownNow()</code> will try to cancel inmediatly current scheduled tasks, so some times the both
	 *     <code>ExecutionException</code> and <code>CancelationException</code> can't be thrown
	 * 
	 * */
   public static void extraExample()
   {
	   
	   //Callable <Object> taskCallable=Executors.callable(task);
	   
	   ExecutorService es= Executors.newSingleThreadExecutor();
	   
	   Future <?>f=es.submit(task);
	   
	   es.shutdownNow();
	   
	   f.cancel(true); // System.out.printf("�Tarea cancelada?: %b\n",es.submit(task).cancel(true));
	   
	   //es.shutdown();
	  
	  
	   
	   try{
		   f.get();
	   }catch(CancellationException ce){
		   System.out.println("main's thread: get() method has thrown a CancellationException"); //<------
	   }
	   catch(ExecutionException ee){
		   System.out.println("main's thread: get() method has thrown an ExecutionException"); 
	   }
	   catch(InterruptedException ie){
		   System.out.println("main's thread: get() method has thrown an InterruptedException"); 
	   }
	   
	   
	 
	 
	 
	  /*
	   try{
		   taskCallable.call();
	   }catch(Exception e){
		   System.out.println("main's thread: join() method has thrown an Exception"); 
	   }
	   */
	   
	
   }
	
}
