package homework;

public class Counter implements Runnable{
    String prefix;
    int start;
    int finish;
    boolean reverse;
    Printer printer;

    public Counter(String prefix, int start, int finish, boolean reverse, Printer printer) {
        if(start <= finish) {
            if(prefix != null && !prefix.equals("")) {
                if(printer != null) {
                    this.prefix = prefix;
                    this.start = start;
                    this.finish = finish;
                    this.reverse = reverse;
                    this.printer = printer;
                }
            }
        }
    }

    @Override
    public void run() {
        if(reverse){
            for(int i=finish; i>=start; i--) {
                printer.lock.lock();
                printer.setPrefix(prefix);
                printer.setCount(i);
                printer.print();
                printer.lock.unlock();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            printer.setCount(start);
            for(int i=start; i<=finish; i++) {
                printer.lock.lock();
                printer.setPrefix(prefix);
                printer.setCount(i);
                printer.print();
                printer.lock.unlock();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
