/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema3.ejercicio04;

import static tema3.ejercicio04.Utils.ASKED4LETTER_ONLINE;
import static tema3.ejercicio04.Utils.LETTER_TAKEN;
import static tema3.ejercicio04.Utils.LOSE_ONLINE;
import static tema3.ejercicio04.Utils.LOST_ONLINE;
import static tema3.ejercicio04.Utils.RIGHT_LETTER;
import static tema3.ejercicio04.Utils.SEPARATOR;
import static tema3.ejercicio04.Utils.VICTORY_ONLINE;
import static tema3.ejercicio04.Utils.WON_ONLINE;
import static tema3.ejercicio04.Utils.WORDS;
import static tema3.ejercicio04.Utils.WRONG_LETTER;

/**
 *
 * @author Perig
 */
public class PartidaOnline {
    public static final int MAX_ERRORS = 6;
    
    public String word;
    public String solvedWord;
    public String mensaje;
    public boolean alreadyWon = false;
    
    public PartidaOnline() {
        word = WORDS[(int)(Math.random()*WORDS.length)];
        solvedWord="";
        mensaje = "";
        for(int i=0;i<word.length();i++) solvedWord+="*";
    }
    
    public synchronized String checkLetterOnline(String lett,OnlineClientThread caller) {
        String letra = lett.toUpperCase();
        String state = "";
        if(word.indexOf(letra)<0) { //letra no encontrada
            caller.setErrors(caller.getErrors() + 1);
            if(caller.getErrors()==MAX_ERRORS) {
                mensaje = LOSE_ONLINE;
                state = LOST_ONLINE;
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
                    if(!alreadyWon) {
                        mensaje = VICTORY_ONLINE;
                        state = WON_ONLINE;
                        alreadyWon = true;
                    } else {
                        mensaje = LOSE_ONLINE;
                        state = LOST_ONLINE;
                    }
                } else {
                    mensaje = RIGHT_LETTER;
                    state = ASKED4LETTER_ONLINE;
                }
            }
        }
        return state;
    }
    public synchronized String getWordAndMessage() {
        System.out.println("Word: "+word);
        return solvedWord + SEPARATOR + mensaje;
    }
    
    public synchronized String getWord() {
        System.out.println("Word: "+word);
        return solvedWord;
    }
}
