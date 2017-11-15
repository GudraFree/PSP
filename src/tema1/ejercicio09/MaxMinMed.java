/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema1.ejercicio09;

/**
 *
 * @author Perig
 */
public class MaxMinMed {
    public static void main(String[] args) {
        String entrada = argsToString(args);
        
    }
    
    static String argsToString(String[] args) {
        String cad="";
        for (String c : args) {
            cad+=":"+c;
        }
        return cad.substring(1);
    }
}
