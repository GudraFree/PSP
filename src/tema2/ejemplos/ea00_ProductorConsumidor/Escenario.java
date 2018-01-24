/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tema2.ejemplos.ea00_ProductorConsumidor;

import tema2.ejemplos.ea00_ProductorConsumidor.waitNotify.Buffer_wait_notify;
import tema2.ejemplos.ea00_ProductorConsumidor.incorrecto.Buffer_incorrecto;

public class Escenario {
    public static void main(String[] args) {
          
        
//        Buffer<Character> buffer = new Buffer_incorrecto<Character>(3);                                                                        
        Buffer<Character> buffer = new Buffer_wait_notify<Character>(3); //1
        
              
        
        
        Thread productor1 = new Productor(buffer, '0');
       Thread productor2 = new Productor(buffer, 'a');
        Thread consumidor1 = new Consumidor(buffer);
        //Thread consumidor2 = new Consumidor(buffer);
        
        productor1.start();
        productor2.start();
        consumidor1.start();
        //consumidor2.start();
}
}
