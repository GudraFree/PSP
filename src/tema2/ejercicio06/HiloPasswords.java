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

    public HiloPasswords(int start, int end, ArrayList<String> passwords, String password) {
        this.start = start;
        this.end = end;
        this.passwords = passwords;
        this.password = password;
    }

    @Override
    public void run() {
        for(int i=start; i<end; i++) {
            for(int j=0; j<passwords.size(); j++) {
                if(password.equals(passwords.get(i)+" "+passwords.get(j))) {
                    // TODO: He encontrado la contraseña
                }
            }
        }
    }
    
    
}
