/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema3.ejercicioKnock;

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
public class KnockKnockClient {
    public static void main(String[] args) throws IOException {
        String host="localhost";
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
        
        Scanner sc = new Scanner(System.in);
        String l = "";
        while((l=in.readLine())!=null) {
            System.out.println(l);
            if(!l.equals("Bye.")) {
                String output = sc.nextLine();
                out.println(output);
            }
        }
    }
    
}
