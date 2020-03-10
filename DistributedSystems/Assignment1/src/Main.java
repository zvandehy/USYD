
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

    public class Main {

        public static void main(String[] args) throws IOException {

//            String txString = "tx|evan3935|Welcome to comp!";
//            String header = txString.substring(0, 3);
//            String sender = txString.substring(3, 11);
//            String content = txString.substring(12);
//            System.out.println(header + sender + "|" + content);
//            System.out.println("--------------------------");
            Blockchain blockchain = new Blockchain();
            blockchain.addTransaction("tx|evan3935|11111111");
            blockchain.addTransaction("tx|evan3935|22222222");
            blockchain.addTransaction("tx|evan3935|33333333");
            blockchain.addTransaction("tx|evan3935|44444444");
            blockchain.addTransaction("tx|evan3935|55555555");
            blockchain.addTransaction("tx|evan3935|66666666");
            System.out.println(blockchain);


        }
    }
