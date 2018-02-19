/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema3.ejercicio04;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Perig
 */
public class AhorcadoServer {
    public PartidaThread partidaPendiente = null;
    
    public static void main(String[] args) throws IOException {
        AhorcadoServer s = new AhorcadoServer();
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
            new AhorcadoServerThread(serverSocket.accept(), s).start();
        }
        
        serverSocket.close();
    }
    
    public synchronized void searchGame(Socket player) {
        if(partidaPendiente==null) {
            partidaPendiente = new PartidaThread(this);
            System.out.println("Creado PartidaThread");
            partidaPendiente.addPlayer(player);
            System.out.println("Añadido jugador");
            partidaPendiente.start();
            System.out.println("Lanzado hilo partida");
        } else {
            partidaPendiente.addPlayer(player);
            System.out.println("Añadido jugador");
        }
    }
    
    public synchronized void chechIfFull() {
        if(partidaPendiente.isFull()) partidaPendiente = null;
    }
}
