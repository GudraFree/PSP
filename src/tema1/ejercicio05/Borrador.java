/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema1.ejercicio05;

import java.io.File;

/**
 *
 * @author Perig
 */
public class Borrador {
    public static void main(String[] args) {
        String archivoABorrar = args[0];
        File carpeta = new File(args[1]);
        borrar(archivoABorrar, carpeta);
    }
    static void borrar(String archivoABorrar, File carpeta) {
        File[] contenido = carpeta.listFiles();
        for(File archivo : contenido) {
            if(archivo.isDirectory()) borrar(archivoABorrar, archivo);
            if(archivo.isFile() && archivo.getName().equals(archivoABorrar)) {
                archivo.delete();
            }
        }
    }
}
