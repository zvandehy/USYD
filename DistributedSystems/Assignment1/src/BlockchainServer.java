import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockchainServer {
    private Blockchain blockchain;
    public BlockchainServer() {this.blockchain = new Blockchain();}

    //getters and setters
    public void setBlockchain(Blockchain blockchain) { this.blockchain = blockchain;}
    public Blockchain getBlockchain() { return this.blockchain;}

    public static void main(String[] args) {
        if (args.length != 1) {
            return;
        }
        int portNumber = Integer.parseInt(args[0]);
        BlockchainServer bcs = new BlockchainServer();
        try {
            ServerSocket server = new ServerSocket(portNumber);
            while(true) {
                Socket client = server.accept();
                InputStream input = client.getInputStream();
                OutputStream output = client.getOutputStream();
                bcs.serverHandler(input, output);
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void serverHandler(InputStream clientInputStream, OutputStream clientOutputStream) {
        BufferedReader inputReader = new BufferedReader(
                new InputStreamReader(clientInputStream));
        PrintWriter outWriter = new PrintWriter(clientOutputStream, true);
        //my code
        String request = "";
        try {
            do {
                request = inputReader.readLine();
                if (request == null) break;
                if (request.length() < 2) {
                    outWriter.println("Error\n");
                } else if (request.substring(0, 2).equals("tx")) {
                    if (blockchain.addTransaction(request) > 0) {
                        outWriter.print("Accepted\n\n");
                        outWriter.flush();
                    } else {
                        outWriter.println("Rejected\n");
                    }
                } else if (request.equals("pb")) {
                    outWriter.println(blockchain.toString());
                } else if (request.equals("cc")) {
                    break;
                } else {
                    outWriter.println("Error\n");
                }
        }
        while (!request.equals("cc")) ;
    }catch (IOException e) {
        e.printStackTrace();
    }
    }
}
