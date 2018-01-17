/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema2.ejercicio06;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Perig
 */
public class ConjuntoHilos {
    int nProcesos;
    String password;
    ArrayList<String> passwords;
    Thread[] hilos;
    Thread pregunta;
    boolean passwordFound;

    public ConjuntoHilos(int nProcesos, String password) {
        this.nProcesos = nProcesos;
        this.password = password;
        passwords = new ArrayList();
        hilos = new Thread[nProcesos];
        passwordFound = false;
        
        try {
            File passwordsTXT = new File("passwords.txt");
            BufferedReader br = new BufferedReader(new FileReader(passwordsTXT));
            String l;
//            System.out.println("ConjuntoHilos: contraseñas leídas son:");
            while ((l=br.readLine()) != null) {
//                System.out.print(l+" ");
                passwords.add(l);
            }
            System.out.println("");
        } catch (FileNotFoundException e) {
            System.out.println("Error, fichero no encontrado");
        } catch (IOException e) {
            System.out.println("Error de E/S");
        }
    }
    
    public void lanzaHilos() {
        int nPasswords = passwords.size();
        int palabrasPorHilo = (int)Math.ceil(nPasswords/(0.0f + nProcesos));
        for (int i=0; i<nProcesos; i++) {
            hilos[i] = new HiloPasswords(i*palabrasPorHilo,Math.min((i+1)*palabrasPorHilo, nPasswords), passwords, password, this);
            hilos[i].start();
        }
    }
    
    public void lanzaHiloPregunta() {
        pregunta = new HiloPregunta(this);
        pregunta.setPriority(10);
        pregunta.start();
    }
    
    public void hilusInterruptus() {
        try {
            for(Thread t : hilos) {
                if(t!=Thread.currentThread()) {
                    t.interrupt();
                    t.join();
                }
            }
            if(pregunta!=Thread.currentThread()) {
                pregunta.interrupt();
                pregunta.join();
            }
        } catch (InterruptedException e) {
            System.out.println("Error de interrupción");
            e.printStackTrace();
        }
        
        if(passwordFound) System.out.println("Se ha encontrado la contraseña");
        else System.out.println("No se ha encontrado");
    }
    
}
