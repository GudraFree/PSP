/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema3.ejercicio03;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author Perig
 */
public class AhorcadoServer {
    public static void main(String[] args) throws IOException {
        System.out.println("Server: empieza ejecución");
        int port = 4444;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch(IOException e) {
            System.err.println("Error al conectarse al puerto");
            System.exit(1);
        }
        System.out.println("Server: conectado a puerto "+port);
        
        boolean listening = true;
        while(listening) {
            new AhorcadoServerThread(serverSocket.accept()).start();
        }
        
        serverSocket.close();
    }
    
    
    
}
