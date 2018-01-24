/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema2.ejercicio06;

import java.util.Scanner;

/**
 *
 * @author Perig
 */
public class HiloPregunta extends Thread {
    Scanner sc;
    ConjuntoHilos ch;
    public HiloPregunta(ConjuntoHilos ch) {
        sc = new Scanner(System.in);
        this.ch = ch;
    }

    @Override
    public void run() {
        System.out.println("HiloPregunta"+": lanzado");
        boolean respuestaValida = false, seguirBuscando = true;
        
        while(seguirBuscando) {
            System.out.println("Holi");
            try {
                respuestaValida= false;
                Thread.sleep(10000);
                while(!respuestaValida) {
                    System.out.println("¿Quiere proseguir la búsqueda de contraseña otros 10 segundos? (s/n)");
                    String respuesta = sc.nextLine();
                    switch (respuesta) {
                        case "s":
                            respuestaValida = true;
                            seguirBuscando = true;
                            break;
                        case "n":
                            respuestaValida = true;
                            seguirBuscando = false;
                            ch.hilusInterruptus();
                            break;
                        default: 
                            System.out.println("Error, introduzca una respuesta válida (s/n)");
                            respuestaValida = false;
                            break;
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("HiloPregunta: interrumpido");
                seguirBuscando = false;
//                Thread.currentThread().interrupt();
                return;
            }
        }
    }
    
    
}
