/*
    Mutual Exclusive Synchronization :  

        1. Mutual exclusion is a property of process synchronization which states that “no two processes can exist in the critical section at any given point of time
                                    
        2. synchronization can be done only for same shared field. (same object or same static function or block of code)

*/

class ThreadDetails {
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
        for (int i = 0; i < 40; i++) {
            System.out.print("*");
        }
        System.out.println();

        // critical section starts
        synchronized(this)      // This -> same object Table-td
        {
            for (int i = 0; i < 5; i++) {
                System.out.println("☠️ Critical Section of "+ Thread.currentThread().getName());
            }
        }

        // critical section Ends
        for (int i = 0; i < 40; i++) {
            System.out.print("-");
        }
        System.out.println();
    }
}

class Thread1 extends Thread {
    ThreadDetails td ;
    public Thread1(String string, ThreadDetails td) {
        super(string);
        this.td =td; 
    }

    @Override
    public void run() {
        System.out.println("Thread 1 Started....");
        ThreadDetails.details();
        td.moreDetails();
        td.trashWithDetails();
        System.out.println("Thread 1 Finished....");
    }
}

class Thread2 extends Thread {
    ThreadDetails td ;
    public Thread2 (String string, ThreadDetails td) {
        super(string);
        this.td=td;
    }

    @Override
    public void run() {
        System.out.println("Thread 2 Started....");
        ThreadDetails.details();    
        td.moreDetails();           
        td.trashWithDetails();   
        System.out.println("Thread 2 Finished....");
        /*
            sync is bet. same critical section of all threads
            but not in different critical sections of same or different threads
        */   
    }
}

public class MutualExclusiveSync {
    public static void main(String[] args) 
    {
        
        ThreadDetails td = new ThreadDetails();
        Thread thread1 = new Thread1("Thread 1",td);
        Thread thread2 = new Thread2("Thread 2",td);
        thread1.start();
        thread2.start();
    }
}
