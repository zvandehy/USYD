
import java.io.*;
import java.net.Socket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

public class BlockchainServerRunnable implements Runnable{

    private Socket clientSocket;
    private Blockchain blockchain;
    private HashMap<ServerInfo, Date> serverStatus;

    public BlockchainServerRunnable(Socket clientSocket, Blockchain blockchain, HashMap<ServerInfo, Date> serverStatus) {
        this.clientSocket = clientSocket;
        this.blockchain = blockchain;
        this.serverStatus = serverStatus;
    }

    public void run() {
        try {
            serverHandler(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.close();
        } catch (IOException e) {
        }
    }

    public void serverHandler(InputStream clientInputStream, OutputStream clientOutputStream) {

        BufferedReader inputReader = new BufferedReader(
                new InputStreamReader(clientInputStream));
        PrintWriter outWriter = new PrintWriter(clientOutputStream, true);

        //this node's serverInfo
        String myHost =  (((InetSocketAddress) clientSocket.getLocalSocketAddress()).getAddress()).toString().replace("/", "");
        int myPort = clientSocket.getLocalPort();
        ServerInfo myServerInfo = new ServerInfo(myHost, myPort);

        try {
            while (true) {//run until "cc"
                String inputLine = inputReader.readLine();//get message from peer
                if (inputLine == null) {
                    break;
                }

                String[] tokens = inputLine.split("\\|");
                switch (tokens[0]) {
                    //add transaction
                    case "tx":
                        if (blockchain.addTransaction(inputLine))
                            outWriter.print("Accepted\n\n");
                        else
                            outWriter.print("Rejected\n\n");
                        outWriter.flush();
                        break;
                    //print blockchain
                    case "pb":
                        outWriter.print(blockchain.toString() + "\n");
                        outWriter.flush();
                        break;
                    case "hb":
                        //get ServerInfo from peer(client) that sent heartbeat
                        String clientHost = (((InetSocketAddress) clientSocket.getRemoteSocketAddress()).getAddress()).toString().replace("/", "");
                        int clientPort = Integer.parseInt(tokens[1]);
                        ServerInfo clientServerInfo = new ServerInfo(clientHost, clientPort);
                        //log the ServerInfo with corresponding time

                        //if this is the first time then need to broadcast si message
                        if(tokens[2].equals("0")) {
                            for(ServerInfo toServerInfo : serverStatus.keySet()) {
                                if(toServerInfo.equals(clientServerInfo) || toServerInfo.equals(myServerInfo)) {
                                    continue;
                                }
                                String message = "si|"+clientSocket.getLocalPort()+"|"+clientHost+"|"+clientPort;
                                Thread thread = new Thread(new SendMessageRunnable(toServerInfo, message));
                                thread.start();
                            }
                        }
                        //add peer to serverStatus after check for first time
                        //so that si message is broadcast to all *other* peers
                        serverStatus.put(clientServerInfo, new Date());
                        return;
                    case "si":

                        //get ServerInfo of Q, the originator who sent the si message
                        String originatorHost = (((InetSocketAddress) clientSocket.getRemoteSocketAddress()).getAddress()).toString().replace("/", "");
                        int originatorPort = Integer.parseInt(tokens[1]);
                        ServerInfo originatorServerInfo = new ServerInfo(originatorHost, originatorPort);

                        //get P's ServerInfo that was relayed from Q
                        String peerHost = tokens[2];
                        int peerPort = Integer.parseInt(tokens[3]);
                        ServerInfo peerServerInfo = new ServerInfo(peerHost, peerPort);

                        //if the server already knows P from before, then do not relay
                        if(!serverStatus.containsKey(peerServerInfo)) {
                            //si message to broadcast to all other peers
                            String message = "si|"+clientSocket.getLocalPort()+"|"+peerHost+"|"+peerPort;
                            //relay to all other peers (exclude itself and P)
                            ArrayList<Thread> threadArrayList = new ArrayList<>();
                            for(ServerInfo toServerInfo : serverStatus.keySet()) {
                                if(toServerInfo.equals(originatorServerInfo) || toServerInfo.equals(peerServerInfo) || toServerInfo.equals(myServerInfo))  { //don't send to Q or to P
                                    continue;
                                } else {
                                    Thread thread = new Thread(new SendMessageRunnable(toServerInfo, message));
                                    threadArrayList.add(thread);
                                    thread.start();
                                }
                                for (Thread thread : threadArrayList) {
                                    thread.join();
                                }
                            }
                            //add peer to serverStatus
                            serverStatus.put(peerServerInfo, new Date());
//                            serverStatus.put(originatorServerInfo, new Date());
                        }
                        return;
                    //close connection
                    case "lb":
                        //if the length of your neighbour blockchain is longer than
                        //yours, a catch up is required.
                        boolean smaller_length = blockchain.getLength() < Integer.parseInt(tokens[2]);
                        //if the length of your neighbour blockchain is the same to
                        //yours, but the hash is smaller than yours.
                        boolean longer_hash = (blockchain.getLength() == Integer.parseInt(tokens[2])) &&
                                (Base64.getEncoder().encodeToString(blockchain.getHead().calculateHash()).length() > tokens[3].length());
                        if(smaller_length || longer_hash) {
                            //must issue a catchup to the peer
                        }
                        return;
                    case "cu":
                        //todo:transfer the designated blocks
                        //may move this logic to another class
                        //todo: design a catchup algorithm
                        if(tokens.length == 1) { //"cu"
                            //reply with the latest/head block
                            //must use ObjectInputStream and ObjectOutputStream to transfer the block

                        } else { //"cu|<hash>"
                            //find the block with the designated hash
                            //reply with the designated block as above
                        }
                        return;
                    case "cc":
                        return;
                    //other messages are invalid
                    default:
                        outWriter.print("Error\n\n");
                        outWriter.flush();
                }
            }
        } catch (IOException e) {
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
