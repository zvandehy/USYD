import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public static void main(String[] args) {

        if(args.length !=1) {
            return;
        }

        int portNumber = Integer.parseInt(args[0]);
        SharedResource sharedResource = new SharedResource();
        try {
            ServerSocket server = new ServerSocket(portNumber);

            while(true) {
                Socket client = server.accept();
                System.out.println("accepted client");
                Worker workerThread = new Worker(client, sharedResource);
                System.out.println("created client");
                workerThread.run();
                System.out.println("runned client");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
