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
        
        String[] comando = {"java","tema1.ejercicio17.BuscaPalabra"};
        ProcessBuilder pb = new ProcessBuilder(comando);
        ArrayList<Process> procesos = new ArrayList();
        
        // recorremos el map para enviar un proceso por cada palabra
        Iterator it = palabraYDiccios.entrySet().iterator();
//        System.out.println("Vamos a lanzar los procesos");
        while(it.hasNext()) {
            Map.Entry<String,ArrayList<File>> entry = (Map.Entry) it.next();
            String palabra = entry.getKey();
            ArrayList<File> diccios = entry.getValue();
            try {
                for (File diccio : diccios) {
                    Process p = pb.start();
//                    System.out.println("proceso lanzado");
                    OutputStream os = p.getOutputStream();
                    os.write((palabra+"\n").getBytes());
                    os.write((diccio.getAbsolutePath()+"\n").getBytes());
                    os.flush();
//                    System.out.println("Información enviada");
                    os.close();
                    procesos.add(p);
                }
            } catch (IOException e) {
                System.out.println("Error de E/S: creación de proceso");
            }
        }
        
        
        // leemos la salida del proceso
//        System.out.println("Leemos la salida de los procesos");
        ArrayList<String[]> lecturas = new ArrayList();
        for (Process p : procesos) {
            BufferedReader brs = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String line;
//            System.out.println("Intentamos leer este proceso");
            try {
                int i = 0;
                while ((line = brs.readLine()) != null) {
                    // debo guardar todo en un arraylist de [nombre,n1,n2] desde una línea 'nombre n1 n2'
                    // luego lo trataré en un treemap de <nombre,[n1,n2]>
                    System.out.println(line);
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
        for(String[] tupla : lecturas) {
            for (String str : tupla) System.out.println(str);
            int[] numeros = resultados.get(tupla[0]);
            if(numeros==null) {
//                System.out.println("Introduciendo nuevo numero");
                int[] numerosNuevos = new int[2];
                numerosNuevos[0] = Integer.parseInt(tupla[1]);
                numerosNuevos[1] = Integer.parseInt(tupla[2]);
                resultados.put(tupla[0], numerosNuevos);
            } else {
//                System.out.println("Sumando a numero existente");
                numeros[0] = numeros[0]+Integer.parseInt(tupla[1]);
                numeros[1] = numeros[1]+Integer.parseInt(tupla[2]);
            }
        }
        
        int[] prueba = {1,2};
        resultados.put("prueba",prueba);
        System.out.println(resultados.size());
        Iterator it2 = resultados.entrySet().iterator();
        while(it2.hasNext()) {
//            System.out.println("Antes de obtener entry");
            Map.Entry entry = (Map.Entry) it2.next();
//            System.out.println("Después de obtener entry");
            String palabra = (String) entry.getKey();
//            System.out.println("Después de obtener palabra");
            int[] numeros = (int[]) entry.getValue();
//            System.out.println("Después de obtener numeros");
            System.out.println(palabra+": "+numeros[0]+", "+numeros[1]);
        }
        
//        System.out.println("programa terminado");
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
