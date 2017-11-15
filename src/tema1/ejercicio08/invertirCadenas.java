/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema1.ejercicio08;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Perig
 */
public class invertirCadenas {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String cadena="";
        ArrayList<String> cadenas = new ArrayList();
        ArrayList<Process> procesos = new ArrayList();
        ArrayList<BufferedWriter> outputs = new ArrayList();
        ArrayList<BufferedReader> inputs = new ArrayList();
        do {
            try {
                System.out.println("Escriba una cadena a invertir (Escriba 'FIN' para dejar de introducir cadenas)");
                cadena=sc.nextLine();
                if(cadena.equals("FIN")) break;
                Process p = Runtime.getRuntime().exec("java tema1.ejercicio08.invertirCadena");
                procesos.add(p);
                OutputStream os = p.getOutputStream();
                os.write(cadena.getBytes());
                os.flush();
                os.close();
            } catch (IOException e) {}
        } while(!cadena.equals("FIN"));
        
        byte[] salida = new byte[100];
        
        for (Process p : procesos) {
            InputStream is = p.getInputStream();
            try {
                is.read(salida);
            } catch (IOException e) {
                System.out.println("Error de lectura");
            }
            cadenas.add(new String(salida));
        }
        
        System.out.println("Cadenas revertidas:");
        for (String c : cadenas) System.out.println(c);
    }
}
