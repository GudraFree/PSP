/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema2.ejercicio01;

/**
 *
 * @author Perig
 */
public class ChequeaPrimoTask implements Runnable {

    int n;
    
    public ChequeaPrimoTask(int numero) {
        n = numero;
    }
    
    

    @Override
    public void run() {
        int divisores=0;
        for (int i=2; i<=n/2; i++) {
            if (n%i==0) ++divisores;
        }
        if(divisores==0) System.out.println(n+" es primo");
        else System.out.println(n+" no es primo");
    }
    
    
}
