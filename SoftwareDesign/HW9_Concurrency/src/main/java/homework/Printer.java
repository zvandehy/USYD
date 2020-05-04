package homework;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Printer {
    private String prefix;
    private int count;
    public Lock lock = new ReentrantLock();

    public Printer() {
        prefix = "";
        count = 0;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void print() {
        System.out.println(prefix + " " + count);
    }
}
