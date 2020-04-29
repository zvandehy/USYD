package au.edu.sydney.soft3202.tutorials.week9.concurrency;

public class MainDriver {

    public static void main(String[] args) {

        ExampleRunnable runnable = new ExampleRunnable();
        Thread containerThread = new Thread(runnable);

        ExampleThread thread = new ExampleThread();

        containerThread.start();
        thread.start();

    }
}
