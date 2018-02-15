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
                System.out.println("Bienvenido al cliente del Ahorcado. Elija una opción:");
                System.out.println("\t1. Loguearse");
                System.out.println("\t2. Registrarse");
                System.out.println("\t3. Salir");
                break;
        }
    }
    
    public String processOutput(String input) { // transforma lo introducido por el usuario en el paquete de datos que quiere recibir el servidor
        String output="";
        switch(state) {
            case WAITING:
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
                }
                break;
        }
        return output;
    }
}
