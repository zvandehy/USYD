import java.util.*;

public class PeriodicLastBlockRunnable implements Runnable {

    private HashMap<ServerInfo, Date> serverStatus;
    private int localPort;
    private final Blockchain blockchain;

    public PeriodicLastBlockRunnable(int localPort, HashMap<ServerInfo, Date> serverStatus, Blockchain blockchain) {
        this.localPort = localPort;
        this.serverStatus = serverStatus;
        this.blockchain = blockchain;
    }

    @Override
    public void run() {
        while(true) {
            // broadcast lb message to 5 random peers
            ArrayList<ServerInfo> randomServerInfos = new ArrayList<>();
            ArrayList<ServerInfo> serverInfos = new ArrayList<>(Arrays.asList(serverStatus.keySet().toArray(new ServerInfo[0])));

            //if there are less than 5 servers in the network, then just set those as the random servers
            if(serverInfos.size() > 5) {
                //otherwise randomize their order and select the first 5 as the random servers
                Collections.shuffle(serverInfos);
                for(int i=0;i<5;i++) {
                    randomServerInfos.add(serverInfos.get(i));
                }
            } else {
                randomServerInfos = serverInfos;
            }

            String hash = blockchain.getHead() == null ? "null" : Base64.getEncoder().encodeToString(blockchain.getHead().calculateHash());
            //message is "lb|<port>|length|lastHash"
            String message = "lb|"+ localPort +"|"+blockchain.getLength()+"|"+hash;

            //send lb message to each peer (using their ServerInfo) in the randomServers list
            for (ServerInfo toServerInfo : randomServerInfos) {
                //the socket in this thread will timeout after 2 seconds
                Thread thread = new Thread(new SendMessageRunnable(toServerInfo, message));
                thread.start();
            }


            // sleep for two seconds
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
            }
        }
    }
}
