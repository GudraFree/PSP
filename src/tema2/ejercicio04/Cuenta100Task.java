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
public class Cuenta100Task extends Thread {
    
    @Override
    public void run() {
        System.out.println("task: Empiezo task");
        for (int i=1; i<=10; i++) {
            System.out.println("task: "+i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                if(i%2==0) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("task: INTERRUPTED EXCEPTION CUENTA 100 con nº "+i);
                if(i%2==0) {
                    System.out.println("Es par así que me vuelvo");
                    return;
                } else {
                    System.out.println("Es impar, sigo");
                }
            }
        }
        
        System.out.println("Hola desde fuera del bucle");
    }
}
