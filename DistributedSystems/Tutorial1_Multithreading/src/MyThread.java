public class MyThread implements Runnable {

    public static void main(String[] args) {
        Thread mt = new Thread(new MyThread());
        mt.start();
    }

    @Override
    public void run() {
        System.out.println("hello world");
    }
}
