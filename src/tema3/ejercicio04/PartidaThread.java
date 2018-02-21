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
    private PartidaOnline partida;
    private AhorcadoServer s;
    private Clientes clientes;

    public PartidaThread(AhorcadoServer s) {
        playerCount = 0;
        players = new ArrayList();
        partida = new PartidaOnline();
        clientes = new Clientes();
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
        s.chechIfFull(); // para settear la partida pendiente del server de esta instancia a null
        
        for(Socket player : players) {
                OnlineClientThread oct = new OnlineClientThread(player, this);
                clientes.add(oct);
                oct.start();
        }
        
        try {
            while(clientes.someIsPlaying()) Thread.sleep(100);
        } catch (InterruptedException e) {}
    }
    
    
    
    public synchronized boolean isFull() {
        return playerCount == MAX_PLAYERS;
    }
    
    public synchronized void addPlayer(Socket t) {
        players.add(t);
        playerCount = players.size();
        notifyAll();
    }
    
    public synchronized PartidaOnline getPartida() {
        return partida;
    }
    
    public synchronized void someoneWon(OnlineClientThread oct) {
        
    }
    
    public class Clientes {
        private ArrayList<OnlineClientThread> hilosCliente;

        public Clientes() {
            hilosCliente = new ArrayList();
        }
        
        public void add(OnlineClientThread t) {
            hilosCliente.add(t);
        }
        
        public boolean someIsPlaying() {
            boolean someIsPlaying = false;
            for(OnlineClientThread oct : hilosCliente) {
                someIsPlaying = someIsPlaying || oct.isAlive();
            }
            return someIsPlaying;
        }
        
        public void someoneWon(OnlineClientThread t) {
            for(OnlineClientThread oct : hilosCliente) {
                if(!oct.equals(t)) {
                    oct.interrupt();
                }
            }
            
        }
        
    }
}
