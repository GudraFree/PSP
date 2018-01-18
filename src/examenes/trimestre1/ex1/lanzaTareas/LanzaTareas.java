package examenes.trimestre1.ex1.lanzaTareas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Perig
 */
public class LanzaTareas {
    File rutaSalida;
    HashMap<String[],File> tareas;
    
    public static void main(String[] args) {
        LanzaTareas lt = new LanzaTareas(args);
    }

    public LanzaTareas(String[] args) {
        //comprobación argumentos
        try {
            compruebaArgumentos(args);
        } catch (SintaxisException e) {
            System.out.println("Error de sintaxis");
            return;
        } catch (FileNotFoundException e) {
            System.out.println("Error: el archivo "+e.getMessage()+" no existe");
            return;
        }
        
        System.out.println("Argumentos comprobados");
        
        ArrayList<ProcessBuilder> proBuilds = new ArrayList();
        HashMap<Process,ProcessBuilder> procesos = new HashMap();
        int nProceso = 0;
        
        for (Map.Entry<String[],File> entry : tareas.entrySet()) { //iteramos la lista de tareas que hemos recibido
            ProcessBuilder pb = new ProcessBuilder();
            
            String[] comando = entry.getKey(); //obtenemos el array de comando y argumentos
            pb.command(comando); //lo asignamos como comando del PB
            
            File directorio = entry.getValue(); //obtenemos el directorio de trabajo (opcional, null si no existe)
            if (directorio!= null) pb.directory(directorio); //si no es null es que se introdujo en compruebaArgumentos, así que ponemos el working directory del PB a este
            else pb.directory(new File(".")); //si no existe, usamos el directorio actual
            proBuilds.add(pb);
            try {
                //lanzo el proceso
                Process p = pb.start();
                procesos.put(p,pb);
            } catch (IOException e) {
                System.out.println("Error de E/S: lanzar tarea");
            }
            
        } // fin for comandos
        
        
        // for lectura
        for (Map.Entry<Process,ProcessBuilder> processEntry : procesos.entrySet()) {
            Process p = processEntry.getKey();
            ProcessBuilder pb = processEntry.getValue();
            
            //relanzamiento
            int errores = 0, exitValue=0;
            try {
                p.waitFor();
                while((exitValue = p.exitValue())>0) {
                    ++errores;
                    
                    //leo su salida estándar
                    BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line;
                    try {
                        while ((line=br.readLine()) != null) {
                            String[] lecturaProceso = line.split(":");
                            System.out.println("Soy "+lecturaProceso[1]+" y he acabado con resultado de ejecución "+lecturaProceso[0]);
                            String cadenaAImprimir = (pb.command().get(0)+" "+(errores +1)+" "+exitValue);
                            Files.write(Paths.get(rutaSalida.getAbsolutePath()), cadenaAImprimir.getBytes(), StandardOpenOption.APPEND);
                        }
                    } catch (IOException e) {
                        System.out.println("Error de E/S: lectura de salida de procesos");
                    }
                    
                    
                    //relanzo proceso
                    p = pb.start();
                } // fin while
                System.out.println("Salí del while");
                exitValue = p.exitValue();
            } catch (Exception e) {
                System.out.println("Excepcion en while");
            }
            pb.environment().put("Tareas",":"+pb.command().get(0)+","+errores+","+exitValue);
            //leo su salida estándar
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            try {
                while ((line=br.readLine()) != null) {
                    String[] lecturaProceso = line.split(":");
                    System.out.println("Soy "+lecturaProceso[1]+" y he acabado con resultado de ejecución "+lecturaProceso[0]);
                    String cadenaAImprimir = (pb.command().get(0)+" "+(errores +1)+" "+exitValue);
                    Files.write(Paths.get(rutaSalida.getAbsolutePath()), cadenaAImprimir.getBytes(), StandardOpenOption.APPEND);
                }
            } catch (IOException e) {
                System.out.println("Error de E/S: lectura de salida de procesos");
            }
        }

        for (Map.Entry p : procesos.entrySet()) {
            
        }
    }
    
    private void compruebaArgumentos(String[] args) throws SintaxisException, FileNotFoundException {
        int i=0;
        String comando = null; // en forma de "comando:arg1:arg2:..."
        File directorio = null;
        tareas = new HashMap();
        try {
            while (!args[i].equals("-f")) { // bucle pareja -t (-d)
                if (args[i].equals("-t")) { // es cadena reservada -t (la primera que esperamos)
                    ++i; //siguiente argumento
                    if (args[i].equals("-t") || args[i].equals("-d") || args[i].equals("-f")) 
                        throw new SintaxisException(); //espera una palabra y recibe una cadena reservada: error de sintaxis
                    else {
                        comando = args[i];
                        ++i;
                        while (!args[i].equals("-d") && !args[i].equals("-f")) { // iteramos los argumentos hasta que lleguemos a la siguiente cadena reservada
                            comando+=" "+args[i];
                            ++i;
                        } // fin while argumentos
                    }
                } else if (args[i].equals("-d") && comando!=null) { // comprobamos que el siguiente argumento es -d, y que se ha asignado un comando anteriormente (la sintaxis es correcta)
                    ++i; //siguiente argumento
                    directorio = new File(args[i]);
                    if(!directorio.isDirectory()) throw new FileNotFoundException(directorio.getName()+ "(directorio del comando "+comando+")");
                } //no es -t ni -d
                else
                    throw new SintaxisException();
                
                if(comando!=null) 
                    tareas.put(comando.split(" "), directorio); // si en esta iteración ha leído y almacenado un comando, lo añade al map

            } // fin bucle parejas -t -d
            // al terminar el bucle, es que ha encontrado -f
            i++; // siguiente argumento
            rutaSalida = new File(args[i]); //el argumento tras -f indica la ruta del archivo que contendrá la salida
            System.out.println(rutaSalida.getAbsolutePath());
            if(!rutaSalida.isFile()) throw new FileNotFoundException(rutaSalida.getName()+" (archivo de salida)"); //si no existe, su correspondiente excepción
        } catch (IndexOutOfBoundsException e) { // si hay algún tipo de fallo en el conteo de argumentos, saltará esta excepción
            throw new SintaxisException(); // lo cual indica que había un fallo de sintaxis
        }
    }
    
    
}
