/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema1.ejercicio09;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Perig
 */
public class Maximo {
    public static void main(String[] args) {
        byte[] entrada = new byte[100];
        int maximo = 0;
        try{
            System.in.read(entrada);
            String numeros = (new String(entrada).trim());
            String n = "";
            for (int i=0; i<numeros.length(); i++) {
                String c = ""+numeros.charAt(i);
                if (c.equals(":")) {
                    if (Integer.parseInt(n) > maximo) maximo = Integer.parseInt(n);
                    n="";
                }
                else n += numeros.charAt(i);
                
            }
            // salida estándar
            System.out.write((maximo+"").getBytes());
            
            // escribir en fichero
//            new FileWriter(new File("Maximo.txt"), false).write(maximo+"");
        } catch (IOException e) {}
    }
}
