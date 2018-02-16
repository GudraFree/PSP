/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema3.ejercicio03;

/**
 *
 * @author Perig
 */
public class Utils {
    // estados
    public static final String WAITING = "10";
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
    public static final String QUERY_ADMIN_WHO = "020"; //recibe user
    public static final String LOGOUT = "021";
    public static final String SEND_LETTER = "022"; // recibe letter
    public static final String SEND_ANOTHER = "023"; // recibe y/n
    
    // operaciones del servidor
    public static final String SHOW_LOGIN_MENU = "110";
    public static final String L_ASK4NAME = "111";
    public static final String L_ASK4PASS = "113";
    public static final String R_ASK4NAME = "112";
    public static final String R_ASK4PASS = "114";
    public static final String R_ASK4ADMIN = "115";
    
    // errores
    public static final String L_NAME_NOT_EXIST = "910";
    public static final String L_PASS_NOT_VALID = "911";
    public static final String R_NAME_ALREADY_EXIST = "912";
    public static final String INVALID_MENU_OPTION = "913";
    public static final String UNEXPECTED_ERROR = "914";
    public static final String CLIENT_ERROR = "915";
    
    // palabras
    public static final String[] WORDS = {"FELPUDO","MANDRIL","CALIFATO","EXPROPIAR","WYVERN"};
    // separador de campos
    public static final String SEPARATOR = "-";
    
}
