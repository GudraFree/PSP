package tema2.ejemplos.ac0_tareaImprimir.extendsThread;
import java.lang.Thread;

/**
 * @author migue
 *<p>One of the ways to create a thread of execution is to declare a class to be a subclass of Thread. This subclass should override the run method of class Thread. 
 *   An instance of the subclass can then be allocated and started.
 *<p>Se crea un objeto <code>Thread</code> (instancia de una clase que desciendede la clase <code>Thread</code>)
 *<pre>
 *<code>
 *class PrimeThread extends Thread {
         long minPrime;
         PrimeThread(long minPrime) {
             this.minPrime = minPrime;
         }

         public void run() {
             // compute primes larger than minPrime
              . . .
         }
     }
 *</code>
 *</pre>
 *<p>Se invoca al m�todo <code>start()</code> de cada objeto <code>Thread</code>; a su vez esta llamada invoca al m�todo <code>run()</code> del objeto <i>runnable</i> asociado
 *<code>
 *   PrimeThread p = new PrimeThread(143);
     p.start();
 *</code>
 */

public class ThreadObjetoThread {

		public static void main(String[] args)
		{
			System.out.println("Creaci�n de threads");
			
			//Creaci�n de objetos 'Threads' a partir de instancias de clase que implementan la interfaz 'Runnable'
			Thread thread1 = new TareaImprimirThread("Tarea1");
			Thread thread2 = new TareaImprimirThread("Tarea2");
			Thread thread3 = new TareaImprimirThread("Tarea3");
			
			System.out.println("Objetos Thread creados, iniciando tareas");
			//Inicia los threads y los lleva a estado 'en ejecuci�n'
			//No se puede predecir en que orden se ejecutar�n
			thread1.start();
			thread2.start();
			thread3.start();
			
			System.out.println("Tareas iniciadas, main termina.\n");
		}
}
