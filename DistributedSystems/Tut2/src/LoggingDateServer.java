import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LoggingDateServer {

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(6015);
            int clientCount = 0;
            while(true) {
                Socket client = server.accept();
                clientCount +=1;
                String fileName = "log" + clientCount + ".txt";
                FileWriter fos = new FileWriter(new File(fileName));
                BufferedWriter out = new BufferedWriter(fos);
                out.write(client.getInetAddress().getHostName());
                out.write(new java.util.Date().toString());
                out.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
