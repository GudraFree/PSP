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

    public ConjuntoHilos(int nProcesos, String password) {
        this.nProcesos = nProcesos;
        this.password = password;
        passwords = new ArrayList();
        hilos = new Thread[nProcesos];
        
        try {
            File passwordsTXT = new File("passwords.txt");
            BufferedReader br = new BufferedReader(new FileReader(passwordsTXT));
            String l;
            while ((l=br.readLine()) != null) {
                passwords.add(l);
            }
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
            hilos[i] = new HiloPasswords(i*palabrasPorHilo,(i+1)*palabrasPorHilo, passwords, password);
            hilos[i].start();
        }
    }
    
    // TODO: método comprobación vida hilos
    
}
