package tema2.ejemplos.executorService;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import tema2.ejemplos.executorService.TareaImprimirRunnable;

public class EjecutorTareas {

		public static void main(String[] args)
		{
	     //Se crean los objetos 'runnable'
		 TareaImprimirRunnable tarea1= new TareaImprimirRunnable("tarea1");
		 TareaImprimirRunnable tarea2= new TareaImprimirRunnable("tarea2");
		 TareaImprimirRunnable tarea3= new TareaImprimirRunnable("tarea3");
		
		 System.out.println("Iniciando Executor");
		 
		 //Se crea un objeto ExecutorService para administrar los subprocesos
		 ExecutorService ejecutorTareas= Executors.newCachedThreadPool();
		 
		 //Se inician las tareas que est�n en estado ejecutable, de forma que ser�n elegidas
		 //para ejecutarse en el futuro
		 ejecutorTareas.execute(tarea1);
		 ejecutorTareas.execute(tarea2);
		 ejecutorTareas.execute(tarea3);
		 
		 //Se cierra el 'ExecutorService' de manera que no admita m�s tareas
		 ejecutorTareas.shutdown();
		 
		 System.out.println("Tareas iniciadas, main termina.\n");	
		}	
}
