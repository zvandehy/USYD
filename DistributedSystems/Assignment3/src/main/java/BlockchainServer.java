import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BlockchainServer {

    public static void main(String[] args) {

        int localPort = 0;
        int remotePort = 0;
        String remoteHost = null;

        Blockchain blockchain = new Blockchain();

        HashMap<ServerInfo, Date> serverStatus = new HashMap<ServerInfo, Date>(); //list of peer nodes in p2p network


        if (args.length == 3) {
            try {
                localPort = Integer.parseInt(args[0]);
                remoteHost = args[1];
                remotePort = Integer.parseInt(args[2]);
                serverStatus.put(new ServerInfo(remoteHost, remotePort), new Date()); //on startup, add the remote peer to the network
            } catch (NumberFormatException e) {
                return;
            }
        } else if(args.length == 1){
            try {
                localPort = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                return;
            }
        }

        //todo: Perform a catchup request with the remote server

        //simulate miners committing blocks to blockchain
        PeriodicCommitRunnable pcr = new PeriodicCommitRunnable(blockchain);
        Thread pct = new Thread(pcr);
        pct.start();

        //start a thread to handle sending heartbeat messages to peers
        //broadcasts hb|localPort|sequenceNumber to all peers
        Thread heartbeats = new Thread(new PeriodicHeartBeatRunnable(localPort, serverStatus));
        heartbeats.start();

        //start thread that removes peers that it hasn't heard from after 4 seconds
        Thread update = new Thread(new PeriodicUpdateServerStatusRunnable(serverStatus));
        update.start();

        //start thread that periodically sends the latest block to 5 random peers
        Thread latestBlock = new Thread(new PeriodicLastBlockRunnable(localPort, serverStatus, blockchain));
        latestBlock.start();

//        ArrayList<Thread> threadArrayList = new ArrayList<>();

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(localPort); //open socket for other peers to connect to

            while (true) {
                Socket clientSocket = serverSocket.accept(); //wait until a peer connects
                //start a thread to handle the connection with the peer
                //allows this server to spend more time in the accept() call
                Thread thread = new Thread(new BlockchainServerRunnable(clientSocket, blockchain, serverStatus));
                thread.start();
//                threadArrayList.add(thread);
            }
        } catch (IllegalArgumentException | IOException e) {
        } finally { //stop all of the processes
            try {
                pcr.setRunning(false);
                pct.join();
                heartbeats.join();
                update.join();
                latestBlock.join();
//                for (Thread thread : threadArrayList) {
//                    try {
//                        thread.join();
//                    } catch (InterruptedException e) {
//                    }
//                }
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException | InterruptedException e) {
            }
        }
    }
}
