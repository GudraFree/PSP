package tema3.ejemplos.KnockKnockServer;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import java.net.*;
import java.io.*;

/**
 * 
 * <p>The basic steps in the Server Program are:
 * 
 * <ol>
 * <li> Create a new <code>java.net.<b>ServerSocket</b></code> object to listen on a specific port.
 *      When writing a server, choose a port that is not already dedicated to some other service.
 *      <p>ServerSocket is a java.net class that provides a system-independent implementation of the
 *         server side of a client/server socket connection. The constructor for ServerSocket throws an exception
 *         if it can't listen on the specified port (for example, the port is already being used).
 *         In this case, the KnockKnockServer has no choice but to exit.
 *         If the server successfully binds to its port, then the ServerSocket object is successfully created 
 *         and the server continues to the next step.
 *
 *<li> Accept a connection from a client with <code>serverSocker.<b>accept()</b></code> method.
 *    <p>The <code>accept</code> method waits until a client starts up and requests a connection on the host and port of this server.
 *     When a connection is requested and successfully established, the accept method returns a new <code>Socket</code> object
 *     which is bound to the same local port and has its remote address and remote port set to that of the client.
 *     The server can communicate with the client over this new Socket and continue to listen for client connection requests on the original ServerSocket.
 *     This particular version of the program doesn't listen for more client connection requests.
 *
 *<li>Get the socket's input and output stream and opens readers and writers on them.
 *   <p> By invoking <code>clientSocket.<b>getOutputStream()</b></code> and <code>clientSocket.<b>getInputStream()</b></code>.
 *
 *
 *<li>Initiate communication with the client by writing to (or reading from) the socket: <code><b>new ObjectProtocol</b></code>  and <code><b>processInput</b></code>
 *
 *    <p>The code creates a <code>KnockKnockProtocol</code> object-the object that keeps track of the current joke, the current state within the joke, and so on.
 *    <p>Them KnockKnockProtocol's processInput method is called to get the first message that the server sends to the client.
 *     For this example, the first thing that the server says is "Knock! Knock!" Next, the server writes the information to the PrintWriter connected to the client socket, 
 *     thereby sending the message to the client.
 *
 *
 *<li>Communicates with the client by reading from and writing to the socket (the while loop).
 *
 *    <p>As long as the client and server still have something to say to each other, the server reads from and writes to the socket, 
 *    sending messages back and forth between the client and the server.
 *    
 *    <p>The while loop iterates on a read from the input stream. The readLine method waits until the client responds by writing something to its output stream (the server's input stream). 
 *       When the client responds, the server passes the client's response to the KnockKnockProtocol object and asks the KnockKnockProtocol object for a suitable reply. 
 *       The server immediately sends the reply to the client via the output stream connected to the socket, using a call to println. If the server's response generated from the
 *       KnockKnockServer object is "Bye." this indicates that the client doesn't want any more jokes and the loop quits.
 *       
 * <li>Closing all of the input and output streams, the client socket, and the server socket (just in this arrange)
 * </ol>
 */


public class KnockKnockServer {
    public static void main(String[] args) throws IOException {
    	
    	//1-Create a new ServerSocket object to listen on a specific port.
    	int portService=4444;
        
    	ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(portService);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + portService);
            System.exit(1);
        }

        
        //2-Accept a connection from a client
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        
        //3-Gets the socket's input and output stream and opens readers and writers on them
        
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(	new InputStreamReader(clientSocket.getInputStream()));
        
        
        //4-Initiate communication with the client by writing to the socket.
        String inputLine, outputLine;
        KnockKnockProtocol kkp = new KnockKnockProtocol();  //Create the object that implements the protocol

        outputLine = kkp.processInput(null);  //In this example, the protocol dictates that the server must
                                              //send the first message (invoking processInput(null)): "Knock! Knock!"
        out.println(outputLine);

        
        //5-Communicates with the client by reading from and writing to the socket (the while loop).
        
        while ((inputLine = in.readLine()) != null) {
             outputLine = kkp.processInput(inputLine);
             out.println(outputLine);
             if (outputLine.equals("Bye."))
                break;
        }
        
       //6-Closing all of the input and output streams, the client socket, and the server socket 
        
        out.close(); //Close streams
        in.close();
        
        clientSocket.close(); //Close the client socket
        
        serverSocket.close(); //Close the server socket
    }
}
