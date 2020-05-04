package homework;

public class Main {
    public static void main(String[] args) {
        Printer myPrinter = new Printer();
        Thread thread1 = new Thread(new Counter("Deadlocks avoided: ", 0,100,false, myPrinter));
        Thread thread2 = new Thread(new Counter("Threads starved: ", 0,100,true, myPrinter));

        thread1.start();
        thread2.start();
    }
}
