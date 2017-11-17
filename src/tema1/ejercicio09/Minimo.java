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
public class Minimo {
    public static void main(String[] args) {
        byte[] entrada = new byte[100];
        int minimo = 0;
        try{
            System.in.read(entrada);
            String[] numeros = (new String(entrada).trim()).split(":");
            for (String nC : numeros) {
                int n = Integer.parseInt(nC);
                if (n < minimo) minimo = n;
            }
            // salida estándar
            System.out.write((minimo+"").getBytes());
            
            // escribir en fichero
//            new FileWriter(new File("Maximo.txt"), false).write(minimo+"");
        } catch (IOException e) {}
    }
}
