package ExecutorService;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExampleThreadPool {
    public static void main(String[] args) {

        /* Fixed threads */
        // ExecutorService executorService = Executors.newFixedThreadPool(3);

        /* Single thread */
        // ExecutorService executorService = Executors.newSingleThreadExecutor();

        /*
         * Cached thread pool - thread get created/reused/destroyed (unused for 60sec)
         * as per requirement
         */
        // ExecutorService executorService = Executors.newCachedThreadPool();

        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        /*-----------------------------*/

        /* Scheduled thread pool */
        ScheduledExecutorService report = Executors.newScheduledThreadPool(1);
        report.scheduleAtFixedRate(() -> {
            System.out.println("Thread Report - Active Threads : " + executorService.getActiveCount()
                    + "\tCompleted Threads : " + executorService.getCompletedTaskCount());
        }, 1, 5, TimeUnit.SECONDS);
        // 1 - initialDelay & 5- period

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("Enter n & get nth prime number : ");
            int num = sc.nextInt();

            if (num == 0) {
                System.out.println("Waiting for all threads to finish...");
                executorService.shutdownNow();
                report.shutdown();
                break;
                // System.exit(0);
            }

            executorService.execute(() -> calculatePrime(num));
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
}