/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ea00_ProductorConsumidor.incorrecto;

import ea00_ProductorConsumidor.Buffer;

/**
 *
 * @author miguelb
 */
public class Buffer_incorrecto<E> implements Buffer<E>  {
    

    private E[] data;
    private int nDatos;
    
    public Buffer_incorrecto(int size) {
        data = (E[]) new Object[size];
        nDatos = 0;
    }
    public synchronized void put(E x) throws InterruptedException {
              
        data[nDatos++] = x;
        imprime("Productor:  ");
        notifyAll();
      }
    
    public synchronized E get() throws InterruptedException {

        nDatos--;
        
        E x = data[0];
        System.arraycopy(data, 1, data, 0, nDatos);
        data[nDatos] = null;
        imprime("Consumidor: ");
        notifyAll();
        return x;
    }
    
    private void imprime(String who){
        
        System.out.print(who +"[");
        for(E d: data) System.out.print(d +" ");
        System.out.println("]");
          
    }
    
}
