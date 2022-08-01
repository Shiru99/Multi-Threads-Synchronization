/* 4. Lock */

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
    Synchronization achieves :

        1. Mutual Exclusion
            MutEx

        2. Visibility 
            value is read from memory before block execution
            value is written to memory after block execution




    Thread local :

        1. scope is per-thread
        2. "Global" in the context of thread instance
        3. each Thread just sees its own thread variable

    ThreadLocal<Integer> ID = new ThreadLocal<>();
    ID.set(99);
    ID.get();



    Processor :

        Core 0                          Core 1
        -----                           -----
        L1,L2        (Caches)           L1,L3 

            L3 cache (common to both cores)

                    ----                        (system bus)

                System Memory

        


    Volatile keyword
        
        marks variable as do not cache i.e. every read-write is directly from memory
    
        single volatile variable, applies volatile behavior to  all other variables visible in a thread (to maintain consistency)
*/

class Counter implements Runnable {

    private volatile int value = 0;

    public int getValue() {
        return value;
    }

    public void incrementValue() {
        this.value++;
    }

    public void decrementValue() {
        this.value--;
    }

    private void fun() {
        this.incrementValue();
        System.out.println(Thread.currentThread().getName() + " Increments : " + this.getValue());
        this.decrementValue();
        System.out.println(Thread.currentThread().getName() + " Decrements : " + this.getValue());
    }

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {

        // fun();
        /*
            T1 Increments : 2
            T4 Increments : 4
            T3 Increments : 3
            T2 Increments : 3
            T2 Decrements : 0
            T3 Decrements : 1
            T4 Decrements : 2
            T1 Decrements : 3
        */

        /* M-1 */
        // synchronized (this) {
        //     fun();
        // }

        /* M-1 & M-2 :
            T1 Increments : 1
            T1 Decrements : 0
            T2 Increments : 1
            T2 Decrements : 0
            T4 Increments : 1
            T4 Decrements : 0
            T3 Increments : 1
            T3 Decrements : 0
        */


        /* M-2 Acquires the lock */
        // lock.lock();
        // try {
        //     fun();
        // } finally {
        //     lock.unlock();
        // }



        /* M-3 tryLock - Acquires the lock only if it is free at the time of invocation */
        // if (lock.tryLock()) { 
        //     try {
        //         fun();
        //     } finally {
        //         lock.unlock();
        //     }
        // }else{
        //     System.out.println(Thread.currentThread().getName()+" - Unable To lock");
        // }

        /*
            T1 Increments : 1
            T2 - Unable To lock
            T4 - Unable To lock
            T3 - Unable To lock
            T1 Decrements : 0
        */

    }
}

public class LockInterface {
    public static void main(String[] args) {

        Counter counter = new Counter();
        new Thread(counter, "T1").start();
        new Thread(counter, "T2").start();
        new Thread(counter, "T3").start();
        new Thread(counter, "T4").start();

    }
}