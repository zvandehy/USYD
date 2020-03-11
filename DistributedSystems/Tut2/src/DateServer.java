import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class DateServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6014);
            while(true) {
                Socket client = serverSocket.accept();
                PrintWriter pout = new PrintWriter(client.getOutputStream(), true);
                pout.println(new java.util.Date().toString());
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
