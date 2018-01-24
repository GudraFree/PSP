
package tema2.ejemplos.ca00_ThreadSaveCollections.ca03_SynchronizedWappers;


/*
3. Synchronized Wrappers

We should not use non-thread-safe collections in concurrent context, as doing so 
may lead to undesired behaviors and inconsistent results. 
We can use synchronized blocks manually to safeguard our code, 
however it’s always wise to use thread-safe collections instead of writing synchronization
code manually.

You already know that, the Java Collections Framework provides factory methods
for creating thread-safe collections. These methods are in the following form:


Collections.synchronizedXXX(collection)

These factory methods wrap the specified collection and return a thread-safe implementation. 
Here, XX can be Collection, List, Map, Set, SortedMap and SortedSet implementations.

For example, the following code creates a thread-safe list collection:

	
List<String> safeList = Collections.synchronizedList(new ArrayList<>());


If we have an existing non-thread-safe collection, we can wrap it in a thread-safe 
collection like this:


Map<Integer, String> unsafeMap = new HashMap<>();
Map<Integer, String> safeMap = Collections.synchronizedMap(unsafeMap);


You see, these factory methods wrap the specified collection in an implementation 
having same interfaces as the wrapped collection but all the methods are synchronized
to provide thread-safety, hence the term ‘synchronized wrappers’. 
Actually, the synchronized collection delegate all work to the wrapped collection.

--------------

NOTE:

When using the iterator of a synchronized collection, we should use synchronized block 
to safeguard the iteration code because the iterator itself is not thread-safe.
Consider the following code:


	
List<String> safeList = Collections.synchronizedList(new ArrayList<>());
 
// adds some elements to the list
 
Iterator<String> iterator = safeList.iterator();
 
while (iterator.hasNext()) {
    String next = iterator.next();
    System.out.println(next);
}



Although the safeList is thread-safe, its iterator is not, so we should manually add synchronized block like this:


synchronized (safeList) {
    while (iterator.hasNext()) {
        String next = iterator.next();
        System.out.println(next);
    }
}

Also note that the iterators of the synchronized collections are fail-fast.

Although synchronized wrappers can be safely used in multi-threaded applications,
they have drawbacks as explained in the next section.


*/



/*Java Collections Framework tiene métodos de factoria para crear colecciones
  seguras frente a threads. Tienen la forma:

Collections.synchronizedXXX(collection)


NOTA: aunque una colección sea segura frente a threat, un iterador no lo es,
así que hay que protegerlo con un bloque synchronized



*/

import java.util.*;

public class SynchronizedWappers {
   
   /*
     private ArrayList<String> list= new ArrayList<String>(); 
     private List<String> safeList = Collections.synchronizedList(list); 
   */ 
   private List<Integer> safeList = Collections.synchronizedList(new ArrayList<Integer>());
    
 
    public SynchronizedWappers() {
        for (int i = 0; i < 10_000; i++) {
            safeList.add(i);
        }
    }
 
    public void runUpdateThread() {
        Thread thread1 = new Thread(new Runnable() {
 
            public void run() {
                for (int i = 10_000; i < 20_000; i++) {
                    safeList.add(i);
                }
            }
        });
 
        thread1.start();
    }
 
 
    public void runIteratorThread() {
        Thread thread2 = new Thread(new Runnable() {
 
            public void run() {
                ListIterator<Integer> iterator = safeList.listIterator();
                
                synchronized(safeList){
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
        SynchronizedWappers tester = new SynchronizedWappers();
 
        tester.runIteratorThread();
        tester.runUpdateThread();
    }
 
}