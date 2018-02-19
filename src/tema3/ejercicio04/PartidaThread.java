/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema3.ejercicio04;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import static tema3.ejercicio04.Utils.*;

/**
 *
 * @author Perig
 */
public class PartidaThread extends Thread {
    private final int MAX_PLAYERS = 3;
    private int playerCount;
    private ArrayList<Socket> players;
    Partida partida;
    AhorcadoServer s;

    public PartidaThread(AhorcadoServer s) {
        playerCount = 0;
        players = new ArrayList();
        partida = new Partida();
        this.s = s;
    }

    @Override
    public void run() {
        while(!isFull()) {
            try {
                for(Socket player : players) {
                    String output = WAITING_FOR_PLAYERS+SEPARATOR+(MAX_PLAYERS-playerCount);
                    System.out.println(output);
                    new PrintWriter(player.getOutputStream(), true).println(output);
                    System.out.println("Enviado output desde hilo Partida. Procediendo a esperar");
                }
                synchronized(this) {
                    this.wait();
                }
            } catch (IOException e) {
            } catch (InterruptedException e) {
            }
        }
        s.chechIfFull();
        try {
            for(Socket player : players) {
                    new PrintWriter(player.getOutputStream(), true).println(ASK4LETTER+SEPARATOR+partida.getInfoNoMessage()+SEPARATOR+START_GAME);
            }
        } catch (IOException e) {}
    }
    
    
    
    public synchronized boolean isFull() {
        return playerCount == MAX_PLAYERS;
    }
    
    public synchronized void addPlayer(Socket t) {
        players.add(t);
        playerCount = players.size();
        notifyAll();
    }
}
