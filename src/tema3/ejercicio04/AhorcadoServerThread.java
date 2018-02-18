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
    Socket socket;
    PrintWriter out;
    BufferedReader in;
    ColeccionPartidas cp;
    PartidaThread pt;

    public AhorcadoServerThread(Socket socket, ColeccionPartidas cp) {
        this.socket = socket;
        this.cp = cp;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            ServerProtocol ap = new ServerProtocol();

            String output = ap.processInput("");
    //        System.out.println("Server: "+output);
            String input;
            out.println(output);

            while((input=in.readLine())!= null) {
                output = ap.processInput(input);
    //            System.out.println(output);
                out.println(output);
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
