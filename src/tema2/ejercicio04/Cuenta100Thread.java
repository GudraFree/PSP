/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema2.ejercicio04;

/**
 *
 * @author Perig
 */
public class Cuenta100Thread extends Thread {
    
    @Override
    public void run() {
        for (int i=1; i<=100; i++) {
            System.out.println(i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("INTERRUPTED EXCEPTION CUENTA 100 con nº "+i);
                if(i%2==0) {
                    System.out.println("Es par así que me vuelvo");
                    return;
                } else 
                    System.out.println("Es impar, sigo");
            }
        }
    }
}
