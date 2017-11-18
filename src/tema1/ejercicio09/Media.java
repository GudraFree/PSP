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
public class Media {
    public static void main(String[] args) {
        byte[] entrada = new byte[100];
        int suma = 0;
        String numeros;
        int contador=0;
        float media;
        try{
            System.in.read(entrada);
            numeros = (new String(entrada).trim());
            String n = "";
            for (int i=0; i<numeros.length(); i++) {
                String c = ""+numeros.charAt(i);
                if (c.equals(":")) {
                    int nInt = Integer.parseInt(n);
                    suma+=nInt;
                    ++contador;
                }
                else n += numeros.charAt(i);
            }
            media = suma / (float)(contador);
            // salida estándar
            System.out.write((media+"").getBytes());
            
            // escribir en fichero
//            new FileWriter(new File("Maximo.txt"), false).write(media+"");
        } catch (IOException e) {}
    }
}
