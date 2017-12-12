/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema2.ejercicio01;

import java.util.Scanner;

/**
 *
 * @author Perig
 */
public class ChequeaPrimos {
    public static void main(String[] args) {
        ChequeaPrimos cp = new ChequeaPrimos();
    }

    public ChequeaPrimos() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce una serie de números separados por espacios para comprobar su primaridad");
        String cadenaN = sc.nextLine();
        String[] numeros = cadenaN.split(" ");
        for (String numero : numeros) {
            int n = Integer.parseInt(numero);
            Thread hilo = new Thread(new ChequeaPrimoTask(n));
            hilo.start();
        }
    }
    
    
}
