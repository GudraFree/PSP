package ae0_join;

/**
 * <p>The join method allows one thread to wait for the completion of another.
 * 
 * <p> If t is a Thread object whose thread is currently executing, <code>t.join();</code> 
 *     causes the current thread to pause execution until t's thread terminates. 
 *     
 * <p>Overloads of join allow the programmer to specify a waiting period.
 *    However, as with sleep, join is dependent on the OS for timing, 
 *    so you should not assume that join will wait exactly as long as you specify.
 *    
 * <p>Like sleep, join responds to an interrupt by exiting with an InterruptedException.
 *
 */

public @interface JoinAndWait {

}
