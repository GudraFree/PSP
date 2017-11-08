package tema2.ejemplos.a0_MainThread;

// EJEMPLO Nº1
public class RunningInMainThread {

    public static void main(String args[]) {

        System.out.printf("%s es el hilo de main", Thread.currentThread());

        System.out.println();

        Runnable runnableTask = new Runnable() {
            public void run() {
                System.out.printf("%s es el hilo de run", Thread.currentThread());
            }
        };
        runnableTask.run();

    }

}
