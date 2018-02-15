/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema3.ejercicio03;

import static tema3.ejercicio03.Utils.WORDS;

/**
 *
 * @author Perig
 */
public class Partida {
    public static final int MAX_ERRORS = 6;
    
    public int errors;
    public String word;
    public String solvedWord;

    public Partida() {
        errors = 0;
        word = WORDS[(int)(Math.random()*WORDS.length)];
        solvedWord="";
        for(int i=0;i<word.length();i++) solvedWord+="*";
    }
    
}
