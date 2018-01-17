
package ea00_ProductorConsumidor;

import java.util.Random;

public class Productor extends Thread {
    private final Random random = new Random();
    private final Buffer<Character> buffer;
    private final char c;

    public Productor(Buffer<Character> buffer, char c) {
        this.buffer = buffer;
        this.c = c;
    }
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                 buffer.put((char) (c + i));
                 Thread.sleep(random.nextInt(5) * 1000);
            } catch (InterruptedException ignore) {
            }
    }
    }
}

