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
public class OnlineClientThread extends Thread {
    Socket player;
    PartidaThread pt;
    PrintWriter out;
    BufferedReader in;
    int errors;

    public OnlineClientThread(Socket player, PartidaThread pt) {
        this.player = player;
        this.pt = pt;
        errors = 0;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(player.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(player.getInputStream()));

            OnlineGameProtocol p = new OnlineGameProtocol(this);
            p.setPartida(pt.getPartida());

            String output = p.processInput("");
    //        System.out.println("Server: "+output);
            String input;
            out.println(output);

            while((input=in.readLine())!= null) {
                output = p.processInput(input);
                if(Thread.currentThread().isInterrupted()) {
                    out.println(Utils.I_LOST_ONLINE);
                    break;
                }
                out.println(output);
                if(output.equals(Utils.END_ONLINE_GAME)) {
                    // TODO: aquí debería volver al hilo AhorcadoServerThread que atiende al cliente, ponerle su estado, etc
                    System.out.println("Rompiendo el bucle del hilo "+Thread.currentThread());
                    break;
                }
            }
            
            System.out.println("Terminando el hilo "+Thread.currentThread());
            in.close();
            out.close();
        } catch (IOException e) {
            System.err.println("Error de E/S en "+Thread.currentThread());
        }
        
    }
    
    public int getErrors() {
        return errors;
    }
    
    public void setErrors(int errors) {
        this.errors = errors;
    }
}
