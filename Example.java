/*
    java.lang.InterruptedException :
            
        Thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity. 


    Join() :
        
        join(0) or join()   ==> waiting state

        join(ms)    ==> time waiting state

    Interrupt: 

        soft-interrupt i.e. different from hardware interrupt

*/

import java.util.*;

public class Example {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Thread> threads = new ArrayList<>();

        Thread stats = new Thread(() -> threadStatus(threads));
        stats.setDaemon(true);
        stats.start();
        while (true) {

            System.out.print("Enter n & get nth prime number : ");

            int num = sc.nextInt();

            if (num == 0) {
                
                System.out.println("Waiting for all threads to finish...");
                waitForThreads(threads);
                System.out.println("Done! "+threads.size()+" primes calculated");

                stats.interrupt();
                if(stats.isInterrupted()){
                    // To clean the resources
                }

                break;
            }

            Thread t = new Thread(() -> calculatePrime(num));
            threads.add(t);
            t.start();

            try {
                t.join(100);    // waits at most 100 ms for thread to die else it will continue
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void waitForThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*
            Barrier Synchronization : 

                In parallel computing, a barrier is a type of synchronization method where it enables multiple threads to wait until all threads have reached a particular point of execution(barrier) before any thread(main-thread) continues.


        */
    }

    private static void calculatePrime(int num) {
        int numOfPrimeFound = 1;
        int number = 2;
        int i;

        while (numOfPrimeFound < num) {

            number++;

            for (i = 2; i * i <= number && number % i != 0; i++)
                ;

            if (i * i > number)
                numOfPrimeFound++;
        }
        System.out.println(num + "th prime : " + number);
    }

    public static void threadStatus(List<Thread> threads) {

        while (true) {

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("No more statistics...");
                break;
            }

            System.out.print("\nThread Status : ");
            threads.forEach(t -> System.out.print(t.getState() + " "));
            System.out.println();
        }
    }
}
