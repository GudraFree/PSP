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
public class Partida {
    public static final int MAX_ERRORS = 6;
    
    public int errors;
    public String word;
    public String solvedWord;
    public String mensaje;

    public Partida() {
        errors = 0;
        word = WORDS[(int)(Math.random()*WORDS.length)];
        solvedWord="";
        mensaje = "";
        for(int i=0;i<word.length();i++) solvedWord+="*";
    }
    
    public synchronized String getInfo() {
        System.out.println("Word: "+word);
        return solvedWord + SEPARATOR + errors + SEPARATOR + mensaje;
    }
    
    public synchronized String getInfoNoMessage() {
        System.out.println("Word: "+word);
        return solvedWord + SEPARATOR + errors;
    }
    
    public String checkLetter(String lett) {
        String letra = lett.toUpperCase();
        String state = "";
        if(word.indexOf(letra)<0) { //letra no encontrada
            errors++;
            if(errors==MAX_ERRORS) {
                mensaje = LOSE;
                state = ANOTHER;
            } else {
                mensaje = WRONG_LETTER;
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
                mensaje = VICTORY;
                state = ANOTHER;
            } else {
                mensaje = RIGHT_LETTER;
                state = ASKED4LETTER;
            }
        }
        return state;
    }
    
    public synchronized String checkLetterOnline(String lett) {
        String letra = lett.toUpperCase();
        String state = "";
        if(word.indexOf(letra)<0) { //letra no encontrada
            errors++;
            if(errors==MAX_ERRORS) {
                mensaje = LOSE_ONLINE;
                state = WAIT_END_GAME_ONLINE;
            } else {
                mensaje = WRONG_LETTER;
                state = ASKED4LETTER_ONLINE;
            }
        } else { //letra encontrada
            if(solvedWord.contains(lett)) {
                mensaje = LETTER_TAKEN;
                state = ASKED4LETTER_ONLINE;
            } else {
                String newSolvedWord = "";
                for(int i=0; i<word.length(); i++) {
                    String l = String.valueOf(word.charAt(i));
                    String c = String.valueOf(solvedWord.charAt(i));
                    if(l.equals(letra)) newSolvedWord+=l;
                    else newSolvedWord+=c;
                }
                solvedWord = newSolvedWord;
                if(word.equals(solvedWord)) {
                    mensaje = VICTORY_ONLINE;
    //                state = ANOTHER;
                } else {
                    mensaje = RIGHT_LETTER;
                    state = ASKED4LETTER_ONLINE;
                }
            }
        }
        return state;
    }
}
