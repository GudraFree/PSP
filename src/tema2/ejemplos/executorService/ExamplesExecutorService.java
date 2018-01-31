package tema2.ejemplos.executorService;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.List;


public class ExamplesExecutorService {
	
	private static ExecutorService executorService = Executors.newCachedThreadPool();
	

	/**
	 * <p>When you are done using the ExecutorService you should shut it down, so the threads do not keep running.
	 *    For instance, if your application is started via a main() method and your main thread exits your application, 
	 *    the application will keep running if you have an active ExexutorService in your application. 
	 *    The active threads inside this ExecutorService prevents the JVM from shutting down. 
	 * <p>To terminate the threads inside the ExecutorService you call its <code>shutdown()</code> method. 
	 *    The ExecutorService will not shut down immediately, but it will no longer accept new tasks, 
	 *    and once all threads have finished current tasks, the ExecutorService shuts down.
	 *    All tasks submitted to the ExecutorService before shutdown() is called, are executed. 
	 *<p>If you want to shut down the ExecutorService immediately, you can call the <code>shutdownNow()</code> method. 
	 *     This will attempt to stop all executing tasks right away, and skips all submitted but non-processed tasks.
	 *     There are no guarantees given about the executing tasks. Perhaps they stop, perhaps the execute until the end.
	 *     It is a best effort attempt. 

	 */
	
	public static void main(String args [])
	{
			
	/*
		  There are a few different ways to delegate tasks for execution
	      to an ExecutorService:
	*/
		
		execute_Runnable();
		
		//submit_Runnable();
		
		//submit_Callable();
		
		//invoke_any();
		
		//invoke_all();
			
	/*
		  There are several boolean methods to test an ExecutorService:
	*/
		
		//awaitTermination();
		
		//isShutdown_isTerminated();
		
    /*
          There are two ways of "close" the ExecutorService:
          
     */
		
		//shutdown();
		
		//shutdownNow();
		
		executorService.shutdown();
		
	}
	
	/**
	 * <p>The execute(Runnable) method takes a <code> java.lang.Runnable</code> object, 
	 *    and executes it asynchronously
	 * 
	 * <p>The Runnable interface defines only one method <code> void run()</code>, 
	 *     which can't neither return a value nor throw an exception. 
	 *     In other words: There is no way of obtaining the result of the executed Runnable
	 *     and no exception can be cached
	 */
	private static void execute_Runnable(){
		
		System.out.printf("main's thread: %s\n", Thread.currentThread());	
		
		executorService.execute(new Runnable() {
		                                        public void run() {
		                                                           System.out.printf("Asynchronous runnable task thread %s\n", Thread.currentThread());
		                                                          }
		                                        });	
	}
	
	/**
	 * <p>The submit(Runnable) method also takes a <code>Runnable</code> implementation and executes it asynchronously,
	 *    but returns a Future object. This Future object can be used to check if the Runnable as finished executing. 
	 * 
	 * <p>Since <code>run()</code> of Runnable returns nothing, get method of Future returns null.
	 * 
	 * <p> Throws the unchecked RejectedExecutionException
	 */
	private static void submit_Runnable(){
		
		System.out.printf("main's thread: %s\n", Thread.currentThread());
		
		Future<?> future = executorService.submit(new Runnable() {
		    													  public void run() {
		    														                 System.out.printf("Asynchronous runnable task thread %s\n", Thread.currentThread());
		    																	    }
		                                         });
		
		System.out.printf("The task has finished?: %s\n", future.isDone());
		
		try{
			
			Object o=future.get();  //wait until the task associated to future object finish 
			assert o==null;         //and returns null if the task has finished correctly.
			                        //If the task ends abnormally, an exception is thrown
			
		}catch(CancellationException ce){
			   System.out.println("main's thread: get() method has thrown a CancellationException");
	    }
		catch(ExecutionException ee){
			   System.out.println("main's thread: get() method has thrown an ExecutionException"); 
		}
		catch(InterruptedException ie){
			   System.out.println("main's thread: get() method has thrown an InterruptedException"); 
		}
		
	}
	
	/**
	 * <p>Submits a value-returning task for execution and returns a Future representing the pen.ding results of the task. 
	 * <p>The Future's get method will return the task's result upon successful completion. 
	 * <p>If you would like to immediately block waiting for a task, you can use constructions of the form result = exec.submit(aCallable).get();
     * <p>Throws the unchecked RejectedExecutionException
	 */
	private static void submit_Callable(){
		
		System.out.printf("main's thread: %s\n", Thread.currentThread());	
		
		//valid too if use <Object> instead of <String>; 
		//in such a case is mandatory casting the result of get: (String) future.get() 
      //Future <Object>future = executorService.submit(new Callable<Object>(){
	   //	                                                  public String call() 
	   //                                                          ...
		
	   Future <String>future=null;
	   try{
		   future = executorService.submit(new Callable<String>(){
		                                                      public String call() throws Exception {
		                                                    	  System.out.printf("Asynchronous callable task thread %s\n", Thread.currentThread());
		                                                          return "Callable Result";
		                                                      }
		                                      });
      //                        executorService.submit(Runnable task,T result); //The Future's get method will return the given result upon successful completion.
	   }catch(RejectedExecutionException ree){
		  //if the task cannot be scheduled for execution  
		   System.out.printf("the task cannot be scheduled for execution");
	   }
		
		
	  try{
		
		  System.out.println("future.get() = " + (String) future.get());
		
	  }catch(CancellationException ce){
		     System.out.println("main's thread: get() method has thrown a CancellationException");
      }
	  catch(ExecutionException ee){
		     System.out.println("main's thread: get() method has thrown an ExecutionException"); 
	  }
	  catch(InterruptedException ie){
		     System.out.println("main's thread: get() method has thrown an InterruptedException"); 
	  }
		
		
	}
	
	/**
	 * <p>The <code>invokeAny()</code> method takes a collection of Callable objects, or subinterfaces of Callable.
	 *    Invoking this method does not return a Future, but returns the result of one of the Callable objects. 
	 *    You have no guarantee about which of the Callable's results you get. Just one of the ones that finish. 
	 *    If one of the tasks complete (or throws an exception), the rest of the Callable's are cancelled. 
	 */
	
	//This code example will print out the object returned by one of the Callable's in the given collection.
	//If you try to run it a few times, the result changes. Sometimes it is "Task 1", sometimes "Task 2" etc. 

	private static void invoke_any(){
		
		//Local class that implements Callable<String>
		class CallableImplementation implements Callable<String>{
			
			private String taskName;
			
			public CallableImplementation(String taskName){this.taskName=taskName;}
			
			public String call() throws Exception {
		        return taskName;
			}
			
	    };
	    
		
	    //Define a set of Callable<String> task and add three Callable<String> tasks
	    Set<Callable<String>> callables = new HashSet<Callable<String>>();
	    callables.add(new CallableImplementation("task1"));
		callables.add(new CallableImplementation("task2"));
		callables.add(new CallableImplementation("task3"));
		
		//All task are given to the executor service and  the result of one of the task
		//(we don't know in advance which of them) is returned. When one of the task finish
		//or throw an exception, the rest of the task are cancelled
		try{
			String result = executorService.invokeAny(callables);
			//              executorService.invokeAny(callables,50, TimeUnit.SECONDS);
			System.out.println("result = " + result);
			
		}catch(ExecutionException ee){
		     System.out.println("main's thread: invokeAny() method has thrown an ExecutionException"); 
	    }catch(InterruptedException ie){
		     System.out.println("main's thread: invokeAny() method has thrown an InterruptedException"); 
	    }	
	}
	
	/**
	 * <p>The <code>invokeAll()</code> method invokes all of the Callable objects you pass to it in the collection passed as parameter. 
	 * The invokeAll() returns a list of Future objects via which you can obtain the results of the executions of each Callable.
	 * <p>Keep in mind that a task might finish due to an exception, so it may not have "succeeded". There is no way on a Future to tell the difference. 
	 */
	
private static void invoke_all(){
		
		//Local class that implements Callable<String>
		class CallableImplementation implements Callable<String>{
			
			private String taskName;
			
			public CallableImplementation(String taskName){this.taskName=taskName;}
			
			public String call() throws Exception {
		        return taskName;
			}
			
	    };
	    
		
	    //Define a set of Callable<String> task and add three Callable<String> tasks
	    Set<Callable<String>> callables = new HashSet<Callable<String>>();
	    callables.add(new CallableImplementation("task1"));
		callables.add(new CallableImplementation("task2"));
		callables.add(new CallableImplementation("task3"));
		
		//All task are given to the executor service and  a list of Future objects is returned. 
		//We CANT'T tell if a task finished normally or because an exception
		
		try{
			List<Future<String>> futures = executorService.invokeAll(callables);
            //                             executorService.invokeAll(callables,50, TimeUnit.SECONDS);
			
			for(Future<String> future : futures){
			    System.out.println("future.get = " + future.get());
			}

		}catch(ExecutionException ee){
		     System.out.println("main's thread: get() method has thrown an ExecutionException"); 
	    }catch(InterruptedException ie){
		     System.out.println("main's thread: get() or invokeAll() methods has thrown an InterruptedException"); 
	    }	
	}


    //awaitTermination Blocks until all tasks have completed execution after a shutdown request,
    //or the timeout occurs, or the current thread is interrupted, whichever happens first.

	public static void awaitTermination() {

		//executing threads
		for (int i = 0; i < 100; i++)   //Se ejecutar�n 100 tareas
			executorService.execute(new Runnable(){public void run() {
										//Leyendo el nombre de los threads en los que se ejecuta cada tarea,
										//se observa como se van reutilizando
														System.out.println("Running in: " + Thread.currentThread());}
                                  						});
		//shutting down the execution service
		executorService.shutdown();

		//waiting for service execution termination or expiration of indicated time
		try {
			//Se aguarda hasta que finalicen todas las tareas (hilos) despu�s de shutdown, 
			//pase el tiempo indicado, o el thread actual sea interrumpido ,
			// lo que suceda antes. Si acaban todas las tareas devuelve true, si no, false
			boolean allDone = executorService.awaitTermination(50, TimeUnit.SECONDS);                                                            

			System.out.println("All done: " + allDone);
         
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	//shutdown() initiates an orderly shutdown in which previously submitted tasks are executed, but no new tasks will be accepted. 
	//           Invocation has no additional effect if already shut down. 
	//           This method does not wait for previously submitted tasks to complete execution. Use awaitTermination to do that.

	//isShutdown() returns true if this executor has been shut down. This not implies that all tasks are done
	
	//isTerminated() returns true if all tasks have completed following shut down. 
	//               Note that isTerminated is never true unless either shutdown or shutdownNow was called first.
	//              isShutdown()==true  && all tasks are done ===>  isTerminated()==true
	//                                       <-------------->
	//              isTerminated()==false   ========>    isShutdown()==false  || not all tasks have finished yet
	public static void isShutdown_isTerminated(){
		
		        //executing threads
				for (int i = 0; i < 100; i++)   //Se ejecutar�n 100 tareas
					executorService.execute(new Runnable(){public void run() {
												//Leyendo el nombre de los threads en los que se ejecuta cada tarea,
												//se observa como se van reutilizando
																System.out.println("Running in: " + Thread.currentThread());}
		                                  						});
				
				assert executorService.isShutdown()==false && executorService.isTerminated()==false;
				System.out.println("Before shutdown: ExecutorService is shutdown?: "+ executorService.isShutdown());
				System.out.println("Before shutdown: ExecutorService has terminated?: "+ executorService.isTerminated());
				
				//shutting down the execution service
				executorService.shutdown();
				
				assert executorService.isShutdown()==true; //isTerminated(): �true or false?
				System.out.println("After shutdown: ExecutorService is shutdown?: "+ executorService.isShutdown());
				System.out.println("After shutdown: ExecutorService has terminated?: "+ executorService.isTerminated());
				
				boolean allDone=false;
				try {
				
					allDone = executorService.awaitTermination(50, TimeUnit.SECONDS);                                                            

					System.out.println("All done: " + allDone);
		         
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				//isShutdown()==true && allDone==true  =====>  isTerminated()==true
				assert executorService.isShutdown()==false || allDone==false || executorService.isTerminated()==true;//isTerminated():�true or false? 
				System.out.println("After awaitTermination: ExecutorService has terminated?: "+ executorService.isTerminated());
	}
	
	/**<p>Initiates an orderly shutdown in which previously submitted tasks are executed, but no new tasks will be accepted.
	 *    Invocation has no additional effect if already shut down. 
	 *<p>This method does not wait for previously submitted tasks to complete execution. Use awaitTermination to do that.
	 *
	 *<p>Trying to submit o execute a task AFTER shutdown the ExecutorService, will throw an unchecked RejectedExecutionException exception
	*/

	
	public static void shutdown(){
		
		//executing threads
		for (int i = 0; i < 100; i++)   //Se ejecutar�n 100 tareas
			executorService.execute(new Runnable(){public void run(){
														               System.out.println("Running in: " + Thread.currentThread());}
                                  						             });
		
		//shutting down the execution service
		executorService.shutdown();
		
		Future f=null;
		try{
			//This submit, after shutdown, will fail
		   f=executorService.submit(new Runnable(){public void run() {
							                                          System.out.println("This message will never be shown");}
      						                                         });
		}catch(RejectedExecutionException ree){
			assert f==null; //Since the task has been rejected, submit returns null
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Task  rejected !!!!!!!!!!!!");
		}
		
	}
	
	/**
	 * <p> After shutdownNow, tasks submitted to the ExecutorService will be in one of these situation: 
	 *     waiting for being scheduled for the first time, having been scheduled yet and therefore in execution but not having finished, completely finished.
	 *     Only the two first are affected by shutdownNow.
	 *    
	 * <p>shutdownNow attempts to stop all actively executing tasks and halts the processing of waiting tasks.
	 *    
	 * <ul>
	 *     <li><p>shutdownNow attempts to stop all actively executing tasks:
	 *         <p>There are no guarantees beyond best-effort attempts to stop processing actively executing tasks. 
	 *           For example, typical implementations will cancel via Thread.interrupt(), so any task that fails to respond to interrupts may never terminate
	 *   <li><p>halts the processing of waiting tasks, and:
	 *       <p>Returns a list of the tasks that were awaiting execution, that is, returns a list of tasks that never commenced execution.
	 * </ul>
	 * 
	 * <p>This method does not wait for actively executing tasks to terminate. Use awaitTermination to do that. 
	 * 
	 *    
	 */
	
	public static void shutdownNow(){
	
		//executorService=Executors.newSingleThreadExecutor();
		
		//executing threads
				for (int i = 0; i < 100; i++)   //Se ejecutar�n 100 tareas
					executorService.execute(new Runnable(){public void run(){
																               System.out.println("Running in: " + Thread.currentThread());}
		                                  						            });
				
				
				//shutting down the execution service
				List<Runnable> waitingTasks=executorService.shutdownNow();
				
				System.out.println("After shutdownNow");
				//We must decide what to do with waiting tasks.
				//For example, (1)run sequentially in main's thread
				//           
				for(Runnable t: waitingTasks){
					t.run();
				}
				
				//(2) or create another ExecutionService and submit tasks to it
				/*
				executorService= Executors.newCachedThreadPool();
				for(Runnable t: waitingTasks){
					executorService.submit(t);
				}
				*/
	}
}


