package tema3.ejemplos;
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

import java.io.*;
import java.net.*;

/**
 * 
 * 
 *<p>The basic steps in a client program are:</p>
 * 
 * <ol>
 * <li>1-Open a socket.
 * <li>2-Open an input stream and output stream to the socket.
 * <li>3-Read from and write to the stream according to the server's protocol.
 * <li>4-Close the streams.
 * <li>5-Close the socket.
 * </ol>
 *
 *
 *<p> The <code>EchoClient</code> client connects to the Echo Server (a well-known service that clients can 
 *rendezvous with on port 7), it reads input from the user on the standard input stream, and then forwards 
 *that text to the Echo server by writing the text to the socket. 
 *The server echoes the input back through the socket to the client. 
 *The client program reads and displays the data passed back to it from the server
 */
public class EchoClient {
	

    public static void main(String[] args) throws IOException {
                              //localhost
    	String hostServerName="tanaris"; //The name -or IP address- of the machine in the local network
    	                                 //that hosts the Server program
    	int servicePort=7; //The port number in the server machine to which the Server program is binded
    	                   //Echo server listen on port 7
    	
        Socket echoSocket = null; //Socket object by means this EchoClient program connects to the Server program
        PrintWriter out = null;   //Stream where EchoClient writes on to send text to Server program
        BufferedReader in = null; //Stream where EchoClient reads from to receive text from Server program

        try {
 //1-Open a socket: a Socket object is created (the constructor requires the name of Server machine
 //                 and Service's port) to get a connection to the server
            echoSocket = new Socket(hostServerName, servicePort); 
            
 //2-Open an input stream and output stream to the socket.
 //In this example readers and writers are created so that it can write Unicode characters over the socket.
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                                        echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostServerName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostServerName);
            System.exit(1);
        }

 //3-Read from and write to the stream according to the server's protocol.
 
	BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
	String userInput;

	while ((userInput = stdIn.readLine()) != null) {  //reads input from the user until the user types an end-of-input character
	    out.println(userInput);                       //sends it to the Echo server
	    System.out.println("echo: " + in.readLine()); //gets a response from the server, and displays it
	}

	
//Well-behaved program always cleans up after itself:
//The order here is important. You should close any streams connected to a socket BEFORE you close the socket itself.	

//4-Close the streams. 
	out.close();
	in.close();
	stdIn.close();
	
//5-Close the socket.
	echoSocket.close();
    }
}

