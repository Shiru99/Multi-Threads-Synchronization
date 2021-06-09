/*
    Mutual Exclusive Synchronization :  

        1. Mutual exclusion is a property of process synchronization which states that “no two processes can exist in the critical section at any given point of time
                                    
        2. synchronization can be done only for same shared resource. (same object or same static function or block of code)

*/

class Stats {

    synchronized static void details() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getId() + " : " + Thread.currentThread().getName());
        }
    }

    synchronized void moreDetails(){
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getId() + " : " + Thread.currentThread().getPriority() + " : " + Thread.currentThread().getName());
        }
    }

    void trashWithDetails(){
        for (int i = 0; i < 40; i++)
            System.out.print("*");
        System.out.println();

        // critical section starts
        synchronized(this)                  // This -> same object Table-td
        {
            for (int i = 0; i < 5; i++)
                System.out.println("☠️ Critical Section of "+ Thread.currentThread().getName());
        }

        // critical section Ends
        for (int i = 0; i < 40; i++)
            System.out.print("-");
        System.out.println();
    }

    static void staticDetails() {
        for (int i = 0; i < 40; i++)
            System.out.print("*");
        System.out.println();

        synchronized(Stats.class)  // critical section in static function
        {
            for (int i = 0; i < 5; i++)
                System.out.println("☠️ Critical Section of "+ Thread.currentThread().getName());
        }

        for (int i = 0; i < 40; i++)
            System.out.print("-");
        System.out.println();
    }
}

class Thread0 extends Thread {

    Stats td ;
    
    public Thread0(String string, Stats td) {
        super(string);
        this.td =td; 
    }

    @Override
    public void run() {

        System.out.println(Thread.currentThread().getName()+" Started....");

        Stats.details();    // sync static method
        td.moreDetails();           // sync instance method
        td.trashWithDetails();      // sync block in method

        System.out.println(Thread.currentThread().getName()+" Finished....");

        /*
            sync is bet. same critical section of all threads
            but not in different critical sections of same or different threads
        */  
    }
}

public class SyncMutualExclusive {
    public static void main(String[] args) 
    {
        Stats td = new Stats();
        Thread thread1 = new Thread0("Thread 1",td);
        Thread thread2 = new Thread0("Thread 2",td);
        thread1.start();
        thread2.start();
    }
}
