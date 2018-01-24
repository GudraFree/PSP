
package tema2.ejemplos.ca00_ThreadSaveCollections.ca04_ConcurrentCollections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;



/*
4. Concurrent Collections
A drawback of synchronized collections is that their synchronization mechanism uses 
the collection object itself as the lock object. That means when a thread is iterating 
over elements in a collection, all other collection’s methods block, causing other
threads having to wait. Other threads cannot perform other operations on the collection
until the first thread release the lock. This causes overhead and reduces performance.

That’s why Java 5 (and beyond) introduces concurrent collections that provide much 
better performance than synchronized wrappers.
The concurrent collection classes are organized in the java.util.concurrent package.
They are categorized into 3 groups based on their thread safety mechanisms.

 

* The first group is copy-on-write collections: this kind of thread-safe collections 
stores values in an immutable array; any change to the value of the collection results
in a new array being created to reflect the new values. 
These collections are designed for situations in which read operations greatly 
predominate write operations. There are two implementations of this kind:
CopyOnWriteArrayList and CopyOnWriteArraySet.

Note that copy-on-write collections have snapshot iterators which do not 
throw ConcurrentModificationException. Since these collections are backed by 
immutable arrays, an iterator can read the values in one of these arrays 
(but never modify them) without danger of them being changed by another thread.

 

* The second group is Compare-And-Swap or CAS collections: the collections in this 
group implement thread safety using an algorithm called Compare-And-Swap (CAS)
which can be understood like this:

To perform calculation and update value of a variable, it makes a local copy of
the variable and performs the calculation without getting exclusive access.
When it is ready to update the variable, it compares the variable’s value with its value at the start and, if they are the same, updates it with the new value.

If they are not the same, the variable must have been changed by another thread.
In this situation, the CAS thread can try the whole computation again using the
new value, or give up, or continue. Collections using CAS include 
ConcurrentLinkedQueue and ConcurrentSkipListMap.

Note that the CAS collections have weakly consistent iterators, which reflect 
some but not necessarily all of the changes that have been made to their backing
collection since they were created. Weakly consistent iterators do not 
throw ConcurrentModificationException.

 

* The third group is concurrent collections using a special lock object 
(java.util.concurrent.lock.Lock): This mechanism is more flexible than classical
synchronization. This can be understood as following:

A lock has the same basic behavior as classical synchronization but a thread can
also acquire it under special conditions: only if the lock is not currently held,
or with a timeout, or if the thread is not interrupted.

Unlike synchronization code, in which an object lock is held while a code block
or a method is executed, a Lock is held until its unlock() method is called. Some implementations make use of this mechanism to divide the collection into parts that can be separately locked, giving improved concurrency. For example, LinkedBlockingQueue has separate locks for the head and the tail ends of the queue, so that elements can be added and removed in parallel.

Other collections using this lock include ConcurrentHashMap and most of the
implementations of BlockingQueue.

Collections in this group also have weakly consistent iterators and do not
throw ConcurrentModificationException.

Let’s summarize the most important points we’ve learned so far today:

    Most collections in the java.util package are not thread-safe in order to provide
maximum performance in single-threaded applications. Vector and Hashtable are the
only two legacy collections that are thread-safe.  

    Synchronized collections can be created by using the Collection utility class’ 
factory methods synchronizedXXX(collection). They are thread-safe but poor at performance.  

    Concurrent collections are improved for thread safety and performance with 
different synchronization mechanisms: copy-on-write, compare-and-swap and special
lock. They are organized in java.util.concurrent package.



*/




import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConcurrentCollections {
   
  
   
    private final BlockingQueue linquedQueue= new LinkedBlockingQueue<Integer>();
 
    public ConcurrentCollections() {
        for (int i = 0; i < 10_000; i++) {
            linquedQueue.add(i);
        }
    }
 
    public void runUpdateThread() {
        Thread thread1 = new Thread(new Runnable() {
 
            public void run() {
                for (int i = 10_000; i < 20_000; i++) {
                    linquedQueue.add(i);
                }
            }
        });
 
        thread1.start();
    }
 
 
    public void runIteratorThread() {
        Thread thread2 = new Thread(new Runnable() {
 
            public void run() {
                Iterator<Integer> iterator = linquedQueue.iterator();
                
                synchronized(linquedQueue){
                    while (iterator.hasNext()) {
                        Integer number = iterator.next();
                        System.out.println(number);
                    }
                }
            }
        });
 
        thread2.start();
    }
 
    public static void main(String[] args) {
        ConcurrentCollections tester = new ConcurrentCollections();
 
        tester.runIteratorThread();
        tester.runUpdateThread();
    }
 
}