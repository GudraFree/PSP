/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema3.ejercicio03;

import static tema3.ejercicio03.Utils.*;

/**
 *
 * @author Perig
 */
public class AhorcadoProtocol {
    private String state;

    public AhorcadoProtocol() {
        state = WAITING;
    }
    
    
    
    public String processInput(String input) { // input del palo opClient:[arg:arg2]
        String[] command = input.split(":");
        System.out.println("State: "+state);
        String output = ""; // output del palo opServer:[datos]
        
        switch(state) {
            case WAITING:
                output += SHOW_LOGIN_MENU;
                state = LOGIN_OPTIONS;
                break;
            case LOGIN_OPTIONS:
                switch(command[0]) {
                    case LOGIN:
                        state = L_NAME;
                        break;
                    case REGISTER:
                        state = R_NAME;
                        break;
                    default:
                        
                }
                break;
//            case ASKED4LETTER:
//                if(word.indexOf(letra)<0) { //letra no encontrada
//                    errors++;
//                    if(errors==MAX_ERRORS) {
//                        output = "Ha perdido... ¿Desea jugar otra vez? (s/n)";
//                        state = ANOTHER;
//                    } else {
//                        output = "Error. Introduzca otra letra";
//                        state = ASKED4LETTER;
//                    }
//                } else { //letra encontrada
//                    String newSolvedWord = "";
//                    for(int i=0; i<word.length(); i++) {
//                        String l = String.valueOf(word.charAt(i));
//                        String c = String.valueOf(solvedWord.charAt(i));
//                        if(l.equals(letra)) newSolvedWord+=l;
//                        else newSolvedWord+=c;
//                    }
//                    solvedWord = newSolvedWord;
//                    if(word.equals(solvedWord)) {
//                        output = "¡Enhorabuena, ha ganado! ¿Desea jugar otra vez? (s/n)";
//                        state = ANOTHER;
//                    } else {
//                        output = "¡Acierto! Introduzca otra letra";
//                        state = ASKED4LETTER;
//                    }
//                }
//                break;
//            case ANOTHER:
//                if(letra.equals("S")) {
//                    errors = 0;
//                    word = WORDS[(int)(Math.random()*WORDS.length)];
//                    solvedWord="";
//                    for(int i=0;i<word.length();i++) solvedWord+="*";
//                    output = "Empieza el juego. Introduzca una letra";
//                    state = ASKED4LETTER;
//                } else {
//                    output = "Hasta otra";
//                }
        }
        
        System.out.println(output);
        return output;
    }
}
