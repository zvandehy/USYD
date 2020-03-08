import java.util.Date;

public class Consumer implements Runnable {

    Channel<Date> queue;

    public Consumer(Channel<Date> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true) {
            //wait 0.5 seconds
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Date currentDate = queue.receive();
            System.out.println("CONSUMER: " + currentDate);
        }
    }
}
