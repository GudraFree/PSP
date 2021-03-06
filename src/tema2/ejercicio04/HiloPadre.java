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
public class HiloPadre {
    public static void main(String[] args) {
        HiloPadre hp = new HiloPadre();
    }

    public HiloPadre() {
        Thread cuenta100 = new Cuenta100Task();
//        Thread factoresPrimos = new FactoresPrimosThread(3452, 5);
        cuenta100.start();
//        factoresPrimos.start();
        try {
            Thread.sleep((int)1500);
            isAliveOrInterrupted(cuenta100);
            cuenta100.interrupt();
            isAliveOrInterrupted(cuenta100);

        } catch (InterruptedException e) {
            System.out.println("Error de interrupción");
        }
    }
    

    private static void isAliveOrInterrupted(Thread aThreadObject) {
        if (aThreadObject.isAlive()) {
            System.out.println("main's thread: task's thread is alive");
        } else {
            System.out.println("main's thread: task's thread is dead");
        }

        if (aThreadObject.isInterrupted()) {
            System.out.println("main's thread: task's thread has been interrupted");
        } else {
            System.out.println("main's thread: task's thread has NOT been interrupted");
        }
    }
    
    
}
