/* 3. Join for Sequential Sync */

class BalanceUpdateThread extends Thread {
    public BalanceUpdateThread(String string) {
        super(string);
    }

    @Override
    public void run() {
        System.out.println("Updating Balance...");
        System.out.println("Balance Updated Successfully");
    }
}

class BalanceUpdateReceipt extends Thread {
    Thread balanceUpdate;

    public BalanceUpdateReceipt(String string, Thread balanceUpdate) {
        super(string);
        this.balanceUpdate = balanceUpdate;
    }

    @Override
    public void run() {

        try {
            balanceUpdate.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*
            Without Join : 
                Updating Balance...
                Printing Receipt...
                Balance Updated Successfully
            
            After Join :
                Updating Balance...
                Balance Updated Successfully
                Printing Receipt...
        */

        System.out.println("Printing Receipt...");

    }
}

public class SequentialSync {
    
    public static void main(String[] args) {

        Thread balanceUpdate = new BalanceUpdateThread("Balance Update");
        Thread balanceUpdateReceipt = new BalanceUpdateReceipt("Balance Update Receipt", balanceUpdate);

        balanceUpdate.start();
        balanceUpdateReceipt.start();

    }
}
