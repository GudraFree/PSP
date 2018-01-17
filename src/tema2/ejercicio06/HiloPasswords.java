/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema2.ejercicio06;

import java.util.ArrayList;

/**
 *
 * @author Perig
 */
public class HiloPasswords extends Thread {
    int start, end;
    ArrayList<String> passwords;
    String password;
    ConjuntoHilos ch;

    public HiloPasswords(int start, int end, ArrayList<String> passwords, String password, ConjuntoHilos ch) {
        this.start = start;
        this.end = end;
        this.passwords = passwords;
        this.password = password;
        this.ch = ch;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread()+": lanzado ("+start+", "+end+")");
        for(int i=start; i<end; i++) {
            for(int j=0; j<passwords.size(); j++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                if(password.equals(passwords.get(i)+" "+passwords.get(j))) {
                    ch.passwordFound = true;
//                    System.out.println(Thread.currentThread()+": encontrada la contraseña: "+passwords.get(i)+" "+passwords.get(j));
                    ch.hilusInterruptus();
                    return;
                }
                if(j%10==0) {
//                    System.out.println(Thread.currentThread()+": comprobación rutinaria de interrupción");
                    if(Thread.interrupted()) {
                        System.out.println(Thread.currentThread()+": interrumpido");
//                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
        }
        System.out.println(Thread.currentThread()+": terminado");
    }
    
    
}
