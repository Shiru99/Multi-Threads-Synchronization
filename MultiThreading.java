/*              Process Vs Thread

        process :
            
            - binary instructions loaded in memory
            - gets access to resources like memory
            - its own stack, heap, registers
            - resources protected from other processes
        
        Thread

            - Unit of execution within process
            - usually has a shared objective
            - has shared resources like memory, heap storage, ...



    Usually Thread ends - after run method hits return statement or exception is thrown

    when does a java application end - when all the user threads have exited
    what if you don't want application exit to wait for a thread ==> Daemon Thread




    Types of Threads :

        User Threads    -   main threads & other threads with - new Thread(runnable).start()
        Daemon Threads  -   program doesn't wait for complete execution 
                            (in general useful for background supporting task)
 

    Multi-Threading Or Concurrency :
        
        Multiple threads don't allocate separate memory area, hence they save memory. Also, context switching between threads takes less time.


    
    Various stages of life cycle of thread :
    

                                      (imaginary Running state)                         
                            [Thread-pool] OS Scheduler selects thread to run                   
                                                ↑
                                                ↑                   1. waiting state
                                                ↑                   sleep(arg)/join(arg)
                                .start()        ↑
new Thread() -----> New state ------------> Runnable  ---->         2. time waiting state
                     (Born)                     ↓                   sleep(time)/join(time)      
                                                ↓
                                                ↓                   3. Blocked State
                                                ↓                   from running thread - 
                                                ↓                   try to acquire the loc
                                        Dead/Terminated state


    Barrier Synchronization : 

            In parallel computing, a barrier is a type of synchronization method where it enables multiple threads to wait until all threads have reached a particular point of execution(barrier) before any thread(main-thread) continues.

    Parallelism : (Running multiple tasks at the same time)

            Two Threads running on two cores. By definition, need multiple cores
    
    Concurrency : (Multiple tasks in progress at the same time)

            Ten Threads running on same core
*/

class ThreadOne extends Thread {
    public ThreadOne(String string) {
        super(string);
    }

    @Override
    public void run() {
        super.run();

        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getId() + " : " + Thread.currentThread().getName());
        }

    }
}

class ThreadTwo extends Thread {
    public ThreadTwo(String string) {
        super(string);
    }

    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getId() + " : " + Thread.currentThread().getName());
        }

    }
}

class RunnableThree implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getId() + " : " + Thread.currentThread().getName());
        }
    }
}

class ThreadFour extends Thread {
    public ThreadFour(String string) {
        super(string);
    }

    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getId() + " : " + Thread.currentThread().getName());

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

class ThreadFive extends Thread {
    public ThreadFive(String string) {
        super(string);
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getId() + " : " + Thread.currentThread().getName());
        }

    }
}

class RunnableSix implements Runnable {

    RunnableSix(String threadName){
        new Thread(this,threadName).start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getId() + " : " + Thread.currentThread().getName());
        }
    }
}

public class MultiThreading {
    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getId() + " : " + Thread.currentThread().getName());

        Thread threadOne = new ThreadOne("Thread One");
        Thread threadTwo = new ThreadTwo("Thread Two");
        Runnable runnableThree = new RunnableThree();
        Thread threadThree = new Thread(runnableThree, "Thread Three");

        threadTwo.setPriority(Thread.MAX_PRIORITY);
        threadOne.setPriority(Thread.MIN_PRIORITY);
        threadOne.setDaemon(true);  // Daemon thread

        // Despite of higher priority, OS has role while selecting threads which may not
        // match priorities

        System.out.println("Thread One Priority : " + threadOne.getPriority());
        System.out.println("Thread Two Priority : " + threadTwo.getPriority());
        System.out.println("Thread Three Priority : " + threadThree.getPriority());

        threadOne.run(); // Main thread will call this method

        threadOne.start(); // Start() => adds thread to thread pool
        threadTwo.start();
        threadThree.start();

        Thread threadFour = new ThreadFour("Thread Four");
        Thread threadFive = new ThreadFive("Thread Five");

        threadFour.start();

        try {
            threadFour.join(); // Main thread will Wait for this thread to die.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        threadFive.start();
        RunnableSix threadSix = new RunnableSix("Thread Six");
    }
}
