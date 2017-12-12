/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema2.ejercicio03;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Perig
 */
public class Sumador {
    public static void main(String[] args) {
        try {
            Sumador s = new Sumador();
        } catch (InterruptedException ex) {
            Logger.getLogger(Sumador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Sumador() throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce una serie de números separados por espacios para sumarlos y mostrarlos");
        String cadenaN = sc.nextLine();
        String[] numeros = cadenaN.split(" ");
        int suma = 0;
        for (String numero : numeros) {
            int n = Integer.parseInt(numero);
            suma+=n;
            System.out.println(suma);
            Thread.sleep(1000);
        }
    }
    
    
}
