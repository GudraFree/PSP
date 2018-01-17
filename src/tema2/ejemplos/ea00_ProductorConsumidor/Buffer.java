

package ea00_ProductorConsumidor;

public interface Buffer<E> {
    void put(E x)    throws InterruptedException;
    E get()          throws InterruptedException;
}


