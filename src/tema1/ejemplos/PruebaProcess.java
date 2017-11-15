package tema1.ejemplos;

import java.io.IOException;
import java.lang.NullPointerException;
import java.io.OutputStream;
//import java.io.OutputStreamWriter;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.File;
import java.io.InputStream;

/**
 *********************************************************
 * pruebaProcess*************************************** A program 'to play' with
 * the java.lang.Runtime Java class
 *
 * @author Miguel Angel Bolivar Perez (mabolivarperez@yahoo.es), IES Fernando
 * Aguilar Quignon - Cádiz - Spain
 * @version 1.0
	 *
 */


public class PruebaProcess {

    static String[] commandLine;
    static String[] enviromentParameters;
    static File workingDirectory;

    static Process unProceso; //'Process' is an abstract class, but we can assign an instance of a subclass of 'Process'
    //(that it's just what the method 'exec' from Runtime class returns

    static OutputStream outputStreamToSubprocessInput;
    static InputStream inputStreamToSubprocessOutput;
    static InputStream inputStreamToSubprocessError;

    static Scanner scanner = new Scanner(System.in);

    private static class Menu {

        final static int MAXOPCION = 14; //It should be the ordinal of the last enum's value
        //Warning: The sequence of the values in the enum 'opcionesMenu' must be THE SAME as the integer values showed in the Menu

        enum opcionesMenu {
            commandLine, enviromentParameters, workingDirectory, exec, destroy, exitValue, waitFor,
            getOutputStream, getInputStream, inputStreamToSubprocessError, escribirEnOutputStream,
            gc, freeMemory, end
        }
        static opcionesMenu[] arrayOpcionesMenu = opcionesMenu.values();

        private static opcionesMenu menu() {

            //Scanner cadena= new Scanner(System.in); 
            int opcion;

            do {
                System.out.println("________________________________________________________________________________________________________________________");
                System.out.println();
                System.out.println("                                     Let's interact with 'Process' objects");
                System.out.println();
                System.out.println("Write the number of the desired option and then press intro: \n");
                System.out.println();
                System.out.println("--------------------Define the characteristic of the system-dependent process that will be created---------------------");
                System.out.println("01-Write the [route\\]  of the program file and the arguments that must be passed to the program");
                System.out.println("02-Set enviroments parameters in this way: Param1=Value1  Param2=Value2 ...");
                System.out.println("03-Set up the working directory");
                System.out.println("-------------------Create a instance of class 'Process' and interact with it--------------------------------");
                System.out.println("04-exec: create an instance of the class 'Process' and a new system-dependent process for the indicated program, enviroment parametres and working directory");
                System.out.println("05-destroy: kill the system-dependent process; the object instance of class 'Process' is NOT destroyed");
                System.out.println("06-exitValue: get the exit code of the just finished system-dependent process");
                System.out.println("07-waitFor: Block this application until the previously created system-dependent process finish");
                System.out.println("----------------------------------Streams to/from the subprocess--------------------------------");
                System.out.println("08-Obtener un 'outPutStream' conectado al input normal del proceso");
                System.out.println("09-Obtener un 'inPutStream' conectado al output normal del proceso");
                System.out.println("10-Obtener un 'inPutStream' conectado a la consola de error del proceso");
                System.out.println("11-Escribir en el 'outPutStream' (se envian bytes al subproceso y luego se reciben bytes del subproceso')");
                System.out.println("-----------------------Ask to the virtual machine--------------------------------------------------");
                System.out.println("12-gc (garbage collector)");
                System.out.println("13-freeMemory");
                System.out.println("14-Quit");
                System.out.println("___________________________________________________________________________________________________________________________");
                System.out.println("");

                opcion = scanner.nextInt();

            } while (opcion < 1 || opcion > MAXOPCION);

            scanner.nextLine();//We consume all the characters left in the actual line (almost, it will be a 'carriage return' because we have read something yet)

            return arrayOpcionesMenu[opcion - 1]; //Warning: The sequence of the values of the enum 'opcionesMenu' must be THE SAME as the integer values showed in the Menu
        }

    }

    public static void main(String[] args) {
        Runtime theRuntimeObject = Runtime.getRuntime(); //We get the only instance of Runtime class for the current Java application
        Menu.opcionesMenu opcion;

        while ((opcion = Menu.menu()) != Menu.opcionesMenu.end) {

            switch (opcion) {

                case commandLine:
                    String line;
                    // do{
                    System.out.println("Write the program file's route that contains the code to built a new process, plus the needed command line arguments");
                    line = scanner.nextLine();
                    // }while (line.equals("")); //We ensure that 'commandLine' is not empty, that is, not any reference to empty String

                    StringTokenizer st = new StringTokenizer(line);  //'line' is never null
                    commandLine = new String[st.countTokens()];     //enabling the do-while:'commandLine' has at least 1 String (the route of the program's file)
                    int i = 0;
                    while (st.hasMoreTokens()) {
                        commandLine[i++] = st.nextToken();
                    }

                    break;

                case enviromentParameters://¡*! Terminar esta opción
                    System.out.println("Write Param1=Value1 Param2=Value2 ...(Write 'end' to finish)");
                    enviromentParameters = new String[1];
                    String[] tmp;
                    int j = 0;
                    while (!(enviromentParameters[j] = scanner.next()).equals("end")) {
                        tmp = new String[enviromentParameters.length + 1];
                        //Copiar las j primeras cadenas de 'enviromentParameters' a 'tmp y luego enviromentParameters=tmp;
                        for(int k=0; k<j; k++) {
                            tmp[k]=enviromentParameters[k];
                        }
                        enviromentParameters = tmp;
                        j++;
                    }

                    // enviromentParameters=scanner.nextLine(); 
                    break;

                case workingDirectory:
                    String directory;
                    System.out.println("Write the path to the working directory ('.' means the current directory)");
                    directory = scanner.next();
                    workingDirectory =new File(directory);
                    break;

                case exec:
                    try {                                           //enviromentParameters
                        unProceso = theRuntimeObject.exec(commandLine[0]);
                        //'exec' returns an instance of a subclass of 'Process' (Process is an abstract class) that
                        //allows to control the process an obtain information about it

                        System.out.printf("Created a 'Process' object and launching the system-dependent command %s", commandLine[0]);

                    } catch (IOException ioe) {
                        System.err.println(ioe.getMessage());
                    } //The file of 'commandLine' or the directory of 'workingDirectory doesn't exists
                    catch (NullPointerException npe) {
                        System.err.println("It hasn't been set up a command line");
                    } //'commandLine' is null
                    catch (IndexOutOfBoundsException ioobe) {
                        System.err.println("It has been set up an empty command line");
                    } //'commandLine' is an empty array (has length 0}    
                    //In the version of 'exec' in which the first argument is a String (the command), it could be throw the exception: 
                    //catch(IllegalArgumentException iae){System.err.println("The name of the file is empty");}  //if 'command' is empty ("") 
                    break;

                case destroy:
                    unProceso.destroy();
                    System.out.println("The system-dependent process has just been destroyed (not the object instance of class 'Process')");
                    break;

                case exitValue:
                    System.out.printf("The exit value of the ended process is %d\n", unProceso.exitValue());
                    break;

                case waitFor:
                    System.out.println("The process of 'pruebaProcess' application is blocked waiting for the termination of de system-dependent process");
                    try {

                        System.out.printf("Uuuaaaammmmm!. The process of the 'pruebaProcess' application has just woke up, because the"
                                + " system-dependent process has terminated returning the exit value %d\n",
                                unProceso.waitFor());
                    } catch (InterruptedException ie) {
                        ie.getMessage();
                        System.err.println("The 'pruebaProcess' thread has been interrupted by some other thread ");
                    }
                    break;

                case getOutputStream:
                    outputStreamToSubprocessInput = unProceso.getOutputStream();
                    System.out.println("We get an outputStream conected to the standard subprocess's input");
                    break;

                case getInputStream:
                    inputStreamToSubprocessOutput = unProceso.getInputStream();
                    System.out.println("We get an inputStream conected to the standard subprocess's output");
                    break;

                case inputStreamToSubprocessError:
                    inputStreamToSubprocessOutput = unProceso.getErrorStream();
                    System.out.println("We get an errorStream conected to the standard subprocess's error console");
                    break;

                case escribirEnOutputStream:/*{ XXX EscribirEnOutputStream
                                                
                                                  OutputStreamWriter outputStreamWriterToSubprocessInput= new OutputStreamWriter(outputStreamToSubprocessInput);	
                                                 
                                                 
                                                 System.out.println("Write the characters that will be redirect to the subprocess");
											     line=scanner.nextLine();
											     try{
											    	 outputStreamWriterToSubprocessInput.write(line,0,line.length());
											     }catch(IOException e){e.getMessage();}
					                            }
					                            
                     */

                    System.out.println("Write the characters that will be redirect to the subprocess (as a group of bytes)");

                    try {
                        byte[] salida = new byte[100];
                        System.in.read(salida);                      //get the bytes from standard 'in'
                        outputStreamToSubprocessInput.write(salida); //send the bytes to subprocess (está en un buffer)
                        outputStreamToSubprocessInput.flush();       //Send now the bytes buffered in this outputStream  (vacía el buffer y envía los datos según necesidad)
                        System.out.println("I'm 'PruebaProcess': I've just send the bytes");

                        byte[] entrada = new byte[100];
                        inputStreamToSubprocessOutput.read(entrada);  //get the bytes from subprocess
                        System.out.println("I'm 'PruebaProcess': I've just received the bytes");
                        System.out.write(entrada);                    //write the bytes to the standard 'out'

                        //By default, the error console is the input console,
                        //so 'outputStreamToSubprocessInput' is the same as 'inputStreamToSubprocessError'
                        //     if(inputStreamToSubprocessError.available()!=0){  //if there are bytes to read
                        //  inputStreamToSubprocessError.read(entrada);  //get the bytes from the subprocess's error console
                        //System.out.println("I'm 'PruebaProcess': I've just received the bytes from subprocess's error console");
                        //     System.err.write(entrada);
                        //     }				                         
                    } catch (IOException ioe) {
                        ioe.getMessage();
                    } catch (NullPointerException npe) {
                        System.err.println("outputStream or inputStream to subprocess hasn't been created yet");
                    }

                    break;

                case gc:
                    theRuntimeObject.gc();
                    System.out.println("A request to invoque the garbage collector has been made");
                    break;

                case freeMemory:
                    System.out.printf("The current amount of free memory is: %d\n", theRuntimeObject.freeMemory());
                    break;
            }
        }

        System.out.println("The program will end normally");
    }

}
