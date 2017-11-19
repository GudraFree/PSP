/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema1.ejercicio17;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author Perig
 */
public class ConsultaDiccionarios {
    TreeMap<String,ArrayList<File>> palabraYDiccios;
    
    public static void main(String[] args) {
        ConsultaDiccionarios cd = new ConsultaDiccionarios(args);
    }
    
    public ConsultaDiccionarios(String[] args) {
        // comprobación de la corrección de los argumentos y almacenamiento
        try {
            compruebaArgumentos(args);
        } catch (SintaxisException e) {
            System.out.println("Error de sintaxis del comando. Ejemplo correcto:\n  -p palabra -d diccio1 diccio2 ... [-p palabra -d diccio1 diccio2 ... ]* -r archivoSalida");
            System.exit(0);
        } catch (FileNotFoundException e) {
            System.out.println("Error, archivo "+e.getMessage()+" no encontrado");
            System.exit(0);
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
                    while (!args[i].equals("-r")) { // iteramos hasta que demos con -r (y sabemos por el if anterior que este argumento no es -r), la siguiente cadena reservada
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
            File ruta = new File(args[i]); //el argumento tras -r indica la ruta del archivo que contendrá la salida
            if(!ruta.exists()) throw new FileNotFoundException(ruta.getName()+" (archivo de salida)"); //si no existe, su correspondiente excepción
        } catch (IndexOutOfBoundsException e) { // si hay algún tipo de fallo en el conteo de argumentos, saltará esta excepción
            throw new SintaxisException(); // lo cual indica que había un fallo de sintaxis
        }
    }
    
    private class SintaxisException extends Exception {
        public SintaxisException() {
        }
    }
}
