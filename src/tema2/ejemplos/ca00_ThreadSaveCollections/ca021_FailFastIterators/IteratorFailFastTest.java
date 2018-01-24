
package tema2.ejemplos.ca00_ThreadSaveCollections.ca021_FailFastIterators;

import java.util.*;
 
/**
 * This test program illustrates how a collection's iterator fails fast
 * and throw ConcurrentModificationException
 * @author www.codejava.net
 *
 */


/*
2. Fail-Fast Iterators
When working with collections, you also need to understand this concurrency policy
with regard to their iterators: Fail-fast iterators.

Consider the following code snippet that iterates a list of Strings:



List<String> listNames = Arrays.asList("Tom", "Joe", "Bill", "Dave", "John");
 
Iterator<String> iterator = listNames.iterator();
 
while (iterator.hasNext()) {
    String nextName = iterator.next();
    System.out.println(nextName);
}



Here, we use the collection’s iterator to traverse through elements in the collection.

Imagine the listNames is shared between two threads: the current thread that executes 
the iteration, and another thread. Now imagine the second thread is modifying the 
collection (adding or removing elements) while the first thread is still iterating 
over the elements. Can you guess what happens?

The iteration code in the first thread throws ConcurrentModificationException and 
fails immediately, hence the term ‘fail-fast iterators’.

Why does the iterator fail so fast? It’s because iterating a collection while it
is being modified by another thread is very dangerous: the collection may have more,
less or no elements after the iterator has been obtained, so that leads to
unexpected behavior and inconsistent result. And this should be avoided as early 
as possible, thus the iterator must throw an exception to stop the execution of 
the current thread.

The following test program mimics a situation that throws ConcurrentModificationException:

*/


/*Los iteradores no son seguros frente a threads: si en un hilo un iterador está 
  recorriendo una colección y otro hilo la está modificando, el iterador lanzará
  una excepción ConcurrentModificationException
*/






public class IteratorFailFastTest {
 
    private List<Integer> list = new ArrayList<>();
 
    public IteratorFailFastTest() {
        for (int i = 0; i < 10_000; i++) {
            list.add(i);
        }
    }
 
    public void runUpdateThread() {
        Thread thread1 = new Thread(new Runnable() {
 
            public void run() {
                for (int i = 10_000; i < 20_000; i++) {
                    list.add(i);
                }
            }
        });
 
        thread1.start();
    }
 
 
    public void runIteratorThread() {
        Thread thread2 = new Thread(new Runnable() {
 
            public void run() {
                ListIterator<Integer> iterator = list.listIterator();
                while (iterator.hasNext()) {
                    Integer number = iterator.next();
                    System.out.println(number);
                }
            }
        });
 
        thread2.start();
    }
 
    public static void main(String[] args) {
        IteratorFailFastTest tester = new IteratorFailFastTest();
 
        tester.runIteratorThread();
        tester.runUpdateThread();
    }
}