
package tema2.ejemplos.da_wait_notify_notifyall;


import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.Vector;


 public class Ejemplo_wait_notify_notifyall {
   
     
    public static void main(String[] args){
        Ejemplo_wait_notify_notifyall e=new Ejemplo_wait_notify_notifyall();
        
//        e.new Ejemplo1();
        
          e.new Ejemplo2("Sincronizado");
//          e.new Ejemplo2("No Sincronizado");
          
        //e.new Ejemplo3();
          
    } 
     
    /*http://labojava.blogspot.com.es/2012/10/ejemplo-de-senalizacion-wait-y-notify.html*/
    public class Ejemplo1 {
   
        Ejemplo1 (){
        // Objeto en comun, se encarga del wait y notify
        Saludo s = new Saludo();
       
        /*Instancio los hilos y le paso como parametros:
         *
         * El Nombre del Hilo(en este caso es el nombre del personal)
         * ----El objeto en comun (Saludo)----
         * Un booleano para verificar si es jefe o empleados
         *
        */       
        Personal Empleado1 = new Personal("Pepe", s, false);
        Personal Empleado2 = new Personal("José", s, false);
        Personal Empleado3 = new Personal("Pedro", s, false);
        Personal Jefe1 = new Personal("JEFE", s, true);
       
             //Lanzo los hilos       
            Empleado1.start();           
            Empleado2.start();           
            Empleado3.start();   
            
          
           
             
            Thread hilos[]={Empleado1,Empleado2,Empleado3};
            for (Thread t: hilos)
                 { /*
                    //Error: se bloquean todos los hilos (Empleados y Jefe)
                    try{
                     t.join();
                   }catch(InterruptedException e){System.out.println(e);}
                   */
                   //Para asegurarnos que los hilos hay ejecutado wait.
                   while(t.getState()!=Thread.State.WAITING) ;
                     
                 }
            
            Jefe1.start();

        }


        public class Personal extends Thread{
            String nombre;
            Saludo saludo;
            boolean esJefe;
   
            public Personal(String nombre, Saludo salu, boolean esJefe){
                this.nombre = nombre;
                this.saludo = salu;
                this.esJefe = esJefe;
            }
   
            public void run(){
                System.out.println(nombre + " llegó.");
                try {
                        Thread.sleep(1000);
                         //Verifico si es personal que esta es jefe o no
                        if(esJefe){
                            saludo.saludoJefe(nombre);
                        }else{
                                saludo.saludoEmpleado(nombre);
                        }       
           
                } catch (InterruptedException ex) {
                    Logger.getLogger(Personal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }



        public class Saludo {
   
            public Saludo(){       
            }
   
            /* Si no es jefe, el empleado va a quedar esperando a que llegue el jefe
            Se hace wait de el hilo que esta corriendo y se bloquea, hasta que
            se le avise que ya puede saludar*/
            
            //Si wait() no se utiliza en un bloque synchronizced se produce una
            //java.lang.IllegalMonitorStateException
            
            public synchronized void saludoEmpleado(String nombre){
                try {
                    wait();
                    System.out.println("\n"+nombre.toUpperCase() + "-: Buenos días jefe.");
                } catch (InterruptedException ex) {
                Logger.getLogger(Saludo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
   
            //Si es jefe, saluda y luego avisa a los empleados para que saluden
            // El notifyAll despierta a todos los hilos que esten bloqueados
            
            //Si notifyAll() no se utiliza en un bloque synchronizced se produce una
            //java.lang.IllegalMonitorStateException
            
            public synchronized void saludoJefe(String nombre){
                System.out.println("\n****** "+nombre + "-: Buenos días empleados. ******");
            notifyAll();
            }    
        }   
 

    }
    
    
    /*https://www.programcreek.com/2009/02/notify-and-wait-example/*/
    public class Ejemplo2 {
    
        Ejemplo2 (String sincronizado){
            
         if (sincronizado.equals("Sincronizado"))
              new ThreadA_sincronizado().start();  
         else new ThreadA_NO_sincronizado().start();
        }
        
        class ThreadA_sincronizado extends Thread {
            
            public void run(){
                ThreadB_sincronizado b = new ThreadB_sincronizado();
                b.start();
 
                synchronized(b){
                    try{
                        System.out.println("ThreadA_sincronizado: Waiting for b to complete...");
                        b.wait();
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
 
                    System.out.println("Total is: " + b.total);
                }
            }
        }
 
        class ThreadB_sincronizado extends Thread{
            int total=0;
            @Override
            public void run(){
                synchronized(this){
                    for(int i=0; i<10000 ; i++){
                        total += i;
                    }
                    notify();
                }
            }
        }
        
        class ThreadA_NO_sincronizado extends Thread {
            
            public void run(){
                ThreadB_NO_sincronizado b = new ThreadB_NO_sincronizado();
                b.start();
                
                    System.out.println("ThreadA_NO_sincronizado");
                                   
 
                    System.out.println("Total is: " + b.total);
                }
            }
        
 
        class ThreadB_NO_sincronizado extends Thread{
            int total=0;
            @Override
            public void run(){
               
                    for(int i=0; i<10000 ; i++){
                        total += i;
                    }
                   
                }
            }
        }
         
     
    
   
    public class Ejemplo3 {
    
        Ejemplo3(){
            Producer producer = new Producer();
            producer.start();
            new Consumer(producer).start();
        }
        
        
        class Producer extends Thread {
 
            static final int MAXQUEUE = 5;
            private Vector messages = new Vector();
 
            @Override
            public void run() {
                try {
                    while (true) {
                        putMessage();
                        sleep((int)(Math.random()*3000));
                    }
                } catch (InterruptedException e) {
                }
            }
 
            private synchronized void putMessage() throws InterruptedException {
                while (messages.size() == MAXQUEUE) {
                    wait();
                }
                messages.addElement(new java.util.Date().toString());
                System.out.println("put message");
                notify();
                //Later, when the necessary event happens, 
                //the thread that is running it calls notify() from a block synchronized
                //on the same object.
            }
 
            // Called by Consumer
            public synchronized String getMessage() throws InterruptedException {
                notify();
                while (messages.size() == 0) {
                    wait();
                    //By executing wait() from a synchronized block,
                    //a thread gives up its hold on the lock and goes to sleep.
                }
                String message = (String) messages.firstElement();
                messages.removeElement(message);
                sleep((int)(Math.random()*2000));
                return message;
            }
        }
 
        class Consumer extends Thread {
 
        Producer producer;
 
            Consumer(Producer p) {
                producer = p;
            }
 
            @Override
            public void run() {
                try {
                    while (true) {
                        String message = producer.getMessage();
                        System.out.println("Got message: " + message);
                        sleep(5000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
          }

    }
        
  
    
}
    


