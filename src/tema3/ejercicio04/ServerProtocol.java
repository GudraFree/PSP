/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema3.ejercicio04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import static tema3.ejercicio04.Utils.*;

/**
 *
 * @author Perig
 */
public class ServerProtocol {
    private String state;
    private String name_login = "";
    private String name_register = "";
    private String pass_register = "";
    private Partida partida = null;
    private DateFormat df;
    private long startTime;
    
    // db parameters
    private String db_driver = "com.mysql.jdbc.Driver";
    private String db_url = "jdbc:mysql://localhost/ahorcado";
    private String db_user = "root";
    private String db_password = "";
    private Connection connection;
    private String sql_name = "select * from usuario where nick=?";
    private String sql_name_pass = "select * from usuario where nick=? and pass=?";
    private String sql_register = "insert into usuario(nick,pass,rol) values (?,?,?)";
    private String sql_games_up = "update usuario set n_jugadas = n_jugadas + 1 where nick=?";
    private String sql_wins_up = "update usuario set n_ganadas = n_ganadas + 1 where nick=?";
    private String sql_game_time_up = "update usuario set tiempo_juego = tiempo_juego + ? where nick=?";
    
    

    public ServerProtocol(String state) {
        this.state = state;
        df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            Class.forName(db_driver);
            connection = DriverManager.getConnection(db_url,db_user,db_password);
        } catch (ClassNotFoundException e) {
            System.out.println("Error, clase no encontrada");
        } catch (SQLException e) {
            System.out.println("Error de SQL");
        }
    }
    
    
    
    public String processInput(String input) { // input del palo opClient-[arg-arg2]
        String[] command = input.split(SEPARATOR);
        String output = ""; // output del palo opServer-[datos-datos]
        System.out.println("Input: "+input);
        
        switch(state) {
            case WAITING_LOGIN:
                System.out.println("State: WAITING");
                output = SHOW_LOGIN_MENU;
                state = LOGIN_OPTIONS;
                break;
            case LOGIN_OPTIONS:
                System.out.println("State: LOGIN_OPTIONS");
                switch(command[0]) {
                    case LOGIN:
                        output = L_ASK4NAME;
                        state = L_NAME;
                        break;
                    case REGISTER:
                        output = R_ASK4NAME;
                        state = R_NAME;
                        break;
                    case CLIENT_ERROR:
                        output = SHOW_LOGIN_MENU+SEPARATOR+INVALID_MENU_OPTION;
                        break;
                    case PLEASE_KILL_ME:
                        output = END_CLIENT_LIFE;
                        break;
                    default:
                        output = SHOW_LOGIN_MENU+SEPARATOR+UNEXPECTED_ERROR;
                }
                break;
            case L_NAME:
                System.out.println("State: L_NAME");
                if(command[0].equals(LOGIN_NAME)) {
                    String receivedName = command[1];
                    System.out.println("Received name: "+receivedName);
                    boolean validName = false;
                    try{
                        PreparedStatement ps = connection.prepareStatement(sql_name);
                        ps.setString(1,receivedName);
                        ResultSet rs = ps.executeQuery();
                        validName = rs.next();
                        rs.close();
                        ps.close();
                    } catch (SQLException e) {
                        state = LOGIN_OPTIONS;
                        return SHOW_LOGIN_MENU + SEPARATOR + UNEXPECTED_ERROR;
                    }
                    
                    if(validName) {
                        name_login = receivedName;
                        output = L_ASK4PASS;
                        state = L_PASS;
                    } else { // error de usuario inexistente, se vuelve al menú
                        state = LOGIN_OPTIONS;
                        output = SHOW_LOGIN_MENU + SEPARATOR + L_NAME_NOT_EXIST;
                    }
                } else {
                    System.out.println("Error de comando equivocado");
                    output = SHOW_LOGIN_MENU+SEPARATOR+UNEXPECTED_ERROR;
                    state = LOGIN_OPTIONS;
                }
                break;
            case L_PASS:
                System.out.println("State: L_PASS");
                if(command[0].equals(LOGIN_PASS)) {
                    String receivedPass = command[1];
                    System.out.println("Received pass: "+receivedPass);
                    boolean validLogin = false;
                    try{
                        PreparedStatement ps = connection.prepareStatement(sql_name_pass);
                        ps.setString(1,name_login);
                        ps.setString(2,receivedPass);
                        ResultSet rs = ps.executeQuery();
                        validLogin = rs.next();
                    
                        if(validLogin) {
                            String rol = rs.getString("rol");
                            output = SHOW_GAME_MENU + SEPARATOR + VALID_LOGIN + SEPARATOR + rol;
                            state = OPTIONS;
                        } else { // error de contraseña incorrecta, se vuelve a pedir nombre de usuario
                            state = L_NAME;
                            output = L_ASK4NAME + SEPARATOR + L_PASS_NOT_VALID;
                        }
                        
                        rs.close();
                        ps.close();
                    } catch (SQLException e) {
                        state = LOGIN_OPTIONS;
                        return SHOW_LOGIN_MENU + SEPARATOR + UNEXPECTED_ERROR;
                    }
                } else {
                    System.out.println("Error de comando equivocado");
                    output = SHOW_LOGIN_MENU+SEPARATOR+UNEXPECTED_ERROR;
                    state = LOGIN_OPTIONS;
                }
                break;
            case R_NAME:
                System.out.println("State: R_NAME");
                if(command[0].equals(REGISTER_NAME)) {
                    String receivedName = command[1];
                    System.out.println("Received name: "+receivedName);
                    boolean validName = false;
                    try{
                        PreparedStatement ps = connection.prepareStatement(sql_name);
                        ps.setString(1,receivedName);
                        ResultSet rs = ps.executeQuery();
                        validName = rs.next();
                        rs.close();
                        ps.close();
                    } catch (SQLException e) {
                        state = LOGIN_OPTIONS;
                        return SHOW_LOGIN_MENU + SEPARATOR + UNEXPECTED_ERROR;
                    }
                    
                    if(!validName) {
                        name_register = receivedName;
                        output = R_ASK4PASS;
                        state = R_PASS;
                    } else { // error de usuario inexistente, se vuelve al menú
                        state = LOGIN_OPTIONS;
                        output = SHOW_LOGIN_MENU + SEPARATOR + R_NAME_ALREADY_EXIST;
                    }
                } else {
                    System.out.println("Error de comando equivocado");
                    output = SHOW_LOGIN_MENU+SEPARATOR+UNEXPECTED_ERROR;
                    state = LOGIN_OPTIONS;
                }
                break;
            case R_PASS:
                System.out.println("State: R_PASS");
                if(command[0].equals(REGISTER_PASS)) {
                    String receivedPass = command[1];
                    System.out.println("Received pass: "+receivedPass);
                    pass_register = receivedPass;
                    output = R_ASK4ADMIN;
                    state = R_ADMIN;
                } else {
                    System.out.println("Error de comando equivocado");
                    output = SHOW_LOGIN_MENU+SEPARATOR+UNEXPECTED_ERROR;
                    state = LOGIN_OPTIONS;
                }
                break;
            case R_ADMIN:
                System.out.println("State: R_ADMIN");
                if(command[0].equals(REGISTER_ADMIN)) {
                    String receivedAdmin = command[1];
                    System.out.println("Received admin: "+receivedAdmin);
                    String rol;
                    switch(receivedAdmin) {
                        case "y": case "Y": case "s": case "S":
                            rol = "admin";
                            break;
                        case "n": case "N":
                            rol = "user";
                            break;
                        default: // error de introducción, no es y/n
                            return SHOW_LOGIN_MENU + SEPARATOR + INVALID_MENU_OPTION;
                    }
                    try {
                        PreparedStatement ps = connection.prepareStatement(sql_register);
                        ps.setString(1,name_register);
                        ps.setString(2,pass_register);
                        ps.setString(3,rol);
                        ps.execute();
                        ps.close();
                    } catch (SQLException e) {
                        state = LOGIN_OPTIONS;
                        return SHOW_LOGIN_MENU + SEPARATOR + UNEXPECTED_ERROR;
                    }
                    output = SHOW_LOGIN_MENU + SEPARATOR + VALID_REGISTER;
                    state = LOGIN_OPTIONS;
                } else {
                    System.out.println("Error de comando equivocado");
                    output = SHOW_LOGIN_MENU+SEPARATOR+UNEXPECTED_ERROR;
                    state = LOGIN_OPTIONS;
                }
                break;
            case OPTIONS:
                System.out.println("State: OPTIONS");
                switch(command[0]) {
                    case PLAY:
                        partida = new Partida();
                        startTime = System.currentTimeMillis();
                        output = ASK4LETTER + SEPARATOR + partida.getInfo() + START_GAME;
                        state = ASKED4LETTER;
                        break;
                    case QUERY:
                        String queryResults = "";
                        try {
                            PreparedStatement ps = connection.prepareStatement(sql_name);
                            ps.setString(1, name_login);
                            ResultSet rs = ps.executeQuery();
                            if(rs.next()) {
                                queryResults = rs.getString("n_jugadas") + SEPARATOR + rs.getString("n_ganadas") + SEPARATOR + df.format(new Date(Long.parseLong(rs.getString("tiempo_juego"))));
                            }
                        } catch (SQLException e) {
                            state = OPTIONS;
                            return SHOW_GAME_MENU + SEPARATOR + UNEXPECTED_ERROR;
                        }
                        output = SHOW_GAME_MENU + SEPARATOR + SHOW_OWN_QUERY + SEPARATOR + queryResults;
                        state = OPTIONS;
                        break;
                    case QUERY_ADMIN:
                        output = ASK4USER;
                        state = QUERY_WHO;
                        break;
                    case LOGOUT:
                        output = SHOW_LOGIN_MENU;
                        state = LOGIN_OPTIONS;
                        break;
                    case CLIENT_ERROR:
                        output = SHOW_GAME_MENU+SEPARATOR+INVALID_MENU_OPTION;
                        break;
                    case PLAY_ONLINE:
                        output = START_ONLINE_GAME;
                        break;
                    default:
                        output = SHOW_GAME_MENU+SEPARATOR+UNEXPECTED_ERROR;
                }
                break;
            case QUERY_WHO:
                System.out.println("State: QUERY_WHO");
                switch(command[0]) {
                    case SEND_USER:
                        String queryResults = "";
                        try {
                            PreparedStatement ps = connection.prepareStatement(sql_name);
                            ps.setString(1, command[1]);
                            ResultSet rs = ps.executeQuery();
                            if(rs.next()) {
                                queryResults = rs.getString("n_jugadas") + SEPARATOR + rs.getString("n_ganadas") + SEPARATOR + df.format(new Date(Long.parseLong(rs.getString("tiempo_juego"))));
                            } else {
                                return ASK4USER + SEPARATOR + USER_NOT_EXIST;
                            }
                        } catch (SQLException e) {
                            state = OPTIONS;
                            return ASK4USER + SEPARATOR + UNEXPECTED_ERROR;
                        }
                        output = SHOW_GAME_MENU + SEPARATOR + SHOW_USER_QUERY + SEPARATOR + queryResults + SEPARATOR + command[1];
                        state = OPTIONS;
                        break;
                        
                }
            case ASKED4LETTER:
                System.out.println("State: ASKED4LETTER");
                switch(command[0]) {
                    case SEND_LETTER:
                        state = partida.checkLetter(command[1]);
                        if(state.equals(ANOTHER)) {
                            output = ASK4ANOTHER;
                            try {
                                updateDatabase();
                            } catch (SQLException e) {
                                state = OPTIONS;
                                return SHOW_GAME_MENU + SEPARATOR + UNEXPECTED_ERROR;
                            }
                        }
                        else output = ASK4LETTER;
                        output += SEPARATOR + partida.getInfo();
                        break;
                    case CLIENT_ERROR:
                        output = ASK4LETTER + SEPARATOR + partida.getInfoNoMessage() + SEPARATOR + INVALID_MENU_OPTION;
                }
                break;
            case ANOTHER:
                System.out.println("State: ANOTHER");
                if(command[0].equals(SEND_ANOTHER)) {
                    String receivedAnother = command[1];
                    System.out.println("Received another: "+receivedAnother);
                    switch(receivedAnother) {
                        case "y": case "Y": case "s": case "S":
                            partida = new Partida();
                            startTime = System.currentTimeMillis();
                            output = ASK4LETTER + SEPARATOR + partida.getInfo() + START_GAME;
                            state = ASKED4LETTER;
                            break;
                        case "n": case "N":
                            output = SHOW_GAME_MENU;
                            state = OPTIONS;
                            break;
                        default: // error de introducción, no es y/n
                            return ASK4ANOTHER + SEPARATOR + INVALID_MENU_OPTION;
                    }
                } else {
                    System.out.println("Error de comando equivocado");
                    output = ASK4ANOTHER+SEPARATOR+UNEXPECTED_ERROR;
                    state = ANOTHER;
                }
                break;
            case WAITING_ONLINE:
                System.out.println("State: WAITING_ONLINE");
                startTime = System.currentTimeMillis();
                output = ASK4LETTER + SEPARATOR + partida.getInfo() + START_GAME;
                state = ASKED4LETTER_ONLINE;
                break;
            case ASKED4LETTER_ONLINE:
                System.out.println("State: ASKED4LETTER_ONLINE");
                switch(command[0]) {
                    case SEND_LETTER:
                        state = partida.checkLetterOnline(command[1]);
                        // TODO: if(pierde) esperar; if(gana) parar a todo el mundo
                        if(state.equals(ANOTHER)) {
                            output = ASK4ANOTHER;
                            try {
                                updateDatabase();
                            } catch (SQLException e) {
                                state = OPTIONS;
                                return SHOW_GAME_MENU + SEPARATOR + UNEXPECTED_ERROR;
                            }
                        }
                        else output = ASK4LETTER;
                        output += SEPARATOR + partida.getInfo();
                        break;
                    case CLIENT_ERROR:
                        output = ASK4LETTER + SEPARATOR + partida.getInfoNoMessage() + SEPARATOR + INVALID_MENU_OPTION;
                }
                break;
            case WAITING_GAME_MENU: 
                System.out.println("State: WAITING_GAME_MENU");
                output = SHOW_GAME_MENU;
                state = OPTIONS;
                break;
        }
        
        System.out.println("Output: "+output);
        return output;
    }
    
    private void updateDatabase() throws SQLException {
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime-startTime;
        PreparedStatement psGames = connection.prepareStatement(sql_games_up);
        psGames.setString(1,name_login);
        psGames.execute();
        psGames.close();
        if(partida.mensaje.equals(VICTORY)) {
            PreparedStatement psWins = connection.prepareStatement(sql_wins_up);
            psWins.setString(1,name_login);
            psWins.execute();
            psWins.close();
        }
        PreparedStatement psGameTime = connection.prepareStatement(sql_game_time_up);
        psGameTime.setLong(1,elapsedTime);
        psGameTime.setString(2,name_login);
        psGameTime.execute();
        psGameTime.close();
    }
    
    public void setPartida(Partida p) {
        partida = p;
    }
    
    public void setState(String state) {
        this.state = state;
    }
}
