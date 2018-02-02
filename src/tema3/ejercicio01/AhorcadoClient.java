/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema3.ejercicio01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author Perig
 */
public class AhorcadoClient {
    private static final String[] AHORCADO = { 
"------|\n" +
"|     \n" +
"|    \n" +
"|    \n" +
"|\n" +
"====", 
"------|\n" +
"|     O\n" +
"|    \n" +
"|    \n" +
"|\n" +
"====",
"------|\n" +
"|     O\n" +
"|     |\n" +
"|    \n" +
"|\n" +
"====",
"------|\n" +
"|     O\n" +
"|    /|\n" +
"|    \n" +
"|\n" +
"====",
"------|\n" +
"|     O\n" +
"|    /|\\\n" +
"|    \n" +
"|\n" +
"====",
"------|\n" +
"|     O\n" +
"|    /|\\\n" +
"|    / \n" +
"|\n" +
"====",
"------|\n" +
"|     O\n" +
"|    /|\\\n" +
"|    / \\\n" +
"|\n" +
"====", };
    
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String host = "localhost";
        int port = 4444;
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Error, host no encontrado");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error de E/S");
            System.exit(1);
        }
        
        System.out.println("¡Bienvenido al juego del Ahorcado!");
        String l="";
        while((l=in.readLine())!=null) {
//            System.out.println("Client: iteración");
            String[] lectura = l.split("-");
            String mensaje = lectura[0];
            String palabra = lectura[1];
            int errores = Integer.parseInt(lectura[2]);
            System.out.println(AHORCADO[errores]);
            System.out.println("\n    "+palabra+"\n");
            System.out.println(mensaje);
            if(!mensaje.equals("Hasta otra")) {
                String letra = sc.nextLine();
                out.println(letra);
//                System.out.println("Cliente: enviada "+letra);
            } else break;
        }
        
        in.close();
        out.close();
        
        socket.close();
    }
    
}
