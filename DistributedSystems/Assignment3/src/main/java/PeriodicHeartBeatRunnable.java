import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class PeriodicHeartBeatRunnable implements Runnable {

    private HashMap<ServerInfo, Date> serverStatus;
    private int sequenceNumber;
    private int localPort;

    public PeriodicHeartBeatRunnable(int localPort, HashMap<ServerInfo, Date> serverStatus) {
        this.localPort = localPort;
        this.serverStatus = serverStatus;
        this.sequenceNumber = 0;
    }

    @Override
    public void run() {
        while(true) {
            // broadcast HeartBeat message to all peers
            ArrayList<Thread> threadArrayList = new ArrayList<>();
            //send hb message to each peer (using their ServerInfo) in the serverStatus network
            for (ServerInfo toServerInfo : serverStatus.keySet()) {
                String message = "hb|"+localPort+"|"+sequenceNumber;
                //the socket in this thread will timeout after 2 seconds
                Thread thread = new Thread(new SendMessageRunnable(toServerInfo, message));
                threadArrayList.add(thread);
                thread.start();
            }
            //wait until each Heartbeat Client (that sends the message) is done
            for (Thread thread : threadArrayList) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                }
            }

            // increment the sequenceNumber
            sequenceNumber += 1;

            // sleep for two seconds
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
        }
    }
}
