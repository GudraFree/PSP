/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema1.ejercicio08;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
                Process p = Runtime.getRuntime().exec("java tema1.ejercicio8.invertirCadena");
                procesos.add(p);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
                outputs.add(bw);
                bw.write(cadena);
                bw.flush();
                bw.close();
            } catch (IOException e) {}
        } while(!cadena.equals("FIN"));
        
        for (Process p : procesos) {
            System.out.println("Holi proceso");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            inputs.add(br);
            try {
                cadena = br.readLine();
                br.close();
            } catch (IOException e) {
                System.out.println("Error de lectura");
            }
            cadenas.add(cadena);
        }
        
        System.out.println("Cadenas revertidas:");
        for (String c : cadenas) System.out.println("\t"+c);
    }
}
