import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class DateClient {

    public static void main(String[] args) {
	try {
	    Socket socket = new Socket("127.0.0.1", 6015);
	    InputStream input = socket.getInputStream();
        BufferedReader bin = new BufferedReader(new InputStreamReader(input));
        String line;
        while ((line = bin.readLine()) != null)
            System.out.println(line);
    } catch (Exception e) {
	    e.printStackTrace();
    }

    }
}
