/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema1.ejercicio08;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 *
 * @author Perig
 */
public class invertirCadena {
    public static void main(String[] args) {
        byte[] entrada = new byte[100];
        String cadena;
        try {
            System.in.read(entrada);
            cadena = (new StringBuilder(new String(entrada).trim()).reverse().toString());
            System.out.write(cadena.getBytes());
            
        } catch (IOException e) {}
    }
}
