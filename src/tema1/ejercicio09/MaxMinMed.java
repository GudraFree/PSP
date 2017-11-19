/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema1.ejercicio09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 *
 * @author Perig
 */
public class MaxMinMed {
    public static void main(String[] args) {
        String entrada = argsToString(args);
        Runtime rt = Runtime.getRuntime();
        byte[] salida = new byte[100];
        String cadena="";
        String[] mensajes = {"Máximo: ", "Mínimo: ","Media: "};
        ArrayList<Process> procesos = new ArrayList();
        try {
            Process pMax = rt.exec("java tema1.ejercicio09.Maximo");
//            System.out.println("Creado proceso max");
            OutputStream osMax = pMax.getOutputStream();
            osMax.write(entrada.getBytes());
            osMax.flush();
            osMax.close();
//            System.out.println("Enviados bytes a max");
            procesos.add(pMax);
            
            Process pMin = rt.exec("java tema1.ejercicio09.Minimo");
//            System.out.println("Creado proceso min");
            OutputStream osMin = pMin.getOutputStream();
            osMin.write(entrada.getBytes());
            osMin.flush();
            osMin.close();
//            System.out.println("Enviados bytes a min");
            procesos.add(pMin);
            
            Process pMed = rt.exec("java tema1.ejercicio09.Media");
            OutputStream osMed = pMed.getOutputStream();
            osMed.write(entrada.getBytes());
            osMed.flush();
            osMed.close();
            procesos.add(pMed);
            String resu;
            for(int i=0; i<procesos.size(); i++) {
//                System.out.println("Leyendo bytes de proceso");
                BufferedReader brCleanUp = new BufferedReader(new InputStreamReader(procesos.get(i).getInputStream()));

                while ((resu = brCleanUp.readLine()) != null) {

                    System.out.println(mensajes[i]+resu);

                }
            }
        } catch (IOException e) {
            System.out.println("Error de E/S");
        }
        
    }
    
    static String argsToString(String[] args) {
        String cad="";
        for (String c : args) {
            cad+=c+":";
        }
        return cad;
    }
}
