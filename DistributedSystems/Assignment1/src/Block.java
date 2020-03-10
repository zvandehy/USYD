import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;

public class Block {
    private Block previousBlock;
    private byte[] previousHash;
    private ArrayList<Transaction> transactions;

    public Block() {
        transactions = new ArrayList<>();
    }

    //getters and setters
    public Block getPreviousBlock() {
        return previousBlock;
    }
    public byte[] getPreviousHash() {
        return previousHash;
    }
    public ArrayList<Transaction> getTransactions() {
        return this.transactions;
    }
    public void setPreviousBlock(Block previousBlock) {
        this.previousBlock = previousBlock;
    }
    public void setPreviousHash(byte[] previousHash) {
        this.previousHash = previousHash;
    }
    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String toString() {
        String cutOffRule = new String(new char[81]).replace("\0", "-") + "\n";
        String prevHashString = String.format("|PreviousHash:|%65s|\n",
                    Base64.getEncoder().encodeToString(previousHash));
        String hashString = String.format("|CurrentHash:|%66s|\n",
                Base64.getEncoder().encodeToString(calculateHash()));
        String transactionsString = "";
        for (Transaction tx : transactions) {
            transactionsString += tx.toString();
        }

        return "Block:\n"
                + cutOffRule
                + hashString
                + cutOffRule
                + transactionsString
                + cutOffRule
                + prevHashString
                + cutOffRule;
    }

    //calculate hash of current block
    public byte[] calculateHash() {
        byte[] hash = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);

            dos.write(previousHash);

            for (Transaction transaction : transactions) {
                String txString = "tx|" + transaction.getSender() + "|" + transaction.getContent();
                dos.writeUTF(txString);
            }

            byte[] bytes = baos.toByteArray();
            hash = messageDigest.digest(bytes);

        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return hash;
    }
}
