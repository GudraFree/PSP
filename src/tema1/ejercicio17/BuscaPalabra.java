/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema1.ejercicio17;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Perig
 */
public class BuscaPalabra {
    public static void main(String[] args) {
        BuscaPalabra bp = new BuscaPalabra();
    }

    public BuscaPalabra() {
        String palabra = "";
        File diccio = null;
        boolean palabraLeida = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        
        // leemos la información pasada al proceso
        try {
//            System.out.println("Hola desde el proceos. Leemos");
            while((line=br.readLine()) != null) {
                if(palabraLeida) diccio = new File(line);
                else {
                    palabra = line;
                    palabraLeida = true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error de E/S en proceso: lectura");
        }
        
        //buscamos la palabra en los diccionarios
        int[] resultados = busqueda(palabra, diccio);
        String resultado = palabra+":"+resultados[0]+":"+resultados[1];
        
        try {
            System.out.write((resultado+"\n").getBytes());
        } catch (IOException e) {
            System.err.println("Error de E/S en proceso: escritura");
        }
    }
    
    int[] busqueda(String palabra, File diccio) {
        int[] res = new int[2];
        res[0] = 3;
        res[1] = 12;
        
        return res;
    }
}
