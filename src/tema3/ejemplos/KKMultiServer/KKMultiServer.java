package KKMultiServer;

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
 * <p>To keep the KnockKnockServer example simple, we designed it to listen for and handle a single connection request.
 *  However, multiple client requests can come into the same port and, consequently, into the same ServerSocket.
 *  Client connection requests are queued at the port, so the server should be designed to accept the connections sequentially.
 *  The former implementation of KnockKnockServer is designed to attend just one connection and thereafter close streams and
 *  sockets and finally terminate, so client connection requests queued at the port exit throwing the java.net.SocketException exception.
 *  
 *  <p> Try this: execute KnockKnockServer and three KnockKnockClient. One of these clients will connect to the server and the other ones
 *   will be queued at the port; once the first client had finished, the queued client will throw the SocketException
 *   
 *    <pre>
 *      .../bin$ java KnockKnockServer.KnockKnockServer
 *      .../bin$ java KnockKnockClient.KnockKnockClient (3 times)
 *    </pre>
 *  
 *  
 *  
 * <p> However, the server can service them simultaneously through the use of threads - one thread per each client connection.
 *     The basic flow of logic in such a server is this:
 *     
 *    <pre>
 *     while (true) {
 *         accept a connection;
 *         create a thread to deal with the client;
 *     }
 *     </pre>
 *     
 *  <p>The thread reads from and writes to the client connection as necessary.
 *  
 *  
 *  <p>We can modify the KnockKnockServer so that it can service multiple clients at the same time. 
 *  Two classes compose our solution: KKMultiServer and KKMultiServerThread. 
 *  
 *  <ul>
 *  <li><b>KKMultiServer</b> loops forever, listening for client connection requests on a ServerSocket. 
 *         When a request comes in, KKMultiServer accepts the connection, creates a new KKMultiServerThread object to process it, 
 *         hands it the socket returned from accept, and starts the thread. Then the server goes back to listening for connection requests.
 *         
 *   <li>The <b>KKMultiServerThread</b> object communicates to the client by reading from and writing to the socket. 
 *       Run the new Knock Knock server and then run several clients in succession.
 *  
 *  </ul>
 *
 */

public class KKMultiServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }

        while (listening)
	    new KKMultiServerThread(serverSocket.accept()).start();

        serverSocket.close();
    }
}
