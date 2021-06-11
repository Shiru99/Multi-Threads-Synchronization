package ExecutorService;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExampleCallableFuture {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        List<Future<String>> sol = new ArrayList<>();

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

            Callable<String> c = new Callable<String>(){
                @Override
                public String call() throws Exception {
                    return calculatePrime(num);
                }
            };

            Future<String> returnVal = executorService.submit(c);
            
            /* Main Thread waits here until callable return the value */ 
            // System.out.println(returnVal.get()); 

            sol.add(returnVal);

            for (Future<String> f : sol) {
                if(f.isDone()){
                    System.out.println(f.get());
                    sol.remove(f);
                }
            }
            
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
