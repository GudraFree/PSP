package tema1;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;
import java.util.Map;
import java.util.Scanner;

public class Ejercicio16 {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		//archivos con los que trataran los hijos
		File fileHijo1= new File("entrada.txt");
		File fileHijo2= new File("salida.txt");
                
		
	
		//pido al usuario que me diga el nombre de las dos clases hijo 
//		Scanner sc=new Scanner(System.in);
//		System.out.println("Introduce el nombre de la clase del primer hijo: ");

//		String claseHijo1=sc.nextLine();
		//contruyo la linea de comando del hijo1
		String[] commandLineHijo1={"java","tema1.Keijo"};

//		System.out.println("Introduce el nombre de la clase del segundo hijo: ");

//		String claseHijo2=sc.nextLine();
		//contruyo la linea de comando del hijo2
		String[] commandLineHijo2={"java","tema1.Keijo"};

		//creo los ProcessBuilder de ambos hijos
		ProcessBuilder phijo1 = new ProcessBuilder(commandLineHijo1);
		ProcessBuilder phijo2= new ProcessBuilder(commandLineHijo2);

	
		//introduzco mi variable de entorno CLASSPATH en las variables de entorno del hijo1
//        Map<String, String> environmenth1 = phijo1.environment();
//        environmenth1.put("CLASSPATH","/home/rubenbp/Dropbox/Curso-DAM/workspace/psp/bin");
        //como ambas clases estan en la misma raiz le dijo al hijo2 que sus variables de entorno seran las mismas que las del hijo1
//        Map<String, String> environmenth2= phijo2.environment();
//        environmenth2.put("CLASSPATH","/home/rubenbp/Dropbox/Curso-DAM/workspace/psp/bin");

        
        //ahora seteo las entradas y salidas de mis hijos, sabiendo que el primer hijo, su salida sera hacia mi, y del segundo su entrada sera mia
        
        //entrada y salida del hijo1
        phijo1.redirectInput(fileHijo1);
        phijo1.redirectOutput();
        
        
        //entrada y salida del hijo2
        phijo2.redirectInput();
        phijo2.redirectOutput(fileHijo2);
        
        //si quisiera tener alguno como INHERIT , se hace asi
        
        //phijo2.redirectInput(ProcessBuilder.Redirect.INHERIT);
        
        //se ejecutan los dos procesos de los hijos
        Process ph1=phijo1.start();
        Process ph2= phijo2.start();
        
        //cuando determino que los hijos tienen tuberias hacia mi despues tengo que capturarlas
        //capturo la salida del hijo1 
        InputStream ph1OUT = ph1.getInputStream();
        //capturo la entrada del hijo2
        OutputStream ph2IN= ph2.getOutputStream();
        
        //leo lo que el hijo1 me manda
		BufferedReader lectura = new BufferedReader (new InputStreamReader (ph1OUT));
		
		String line="";
		//aqui voy guardando lo que recibo del hijo
		String lineaConvertida="";

	    while ((line = lectura.readLine ()) != null) 
	      {
	    	  System.out.println(line);
	    	  //lineaConvertida+=line;
	    	  //puede guardarse todo el contenido en un String o directamente ir escribiendo en cada paso del while
	  	       ph2IN.write(line.getBytes());

	     

          }
	    
	    //cierro el flujo con el  hijo1 una vez leido
	    ph1OUT.close();
	    //hasta que no acabe el hijo1 no dejo que el padre siga su ejecucion
	    //ph1.waitFor();
	    
	    //escribo en el hijo2 lo que he recibido del hijo1
	   // ph2IN.write(lineaConvertida.getBytes());
	    //cierro el flujo de escritura con el hijo2
	    ph2IN.close();
	    //hasta que el hijo2 no acabe, el padre no podra seguir ejecutando sus ordenades
        //ph2.waitFor();
        
        
       
        
        
     


	}

}
