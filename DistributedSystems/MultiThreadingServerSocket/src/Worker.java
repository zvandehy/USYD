import java.io.*;
import java.net.Socket;

public class Worker implements Runnable {
    Socket clientSocket;
    SharedResource sharedResource;

    public Worker(Socket clientSocket, SharedResource sharedResource) {
        this.clientSocket = clientSocket;
        this.sharedResource = sharedResource;
    }

    @Override
    public void run() {
        try {
            InputStream input = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            BufferedReader inputReader = new BufferedReader(
                    new InputStreamReader(input));
            PrintWriter outWriter = new PrintWriter(output, true);
            System.out.println("Worker says hello" + "\n");
            String request = "";
            try {
                do {
                    request = inputReader.readLine();
                    if (request == null) break;
                    if (request.equals("inc")) {
                        outWriter.println("Increment" + "\n");
                        sharedResource.increment();
                    } else if (request.equals("pb")) {
                        outWriter.println(sharedResource.getShared() + "\n");
                    } else if (request.equals("cc")) {
                        outWriter.println("Closing\n");
                    } else {
                        outWriter.println("Error\n");
                    }
                }
                while (!request.equals("cc"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
