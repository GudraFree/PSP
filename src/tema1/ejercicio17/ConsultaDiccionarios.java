/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema1.ejercicio17;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Perig
 */
public class ConsultaDiccionarios {
    TreeMap<String,ArrayList<File>> palabraYDiccios;
    File rutaSalida;
    
    public static void main(String[] args) {
        ConsultaDiccionarios cd = new ConsultaDiccionarios(args);
    }
    
    public ConsultaDiccionarios(String[] args) {
        // comprobación de la corrección de los argumentos y almacenamiento
        try {
            compruebaArgumentos(args);
        } catch (SintaxisException e) {
            System.out.println("Error de sintaxis del comando. Ejemplo correcto:\n  -p palabra -d diccio1 diccio2 ... [-p palabra -d diccio1 diccio2 ... ]* -r archivoSalida");
//            System.exit(0);
            return;
        } catch (FileNotFoundException e) {
            System.out.println("Error, archivo "+e.getMessage()+" no encontrado");
//            System.exit(0);
            return;
        } 
        
        String[] comando = {"java","tema1.ejercicio17.BuscaPalabra"};
        ProcessBuilder pb = new ProcessBuilder(comando);
        ArrayList<Process> procesos = new ArrayList();
        
        // recorremos el map para enviar un proceso por cada palabra
        Iterator it = palabraYDiccios.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String,ArrayList<File>> entry = (Map.Entry) it.next();
            String palabra = entry.getKey();
            ArrayList<File> diccios = entry.getValue();
            try {
                for (File diccio : diccios) {
                    Process p = pb.start();
                    OutputStream os = p.getOutputStream();
                    os.write((palabra+"\n").getBytes());
                    os.write((diccio.getAbsolutePath()+"\n").getBytes());
                    os.flush();
                    os.close();
                    procesos.add(p);
                }
            } catch (IOException e) {
                System.out.println("Error de E/S: creación de proceso");
            }
        }
        
        
        // leemos la salida del proceso
        ArrayList<String[]> lecturas = new ArrayList();
        for (Process p : procesos) {
            BufferedReader brs = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String line;
            try {
                int i = 0;
                while ((line = brs.readLine()) != null) {
//                    System.out.println(line);
                    lecturas.add(line.split(":"));
                }
                while ((line = bre.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.out.println("Error de E/S: lectura de datos del proceso");
            }
        }
        
        //tratamiento de palabras
        TreeMap<String,int[]> resultados = new TreeMap();
        for(String[] lectura : lecturas) {
//            for (String str : lectura) System.out.println(str);
            int[] numeros = resultados.get(lectura[0]);
            if(numeros==null) {
                int[] numerosNuevos = new int[2];
                numerosNuevos[0] = Integer.parseInt(lectura[1]);
                numerosNuevos[1] = Integer.parseInt(lectura[2]);
                resultados.put(lectura[0], numerosNuevos);
            } else {
                numeros[0] = numeros[0]+Integer.parseInt(lectura[1]);
                numeros[1] = numeros[1]+Integer.parseInt(lectura[2]);
            }
        }
        
        // escritura en fichero
        StandardOpenOption append = StandardOpenOption.TRUNCATE_EXISTING;
        for (Map.Entry entry : resultados.entrySet()) {
            String palabra = (String) entry.getKey();
            int[] numeros = (int[]) entry.getValue();
            String cadena = palabra+": "+numeros[0]+", "+numeros[1]+" \n";
            try {
                Files.write(Paths.get(rutaSalida.getAbsolutePath()), cadena.getBytes(), append);
                append = StandardOpenOption.APPEND;
            } catch (IOException e) {
                System.out.println("Error de E/S: escritura en fichero de salida");
            }
//            System.out.println(cadena);
        }
        
    }
    
    private void compruebaArgumentos(String[] args) throws SintaxisException, FileNotFoundException {
        // -p palabra -d diccio1 diccio2 ... [-p palabra -d diccio1 diccio2 ... ]* -r archivoSalida
        int i=0;
        palabraYDiccios = new TreeMap();
        ArrayList<File> diccios;
        String palabra = null;
        try {
            while (!args[i].equals("-r")) { // bucle pareja -p -d
                if (args[i].equals("-p")) { // es cadena reservada -p (la primera que esperamos)
                    ++i; //siguiente argumento
                    if (args[i].equals("-p") || args[i].equals("-d") || args[i].equals("-r")) 
                        throw new SintaxisException(); //espera una palabra y recibe una cadena reservada: error de sintaxis
                    else {
                        palabra = args[i].toLowerCase();
                        ++i;
                    }
                } else if (args[i].equals("-d") && palabra!=null) { // comprobamos que el siguiente argumento es -d, y que se ha asignado una palabra anteriormente (la sintaxis es correcta)
                    diccios = new ArrayList();
                    ++i; //siguiente argumento
                    if(args[i].equals("-r")) // si tras -d viene -r, es un error de sintaxis
                        throw new SintaxisException();
                    while (!args[i].equals("-r") && !args[i].equals("-p")) { // iteramos hasta que demos con -r (y sabemos por el if anterior que este argumento no es -r), la siguiente cadena reservada
                        File diccio = null;
                        diccio = new File(args[i]); //intentamos crear un File con la ruta
                        if (!diccio.exists()) //comprobamos que exista la ruta
                            throw new FileNotFoundException(diccio.getName()+" (diccionario)"); // si no existe, excepción
                        else { // si existe, lo añadimos al ArrayList
                            diccios.add(diccio);
                            ++i; //siguiente argumento
                        }
                    } // fin while diccios
                    // hemos recogido todos los archivos y existen. La palabra está asignada. guardamos en el TreeMap
                    palabraYDiccios.put(palabra, diccios);
                } //no es -p ni -d
                else
                    throw new SintaxisException();
            } // fin bucle parejas -p -d
            // al terminar el bucle, es que ha encontrado -r
            i++; // siguiente argumento
            rutaSalida = new File(args[i]); //el argumento tras -r indica la ruta del archivo que contendrá la salida
            if(!rutaSalida.exists()) throw new FileNotFoundException(rutaSalida.getName()+" (archivo de salida)"); //si no existe, su correspondiente excepción
        } catch (IndexOutOfBoundsException e) { // si hay algún tipo de fallo en el conteo de argumentos, saltará esta excepción
            throw new SintaxisException(); // lo cual indica que había un fallo de sintaxis
        }
    }
    
    private class SintaxisException extends Exception {
        public SintaxisException() {
        }
    }
}
