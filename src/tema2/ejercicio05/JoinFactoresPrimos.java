/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema2.ejercicio05;

import java.util.Scanner;

/**
 *
 * @author Perig
 */
public class JoinFactoresPrimos {
    public static void main(String[] args) {
        //pedimos el número
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca un número a descomponer en factores primos");
        long n = sc.nextLong();
        sc.nextLine(); //limpia scanner
        
        //empezamos el hilo
        Thread t = new DescomponThread(n);
        t.start();
        
        //esperaremos x segundos, por defecto 10, y luego preguntaremos al usuario si desea seguir
        long x = 10000;
        boolean seguir = false;
        String respuesta = "";
        try {
            do {
                t.join(x);
                if(t.isAlive()) {
                    System.out.println("¿Quiere seguir esperando? (s/n)");
                    respuesta = sc.nextLine();
                    if (respuesta.equals("s")) {
                        seguir = true;
                        System.out.println("¿Cuánto tiempo quiere esperar? En segundos");
                        x = 1000 * sc.nextInt();
                        sc.nextLine(); //limpia scanner
                    } else if (respuesta.equals("n")) {
                        System.out.println("Se interrumpirá el hilo");
                        t.interrupt();
                        t.join();
                        seguir = false;
                    } else {
                        System.out.println("Opción introducida no válida. Esperando otros 10s");
                        seguir = true;
                    }
                } else {
                    seguir=false;
                    System.out.println("El hilo ha terminado naturalmente");
                }
            } while(seguir);
        } catch (InterruptedException e) {
            System.out.println("Error de interrupción");
        }
    }
}
