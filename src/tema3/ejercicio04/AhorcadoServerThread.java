/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema3.ejercicio04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Perig
 */
public class AhorcadoServerThread extends Thread {
    public Socket socket;
    PrintWriter out;
    BufferedReader in;
    AhorcadoServer s;
    PartidaThread pt;
    ServerProtocol ap;

    public AhorcadoServerThread(Socket socket, AhorcadoServer s) {
        this.socket = socket;
        this.s = s;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            ap = new ServerProtocol(Utils.WAITING_LOGIN);

            String output = ap.processInput("");
            String input;
            out.println(output);

            while((input=in.readLine())!= null) {
                output = ap.processInput(input);
                if(output.equals(Utils.START_ONLINE_GAME)) { //hemos introducido la opción de jugar online
                    System.out.println("Empezado juego online");
                    s.searchGame(this); //buscamos partida y nos pasamos como jugador
                    pt = s.partidaPendiente; // almacenamos el hilo partida
                    try {
                        pt.join(); //esperamos a que termine la partida. Nuestro hilo espera aquí pacientemente en el estado WAITING_GAME_MENU, preparado para mostrar el menú cuando se vuelva
                    } catch (InterruptedException e) {
                    }
                    
                } else {
                    out.println(output);
                }
                if(output.equals(Utils.END_CLIENT_LIFE)) {
                    System.out.println("Terminando conexión con cliente de manera natural");
                    break;
                }
            }

            in.close();
            out.close();

            socket.close();
        } catch (IOException e) {
            System.err.println("Error de E/S en "+Thread.currentThread());
        }
    }
}
