/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema1;

import tema1.ejercicio16.*;
import tema1.ejercicio08.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 *
 * @author Perig
 */
public class Keijo {
    public static void main(String[] args) {
        byte[] entrada = new byte[100];
        String cadena;
//        System.out.println("Hola desde el proceso");
        try {
            System.in.read(entrada);
//            System.out.println("Leídos bytes de entrada");
            cadena = (new String(entrada).trim())+"!";
//            System.out.println(cadena);
            System.out.write(cadena.getBytes());
            
        } catch (IOException e) {}
    }
}
