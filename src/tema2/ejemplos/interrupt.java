package ad0_interrupts;

/**
 * <p>An interrupt is an indication to a thread that it should stop what it is doing and
 *    do something else. 
 * <p>It's up to the programmer to decide exactly how a thread responds to an interrupt:
 * 
 * <ul>
 *    <li><p>the thread is warned about some issue, it does something related to that issue and then continues with
 *           the task it was doing when it was interrupted
 *    <li> <p>but it is very common for the interrupted thread to terminate: throwing an exception, ending or exiting
 * </ul>
 * 
 * <p>The interrupt mechanism involves always two threads, a sending thread and a receiving thread, 
 *     each of them must do its part of the work:
 * 
 * <ol>
 *   <li> <p>A thread sends an interrupt to a second thread by invoking <code>interrupt()</code> method
 *           on the <code>Thread</code> object for the thread to be interrupted.
 * 
 *   <li> <p>For the interrupt mechanism to work correctly, the interrupted thread must support its own interruption.
 * </ol>
 */

public @interface interrupt {

}
