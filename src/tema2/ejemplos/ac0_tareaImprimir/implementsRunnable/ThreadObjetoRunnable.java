package tema2.ejemplos.ac0_tareaImprimir.implementsRunnable;
import java.lang.Thread;

/**
 * @author migue
 *
 *<h3>Concept of Thread</h3>
 *<p>A <i>thread</i> is a thread of execution in a program. The Java Virtual Machine allows an application to have multiple threads of execution running concurrently. 
 *<p><b>Every thread has a priority</b>. Threads with higher priority are executed in preference to threads with lower priority.
 *<p>Each thread may or may not also be marked as a <b>daemon</b>.
 *<p>When code running in some thread creates a new Thread object, the new thread has its priority initially set equal to the priority of the creating thread,
 *   and is a daemon thread if and only if the creating thread is a daemon.
 *   
 *<p>When a Java Virtual Machine starts up, there is usually a single non-daemon thread (which typically calls the method named main of some designated class).
 *  <b>The Java Virtual Machine continues to execute threads until either of the following occurs</b>:
 *  <ul>
 *    <li>The exit method of class Runtime has been called and the security manager has permitted the exit operation to take place. 
 *    <li>All threads that are not daemon threads have died, either by returning from the call to the run method 
 *        or by throwing an exception that propagates beyond the run method.
 *  </ul>
 *<br>
 *<h3>How to create a thread</h3>
 *<p>There are two ways to create a new thread of execution. One way is to create a thread is to declare a class that implements the <code>Runnable</code> interface. 
 *   That class then implements the <code>run()</code> method. An instance of the class can then be allocated, passed as an argument when creating Thread, and started.
 *<p>The Runnable interface should be implemented by any class whose instances are intended to be executed by a thread.
 *   The class must define a method of no arguments called run.
 *<p>When an object implementing interface Runnable is used to create a thread, starting the thread causes the object's run method to be called 
 *   in that separately executing thread. 
 *<p>Every thread has a name for identification purposes. More than one thread may have the same name. 
 *   If a name is not specified when a thread is created, a new name is generated for it. 
 *  
 *<p>Se crea un objeto <code>Thread</code> a partir de un objeto <i>runnable</i> (instancia de una clase que implemente la interfaz <code>Runnable</code>).
 *<pre>
 *<code>
 *class PrimeRun implements Runnable {
         long minPrime;
         PrimeRun(long minPrime) {
             this.minPrime = minPrime;
         }

         public void run() {
             // compute primes larger than minPrime
              . . .
         }
     }

 *</code>
 *</pre>
 *   Se invoca al m�todo <code>start()</code> de cada objeto <code>Thread</code>; a su vez esta llamada invoca al m�todo <code>run()</code> del objeto <i>runnable</i> asociado
 *<code>
 *   PrimeRun p = new PrimeRun(143);
     new Thread(p).start();
 *</code>
 *   
 *   
 *<p>El hilo principal del proceso creado por la JVM viene dado por el c�digo del m�todo <code>main()</code>
 *   Puesto que en este ejemplo se crean varios threads, cuando termina el m�todo main() acaba tambi�n el hilo principal, pero no el proceso, ya que (posiblemente) est�n activos uno o mas de los threads lanzados.
 *   Solo cuando todos los threads hayan finalizado, finalizar� el proceso.
 
 *   
 *<p>En este ejemplo se lanzan varios threads, cuya "tarea" consiste en anunciar con un mensaje cuando comienzan a ejecutarse, se 'duermen' un tiempo aleatorio 
 *   -de este modo se simula un procesamiento que tarda un cierto tiempo en finalizar- y un mensaje antes de finalizar.
 *<p>No se puede predecir el orden en el que comenzar�n, avanzar�n y finalizar�n los hilos, aun conociendo el orden en el que fueron creados e iniciados. Si se realizan varias ejecuciones, se observa que los 4 hilos (el principal y los 3 lanzados) avanzan -y finalizan- en diferente orden cada vez
 *   (incluso puede ocurrir que el hilo con el tiempo aleatorio mas corto no sea elegido o termine antes que otro con un tiempo aleatorio mayor)
 *
 *  
 *   @see API's: 
 *      Interface Runnable, Class Thread, Class ThreadGroup, Class ThreadGroup, Interface Thread.UncaughtExceptionHandler
 */

public class ThreadObjetoRunnable {

		public static void main(String[] args)
		{
			System.out.println("Creaci�n de threads");
			
			//Creaci�n de objetos 'Threads' a partir de instancias de clase que implementan la interfaz 'Runnable'
			Thread thread1 = new Thread(new TareaImprimirRunnable("Tarea1"));
			Thread thread2 = new Thread(new TareaImprimirRunnable("Tarea2"));
			Thread thread3 = new Thread(new TareaImprimirRunnable("Tarea3"));
			
			System.out.println("Objetos Thread creados, iniciando tareas");
			//Inicia los threads y los lleva a estado 'en ejecuci�n'
			//No se puede predecir en que orden se ejecutar�n
			thread1.start();
			thread2.start();
			thread3.start();
			
			System.out.println("Tareas iniciadas, main termina.\n");
		}
}
