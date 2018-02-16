/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema3.ejercicio03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author Perig
 */
public class AhorcadoClient {
    
    
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String host = "localhost";
        int port = 4444;
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Error, host no encontrado");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error de E/S");
            System.exit(1);
        }
        
        ClientProtocol cp = new ClientProtocol();
        String l = "";
        while((l=in.readLine())!=null) {
            cp.processInput(l);
            String input = sc.nextLine();
            out.println(cp.processOutput(input));
        }
        
        in.close();
        out.close();
        
        socket.close();
    }
    
}
