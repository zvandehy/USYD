package au.edu.sydney.soft3202.tutorials.week9.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    private int account = 100;
    public Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Bank account = new Bank();
        Thread adding = new Thread(new AddMoney(account));
        Thread subtracting = new Thread(new TakeMoney(account));
        adding.start();
        subtracting.start();
    }

    public void addMoney(int amount) {
        if (amount < 1) {
            System.out.print("Trying to add a negative amount to the account!");
            System.out.println(" Transaction rejected.");
            return;
        }
        int newBalance = account + amount;
        System.out.print("Account balance is " + account + ". Adding " + amount + ".");
        account = newBalance;
        System.out.println("New balance is " + account + ".");
        return;
    }

    public void subtractMoney(int amount) {
        if (amount < 1) {
            System.out.print("Trying to subtract a negative amount from the account!");
            System.out.println(" That's generous, but the transaction is rejected.");
            return;
        }
        int newBalance = account - amount;
        System.out.print("Account balance is " + account + ". Subtracting " + amount + ".");
        account = newBalance;
        System.out.println("New balance is " + account + ".");
        return;
    }
}