package tema1.ejemplos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class EjemploProcessBuilder {

  public static void main(String args[]){
	  
	//Windows: String command="dir";
    //Windows: String comand= "dir \";
    //Linux (comando 'ls' SIN argumentos):   String command= "ls";
    //Linux (comando 'ls' CON argumentos):
	  String command="ls /";  
  
     System.out.println(executeCommand(command,true)); 
  }
	
 
  public static String executeCommand(String command, boolean waitForResponse) {
	  
    //Windows: String commandLine="cmd /c " + command;
    //Windows: String [] commandLine= {"cmd",  "/c", command}
	//Windows (ejecutar un comando java): "cmd /c java -cp bin " + command
    //Linux (comando 'ls' SIN argumentos):   String commandLine= "bash -c " + command;
    //Linux (comando 'ls' CON argumentos):
	  String [] commandLine={"bash",  "-c", command};
	//Linux (ejecutar un comando java): "bash -c java -cp bin " + command
	  
	  
	String response = "";
	 
	ProcessBuilder pb = new ProcessBuilder(commandLine);
	
	pb.redirectErrorStream(true);
	 
	System.out.println("Linux command: " + command);
	 
	try {
	     Process shell = pb.start();
	 
	     if (waitForResponse) {
	 
	      // To capture output from the shell
	      InputStream shellIn = shell.getInputStream();
	 
	      // Wait for the shell to finish and get the return code
	      int shellExitStatus = shell.waitFor();
	      
	      System.out.println("Exit status" + shellExitStatus);
	 
	      response = convertStreamToStr(shellIn);
	 
	      shellIn.close();
	     }
	}
	 
	catch (IOException e) {
		System.out.println("Error occured while executing Linux command. Error Description: "+ e.getMessage());
	}
 
	catch (InterruptedException e) {
		System.out.println("Error occured while executing Linux command. Error Description: "+ e.getMessage());
	}

	return response;
  }
	 
	/*
	* To convert the InputStream to String we use the Reader.read(char[]
	* buffer) method. We iterate until the Reader return -1 which means
	* there's no more data to read. We use the StringWriter class to
	* produce the string.
	*/
	 
public static String convertStreamToStr(InputStream is) throws IOException {
	 
  if (is != null) {
     Writer writer = new StringWriter();
	 
     char[] buffer = new char[1024];
     try {
	  Reader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
	  int n;
	  while ((n = reader.read(buffer)) != -1) {
		writer.write(buffer, 0, n);
    	  }
     }finally {
	is.close();
   }
   return writer.toString();
  }
  else {
	return "";
	}
 }
	 
}