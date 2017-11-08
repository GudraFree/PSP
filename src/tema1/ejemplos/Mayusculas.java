package tema1.ejemplos;


import java.io.IOException;

public class Mayusculas {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		byte[] entrada = new byte[100];

		try {
			//System.err.write((new String("This is a forced error message\n")).getBytes()); //We send bytes to the standard error console
			//System.out.println("I'm 'Mayusculas': Waiting for receiving some bytes through standard 'in'");
			System.in.read(entrada); 
			entrada=(new String(entrada)).toUpperCase().getBytes(); //Convert the bytes to String, get the upper case String, and get bytes again
			//System.out.println("I'm 'Mayusculas': Bytes received, writing these bytes on standard 'out':");
			System.out.write(entrada);
			
			System.err.write(new String("\nSubproceso Mayusculas: Error simulado para probar salida de error").getBytes());
			
		} catch (IOException ioe) {ioe.getMessage();}

	}

}
