/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema3.ejercicio04;

import java.util.ArrayList;

/**
 *
 * @author Perig
 */
public class PartidaThread extends Thread {
    private final int MAX_PLAYERS = 3;
    private int playerCount;
    private ArrayList<AhorcadoServerThread> players;

    public PartidaThread() {
        playerCount = 0;
        players = new ArrayList();
    }
    
    public boolean isFull() {
        return playerCount == MAX_PLAYERS;
    }
    
    public void addPlayer(AhorcadoServerThread t) {
        players.add(t);
        // TODO: mandar un mensaje a cada jugador
        for(AhorcadoServerThread st : players) {
            
        }
    }
}
