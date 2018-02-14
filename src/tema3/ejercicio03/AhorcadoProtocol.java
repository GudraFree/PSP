/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema3.ejercicio03;

import tema3.ejercicio02.*;
import tema3.ejercicio01.*;

/**
 *
 * @author Perig
 */
public class AhorcadoProtocol {
    private static final int WAITING = 1;
    private static final int ASKED4LETTER = 2;
    private static final int VICTORY = 3;
    private static final int LOSE = 4;
    private static final int ANOTHER = 5;
    private static final int MAX_ERRORS = 6;
    private static final String[] WORDS = {"FELPUDO","MANDRIL","CALIFATO","EXPROPIAR","WYVERN"};
    
    private int errors;
    private String word;
    private String solvedWord;
    private int state;

    public AhorcadoProtocol() {
        errors = 0;
        word = WORDS[(int)(Math.random()*WORDS.length)];
        solvedWord="";
        for(int i=0;i<word.length();i++) solvedWord+="*";
        state = WAITING;
    }
    
    
    
    public String processInput(String input) {
        String letra = "";
        if(input!=null) letra = input.toUpperCase();
        String output = "";
        String mensaje = "";
        
        System.out.println("State: "+state);
        
        switch(state) {
            case WAITING:
                mensaje = "Empieza el juego. Introduzca una letra";
                state = ASKED4LETTER;
                break;
            case ASKED4LETTER:
                if(word.indexOf(letra)<0) { //letra no encontrada
                    errors++;
                    if(errors==MAX_ERRORS) {
                        mensaje = "Ha perdido... ¿Desea jugar otra vez? (s/n)";
                        state = ANOTHER;
                    } else {
                        mensaje = "Error. Introduzca otra letra";
                        state = ASKED4LETTER;
                    }
                } else { //letra encontrada
                    String newSolvedWord = "";
                    for(int i=0; i<word.length(); i++) {
                        String l = String.valueOf(word.charAt(i));
                        String c = String.valueOf(solvedWord.charAt(i));
                        if(l.equals(letra)) newSolvedWord+=l;
                        else newSolvedWord+=c;
                    }
                    solvedWord = newSolvedWord;
                    if(word.equals(solvedWord)) {
                        mensaje = "¡Enhorabuena, ha ganado! ¿Desea jugar otra vez? (s/n)";
                        state = ANOTHER;
                    } else {
                        mensaje = "¡Acierto! Introduzca otra letra";
                        state = ASKED4LETTER;
                    }
                }
                break;
            case ANOTHER:
                if(letra.equals("S")) {
                    errors = 0;
                    word = WORDS[(int)(Math.random()*WORDS.length)];
                    solvedWord="";
                    for(int i=0;i<word.length();i++) solvedWord+="*";
                    mensaje = "Empieza el juego. Introduzca una letra";
                    state = ASKED4LETTER;
                } else {
                    mensaje = "Hasta otra";
                }
        }
        
        output = mensaje+"-"+solvedWord+"-"+errors;
        return output;
    }
}
