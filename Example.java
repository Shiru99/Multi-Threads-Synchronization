import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

            if (num == 0)
                break;

            Thread t = new Thread(() -> calculatePrime(num));
            threads.add(t);
            t.start();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private static void calculatePrime(int num) {
        int numOfPrimeFound = 0;
        int number = 1;
        int i;

        while (numOfPrimeFound < num) {
            number++;
            for (i = 2; i <= number && number % i != 0; i++) {
            }
            if (i == number)
                numOfPrimeFound++;
        }
        System.out.println(num + "th prime : " + number);
    }

    public static void threadStatus(List<Thread> threads) {

        while (true) {
            System.out.print("\nThread Status : ");
            threads.forEach(t -> System.out.print(t.getState() + " "));
            System.out.println();

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
