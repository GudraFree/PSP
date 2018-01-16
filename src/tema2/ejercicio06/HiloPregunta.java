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
    // TODO: implementar interrupción de todos los hilos
    Scanner sc;
    public HiloPregunta() {
        sc = new Scanner(System.in);
    }

    @Override
    public void run() {
        boolean respuestaValida = false, seguirBuscando = true;
        
        while(seguirBuscando) {
            try {
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
                            break;
                        default: 
                            respuestaValida = false;
                            break;
                    }
                }
            } catch (InterruptedException e) {

            }
        }
    }
    
    
}
