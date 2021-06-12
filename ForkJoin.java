/*


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

*/
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoin {
    public static void main(String[] args) {

        int nums[] = { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096 };
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        CalculatePrimeTask calculatePrimeTask = new CalculatePrimeTask(nums, 0, nums.length - 1);
        int primeSum = forkJoinPool.invoke(calculatePrimeTask);
        System.out.println("Sum of all primes : " + primeSum);
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
        invokeAll(subTask1, subTask2);
        return subTask1.join() + subTask2.join();

    }

    private static int calculatePrime(int num) {
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

/*
 * main - 1th prime : 2 ForkJoinPool.commonPool-worker-9 - 2th prime : 3
 * ForkJoinPool.commonPool-worker-5 - 16th prime : 53
 * ForkJoinPool.commonPool-worker-7 - 4th prime : 7
 * ForkJoinPool.commonPool-worker-3 - 128th prime : 719
 * ForkJoinPool.commonPool-worker-5 - 32th prime : 131
 * ForkJoinPool.commonPool-worker-7 - 8th prime : 19
 * ForkJoinPool.commonPool-worker-5 - 64th prime : 311
 * ForkJoinPool.commonPool-worker-9 - 256th prime : 1619
 * ForkJoinPool.commonPool-worker-13 - 512th prime : 3671
 * ForkJoinPool.commonPool-worker-11 - 1024th prime : 8161
 * ForkJoinPool.commonPool-worker-7 - 2048th prime : 17863
 * ForkJoinPool.commonPool-worker-15 - 4096th prime : 38873
 * 
 * Sum of all primes : 71432
 */