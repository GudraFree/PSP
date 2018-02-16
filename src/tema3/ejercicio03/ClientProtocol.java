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
public class ClientProtocol {
    private String state;

    public ClientProtocol() {
        state = WAITING;
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
                            System.out.println("Error, opción inválida");
                            break;
                        case UNEXPECTED_ERROR:
                            System.out.println("Error no esperado");
                            break;
                        default:
                            System.out.println("Error por defecto. Este error no debería existir");
                    }
                }
                System.out.println("Bienvenido al cliente del Ahorcado. Elija una opción:");
                System.out.println("\t1. Loguearse");
                System.out.println("\t2. Registrarse");
                System.out.println("\t3. Salir");
                state = LOGIN_OPTIONS;
                break;
            case L_ASK4NAME:
                // posiblemente escribir errores
                System.out.println("Introduzca nombre:");
                state = L_NAME; 
        }
    }
    
    public String processOutput(String input) { // transforma lo introducido por el usuario en el paquete de datos que quiere recibir el servidor
        String output="";
        switch(state) {
            case LOGIN_OPTIONS:
                switch(input) {
                    case "1":
                        output += LOGIN;
                        break;
                    case "2":
                        output += REGISTER;
                        break;
                    case "3":
                        System.out.println("Hasta la próxima");
                        System.exit(0);
                    default:
                        output += CLIENT_ERROR;
                }
                break;
            case L_NAME:
                output += LOGIN_NAME + SEPARATOR + input;
                break;
        }
        return output;
    }
}
