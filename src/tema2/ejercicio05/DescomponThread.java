/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema2.ejercicio05;

import java.util.ArrayList;

/**
 *
 * @author Perig
 */
public class DescomponThread extends Thread {
    long n;
    ArrayList<Long> factoresPrimos;
    public DescomponThread(long n) {
        this.n = n;
        factoresPrimos = new ArrayList();
    }

    @Override
    public void run() {
        for (long i=1; i<=n/2; i++) {
//            System.out.println("Factor "+i);
            if(n%i==0 && esPrimo(i)) {
//                System.out.println("Es primo");
                factoresPrimos.add(i);
            }
            if(Thread.interrupted()) {
                System.out.println("Hilo interrumpido");
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        for(long l : factoresPrimos) {
            System.out.print(l+"  ");
        }
        System.out.println("");
    }
    
    private boolean esPrimo(long n) {
        long divisores=0;
        for (long i=2; i<=n/2; i++) {
            if (n%i==0) ++divisores;
        }
        return (divisores==0);
    }
     
}
