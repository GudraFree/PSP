package tema2.ejemplos.b_synchronization;
/**
 * <h3>Synchronization</h3>
 * 
 * 
 *<p>Threads communicate primarily by sharing access to fields and the objects reference fields refer to.
 *   This form of communication is extremely efficient, but makes two kinds of errors possible: 
 *   <i>thread interference</i> and <i<memory consistency errors</i>.
 *   The tool needed to prevent these errors is <i>synchronization</i>.
 *   
 *<ul>
 *   <li><b>Thread Interference</b> 
 *       describes how errors are introduced when multiple threads access shared data.
 *   <li><b>Memory Consistency Errors</b>
 *       describes errors that result from inconsistent views of shared memory.
 *   <li><b>Synchronized Methods</b> 
 *       describes a simple idiom that can effectively prevent thread interference and memory consistency errors.
 *   <li><b>Implicit Locks and Synchronization</b>
 *       describes a more general synchronization idiom, and describes how synchronization is based on implicit locks.
 *   <li><b>Atomic Access</b>
 *       talks about the general idea of operations that can't be interfered with by other threads.
 *</ul>
 */
public @interface Synchronization {

}
