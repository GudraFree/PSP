package tema2.ejemplos.ac0_tareaImprimir.extendsThread;
import java.lang.Runnable;
import java.lang.Thread;
import java.util.Random;

/**
 * <p>Another way to create a thread of execution is to declare a class to be a subclass of <code>Thread</code>. 
 *    This subclass should override the <code>run()</code> method of class Thread. 
 *    An instance of the subclass can then be allocated (<b><code>object=new <i>classExtendsThread()</i></code></b>)
 *    and started (<b><code>object.start()</code></b>).
 * 
 *
 */
public class TareaImprimirThread extends Thread{
	private final int tiempoInactividad;  //tiempo de inactividad aleatorio para esta tarea
	private final String nombreTarea; //Nombre de la tarea;
	private final static Random generador = new Random();
	
	public TareaImprimirThread(String nombre){
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
