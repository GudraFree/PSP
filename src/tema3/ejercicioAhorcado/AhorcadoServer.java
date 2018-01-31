/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema3.ejercicioAhorcado;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Perig
 */
public class AhorcadoServer {
    public static void main(String[] args) throws IOException {
        int port = 4444;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch(IOException e) {
            System.err.println("Error al conectarse al puerto");
            System.exit(1);
        }
        
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Error al aceptar el socket cliente");
            System.exit(1);
        }
        
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        AhorcadoProtocol ap = new AhorcadoProtocol();
        
        String output = ap.processInput(null);
        out.println(output);
        String input;
        
        while((input=in.readLine())!= null) {
            output = ap.processInput(input);
            out.println(output);
            if(input.equals("Hasta otra")) break;
        }
        
        // TODO: cerrar flujos, comprobar
    }
    
    
    
}
