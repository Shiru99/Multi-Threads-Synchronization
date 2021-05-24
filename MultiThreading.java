///////////////////////////////  Multi-Threading  //////////////////////////////
/*


    Various stages of life cycle of thread

        1. New
        2. Runnable  [Thread-pool]
        3. Running
        4. Waiting
        5. Dead



    MULTITHREADING (executing two or more threads simultaneously to maximum utilization of CPU)

        1. Multithreaded applications execute two or more threads run concurrently. Hence, it is also known as Concurrency in Java. Each thread runs parallel to each other. 

        2. Mulitple threads don't allocate separate memory area, hence they save memory. Also, context switching between threads takes less time.


    

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
        super.run();
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
        Thread threadFive = new ThreadFour("Thread Five");

        threadFour.start();

        try {
            threadFour.join(5000); // Waits for this thread to die.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        threadFive.start();
    }
}
