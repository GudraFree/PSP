/*
 * archivo1 -> E-programa1-S -> E-programa2-S -> archivo2
 */
package tema1.ejercicio16;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 *
 * @author Perig
 */
public class EncadenaIO {
    public static void main(String[] args) { //java EncadenaIO programa1 programa2 archivo1 archivo2
        byte[] salida = new byte[100];
        ProcessBuilder pb1, pb2;
        Process p1=null, p2=null;
        String programa1="", programa2="";
        File file1=null, file2=null;
        
        // controlar la entrada (parcialmente, no controla que los programas existan)
        try {
            programa1 = args[0];
            programa2 = args[1];
            file1 = new File(args[2]);
            file2 = new File(args[3]);
            if (!file1.exists()) throw new FileNotFoundException("primer");
            if (!file2.exists()) throw new FileNotFoundException("segundo");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error, no se introdujeron todos los argumentos. Ejemplo:\n\tjava EncadenaIO programa1 programa2 archivo1 archivo2");
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.out.println("Error, el "+e.getMessage()+" archivo introducido no existe");
            System.exit(0);
        }
        String[] comando1 = {"java","tema1.ejercicio16."+programa1};
        String[] comando2 = {"java","tema1.ejercicio16."+programa2};
        pb1 = new ProcessBuilder(comando1);
        pb2 = new ProcessBuilder(comando2);
        
        //creando el primer proceso
        pb1.redirectInput(file1);
        pb1.redirectOutput();
        
        //creando el segundo proceso
        pb2.redirectInput(); // como usamos el mismo PB, tenemos que cambiar de vuelta 
        pb2.redirectOutput(file2);                       // la entrada a PIPE, la cambiamos para el primer proceso
        
        //lanzando ambos procesos
        try {
            p1 = pb1.start();
            System.out.println("Proceso 1 creado");
            p2 = pb2.start();
            System.out.println("Proceso 2 creado");
        } catch (IOException e) {
            System.out.println("Error de E/S");
        }
        
        // recoger salida de p1 e introducirla suavemente, con amor, en la apretada entrada de p2
        try {
            String line ="";
            BufferedReader br = new BufferedReader(new InputStreamReader(p1.getInputStream()));
            OutputStream os = p2.getOutputStream();
            System.out.println("Creados reader (1) y OS (2). Procediendo a leer las líneas");

            while ((line = br.readLine()) != null) {
                System.out.println("linea "+line);
                os.write(line.getBytes());
                os.flush();
            }
            os.close();
            
            System.out.println("Bucle terminado");
        } catch (IOException e) {
            System.out.println("Error de E/S");
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Null Pointer Exception");
        }
        
        System.out.println("Programa terminado");
    }
    
}
