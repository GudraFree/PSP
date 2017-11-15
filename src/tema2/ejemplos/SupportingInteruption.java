package ad0_interrupts.a_supporting;


/**
 * <h3>Supporting Interruption</h3>
 * 
 * How does a thread support its own interruption? 
 * This depends on what it's currently doing:
 * 
 *  <ol>
 *  <li> <p> The <code>java.lang.InterruptedException</code> exception is thrown when a thread is waiting, sleeping, or otherwise occupied,
 *           and the thread is interrupted, either before or during the activity.
 *       <p> If the thread is frequently invoking methods that throw <code>InterruptedException</code>,
 *           it simply returns from the run method after it catches that exception. 
 *       <p> See first example: suppose the SleepMessages example code  were in the <code>run()</code> method of a thread's Runnable object. 
 *           Then it might be modified as show bellow to support interrupts as a <code>InterruptedException</code> thrown by <code>sleep()</code>.
 *           Many methods that throw InterruptedException, such as sleep, are designed to cancel their current operation
 *           and return immediately when an interrupt is received.
 *  <li> <p> What if a thread goes a long time without invoking a method that throws <code>InterruptedException</code>? 
 *           Then it must periodically invoke <code>Thread.interrupted()</code>, which returns true if an interrupt has been received. 
 *       <p> See second example: computingIntensively. In this simple example, the code simply tests for the interrupt and exits the thread if one has been received.
 *           In more complex applications, it might make more sense to throw an InterruptedException:         
 *           <code>
 *           <pre>
               if (Thread.interrupted()) {
                  throw new InterruptedException();
               }
             </pre>
             </code>
 *        <p>This allows interrupt handling code to be centralized in a catch clause.
 *
 *  </ol>
 *  
 *  <h3> The Interrupt Status Flag</h3>
 *  
 *  <p> The interrupt mechanism is implemented using an internal flag known as the <b>interrupt status</b>. Invoking <code>Thread.interrupt()</code> sets this flag.
 *  
 *  <ul>
 *    <li> <p>  The boolean static method <code>Thread.interrupted()</code> tests whether the current thread has been interrupted.
 *         <p>  When a thread checks for an interrupt by invoking the static method <code>Thread.interrupted()</code>, interrupt status is cleared. 
 *              In other words, if this method were to be called twice in succession, the second call would return false
 *              (unless the current thread were interrupted again, after the first call had cleared its interrupted status
 *              and before the second call had examined it). 
 *         <p>  A thread interruption ignored because a thread was not alive at the time of the interrupt will be reflected by this method returning false.
 *         <p>  Occasionally a method may wish to <b>test whether the current thread has been interrupted</b>, and if so, to immediately throw this exception. 
 *              The following code can be used to achieve this effect: 
 *              
 *         <code>
 *         <pre>
                 if (Thread.interrupted())  // Clears interrupted status!
                      throw new InterruptedException();
 *         </pre>
 *         </code>
 *         
 *         <p>By convention, any method that exits by throwing an <code>InterruptedException</code> clears interrupt status when it does so.
 *            However, it's always possible that interrupt status will immediately be set again, by another thread invoking interrupt
 *         
 *       
 *         
 *    <li> <p> The non-static <code>isInterrupted()</code> method, which is <b>used by one thread to query the interrupt status of another</b>,
 *             does not change the interrupt status flag.
 *         <p> See third example: the thread associated to 'aThreadObject' will be interrupted half of times
 *         <p> A thread interruption ignored because a thread was not alive at the time of the interrupt will be reflected by this method returning false.
 *   </ul>
 *
 *
 *
 */

public class SupportingInteruption {

	
	
	public static void main(String args[])
	{

	SupportingInteruption si=new SupportingInteruption(); //We must create an instance of SupportingInteruption to access inner class
		                                                  //because static methods (as main) can't access non static members (as SleepMessages class)
	Runnable task;
	
	//1-An example of catching a 'InterruptedException' exception (see SleepMessages's run() method)	
	task= si.new SleepMessages();
	new Thread(task).start();
	
	//2-An example of a thread testing if it has been interrupted (see ComputingIntensively's run() method)
	int[] inputs={12345,54321,14151};
	task=si.new ComputingIntensively(inputs);
	Thread aThreadObject=new Thread(task);
	aThreadObject.start();
	
	
	//3-An example of one thread (main's thread) testing if another thread (aThreadObject's thread)
	//  has been interrupted, by means of the Thread object (aThreadObject) than represents this another thread
	
	if (Math.random()< 0.5)       //The thread represented by 'aThreadObject' will be interrupted half of the times
		aThreadObject.interrupt();
	
	if (aThreadObject.isInterrupted())
		System.out.printf("From main: The thread %s has been interrupted\n", aThreadObject.getName());
	else
		System.out.printf("From main: The thread %s has NOT been interrupted\n", aThreadObject.getName());
	
	
	
	}
	
	public class SleepMessages implements Runnable{
	    public void run() {
	        String importantInfo[] = {
	            "Mares eat oats",
	            "Does eat oats",
	            "Little lambs eat ivy",
	            "A kid will eat ivy too"
	        };

	        for (int i = 0; i < importantInfo.length; i++) {
	            try{
	            	//Pause for 4 seconds
	            	Thread.sleep(4000);
	            	//Print a message
	            	System.out.println(importantInfo[i]);
	            }catch(InterruptedException ee){
	            	//option 1: Leaving the current computation and 
	            	//          do something that makes sense as a consequence of
	            	//          the interruption thrown by the method and later on
	            	//          continue with the previous computation
	            	System.out.println("From SleepMessages: Ey!, sleep has thrown an InterruptedException");
	            	//option 2: canceling current computation, so return
	            	//          note: run() can't throws exceptions
	            	return;
	            }
	        }
	    }
	}

	public class ComputingIntensively implements Runnable{
		private int[] inputs;
		private boolean[] outputs;
		int i;
		
		public ComputingIntensively(int[] inputs){
			this.inputs=inputs.clone();
			outputs=new boolean[inputs.length];
			i=0;
		}
		
		public void run(){
			for (int j = 0; j < inputs.length; j++) {
				heavyCrunch(inputs[j]);
				if (Thread.interrupted()) {
					System.out.println("From ComputingIntensively: I've been interrupted: no more crunching");
					// We've been interrupted: no more crunching.
					//Invocation to 'Thread.interrupted()' method will cause that the 'interrupt'-flat is set to "false" 
					//(if we checked again, 'Thread.interrupted()' would return 'false',
					//-unless in mid time some other thread would have interrupted the current thread again-)
					// We leave the current computation
					return;	
			    }
		}
	   }
		private void heavyCrunch(int input){
			//return true if there exits the sequence of digits represented by 'input'
			//in the decimal part of PI, else otherwise
			
			//...uf!, lets suppose so
			System.out.printf("From ComputingIntensively-heavyCrunch: Let's suppose that PI contains the secuence %d\n", input);
			outputs[i++]=true;
		}
		
	}
	
	
	
}
