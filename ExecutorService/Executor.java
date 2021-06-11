/*

    High vs Low level API :

        1. API: Application Program Interface. A specification for standard program routines that can be called by other programs. 
        2. High-level usually means generic and relatively simple, low-level usually means more detail-oriented and hence capable of more detailed control


    Thread creation is resource intensive ==> Reuse Threads using 'Thread Pool'

    Executor service :

        High level API for executing tasks

        1. manages runnable (or tasks)
        2. provides extra abilities (like thread pool)
        3. enables results (callable)

    Types of Executors :

        1. fixed thread pool executor           - Executors.newFixedThreadPool(numOfThreads)
        2. single thread executor               - Executors.newSingleThreadExecutor()
        3. cached thread pool executor
        4. scheduled thread pool executor
        5. work stealing thread pool executor

    'ExecutorService' - interface extends 'Executor'
    'ScheduledExecutorService' - interface extends 'ExecutorService' & 'Executor'

    Class ThreadPoolExecutor - implements 'ExecutorService' & 'Executor'

    Class - 'Executors' : 
        
        Factory and utility methods for Executor, ExecutorService, ThreadFactory, ScheduledExecutorService, and Callable classes defined in this package.
        
*/

package ExecutorService;

public class Executor {
    public static void main(String[] args) {
        
    }
}
