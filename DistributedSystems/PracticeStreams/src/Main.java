import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

        String txString = "tx|evan3935|Welcome to comp!";
        String header = txString.substring(0,3);
        String sender = txString.substring(3,11);
        String content = txString.substring(12);
        System.out.println(header+sender+"|"+content);
        System.out.println("--------------------------");



        ArrayList<String> messages = new ArrayList<>();
        //byte array streams are useful for writing to multiple locations and simply writing bytes
        //I believe in this instance we are using the byte array stream because we want to encrypt the messages
        //a MessageDigest (used for encryption) only takes bytes as a parameter
        //file streams are useful for engaging with other files ('test.txt')
//        OutputStream out = new FileOutputStream(new File("test.txt"));
        OutputStream out = new ByteArrayOutputStream();

        //lets the java application write to any ('out') output stream
        DataOutputStream dos = new DataOutputStream(out);

        messages.add("Hello");
        messages.add("World");
        messages.add("!");

        try {
            for (String msg: messages) {
                dos.writeUTF(msg);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        //these are only being casted because 'out' is of the superclass "OutputStream"
        String str = ((ByteArrayOutputStream) out).toString();
        byte[] bytes = ((ByteArrayOutputStream) out).toByteArray();
        System.out.println(str);
        //  Hello World !
        System.out.println(bytes);
        //[B@610455d6
        //these bytes will be hashed/encrypted

    }
}
