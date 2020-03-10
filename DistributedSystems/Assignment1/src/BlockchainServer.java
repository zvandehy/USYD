import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class BlockchainServer {
    private Blockchain blockchain;
    public BlockchainServer() {blockchain = new Blockchain();}

    //getters and setters
    public void setBlockchain(Blockchain blockchain) { this.blockchain = blockchain;}
    public Blockchain getBlockchain() { return blockchain;}

    public static void main(String[] args) {
        if (args.length != 1) {
            return;
        }
        int portNumber = Integer.parseInt(args[0]);
        BlockchainServer bcs = new BlockchainServer();
        try {
            ServerSocket server = new ServerSocket(portNumber);
            while(true) {
                Socket socket = server.accept();
                InputStream input = socket.getInputStream();
                OutputStream output = socket.getOutputStream();
                bcs.serverHandler(input, output);
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
        while(true){
            try {
                String request = inputReader.readLine();
                if (request.substring(0, 2).equals("tx")) {
                    System.out.println("Handle tx");
//                    if(blockchain.addTransaction(request) > 0) {
//                        System.out.print("Accepted\n\n");
//                    } else {
//                        System.out.print("Rejected\n\n");
//                    }
                } else if (request.equals("pb")) {
                    System.out.println("Handle pb");
//                    System.out.print(blockchain.toString() + "\n");
                } else if (request.equals("cc")) {
                    System.out.println("Handle cc");
//                    socket.close();
                    break;
                } else {
                    System.out.println("Handle error");
//                    System.out.print("Error\n\n");
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

    }
}
