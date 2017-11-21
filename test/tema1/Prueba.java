/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema1;

/**
 *
 * @author Perig
 */
public class Prueba {
    public static void main(String[] args) {
        String cadena = "Hola desde un programa de prueba";
        int i = cadena.indexOf("Hola");
        System.out.println(i);
        cadena = cadena.substring(i+1);
        System.out.println(cadena.indexOf("Hola"));
    }
}
