/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema1.ejercicio8;

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
        String cadena;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            cadena = (new StringBuilder(br.readLine()).reverse().toString());
            new BufferedWriter(new OutputStreamWriter(System.out)).write(cadena);
        } catch (IOException e) {}
    }
}
