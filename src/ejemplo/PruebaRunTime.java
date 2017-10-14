package ejemplo;

import java.util.Scanner;
import java.io.IOException;

/**
 *********************************************************
 * pruebaRunTime*************************************** A program 'to play' with
 * the java.lang.Runtime Java class
 *
 * @author Miguel Angel Bolivar Perez (mabolivarperez@yahoo.es), IES Fernando
 * Aguilar Quignon - C�diz - Spain
 * @version 1.0
 *
 */
public class PruebaRunTime {

    private static class Menu {

        final static int MAXOPCION = 13; //It should be the ordinal of the last enum's value
        //Warning: The sequence of the values in the enum 'opcionesMenu' must be THE SAME as the integer values showed in the Menu

        enum opcionesMenu {
            availableProcessors, freeMemory, totalMemory, maxMemory, gc, exec, addShutdownHook, try_re_register_a_ShutdownHook,
            shutting_down_and_try_re_register_a_shutdownHook, removeShutdownHook, exit, halt, end
        }
        static opcionesMenu[] arrayOpcionesMenu = opcionesMenu.values();

        private static opcionesMenu menu() {

            Scanner entrada = new Scanner(System.in); //<-----Oh! bad, bad boy

            int opcion;

            do {
                System.out.println("___________________________________________________________________________________");
                System.out.println();
                System.out.println("Let's interact with the (single) object 'Runtime' of the Java application that allows the application to interface with the environment in which the application is running ");
                System.out.println();
                System.out.println("Write the number of the desired option and then press intro: \n");
                System.out.println("-----------------------------------Processes's Resources: Processors and Memory---------------------");
                System.out.println("01-availableProcessors");
                System.out.println("02-freeMemory");
                System.out.println("03-totalMemory");
                System.out.println("04-maxMemory");
                System.out.println("05-gc (garbage collector)");
                System.out.println("------------------------------------------Playing with Processes----------------------------------");
                System.out.println("06-exec");
                System.out.println("---------------------------------------------Shutdown Hooks---------------------------------------");
                System.out.println("07-addShutdownHook. Register a new virtual-machine shutdown hook");
                System.out.println("08-try re-registering the last registered shutdownHook (an 'IllegalArgumentException' will be thrown)");
                System.out.println("(*under construction)09-shutting down de virtual machine (exit) and try registering a new shutdownHook (an 'IllegaStateException' will be thrown)");
                System.out.println("10-removeShutdownHook. De-register the last registered shutdown hook, if any");
                System.out.println("-----------------------------------------Finishing: exit, halt, end-------------------------------");
                System.out.println("11-exit. The program is just going to initiate its shutdown sequence, then it will return 0 status code");
                System.out.println("\tNote: typing ^C, user logoff o system shutdown will cause shutdown of the application's Java virtual machine and therefore the exits of the program");
                System.out.println("12-halt. The program ends abnormally, returning 1 status code");
                System.out.println("13-end.  Let the program ends normally \n");
                System.out.println("___________________________________________________________________________________");
                System.out.println("");

                opcion = entrada.nextInt();

            } while (opcion < 1 || opcion > MAXOPCION);

            return arrayOpcionesMenu[opcion - 1]; //Warning: The sequence of the values of the enum 'opcionesMenu' must be THE SAME as the integer values showed in the Menu
        }

    }

    //***************************************
    static String programa;
    static String parametrosEntorno;
    static String directorioTrabajo;

    private static class PruebaShutdownHook extends Thread {

        public static int id = 0;
        public static PruebaShutdownHook ultimoShutdownHookRegistrado;

        public int idShutdownHookRegistrado;
        private String message;

        public PruebaShutdownHook() {
            idShutdownHookRegistrado = ++id;
            message = "The shutdownHook # " + idShutdownHookRegistrado + " makes a thread-safe short-running computation,avoiding deadlocks with no user interaction and carefully relaying in services";
            ultimoShutdownHookRegistrado = this;
        }

        public void run() {
            System.out.println(message);
        }

    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        Runtime run = Runtime.getRuntime();  //We get the only instance of Runtime class for the current Java application
        Menu.opcionesMenu opcion;

        while ((opcion = Menu.menu()) != Menu.opcionesMenu.end) {

            switch (opcion) {

                case availableProcessors:
                    System.out.printf("There are available %d processors\n",run.availableProcessors());
                    break;

                case freeMemory:
                    System.out.printf("The amount of free memory in the Java Virtual Machine available for FUTURE allocated objects are %d bytes \n",
												           run.freeMemory());
                    break;

                case totalMemory:
                    System.out.printf("The total amount of memory currently available in the Java Virtual Machine available for CURRENT and FUTURE objects are %d bytes\n",
												           run.totalMemory());
                    break;

                case maxMemory:
                    System.out.printf("The max amount of memory that the Java virtual machine will attempt to use are %d bytes\n",
														  run.maxMemory());
                    break;

                case gc:
                    System.out.println("We request (it's not a subrrutine call) that the garbage collector does its work");
                    run.gc();  //It's equivalent to System.gc();
                    break;

                case exec: {
                    Scanner cadena = new Scanner(System.in);
                    System.out.println("Introduce (la ruta del fichero de) el programa a ejecutar, con los argumentos de l�nea de comandos necesarios");
                    programa = cadena.next();

                    try {
                        run.exec(programa);
                    } catch (IOException ioe) {
                        System.out.println("No pudo ser ejecutado el programa por error de E/S");
                    }
                }
                break;

                case addShutdownHook:
                    run.addShutdownHook(new PruebaShutdownHook());
                    System.out.println("Registered shutdown Hook #" + PruebaShutdownHook.ultimoShutdownHookRegistrado.id);
                    break;

                case try_re_register_a_ShutdownHook:
                    run.addShutdownHook(PruebaShutdownHook.ultimoShutdownHookRegistrado);
                    System.out.println("Registered shutdown Hook #" + PruebaShutdownHook.ultimoShutdownHookRegistrado.id);
                    break;

                case shutting_down_and_try_re_register_a_shutdownHook:
                    System.out.println("Falta por implementar: lanzar un thread que cree y registre nuevos shutdownHook");
                    break;

                case removeShutdownHook:
                    run.removeShutdownHook(PruebaShutdownHook.ultimoShutdownHookRegistrado);
                    System.out.println("De-registered shutdown Hook #" + PruebaShutdownHook.ultimoShutdownHookRegistrado.id);
                    break;

                case exit:
                    System.out.println("The program initiates its shutdown sequence and then halts, so we return a 0 status code");
                    run.exit(0); //the same as the more convenient System.exit()
                    //System.out.println("This message is never prompted, because we'll never return from the call to exit()");
                    break;

                case halt:
                    System.out.println("The program ends abnormally, without initiating its shutdown sequence, so we return a !=0 status code");
                    run.halt(0);
                    //System.out.println("This message is never prompted, because we'll never return from the call to halt()");
                    break;
            }
        }

        System.out.println("The program will end normally");
    }

}



