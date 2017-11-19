/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema1;

import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author Perig
 */
public class Maximo {
    public static void main(String[] args) throws IOException {
        System.out.println("Mi método");
        metodoPerig();
        System.out.println("\n\nEl de Rubén");
        metodoRuben();
    }
    
    static void metodoPerig() throws IOException {
        byte[] entrada = new byte[100];
        int maximo = 0;
        try{
            System.in.read(entrada);
            String numeros = (new String(entrada).trim());
            System.out.println("Cadena "+numeros);
            String n = "";
            int length = numeros.length();
            System.out.println("Longitud "+length);
            for (int i=0; i<length; i++) {
                char c = numeros.charAt(i);
                System.out.println("Carácter "+c);
                if (c=='-') {
                    System.out.println("Encontré :");
                    int nInt = Integer.parseInt(n);
                    System.out.println("El número es "+nInt);
                    if (nInt > maximo) {
                        maximo = nInt;
                        System.out.println("Y ahora es el máximo");
                    }
                    n="";
                }
                else {
                    n += c;
                    System.out.println("Ahora n es "+n);
                }
                
            }
            String maxCadena = maximo+"";
            // salida estándar
//            System.out.println(new String(maxCadena.getBytes()));
            System.out.println(maxCadena);
            
            // escribir en fichero
//            new FileWriter(new File("Maximo.txt"), false).write(maximo+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static void metodoRuben() {
        byte[] entrada = new byte[100];
        try {
            //entrada es lo que recibe
            System.in.read(entrada);
            String entradaS = new String(entrada);
            System.out.println(entradaS);
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
