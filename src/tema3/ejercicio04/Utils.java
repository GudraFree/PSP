/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema3.ejercicio04;

/**
 *
 * @author Perig
 */
public class Utils {
    // separador de campos
    public static final String SEPARATOR = "-";
    
    // estados
    public static final String LOGIN_OPTIONS = "00";
    public static final String L_NAME = "01";
    public static final String L_PASS = "02";
    public static final String R_NAME = "03";
    public static final String R_PASS = "04";
    public static final String R_ADMIN = "05";
    public static final String OPTIONS = "06";
    public static final String QUERY_WHO = "07";
    public static final String ASKED4LETTER = "08";
    public static final String ANOTHER = "09";
    public static final String WAITING_LOGIN = "10";
    public static final String WAITING_ONLINE = "11";
    public static final String ASKED4LETTER_ONLINE = "12";
    public static final String WAITING_GAME_MENU = "13";
    public static final String WAIT_END_GAME_ONLINE = "14";
    
    //operaciones del cliente
    public static final String LOGIN = "010";
    public static final String REGISTER = "011";
    public static final String LOGIN_NAME = "012"; //recibe name
    public static final String LOGIN_PASS = "013"; //recibe pass
    public static final String REGISTER_NAME = "014"; //recibe name
    public static final String REGISTER_PASS = "015"; //recibe pass
    public static final String REGISTER_ADMIN = "016"; //recibe y/n
    public static final String PLAY = "017";
    public static final String QUERY = "018";
    public static final String QUERY_ADMIN = "019";
    public static final String SEND_USER = "020"; //recibe user
    public static final String LOGOUT = "021";
    public static final String SEND_LETTER = "022"; // recibe letter
    public static final String SEND_ANOTHER = "023"; // recibe y/n
    public static final String PLEASE_KILL_ME = "024";
    public static final String PLAY_ONLINE = "025";
    
    // operaciones del servidor
    public static final String SHOW_LOGIN_MENU = "110";
    public static final String L_ASK4NAME = "111";
    public static final String L_ASK4PASS = "113";
    public static final String R_ASK4NAME = "112";
    public static final String R_ASK4PASS = "114";
    public static final String R_ASK4ADMIN = "115";
    public static final String SHOW_GAME_MENU = "116";
    public static final String SHOW_OWN_QUERY = "117";
    public static final String ASK4USER = "118";
    public static final String SHOW_USER_QUERY = "119";
    public static final String ASK4LETTER = "120";
    public static final String ASK4ANOTHER = "121";
    public static final String START_ONLINE_GAME = "122";
    public static final String ASK4LETTER_ONLINE = "123";
    
    // mensajes del servidor
    public static final String VALID_LOGIN = "210";
    public static final String VALID_REGISTER = "211";
    public static final String START_GAME = "212";
    public static final String RIGHT_LETTER = "213";
    public static final String WRONG_LETTER = "214";
    public static final String VICTORY = "215";
    public static final String LOSE = "216";
    public static final String END_CLIENT_LIFE = "217";
    public static final String LOSE_ONLINE = "218";
    public static final String VICTORY_ONLINE = "219";
    public static final String LETTER_TAKEN = "220";
    
    // mensajes de PartidaThread
    public static final String WAITING_FOR_PLAYERS = "310"; // seguido de n players
    
    // mensajes error cliente
    public static final String M_UNEXPECTED_ERROR = "Error no esperado. ";
    public static final String M_DEFAULT_ERROR = "Error por defecto. Este error no debería producirse. ";
    public static final String M_INVALID_OPTION = "Error, opción inválida. ";
    
    // errores
    public static final String L_NAME_NOT_EXIST = "910";
    public static final String L_PASS_NOT_VALID = "911";
    public static final String R_NAME_ALREADY_EXIST = "912";
    public static final String INVALID_MENU_OPTION = "913";
    public static final String UNEXPECTED_ERROR = "914";
    public static final String CLIENT_ERROR = "915";
    public static final String USER_NOT_EXIST = "916";
    
    // arrays
    public static final String[] WORDS = {"FELPUDO","MANDRIL","CALIFATO","EXPROPIAR","WYVERN"};
    public static final String[] AHORCADO = { 
"------|\n" +
"|     \n" +
"|    \n" +
"|    \n" +
"|\n" +
"====", 
"------|\n" +
"|     O\n" +
"|    \n" +
"|    \n" +
"|\n" +
"====",
"------|\n" +
"|     O\n" +
"|     |\n" +
"|    \n" +
"|\n" +
"====",
"------|\n" +
"|     O\n" +
"|    /|\n" +
"|    \n" +
"|\n" +
"====",
"------|\n" +
"|     O\n" +
"|    /|\\\n" +
"|    \n" +
"|\n" +
"====",
"------|\n" +
"|     O\n" +
"|    /|\\\n" +
"|    / \n" +
"|\n" +
"====",
"------|\n" +
"|     O\n" +
"|    /|\\\n" +
"|    / \\\n" +
"|\n" +
"====", };
    
}
