import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        if (args.length != 2) {
            return;
        }
        String serverName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        Client client = new Client();
        try {
            Socket socket = new Socket(serverName, portNumber);
            System.out.println("Connected" + "\n");
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            client.clientHandler(input,output);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void clientHandler(InputStream serverInputStream, OutputStream serverOutputStream) {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(serverInputStream));
        PrintWriter outWriter = new PrintWriter(serverOutputStream, true);

        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine()) {

            String request = sc.nextLine();
            outWriter.println(request);
            if(request.equals("cc")) {
                break;
            }

            try {
                Thread.sleep(100);
                while(inputReader.ready()) {
                    System.out.println(inputReader.readLine());
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
