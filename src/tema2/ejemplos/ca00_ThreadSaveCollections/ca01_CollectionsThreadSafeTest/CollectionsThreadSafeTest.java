package tema2.ejemplos.ca00_ThreadSaveCollections.ca01_CollectionsThreadSafeTest;


import java.util.*;
 
/**
 * This test program compares performance of Vector versus ArrayList
 * @author www.codejava.net
 *
 */



/*
1. Why are almost collection classes not thread-safe?
Do you notice that all the basic collection classes - ArrayList, LinkedList, HashMap, HashSet, TreeMap, TreeSet, etc - all are not synchronized? In fact, all collection classes (except Vector and Hashtable) in the java.util package are not thread-safe. The only two legacy collections are thread-safe: Vector and Hashtable. WHY?

Here’s the reason: Synchronization can be very expensive!

You know, Vector and Hashtable are the two collections exist early in Java history, and they are designed for thread-safe from the start (if you have chance to look at their source code, you will see their methods are all synchronized!). However, they quickly expose poor performance in multi-threaded programs. As you may know, synchronization requires locks which always take time to monitor, and that reduces the performance.

That’s why the new collections (List, Set, Map, etc) provide no concurrency control at all to provide maximum performance in single-threaded applications.

The following test program compares performance between Vector and ArrayList - the two similar collections (Vector is thread-safe and ArrayList is not):


*/

//La colecciones antiguas de java (Vector, Hashtable) eran seguras con threads, 
//pero eso provoca que sean menos eficientes (ya que todos los métodos son synchronized)
//Las colecciones actuales (List, Set, Map, etc) no soportan control de concurrencia,
//pero a cambio son mas eficientes


public class CollectionsThreadSafeTest {
 
    public void testVector() {
        long startTime = System.currentTimeMillis();
 
        Vector<Integer> vector = new Vector<>();
 
        for (int i = 0; i < 10_000_000; i++) {
            vector.addElement(i);
        }
 
        long endTime = System.currentTimeMillis();
 
        long totalTime = endTime - startTime;
 
        System.out.println("Test Vector: " + totalTime + " ms");
 
    }
 
    public void testArrayList() {
        long startTime = System.currentTimeMillis();
 
        List<Integer> list = new ArrayList<>();
 
        for (int i = 0; i < 10_000_000; i++) {
            list.add(i);
        }
 
        long endTime = System.currentTimeMillis();
 
        long totalTime = endTime - startTime;
 
        System.out.println("Test ArrayList: " + totalTime + " ms");
 
    }
 
    public static void main(String[] args) {
        CollectionsThreadSafeTest tester = new CollectionsThreadSafeTest();
 
        tester.testVector();
 
        tester.testArrayList();
 
    }
 
}