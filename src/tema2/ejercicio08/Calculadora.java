/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema2.ejercicio08;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Perig
 */
public class Calculadora {
    public static void main(String[] args) {
        ArrayList<Integer> numeros = new ArrayList();
        Scanner sc = new Scanner(System.in);
        boolean introducir = false;
        do {
            System.out.println("Introduce número");
            String l = sc.nextLine();
            introducir = !l.equals("");
            if(introducir) {
                try {
                    int n = Integer.parseInt(l);
                    numeros.add(n);
                } catch (NumberFormatException e) {
                    System.out.println("Error, número introducido no válido");
                }
            }
        } while (introducir);
                
    }
}
