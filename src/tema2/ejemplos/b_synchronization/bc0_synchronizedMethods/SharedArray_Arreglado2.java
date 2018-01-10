package tema2.ejemplos.b_synchronization.bc0_synchronizedMethods;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class SharedArray_Arreglado2 {

	static class SimpleArray{
		
		private final int array[];
		private int writeIndex=0;
		private final static Random generator=new Random();
		
		public SimpleArray(int size){
			array= new int[size];
		}
		
		public  void add(int value)
		{
			
			
			synchronized(this)
                        { 
                         
			
                         int position= writeIndex;
                        
                          
                         try{
				Thread.sleep(generator.nextInt(500));
			 }catch(InterruptedException ie){
				ie.printStackTrace();
			 }  
                            
			 array[position]=value;
			
			 System.out.printf("%s: wrote %2d in position %d, ",Thread.currentThread().getName(),value,position);
			                
                         ++writeIndex;
                        }
			
                        
                        
			System.out.printf("Next write index: %d\n", writeIndex);
		}
		
		public String toString()
		{
		 String stringArray="\nSimpleArray content:\n";
		 
		 for(int i=0;i<array.length;i++)
			 stringArray += array[i]+ " ";
		 
		 return stringArray;
		}
		
	}
	
	public static class ArrayWriter implements Runnable{
		private final SimpleArray sharedSimpleArray;
		private final int initialValue;
		
		public ArrayWriter(int value, SimpleArray array){
			initialValue=value;
			sharedSimpleArray=array;
		}
		
		public void run()
		{
			for(int i=initialValue;i<initialValue+3;i++)
				sharedSimpleArray.add(i);
		}
	}
	
	public static void main(String[] arg)
	{
		//A 'SimpleArray' to be shared
		SimpleArray sharedSimpleArray=new SimpleArray(6);
		
		//Two 'ArrayWriter' that write in the same shared 'SimpleArray'
		ArrayWriter writer1=new ArrayWriter(1,sharedSimpleArray);
		ArrayWriter writer2=new ArrayWriter(11,sharedSimpleArray);
		
		//An Executor is defined and both write task are given to it
		ExecutorService executor=Executors.newCachedThreadPool();
		//(1)These two task write in the sharedArray, interleaving
		//   the values randomly.
		//(2)A same position in the array may be written twice: one
		//   because one task and the other because the other task
		//(3)Same positions may not be never written
		executor.execute(writer1);
		executor.execute(writer2);
		
		executor.shutdown();
		
		try{//Wait until both task finish
			if(executor.awaitTermination(1, TimeUnit.MINUTES))
				System.out.println(sharedSimpleArray);
			else
				System.out.println("Waiting time for task to end is over");
		}catch(InterruptedException ie){
			System.out.println("An interruption was recived while waiting tasks to finish");
		}
	}
	
}
