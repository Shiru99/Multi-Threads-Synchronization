package ExecutorService;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Callable_Future {
    public static void main(String[] args) {

        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("from Runnable");
            }
        };

        Callable<String> c = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("from Callable");
                Thread.sleep(2000);
                return "Hello World";
            }
        };
        
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        /* execute - Only Runnables */
        executorService.execute(r);

        /* execute - Runnables or Callables */
        executorService.submit(r);

        Future<String> returnVal = executorService.submit(c);

        try {   // Main Thread waits here until callable return the value
            System.out.println(returnVal.get()); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e1) {
            e1.printStackTrace();
        }

        executorService.shutdown();

        System.out.println("Bye");
    }
}