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
public class FactoresPrimosThread extends Thread {
    int n;
    int nFactores;

    public FactoresPrimosThread(int n, int nFactores) {
        this.n = n;
        this.nFactores = nFactores;
    }

    @Override
    public void run() {
        int factoresPrimos = 0;
        for (int i=1; i<=n/2; i++) {
            System.out.println("Factor "+i);
            if(n%i==0 && esPrimo(i)) {
                System.out.println("Es primo");
                ++factoresPrimos;
            }
            if(Thread.interrupted())
                if(factoresPrimos>=nFactores) return;
        }
    }
    
    private boolean esPrimo(int n) {
        int divisores=0;
        for (int i=2; i<=n/2; i++) {
            if (n%i==0) ++divisores;
        }
        return (divisores==0);
    }
    
    
    
}
