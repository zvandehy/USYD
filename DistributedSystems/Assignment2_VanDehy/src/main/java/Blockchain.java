import java.util.Base64;
import java.util.Vector;

public class Blockchain {

    private Block head;
    private Vector<Transaction> pool;
    private int length;

    public Blockchain() {
        pool = new Vector<>();
        length = 0;
    }

    public Block getHead() {
        return head;
    }

    public void setHead(Block head) {
        this.head = head;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Vector<Transaction> getPool() {
        return pool;
    }

    public void setPool(Vector<Transaction> pool) {
        this.pool = pool;
    }

    public boolean addTransaction(String txString) {
        String[] tokens = txString.split("\\|");
        if (tokens.length != 3) {
            return false;
        }
        if (!tokens[0].equals("tx")) {
            return false;
        }
        Transaction transaction = new Transaction();
        transaction.setSender(tokens[1]);
        transaction.setContent(tokens[2]);
        if (!transaction.isValid()) {
            return false;
        }
        getPool().add(transaction);
        return true;
    }

    public boolean commit(int nonce) {
        if (getPool().size() == 0) {
            return false;
        }

        Block newBlock = new Block();
        if (head == null) {
            newBlock.setPreviousHash(new byte[32]);
        } else {
            newBlock.setPreviousHash(head.calculateHash());
        }
        newBlock.setTransactions(getPool());
        byte[] hash = newBlock.calculateHashWithNonce(nonce);
        String hashString = Base64.getEncoder().encodeToString(hash);
        if(hashString.startsWith("A")) {
            newBlock.setPreviousBlock(head);
            head = newBlock;
            setPool(new Vector<>());
            length += 1;
            return true;
        }
        return false;
    }

    public String toString() {
        String cutOffRule = new String(new char[81]).replace("\0", "-") + "\n";
        String poolString = "";
        for (Transaction tx : getPool()) {
            poolString += tx.toString();
        }

        String blockString = "";
        Block bl = head;
        while (bl != null) {
            blockString += bl.toString();
            bl = bl.getPreviousBlock();
        }

        return "Pool:\n"
                + cutOffRule
                + poolString
                + cutOffRule
                + blockString;
    }
}
