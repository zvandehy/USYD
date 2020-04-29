package au.edu.sydney.soft3202.tutorials.week9.concurrency;

public class AddMoney implements Runnable {
    private Bank account;

    public AddMoney(Bank account) {
        this.account = account;
    }

    /**
     * Add 1000 to the given account, 60 times
     */
    public void run() {
        for (int i = 0; i < 60; ++i) {
//            try {
//                Thread.sleep(500);
//            } catch (Exception e) {
//                System.err.println("Already interrupted.");
//            }
            account.lock.lock();
            account.addMoney(1000);
            account.lock.unlock();
        }
    }
}