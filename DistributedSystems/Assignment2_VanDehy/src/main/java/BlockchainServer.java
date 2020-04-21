import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockchainServer {

    public static void main(String[] args) {

        if (args.length != 1) {
            return;
        }
        int portNumber = 0;
        try {
            portNumber = Integer.parseInt(args[0]);
        } catch(Exception e) {
            return;
        }
        if(portNumber < 1024 || portNumber > 65535) {
            return;
        }
        Blockchain blockchain = new Blockchain();


        PeriodicCommitRunnable pcr = new PeriodicCommitRunnable(blockchain);
        Thread pct = new Thread(pcr);
        pct.start();

        ServerSocket server = null;
        try {
            server = new ServerSocket(portNumber);
            while(true) {
                Socket client = server.accept();
                new Thread(new BlockchainServerRunnable(client, blockchain)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        pcr.setRunning(false);
        try {
            pct.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // implement any helper method here if you need any

}
