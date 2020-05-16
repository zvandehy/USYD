import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;

public class PeriodicUpdateServerStatusRunnable implements Runnable {


    private HashMap<ServerInfo, Date> serverStatus;

    public PeriodicUpdateServerStatusRunnable(HashMap<ServerInfo, Date> serverStatus) {
        this.serverStatus = serverStatus;
    }

    @Override
    public void run() {
        while(true) {
            //for each peer in the network
            //if haven't heard from server in 4 seconds, must remove from serverStatus (probably goes in another class)
            for (Entry<ServerInfo, Date> entry : serverStatus.entrySet()) {
                // if greater than 2T, remove
                if (new Date().getTime() - entry.getValue().getTime() > 4000) {
                    serverStatus.remove(entry);
                }
            }

            // sleep for two seconds
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
            }
        }
    }
}
