package tema2.ejemplos.ac0_tareaImprimir.implementsRunnable;
import java.lang.Runnable;
import java.lang.Thread;
import java.util.Random;

/**
 * <p>La forma habitual de definir aplicaciones multihilo en Java es mediante la implementaci�n de la interfaz <code>Runnable</code>:
 * <ul>
 * <li>Un objeto <i>Runnable</i> define una "tarea" que puede ejecutarse concurrentemente con otras tareas. Para ello, <b>la clase del objeto concurrente debe implementar la interfaz <c>Runnable</c></b>
 * <li>La interfaz <code>Runnable</code> declara un solo m�todo, <b><code>run()</code></b>, el cual contiene el c�digo que define la tarea concurrente.
 * </ul>
 * 
 * <p>En este ejemplo se utilizan:
 * <ul>
 * <li>Un objeto <code>Random</code> y m�todo <code>nextInt()</code> para generar un entero aleatorio
 * <li>El m�todo est�tico <code>Thread<b>sleep()</b></code> que hace que la tarea se duerma durante un tiempo, expresado en milisegundos, fijado aleatoriamente.
 *     La excepci�n verificada <code>InterruptedException</code>, si se hace una llamada al m�todo <code>interrupt</code> del Thread (del objeto <i>runnable</i>) 'dormido'
 * </ul>
 */
public class TareaImprimirRunnable implements Runnable{
	private final int tiempoInactividad;  //tiempo de inactividad aleatorio para esta tarea
	private final String nombreTarea; //Nombre de la tarea;
	private final static Random generador = new Random();
	
	public TareaImprimirRunnable(String nombre){
		nombreTarea=nombre;  //Establece el nombre de la tarea
		tiempoInactividad=generador.nextInt(5000); //Establece un tiempo aleatorio entre 0 y 5 segundos (5000 milisegundos)
	}
	
	public void run(){
		
		try{
			System.out.printf("%s va a estar inactivo durante %d milisegundos.\n", nombreTarea, tiempoInactividad); 
			Thread.sleep(tiempoInactividad); //Deja la tarea inactiva
		}catch(InterruptedException e){
			System.out.printf("%s %s\n", nombreTarea,"termin� de forma prematura debido a una interrupci�n");
		}
		
		System.out.printf("%s termin� su inactividad\n", nombreTarea);
	}
}
