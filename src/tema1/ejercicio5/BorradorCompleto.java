/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema1.ejercicio5;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Perig
 */
public class BorradorCompleto {
    public static void main(String[] args) {
        String archivoABorrar = args[0];
        ArrayList<String> comandosAEjecutar = new ArrayList();
        for(int i=1; i<args.length; i++) {
            comandosAEjecutar.add("java tema1.ejercicio5.Borrador "+archivoABorrar+" "+args[i]);
        }
        for(String comando: comandosAEjecutar) {
            try { Runtime.getRuntime().exec(comando); } catch (IOException e) {}
        }
    }
    
}
