/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema2.ejercicio02;

import java.util.Scanner;

/**
 *
 * @author Perig
 */
public class ChequeaPares {
    public static void main(String[] args) {
        ChequeaPares cp = new ChequeaPares();
    }

    public ChequeaPares() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce una serie de números separados por espacios para comprobar su paridad");
        String cadenaN = sc.nextLine();
        String[] numeros = cadenaN.split(" ");
        for (String numero : numeros) {
            int n = Integer.parseInt(numero);
            Thread hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    if(n%2==0) System.out.println(n+" es par");
                    else System.out.println(n+" es impar");
                }
                
            });
            hilo.start();
        }
    }
    
    
}
