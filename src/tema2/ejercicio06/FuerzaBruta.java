/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema2.ejercicio06;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Perig
 */
public class FuerzaBruta {
    Scanner sc;
    ConjuntoHilos cp;
    
    public static void main(String[] args) {
        FuerzaBruta fb = new FuerzaBruta();
        // introducimos datos
        System.out.println("Introduzca la contraseña a adivinar: ");
        String password = fb.sc.nextLine();
        System.out.println("Introduce el número de hilos a usar:");
        int nHilos = fb.sc.nextInt();
        fb.sc.nextLine(); // limpia scanner
        
        // lanzamos los hilos que buscan la contraseña
        fb.cp = new ConjuntoHilos(nHilos, password);
        fb.cp.lanzaHilos();
        
        //Creamos y lanzamos el hilo que preguntará si queremos continuar la búsqueda
        Thread pregunta = new HiloPregunta();
        pregunta.setPriority(10);
        pregunta.start();
        
        // TODO: comprobar:
        // - si se ha encontrado la contraseña
        // - si han terminado de buscar
        // - si la pregunta manda interrumpir todo el programa
    }

    public FuerzaBruta() {
        sc = new Scanner(System.in);
    }
    
}
