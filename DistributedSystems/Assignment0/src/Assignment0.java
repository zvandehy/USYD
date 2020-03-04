import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

public class Assignment0 {

    public static void main(String[] args) {
        ArrayList<String> messages = new ArrayList<>();
        messages.add("12345");
        messages.add("12345");
        System.out.println(hashMessages(messages));
    }

    public static String hashMessages(ArrayList<String> messages) {
        String hashString = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);

            //my code
            for (String msg: messages) {
                dos.writeUTF(msg);
            }

            byte[] bytes = baos.toByteArray();
            byte[] hash = messageDigest.digest(bytes);
            hashString = Base64.getEncoder().encodeToString(hash);

        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return hashString;
    }
}
