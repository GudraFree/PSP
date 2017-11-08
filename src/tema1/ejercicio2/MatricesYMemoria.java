/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema1.ejercicio2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Perig
 */
public class MatricesYMemoria {
    static float freeMemory, totalMemory, maxMemory;
    public static void main(String[] args) {
        boolean continuar = true;
        ArrayList<int[]> matrices = new ArrayList();
        while(continuar) {
            Runtime run = Runtime.getRuntime();
            freeMemory = run.freeMemory(); totalMemory = run.totalMemory(); maxMemory = run.maxMemory();
            if((freeMemory/totalMemory)<0.1f) continuar = solicitud();
            matrices.add(new int[100]);
        }
    }
    static boolean solicitud() {
        return false;
    }
}
