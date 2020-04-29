package au.edu.sydney.soft3202.tutorials.week9.concurrency;

public class TakeMoney implements Runnable {
    private Bank account;

    public TakeMoney(Bank account) {
        this.account = account;
    }

    /**
     * Take 1000 from the given account, 60 times
     */
    public void run() {
        for (int i = 0; i < 60; ++i) {
//            try {
//                Thread.sleep(500);
//            } catch (Exception e) {
//                System.err.println("Already interrupted.");
//            }
            account.lock.lock();
            account.subtractMoney(1000);
            account.lock.unlock();
        }
    }
}