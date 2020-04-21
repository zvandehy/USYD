import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class BlockchainClientRunnable implements Runnable {

    private String reply;
    private String serverName;
    private int portNumber;
    private String message;

    public BlockchainClientRunnable(int serverNumber, String serverName, int portNumber, String message) {
        this.reply = "Server" + serverNumber + ": " + serverName + " " + portNumber + "\n"; // header string
        this.serverName = serverName;
        this.portNumber = portNumber;
        this.message = message;
    }

    public void run() {
        try {
            Socket socket = new Socket(serverName, portNumber); //create connection
            socket.setSoTimeout(2000);
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            sendMessage(input,output);
            socket.close();
        } catch(Exception e) {
            reply += "Server is not available\n";
        }
    }

    public String getReply() {
        return reply;
    }

    public void sendMessage(InputStream serverInputStream, OutputStream serverOutputStream) {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(serverInputStream));
        PrintWriter outWriter = new PrintWriter(serverOutputStream, true);

        outWriter.println(message); //forward request to server

        try {
            Thread.sleep(100);
            while(inputReader.ready()) {
                String str = inputReader.readLine();
                if(!str.equals("\n") && !str.equals("")) {
                    reply += str+"\n"; //The reply from the server should be stored in reply
                }
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        outWriter.println("cc");//close the connection as soon as the response comes back
    }
}