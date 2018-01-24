package tema2.ejemplos.executorService;

import java.lang.Runnable;
import java.lang.Thread;
import java.util.Random;

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
