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
public class ClientProtocol {
    private String state;
    private boolean shouldAsk4Input = true;
    private boolean shouldSendInput = true;
    private boolean isAdmin = false;

    public ClientProtocol() {
        state = WAITING_LOGIN;
    }
    
    public void processInput(String input) { // interpreta el paquete de datos recibido del servidor
        String[] command = input.split(SEPARATOR);
        switch(command[0]) { // actúa según las distintas operaciones que envía el servidor
            case SHOW_LOGIN_MENU:
                if(command.length>1) { // se ha añadido un argumento extra, un mensaje de error
                    switch(command[1]) {
                        case L_NAME_NOT_EXIST:
                            System.out.println("Error, ese usuario no existe.");
                            break;
                        case R_NAME_ALREADY_EXIST:
                            System.out.println("Error, ese usuario ya está registrado.");
                            break;
                        case INVALID_MENU_OPTION:
                            System.out.println(M_INVALID_OPTION);
                            break;
                        case UNEXPECTED_ERROR:
                            System.out.println(M_UNEXPECTED_ERROR);
                            break;
                        case VALID_REGISTER:
                            System.out.println("Registro realizado con éxito");
                            break;
                        default:
                            System.out.println(M_DEFAULT_ERROR);
                    }
                }
                System.out.println("Bienvenido al cliente del Ahorcado. Elija una opción:");
                System.out.println("\t1. Loguearse");
                System.out.println("\t2. Registrarse");
                System.out.println("\t3. Salir");
                state = LOGIN_OPTIONS;
                break;
            case L_ASK4NAME:
                shouldAsk4Input = true;
                shouldSendInput = true;
                if(command.length>1) { // se ha añadido un argumento extra, un mensaje de error
                    switch(command[1]) {
                        case L_PASS_NOT_VALID:
                            System.out.println("Error, contraseña equivocada.");
                            break;
                        case UNEXPECTED_ERROR:
                            System.out.println(M_UNEXPECTED_ERROR);
                            break;
                        default:
                            System.out.println(M_DEFAULT_ERROR);
                    }
                }
                System.out.println("Introduzca nombre:");
                state = L_NAME; 
                break;
            case L_ASK4PASS:
                shouldAsk4Input = true;
                shouldSendInput = true;
                System.out.println("Introduzca contraseña:");
                state = L_PASS;
                break;
            case R_ASK4NAME:
                shouldAsk4Input = true;
                shouldSendInput = true;
                System.out.println("Introduzca nombre:");
                state = R_NAME;
                break;
            case R_ASK4PASS:
                shouldAsk4Input = true;
                shouldSendInput = true;
                System.out.println("Introduzca contraseña:");
                state = R_PASS;
                break;
            case R_ASK4ADMIN:
                shouldAsk4Input = true;
                shouldSendInput = true;
                System.out.println("¿Es admin? (s/n):");
                state = R_ADMIN;
                break;
            case SHOW_GAME_MENU:
                shouldAsk4Input = true;
                shouldSendInput = true;
                if(command.length>1) {
                    switch(command[1]) {
                        case VALID_LOGIN:
                            System.out.println("Logueo realizado con éxito");
                            isAdmin = command[2].equals("admin");
                            break;
                        case UNEXPECTED_ERROR:
                            System.out.println(M_UNEXPECTED_ERROR);
                            break;
                        case SHOW_OWN_QUERY: // SHOW_GAME_MENU:SHOW_OWN_QUERY:jugadas:ganadas:tiempo
                            System.out.println("Tus estadísticas de juego son:");
                            System.out.println("\tPartidas jugadas: "+command[2]);
                            System.out.println("\tPartidas ganadas: "+command[3]);
                            System.out.println("\tTiempo de juego: "+command[4]);
                            System.out.println("");
                            break;
                        case SHOW_USER_QUERY: // SHOW_GAME_MENU:SHOW_OWN_QUERY:jugadas:ganadas:tiempo:user
                            System.out.println("Las estadísticas de juego de "+command[5]+" son:");
                            System.out.println("\tPartidas jugadas: "+command[2]);
                            System.out.println("\tPartidas ganadas: "+command[3]);
                            System.out.println("\tTiempo de juego: "+command[4]);
                            System.out.println("");
                            break;
                    }
                }
                System.out.println("Elija una opción");
                System.out.println("\t1. Jugar");
                System.out.println("\t2. Jugar online");
                System.out.println("\t3. Consultar tus estadísticas");
                if(isAdmin) {
                    System.out.println("\t4. Consultar estadísticas de cualquier usuario");
                    System.out.println("\t5. Salir");
                } else {
                    System.out.println("\t4. Salir");
                }
                state = OPTIONS;
                break;
            case ASK4USER:
                shouldAsk4Input = true;
                shouldSendInput = true;
                if(command.length>1) {
                    switch(command[1]) {
                        case USER_NOT_EXIST:
                            System.out.println("Error, ese usuario no existe");
                            break;
                        case UNEXPECTED_ERROR:
                            System.out.println(M_UNEXPECTED_ERROR);
                            break;
                        default: 
                            System.out.println(M_DEFAULT_ERROR);
                    }
                }
                System.out.println("Introduzca un nombre de usuario:");
                state = QUERY_WHO;
                break;
            case ASK4LETTER:
                shouldAsk4Input = true;
                shouldSendInput = true;
                if (command.length==4) { // op:solvedWord:errors:mensaje
                    System.out.println(AHORCADO[Integer.parseInt(command[2])]); //imprime ahorcado
                    System.out.println(command[1]); // imprime la palabra mostrada (con asteriscos y letras)
                    switch(command[3]) {
                        case START_GAME:
                            System.out.print("Empieza el juego. ");
                            break;
                        case RIGHT_LETTER:
                            System.out.print("¡Acierto! ");
                            break;
                        case WRONG_LETTER:
                            System.out.print("Error... ");
                            break;
                        case INVALID_MENU_OPTION:
                            System.out.print(M_INVALID_OPTION);
                            break;
                    }
                }
                System.out.println("Introduzca una letra:");
                state = ASKED4LETTER;
                break;
            case ASK4ANOTHER:
                shouldAsk4Input = true;
                shouldSendInput = true;
                if (command.length==4) { // op:solvedWord:errors:mensaje
                    System.out.println(AHORCADO[Integer.parseInt(command[2])]); //imprime ahorcado
                    System.out.println(command[1]); // imprime la palabra mostrada (con asteriscos y letras)
                    switch(command[3]) { // chequea mensaje
                        case VICTORY:
                            System.out.print("¡Enhorabuena, ha ganado! ");
                            break;
                        case LOSE:
                            System.out.print("Oh, ha perdido... ");
                            break;
                    }
                } else if (command.length==2) {
                    switch(command[1]) {
                        case INVALID_MENU_OPTION:
                            System.out.println(M_INVALID_OPTION);
                            break;
                        case UNEXPECTED_ERROR:
                            System.out.println(M_UNEXPECTED_ERROR);
                            break;
                        default:
                            System.out.println(M_DEFAULT_ERROR);
                    }
                }
                System.out.println("¿Desea jugar otra vez? (y/n):");
                state = ANOTHER;
                break;
            case END_CLIENT_LIFE:
                System.out.println("Hasta otra");
                System.exit(0);
                break;
            case WAITING_FOR_PLAYERS:
                System.out.println("Esperando a "+command[1]+" jugadores");
                shouldAsk4Input = false;
                shouldSendInput = false;
                break;
            case ASK4LETTER_ONLINE:
                shouldAsk4Input = true;
                shouldSendInput = true;
                if (command.length==4) { // op:solvedWord:mensaje:error
                    System.out.println(AHORCADO[Integer.parseInt(command[3])]); //imprime ahorcado
                    System.out.println(command[1]); // imprime la palabra mostrada (con asteriscos y letras)
                    switch(command[2]) {
                        case START_GAME:
                            System.out.print("Empieza el juego. ");
                            break;
                        case RIGHT_LETTER:
                            System.out.print("¡Acierto! ");
                            break;
                        case WRONG_LETTER:
                            System.out.print("Error... ");
                            break;
                        case LETTER_TAKEN:
                            System.out.print("Letra ya cogida. ");
                        case INVALID_MENU_OPTION:
                            System.out.print(M_INVALID_OPTION);
                            break;
                    }
                }
                System.out.println("Introduzca una letra:");
                state = ASKED4LETTER_ONLINE;
                break;
            case I_LOST_ONLINE:
                shouldAsk4Input = false;
                shouldSendInput = true;
                state = LOST_ONLINE;
                System.out.println("He perdido...");
                break;
            case I_WON_ONLINE:
                shouldAsk4Input = false;
                shouldSendInput = true;
                state = WON_ONLINE;
                System.out.println("¡He ganado!");
                break;
            case END_ONLINE_GAME:
                shouldAsk4Input = false;
                shouldSendInput = true;
                System.out.println("Terminada partida online");
                state = "default";
                break;
        }
    }
    
    public String processOutput(String input) { // transforma lo introducido por el usuario en el paquete de datos que quiere recibir el servidor
        String output="";
        switch(state) {
            case LOGIN_OPTIONS:
                switch(input) {
                    case "1":
                        output = LOGIN;
                        break;
                    case "2":
                        output = REGISTER;
                        break;
                    case "3":
                        output = PLEASE_KILL_ME;
                        break;
                    default:
                        output = CLIENT_ERROR;
                }
                break;
            case L_NAME:
                output = LOGIN_NAME + SEPARATOR + input;
                break;
            case L_PASS:
                output = LOGIN_PASS + SEPARATOR + input;
                break;
            case R_NAME:
                output = REGISTER_NAME + SEPARATOR + input;
                break;
            case R_PASS:
                output = REGISTER_PASS + SEPARATOR + input;
                break;
            case R_ADMIN:
                output = REGISTER_ADMIN + SEPARATOR + input;
                break;
            case OPTIONS:
                switch(input) {
                    case "1":
                        output = PLAY;
                        break;
                    case "2":
                        output = PLAY_ONLINE;
                        break;
                    case "3":
                        output = QUERY;
                        break;
                    case "4":
                        if(isAdmin) output = QUERY_ADMIN;
                        else output = LOGOUT;
                        break;
                    case "5": 
                        if(isAdmin) output = LOGOUT;
                        else output = CLIENT_ERROR;
                        break;
                    default:
                        output = CLIENT_ERROR;
                }
                break;
            case QUERY_WHO:
                output = SEND_USER + SEPARATOR + input;
                break;
            case ASKED4LETTER:
                if(input.length() == 1) {
                    output = SEND_LETTER + SEPARATOR + input;
                } else output = CLIENT_ERROR;
                break;
            case ANOTHER:
                output = SEND_ANOTHER + SEPARATOR + input;
                break;
            case ASKED4LETTER_ONLINE:
                if(input.length() == 1) {
                    output = SEND_LETTER + SEPARATOR + input;
                } else output = CLIENT_ERROR;
                break;
            case WON_ONLINE:
                output = ("He ganado online y quiero mi menu");
                break;
            case LOST_ONLINE:
                output = ("HE PERDIDOOOOGLMASKASD");
                break;
            case "default":
                output = "";
                break;
        }
        return output;
    }
    
    public boolean shouldAsk4input() {
        return shouldAsk4Input;
    }
    
    public boolean shouldSendInput() {
        return shouldSendInput;
    }
}
