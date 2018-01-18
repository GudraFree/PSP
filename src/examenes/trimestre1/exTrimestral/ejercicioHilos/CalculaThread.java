/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examenes.trimestre1.exTrimestral.ejercicioHilos;

import java.util.ArrayList;

/**
 *
 * @author Perig
 */
public class CalculaThread extends Thread {
    
    ArrayList<String> operacion;
    String aCalcular;
    int lugarEnOperacion;

    public CalculaThread(ArrayList<String> operacion, String aCalcular, int lugarEnOperacion) {
        this.operacion = operacion;
        this.aCalcular = aCalcular;
        this.lugarEnOperacion = lugarEnOperacion;
    }
    
    
    
    

    @Override
    public void run() {
        String n1="", n2="", op="", resultado="";
        boolean n1Listo = false;
        for (int i=1; i<aCalcular.length()-1;i++) {
            String c = aCalcular.substring(i,i+1);
            if(c.equals("+") || c.equals("*")) {
                n1Listo = true;
                op = c;
            }
            else if(!n1Listo) n1+=c;
            else n2+=c;
        }
        
        if (op.equals("+")) resultado = ""+ (Integer.parseInt(n1)+Integer.parseInt(n2));
        if (op.equals("*")) resultado = ""+ (Integer.parseInt(n1)*Integer.parseInt(n2));
        
        operacion.set(lugarEnOperacion, resultado);
    }
    
}
