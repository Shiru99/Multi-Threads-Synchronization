
class ThreadZero extends Thread {
    public ThreadZero(ThreadGroup threadGroup, String string) {
        super(threadGroup, string);
    }

    @Override
    public void run() {
        super.run();

        try {
            sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(
            Thread.currentThread().getThreadGroup().getName() + " - " +
            Thread.currentThread().getId()+ " : " +
            Thread.currentThread().getName()
        );

    }
}

public class GroupThreads {
    public static void main(String[] args) {
        ThreadGroup alpha = new ThreadGroup("Parent Alpha");
        Thread t1 = new ThreadZero(alpha, "one");
        Thread t2 = new ThreadZero(alpha, "two");

        ThreadGroup beta = new ThreadGroup("Parent Beta");
        Thread t3 = new ThreadZero(beta, "three");
        Thread t4 = new ThreadZero(beta, "four");

        System.out.println(beta.activeCount());

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        System.out.println(beta.isDestroyed());
        System.out.println(beta.activeCount());

        System.out.println(beta.getName() + ":");
        alpha.list();

        alpha.stop();
        System.out.println(beta.getName() + ":");
        alpha.list();
    }
}
