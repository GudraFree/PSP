/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema1.ejercicio09;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author Perig
 */
public class Maximo {
    public static void main(String[] args) throws IOException {
        metodoPerig();
//        metodoRuben();
    }
    
    static void metodoPerig() throws IOException {
        byte[] entrada = new byte[100];
        int maximo = 0;
        try{
            System.in.read(entrada);
            String numeros = (new String(entrada));
            String n = "";
            for (int i=0; i<numeros.length(); i++) {
                char c = numeros.charAt(i);
                if (c==':') {
                    int nInt = Integer.parseInt(n);
                    if (nInt > maximo) maximo = nInt;
                    n="";
                }
                else n += c;
                
            }
            String maxCadena = maximo+"";
            // salida estándar
            System.out.write(maxCadena.getBytes());
            
            // escribir en fichero
//            new FileWriter(new File("Maximo.txt"), false).write(maximo+"");
        } catch (Exception e) {
            System.out.write(e.getMessage().getBytes());
        }
    }
    
    static void metodoRuben() {
        byte[] entrada = new byte[100];
        try {
            //entrada es lo que recibe
            System.in.read(entrada);
            String entradaS = new String(entrada);
            int numeros[] = new int[entradaS.length() - 1];
            pasoArray(entradaS, numeros);
            int maximo = sacaMaximo(numeros);
            String maximoS = "El maximo es: " + maximo;
            //se manda de vuelta
            System.out.write(maximoS.getBytes());

        } catch (IOException ioe) {
            ioe.getMessage();
        }
        
        
    }
    public static void pasoArray(String numero, int numeros[]) {

        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = Character.getNumericValue(numero.charAt(i));
        }
    }

    public static int sacaMaximo(int numeros[]) {
        Arrays.sort(numeros);

        return numeros[numeros.length - 1];
    }
}
