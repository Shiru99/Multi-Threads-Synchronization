// Producer-Consumer Problem

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ProductionPlantWithCondition extends ProductionPlant {

    Lock lock = new ReentrantLock();
    Condition produceConditionalLock = lock.newCondition();
    Condition consumeConditionalLock = lock.newCondition();

    int product;
    boolean isProductReady = false; // Semaphore - binary

    void produce(int product) {
        lock.lock();
        try {
            if (isProductReady) {
                try {
                    produceConditionalLock.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.product = product;
            System.out.println("Conditional Product Produced - " + this.product);
            isProductReady = true;
            consumeConditionalLock.signal();
        } finally {
            lock.unlock();
        }

    }

    void consume() {

        lock.lock();
        try {
            if (!isProductReady) {
                try {
                    consumeConditionalLock.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Conditional Product Consumed - " + this.product);
            isProductReady = false;
            produceConditionalLock.signal();
        } finally {
            lock.unlock();
        }
    }
}

public class SyncConditionalWithCondition {
    public static void main(String[] args) {
        ProductionPlant productionPlant = new ProductionPlantWithCondition();

        Thread p = new Producer("Producer", productionPlant);
        Thread c = new Consumer("Consumer", productionPlant);

        p.start();
        c.start();
    }
}