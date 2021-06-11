package ExecutorService;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExampleCompletableFuture {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        Scanner sc = new Scanner(System.in);
        ExecutorService executorService = Executors.newCachedThreadPool();

        while (true) {

            System.out.print("Enter n & get nth prime number : ");
            int num = sc.nextInt();

            if (num == 0) {
                System.out.println("Waiting for all threads to finish...");
                executorService.shutdown();
                break;
            }

            /* by default uses common thread pool (Fork-Join Thread Pool) */
            // CompletableFuture.supplyAsync(() -> calculatePrime(num))
            //         .thenAccept((String returnVal) -> System.out.println(returnVal));
            

            CompletableFuture.supplyAsync(() -> calculatePrime(num), executorService)
                    .thenAccept(System.out::println);
        }
    }

    private static String calculatePrime(int num) {
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
        return num + "th prime : " + number;
    }
}
