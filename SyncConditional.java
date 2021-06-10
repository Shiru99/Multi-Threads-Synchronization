// Producer-Consumer Problem

class ProductionPlant {

    int product;
    boolean isProductReady = false; // Semaphore - binary

    synchronized void produce(int product) {
        if (isProductReady)
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        this.product = product;
        System.out.println("Product Produced - " + this.product);
        isProductReady = true;
        notify();
    }

    synchronized void consume() {
        if (!isProductReady)
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        System.out.println("Product Consumed - " + this.product);
        isProductReady = false;
        notify();
    }

}

class Producer extends Thread {
    ProductionPlant productionPlant;

    public Producer(String string, ProductionPlant productionPlant) {
        super(string);
        this.productionPlant = productionPlant;
    }

    @Override
    public void run() {
        super.run();
        for (int i = 1; i < 6; i++) {
            // try {
            // sleep(100);
            // } catch (InterruptedException e) {
            // e.printStackTrace();
            // }
            productionPlant.produce(i * i);
        }
    }
}

class Consumer extends Thread {
    ProductionPlant productionPlant;

    public Consumer(String string, ProductionPlant productionPlant) {
        super(string);
        this.productionPlant = productionPlant;
    }

    @Override
    public void run() {
        super.run();
        for (int i = 1; i < 6; i++) {
            // try {
            // sleep(100);
            // } catch (InterruptedException e) {
            // e.printStackTrace();
            // }
            productionPlant.consume();
        }
    }
}

public class SyncConditional {
    public static void main(String[] args) {
        ProductionPlant productionPlant = new ProductionPlant();

        Thread p = new Producer("Producer", productionPlant);
        Thread c = new Consumer("Consumer", productionPlant);

        p.start();
        c.start();
    }
}
