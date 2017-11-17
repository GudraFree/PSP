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
public class MaxMinMed {
    public static void main(String[] args) {
        String entrada = argsToString(args);
        Runtime rt = Runtime.getRuntime();
        byte[] salida = new byte[100];
        String max, min, med;
        try {
            Process pMax = rt.exec("java tema1.ejercicio09.Maximo");
            System.out.println("Creado proceso max");
            pMax.getOutputStream().write(entrada.getBytes());
            System.out.println("Enviados bytes a max");
            pMax.getInputStream().read(salida);
            System.out.println("Leídos bytes de max");
            max = (new String(salida).trim());
            Process pMin = rt.exec("java tema1.ejercicio09.Minimo");
            System.out.println("Creado proceso min");
            pMin.getOutputStream().write(entrada.getBytes());
            System.out.println("Enviados bytes a min");
            pMin.getInputStream().read(salida);
            System.out.println("Leidos bytes de min");
            min = (new String(salida).trim());
            Process pMed = rt.exec("java tema1.ejercicio09.Media");
            pMed.getOutputStream().write(entrada.getBytes());
            pMed.getInputStream().read(salida);
            med = (new String(salida).trim());
            
            
        // salida estandar
            
            System.out.println("Máximo: "+max+"\nMínimo: "+min+"\nMedia: "+med);
        } catch (IOException e) {
            System.out.println("Error de E/S");
        }
        
    }
    
    static String argsToString(String[] args) {
        String cad="";
        for (String c : args) {
            System.out.println(c);
            cad+=":"+c;
        }
        cad =cad.substring(1);
        System.out.println(cad);
        return cad;
    }
}
