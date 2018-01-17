
package ea00_ProductorConsumidor;

import java.util.Random;

public class Consumidor extends Thread {
    private final Random random = new Random();
    private final Buffer<Character> buffer;
    
    public Consumidor(Buffer<Character> buffer) {
        this.buffer = buffer;
    }
        
    public void run() {
        while (true) {
            try {
                 Character msg = buffer.get();
                 Thread.sleep(random.nextInt(2) * 1000);
            } catch (InterruptedException ignore) {
            }
        }
    }
}

