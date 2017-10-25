/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema1.ejercicio8;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
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
        ArrayList<BufferedInputStream> inputs = new ArrayList();
        do {
            try {
                System.out.println("Escriba una cadena a invertir (Escriba 'FIN' para dejar de introducir cadenas)");
                cadena=sc.nextLine();
                if(cadena.equals("FIN")) break;
                Process p = Runtime.getRuntime().exec("java tema1.ejercicio8.invertirCadena");
                procesos.add(p);
                BufferedWriter bos = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
                outputs.add(bos);
                bos.write(cadena);
                bos.flush();
            } catch (IOException e) {}
        } while(!cadena.equals("FIN"));
    }
}
