/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema1.ejercicio4;

import java.io.InputStream;
import java.util.Scanner;


/**
 *
 * @author Perig
 */
public class LanzaJava {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String entrada, ruta ="", programa="", comando="";
        System.out.println("Introduzca el nombre o ruta de un programa java");
        entrada = sc.nextLine();
        if(entrada.contains("\\")) {
            ruta = "-cp "+entrada.substring(0, entrada.lastIndexOf("\\")-1);
            programa = " "+entrada.substring(entrada.lastIndexOf("\\"));
        } else {
            programa=entrada;
        }
        comando = "java "+ruta+programa;
        try {
            Process proceso = Runtime.getRuntime().exec(comando);
            InputStream salida = proceso.getInputStream();
            int c;
            while((c=salida.read())!=-1) {
                System.out.print((char)c);
            }
            System.out.println("");
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
        }
    }
}
