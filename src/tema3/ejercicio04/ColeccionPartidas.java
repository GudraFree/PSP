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
public class ColeccionPartidas {
    public ArrayList<PartidaThread> partidas;

    public ColeccionPartidas() {
        partidas = new ArrayList();
    }
    
    public void buscarPartida(AhorcadoServerThread t) {
        boolean encontrado = false;
        for (PartidaThread pt : partidas) {
            if(!pt.isFull()) {
                // TODO: unirse a partida
                encontrado = true;
            }
        }
        
        if(!encontrado) {
            PartidaThread pt = new PartidaThread();
            partidas.add(pt);
            pt.start(); // debería estar aquí?
            pt.addPlayer(t);
        }
    }
}
