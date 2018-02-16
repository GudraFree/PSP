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
public class ServerProtocol {
    private String state;

    public ServerProtocol() {
        state = WAITING;
    }
    
    
    
    public String processInput(String input) { // input del palo opClient:[arg:arg2]
        String[] command = input.split(SEPARATOR);
        String output = ""; // output del palo opServer:[datos]
        System.out.println("Input: "+input);
        
        switch(state) {
            case WAITING:
                System.out.println("State: WAITING");
                output += SHOW_LOGIN_MENU;
                state = LOGIN_OPTIONS;
                break;
            case LOGIN_OPTIONS:
                System.out.println("State: LOGIN_OPTIONS");
                switch(command[0]) {
                    case LOGIN:
                        output += L_ASK4NAME;
                        state = L_NAME;
                        break;
                    case REGISTER:
                        output += R_ASK4NAME;
                        state = R_NAME;
                        break;
                    case CLIENT_ERROR:
                        output += SHOW_LOGIN_MENU+SEPARATOR+INVALID_MENU_OPTION;
                        break;
                    default:
                        output += SHOW_LOGIN_MENU+SEPARATOR+UNEXPECTED_ERROR;
                }
                break;
            case L_NAME:
                System.out.println("State: L_NAME");
                if(command[0].equals(LOGIN_NAME)) {
                    String receivedName = command[1];
                    System.out.println("Received name: "+receivedName);
                    // TODO: consultar receivedName
                    boolean validName = false; //consulta aquí; placeholder
                    if(validName) {
                        output += L_ASK4PASS;
                        state = L_PASS;
                    } else {
                        state = LOGIN_OPTIONS;
                        output += SHOW_LOGIN_MENU + SEPARATOR + L_NAME_NOT_EXIST;
                    }
                } else {
                    System.out.println("Error de comando equivocado");
                    output += SHOW_LOGIN_MENU+SEPARATOR+UNEXPECTED_ERROR;
                    state = LOGIN_OPTIONS;
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
        
        System.out.println("Output: "+output);
        return output;
    }
}