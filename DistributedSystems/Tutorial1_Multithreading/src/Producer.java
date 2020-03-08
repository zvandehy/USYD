import java.util.Date;

public class Producer implements Runnable {

    Channel<Date> queue;

    public Producer(Channel<Date> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            //wait 0.5 seconds
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Date newDate = new Date();
            queue.send(newDate);
            System.out.println("PRODUCER: " + newDate);
        }
    }
}
