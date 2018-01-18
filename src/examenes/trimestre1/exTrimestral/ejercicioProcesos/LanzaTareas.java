/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examenes.trimestre1.exTrimestral.ejercicioProcesos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Perig
 */
public class LanzaTareas {
    ArrayList<ProcessBuilder> pbs;
    
    public static void main(String[] args) {
        LanzaTareas lt = new LanzaTareas();
        
        //comprobación argumentos
        try {
            lt.compruebaArgumentos(args);
        } catch (SintaxisException e) {
            System.out.println("Error de sintaxis");
            return;
        } catch (FileNotFoundException e) {
            System.out.println("Error: el archivo "+e.getMessage()+" no existe");
            return;
        }
        
        //pasado este bloque, tenemos los argumentos bien y almacenados en ProcessBuilders
        
        //definimos nuestras listas de procesos
        ArrayList<Process> procesos = new ArrayList();
        ArrayList<OutputStream> entradas = new ArrayList();
        ArrayList<InputStream> salidas  = new ArrayList();
        
        //lanzamos todos los procesos
        for(ProcessBuilder pb : lt.pbs) {
            try {
                Process p = pb.start();
                procesos.add(p);
                if (pb.redirectOutput() == Redirect.PIPE) { //si la input no ha sido redireccionada a fichero, seguirá en PIPE
                    entradas.add(p.getOutputStream());
                }
                if (pb.redirectInput() == Redirect.PIPE) { //si la input no ha sido redireccionada a fichero, seguirá en PIPE
                    salidas.add(p.getInputStream());
                }
            } catch (IOException e) {
                System.out.println("Error de E/S: creación procesos");
            }
        }
        
        //escribimos en los procesos que lo requieran
        try {
            for(OutputStream os : entradas) {
                Scanner sc = new Scanner(System.in);
                String line = sc.nextLine();
                os.write((line+"\n").getBytes());
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            System.out.println("Error de E/S: escritura en procesos");
        }
        
        //leemos de los procesos que lo requieran
        try {
            for(InputStream is : salidas) {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error de E/S: escritura en procesos");
        }
        
        // fin del programa
    }
    
    private void compruebaArgumentos(String[] args) throws SintaxisException, FileNotFoundException {
        int i=0;
        String comando = null; // en forma de "comando:arg1:arg2:..."
        ProcessBuilder pb = null;
        boolean asignadaVE = false; // control de introducción de variables de entorno
        try {
            while (i<args.length) { // bucle tareas -t
                if (args[i].equals("-t")) { // es cadena reservada -t (la primera que esperamos)
                    pb = new ProcessBuilder();
                    ++i; //siguiente argumento
                    if (args[i].equals("-t") || args[i].equals("-d") || args[i].equals("-fe") || args[i].equals("-fs") || args[i].equals("-v") ) 
                        throw new SintaxisException(); //espera una palabra y recibe una cadena reservada: error de sintaxis
                    else {
                        comando = args[i];
                        ++i;
                        while (!args[i].equals("-d") && !args[i].equals("-fe") && !args[i].equals("-fs") && !args[i].equals("-v")) { // iteramos los argumentos hasta que lleguemos a la siguiente cadena reservada
                            comando+=" "+args[i];
                            ++i;
                        } // fin while argumentos
                        //tenemos el comando, se lo asignamos al PB
                        asignadaVE = false; //se resetea al introducir nuevo comando;
                        pb.command(comando.split(" "));
                    }
                } else if (args[i].equals("-d") && comando!=null) { // comprobamos que el siguiente argumento es -d, y que se ha asignado un comando anteriormente (la sintaxis es correcta)
                    ++i; //siguiente argumento
                    File directorio = new File(args[i]);
                    if(!directorio.isDirectory()) throw new FileNotFoundException(directorio.getName()+ "(directorio del comando "+comando+")");
                    pb.directory(directorio);
                } //no es -t ni -d
                else if (args[i].equals("-v") && comando!=null){
                    ++i; //siguiente argumento
                    Map<String,String> env = pb.environment();
                    while (!args[i].equals("-fe") && !args[i].equals("-fs") && !args[i].equals("-t")) { // recorre la pareja (VE,valor) hasta cadena reservada
                        String ve = args[i]; // nombre de la variable de entorno
                        if(env.get(ve)!=null) { // comprobamos que la variable de entorno introducida existe
                            ++i; // siguiente valor (valor variable entorno)
                            // actualizamos el valor de la variable de entorno de nuestro PB
                            env.put(ve, args[i]);
                            asignadaVE = true;
                            ++i; //siguiente valor (siguiente variable de entorno o palabra reservada
                        } else {
                            throw new SintaxisException();
                        } 
                    }
                } else if (args[i].equals("-fe") && comando!=null && asignadaVE) {
                    ++i;
                    File fe = new File(args[i]);
                    if(fe.isFile()) { //comprobamos que existe y es un archivo
                        pb.redirectInput(fe);
                    }
                    else {
                        pb.redirectInput(); //ponerlo por defecto
                        throw new FileNotFoundException("El archivo "+fe.getName()+" no existe");
                    }
                } else if (args[i].equals("-fs") && comando!=null && asignadaVE) {
                    ++i;
                    File fs = new File(args[i]);
                    if(fs.isFile()) { //comprobamos que existe y es un archivo
                        pb.redirectOutput(fs);
                    }
                    else {
                        pb.redirectOutput(); //ponerlo por defecto
                        throw new FileNotFoundException("El archivo "+fs.getName()+" no existe");
                    }
                    
                }
                else
                    throw new SintaxisException();
                
                if(pb!=null) 
                    pbs.add(pb); // si en esta iteración ha leído y almacenado un comando, lo añade al arrayList de PBs

            } 
        } catch (IndexOutOfBoundsException e) { // si hay algún tipo de fallo en el conteo de argumentos, saltará esta excepción
            throw new SintaxisException(); // lo cual indica que había un fallo de sintaxis
        }
    }
}
