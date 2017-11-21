/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema1.ejercicio17;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        int definiciones=0, apariciones=0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(diccio));
            String line;
            while ((line=br.readLine()) != null) {
                if(line.equals(palabra+":")) ++definiciones;
                int i;
                while ((i=line.indexOf(palabra)) > -1) {
                    ++apariciones;
                    line = line.substring(i+1); //sumamos 1 para que corte la palabra: acronimo > cronimo (indexOf(acronimo)=-1)
                }
            }
            res[0] = definiciones;
            res[1] = apariciones;
        } catch (FileNotFoundException e) {
            System.err.println("Error, archivo no encontrado");
        } catch (IOException e) {
            System.err.println("Error de E/S en proceso: búsqueda palabra");
        }
        
        return res;
    }
}
