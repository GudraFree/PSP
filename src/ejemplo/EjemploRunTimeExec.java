package ejemploRunTimeExec;

import java.io.*;

public class EjemploRunTimeExec {

    public static void main(String args[]) {

        try {
            String line;

            String commandLine = "cmd /c dir";
            //String [] commandLine= {"cmd",  "/c", "dir \\"};
            //Linux (comando 'ls' SIN argumentos):   String commandLine= "bash -c  ls";
            //Linux (comando 'ls' CON argumentos):    String [] commandLine={"bash",  "-c", "ls /"};

            Process p = Runtime.getRuntime().exec(commandLine);

            BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));

            BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            while ((line = bri.readLine()) != null) {
                System.out.println(line);
            }
            bri.close();

            while ((line = bre.readLine()) != null) {
                System.out.println(line);
            }
            bre.close();

            p.waitFor();

            System.out.println("Done.");
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
