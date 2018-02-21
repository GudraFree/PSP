/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema3.ejercicio04;

import static tema3.ejercicio04.Utils.*;

/**
 *
 * @author Perig
 */
public class OnlineGameProtocol {
    String state;
    long startTime;
    PartidaOnline partida;
    OnlineClientThread oct;

    public OnlineGameProtocol(OnlineClientThread oct) {
        this.oct = oct;
        state = WAITING_ONLINE;
    }
    
    
    
    public String processInput(String input) { // input del palo opClient-[arg-arg2]
        String[] command = input.split(SEPARATOR);
        String output = ""; // output del palo opServer-[datos-datos]
        System.out.println("Input: "+input);
        
        switch(state) {
            case WAITING_ONLINE:
                System.out.println("Thread: "+Thread.currentThread()+"; State: WAITING_ONLINE");
                startTime = System.currentTimeMillis();
                output = ASK4LETTER_ONLINE + SEPARATOR + partida.getWord() + SEPARATOR + START_GAME + SEPARATOR + oct.getErrors();
                // op:solvedWord:mensaje:error
                state = ASKED4LETTER_ONLINE;
                break;
            case ASKED4LETTER_ONLINE:
                System.out.println("Thread: "+Thread.currentThread()+"; State: ASKED4LETTER_ONLINE");
                switch(command[0]) {
                    case SEND_LETTER:
                        state = partida.checkLetterOnline(command[1], oct);
                        if(state.equals(LOST_ONLINE)) {
                            output = I_LOST_ONLINE;
                            
                        } else if (state.equals(WON_ONLINE)) {
                            output = I_WON_ONLINE;
                        }
                        else output = ASK4LETTER_ONLINE;
                        output += SEPARATOR + partida.getWordAndMessage() + SEPARATOR + oct.getErrors();
                        break;
                    case CLIENT_ERROR:
                        output = ASK4LETTER_ONLINE + SEPARATOR + partida.getWord() + SEPARATOR + INVALID_MENU_OPTION;
                }
                break;
            case LOST_ONLINE:
                // TODO: si pierde, debería esperar
                break;
            case WON_ONLINE:
                // TODO: si gana, debe:
                // - interrumpir a los que sigan jugando
                // - notificar a los que hayan terminado (¿puede catchear una interrupción?)
                oct.pt.someoneWon(oct);
                break;
            case WAITING_GAME_MENU: 
                System.out.println("Thread: "+Thread.currentThread()+"; State: WAITING_GAME_MENU");
                output = SHOW_GAME_MENU;
                state = OPTIONS;
                break;
        }
        
        System.out.println("Output: "+output);
        return output;
    }
    
    public void setPartida(PartidaOnline p) {
        partida = p;
    }
}
