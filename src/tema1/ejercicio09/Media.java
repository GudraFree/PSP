/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema1.ejercicio09;

import java.io.IOException;

/**
 *
 * @author Perig
 */
public class Media {
    public static void main(String[] args) {
        byte[] entrada = new byte[100];
        int suma = 0;
        String[] numeros;
        try{
            System.in.read(entrada);
            numeros = (new String(entrada).trim()).split(":");
            for (String nC : numeros) {
                int n = Integer.parseInt(nC);
                suma+=n;
            }
            float media = suma / (float)(numeros.length);
            System.out.write((suma+"").getBytes());
        } catch (IOException e) {}
    }
}
