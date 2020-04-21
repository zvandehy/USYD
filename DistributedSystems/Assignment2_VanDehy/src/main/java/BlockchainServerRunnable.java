import java.io.*;
import java.net.Socket;

public class BlockchainServerRunnable implements Runnable{

    private Socket clientSocket;
    private Blockchain blockchain;

    public BlockchainServerRunnable(Socket clientSocket, Blockchain blockchain) {
        // implement your code here
        this.clientSocket = clientSocket;
        this.blockchain = blockchain;
    }

    public void run() {
        try {
            InputStream input = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            BufferedReader inputReader = new BufferedReader(
                    new InputStreamReader(input));
            PrintWriter outWriter = new PrintWriter(output, true);
            //my code
            String request = "";
            do {
                request = inputReader.readLine();
                if (request == null) break;
                if (request.length() < 2) {
                    outWriter.println("Error\n");
                } else if (request.substring(0, 2).equals("tx")) {
                    if (blockchain.addTransaction(request)) {
                        outWriter.print("Accepted\n\n");
                        outWriter.flush();
                    } else {
                        outWriter.println("Rejected\n");
                    }
                } else if (request.equals("pb")) {
                    outWriter.println(blockchain.toString());
                } else if (request.equals("cc")) {
                    clientSocket.close();
                    break;
                } else {
                    outWriter.println("Error\n");
                }
            }
            while (!request.equals("cc"));
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // implement any helper method here if you need any
}
