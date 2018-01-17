package ea00_ProductorConsumidor.waitNotify;

import ea00_ProductorConsumidor.Buffer;

/**
 *
 * @author miguelb
 */
public class Buffer_wait_notify<E> implements Buffer<E>  {
    

    private E[] data;
    private int nDatos;
    
    public Buffer_wait_notify(int size) {
        data = (E[]) new Object[size];
        nDatos = 0;
    }
    public synchronized void put(E x) throws InterruptedException {
            
        while (nDatos >= data.length)
               wait(); 
        
        data[nDatos++] = x;
        imprime("Productor:  ");
        notifyAll();
      }
    
    public synchronized E get() throws InterruptedException {

        while (nDatos <= 0)
            wait();
        
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

