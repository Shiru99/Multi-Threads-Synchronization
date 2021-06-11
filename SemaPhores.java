/*
    Semaphores : 'permit-based' access

        limited number of threads at a time (Lock- only one thread at a time)
        used to managing limited resources in a concurrent environment


    acquire() vs acquireUninterruptibly() 

        acquire() is interruptible. That means if a thread A is calling acquire() on a semaphore, and thread B interrupts threads A by calling interrupt(), then an InterruptedException will be thrown on thread A.

        On the other hand acquireUninterruptibly() is not interruptible. That means if a thread A is calling acquireUninterruptibly() on a semaphore, and thread B interrupts threads A by calling interrupt(), then no InterruptedException will be thrown on thread A, just that thread A will have its interrupted status set after acquireUninterruptibly() returns.
*/

import java.util.*;
import java.util.concurrent.Semaphore;

public class SemaPhores {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        List<Thread> threads = new ArrayList<>();

        Semaphore sema = new Semaphore(3, true);    // fair == true --> Queue

        while (true) {

            System.out.print("Enter n & get nth prime number : ");

            int num = sc.nextInt();

            if (num == 0) {
                System.out.println("Waiting for all threads to finish...");
                waitForThreads(threads);
                System.out.println("Done! " + threads.size() + " primes calculated");
                break;
            }

            Thread t = new Thread(() -> {
                // sema.acquireUninterruptibly();   // ignores interruption
                try {
                    sema.acquire();
                    calculatePrime(num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally{
                    sema.release();
                }
                
            });
            threads.add(t);
            t.start();

            try {
                t.join(100); // waits at most 100 ms for thread to die else it will continue
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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

    private static void waitForThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}