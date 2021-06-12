/*

    ForkJoin :

        breakdown the Task into smaller sub-Tasks recursively. (Recursion)
        And these sub-tasks can run concurrently

        BUT, creating new thread everytime is resource intensive ==> ForkJoinPool

        ForkJoinPool - 

            special implementation of ExecutiveService for specific usecase

            ForkJoinTask - special type of task designed to run in a ForkJoinPool

            1. create instance of ForkJoinTask
            2. get instance of the ForkJoinPool
            3. submit instance of ForkJoinTask implementation to the ForkJoinPool

            Work Stealing 

                distribute equally - if one of the thread taking longer time ForkJoinPool steals work from it & allocate to other relatively faster thread

            ForkJoinTask (super class) -> RecursiveAction | RecursiveTask (child class)

                RecursiveTask - accumulation required (sum of all i-th primes in array)
                RecursiveAction - accumulation NOT required (add constant to each element of array)

            

    OutPut :

        main -  1th prime : 2
        ForkJoinPool.commonPool-worker-3 -      128th prime : 719
        main -  2th prime : 3
        ForkJoinPool.commonPool-worker-13 -     16th prime : 53
        ForkJoinPool.commonPool-worker-13 -     32th prime : 131
        ForkJoinPool.commonPool-worker-3 -      256th prime : 1619
        ForkJoinPool.commonPool-worker-13 -     64th prime : 311
        ForkJoinPool.commonPool-worker-13 -     8th prime : 19
        ForkJoinPool.commonPool-worker-13 -     512th prime : 3671
        ForkJoinPool.commonPool-worker-7 -      4096th prime : 38873
        ForkJoinPool.commonPool-worker-9 -      2048th prime : 17863
        ForkJoinPool.commonPool-worker-11 -     4th prime : 7
        ForkJoinPool.commonPool-worker-5 -      1024th prime : 8161

        Sum of all primes : 71432

    /----------------------------------------------/

    nums.length = 2049
    Sum of all primes : 13505681            Time taken by ForkJoinPool - 344ms
    Sum of all primes : 13505681            Time taken by ForEach - 1022ms

*/
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoin {
    public static void main(String[] args) {

        int nums[] = { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096 };

        // System.out.println(nums.length);
        // long t0 = System.currentTimeMillis();

        // using existing thread pool
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool(); 

        // Creating new ThreadPool is resource intensive
        // ForkJoinPool forkJoinPool = new ForkJoinPool();  

        CalculatePrimeTask calculatePrimeTask = new CalculatePrimeTask(nums, 0, nums.length - 1);
        int primeSum = forkJoinPool.invoke(calculatePrimeTask);
        System.out.println("Sum of all primes : " + primeSum);
        // long t1 = System.currentTimeMillis();
        // System.out.println("Sum of all primes : " + primeSum + "\t\tTime taken by ForkJoinPool - "+(t1-t0)+"ms");
        

        // int primeNumSum = 0;
        // for (int i : nums) {
        //     primeNumSum+=CalculatePrimeTask.calculatePrime(i);
        // }
        // long t2 = System.currentTimeMillis();
        // System.out.println("Sum of all primes : " + primeNumSum + "\t\tTime taken by ForEach - "+(t2-t1)+"ms");
    }
}

/**
 * CalculatePrimeTask : Calculate the sum of i-th primes numbers
 */
class CalculatePrimeTask extends RecursiveTask<Integer> {

    int nums[];
    int start, end;

    public CalculatePrimeTask(int[] nums, int start, int end) {
        this.nums = nums;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {

        if (start == end) {
            System.out.println(Thread.currentThread().getName() + " - \t" + nums[start] + "th prime : "
                    + calculatePrime(nums[start]));
            return calculatePrime(nums[start]);
        }

        int mid = (start + end) / 2;
        CalculatePrimeTask subTask1 = new CalculatePrimeTask(nums, start, mid);
        CalculatePrimeTask subTask2 = new CalculatePrimeTask(nums, mid + 1, end);

        // void java.util.concurrent.ForkJoinTask.invokeAll
        // Forks all tasks in the specified collection, returning when isDone holds for each task
        invokeAll(subTask1, subTask2);  
        
        return subTask1.join() + subTask2.join();   // wait for the results
    }

    static int calculatePrime(int num) {
        int numOfPrimeFound = 1;
        int primeNumber = 2;
        int i;

        while (numOfPrimeFound < num) {

            primeNumber++;

            for (i = 2; i * i <= primeNumber && primeNumber % i != 0; i++)
                ;

            if (i * i > primeNumber)
                numOfPrimeFound++;
        }
        return primeNumber;
    }
}